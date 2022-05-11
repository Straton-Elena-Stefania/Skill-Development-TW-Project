package com.timetraveling.controllers.notification;

import com.rabbitmq.client.*;
import com.timetraveling.controllers.Communicative;
import com.timetraveling.models.email.Email;
import com.timetraveling.models.skillmodel.SkillHibernateRepository;
import com.timetraveling.models.skillmodel.SkillModel;
import com.timetraveling.models.skills.SkillStatus;
import com.timetraveling.models.users.User;
import com.timetraveling.models.users.UserHibernateRepository;
import com.timetraveling.models.users.UserRepository;
import com.timetraveling.models.userskills.UserSkillHibernateRepository;
import com.timetraveling.utils.async.MQConfiguration;
import com.timetraveling.utils.async.QueueSubscriber;
import com.timetraveling.utils.async.push.EmailBuilder;
import com.timetraveling.utils.async.push.EmailRouter;
import com.timetraveling.utils.async.push.EmailTask;
import com.timetraveling.utils.async.push.Routing;
import com.timetraveling.utils.configuration.Configuration;
import jakarta.servlet.AsyncContext;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.mail.Session;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

@WebServlet(name = "PushServlet", value = "/shoutServlet", asyncSupported=true)
public class PushServlet extends HttpServlet implements Communicative, Routing {
    /**
     * Aceasta este o lista cu wildcard-uri pe care aplicatia le
     * asculta pentru a primi mesaje ale caror chei de rutare se potrivesc
     * cu ceea ce ascultam noi.
     */
    private static final List<String> BINDING_KEYS = new ArrayList<>();

    private List<AsyncContext> contexts = new LinkedList<>();
    private Map<Integer, AsyncContext> userContexts = new HashMap<>();
    private Map<Integer, AsyncContext> notificationPopups = new HashMap<>();

    private final UserRepository userRepository = new UserHibernateRepository();
    private final SkillHibernateRepository skillHibernateRepository = new SkillHibernateRepository();
    private final UserSkillHibernateRepository userSkillHibernateRepository = new UserSkillHibernateRepository();

    private ConnectionFactory factory;
    private Connection connection;
    private Channel channel;
    private final String QUEUE_NAME = "push_queue";
    private final String EXCHANGE_NAME = "updates";

    private Configuration configuration = new Configuration();

    @Override
    public void init() {
        BINDING_KEYS.add("*.push.update");

        this.factory = new ConnectionFactory();
        factory.setUsername(MQConfiguration.AMQP_USER);
        factory.setPassword(MQConfiguration.AMQP_PASSWORD);

        if (Configuration.DEPLOYMENT_MODE.equals("docker")) {
            factory.setHost(MQConfiguration.AMQP_HOST);
        } else {
            factory.setHost(MQConfiguration.AMQP_HOST_LOCAL);
        }


        try {
            initializeConnection();
            subscribeForPushNotifications(BINDING_KEYS);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

    public void initializeConnection() throws IOException, TimeoutException {
        this.connection = this.factory.newConnection();
        this.channel = this.connection.createChannel();
    }

    public void subscribeForPushNotifications(List<String> bindingKeys) throws IOException {
        channel.exchangeDeclare(EXCHANGE_NAME, "topic");

        for (String bindingKey: bindingKeys) {
            channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, bindingKey);
            System.out.println("RabbitMQ: Bound email subscriber to binding key: " + bindingKey);
        }

        /**
         * Acesta este un callback care descrie ce actiune sa facem cu
         * un mesaj. Cand am primit un mesaj, am construit un email
         * pe baza informatiilor primite
         */
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" +
                    delivery.getEnvelope().getRoutingKey() + "':'" + message + "'");

            String routingKey = delivery.getEnvelope().getRoutingKey();
            /*Set<AsyncContext> asyncContexts = new HashSet<>(this.userContexts);
            this.contexts.clear();*/

            String htmlMessage = message + '\n';
            List<String> messages = new ArrayList<>();
            ServletContext sc = getServletContext();

            if (sc.getAttribute("messages") == null) {
                sc.setAttribute("messages", htmlMessage);
                //messages.add(htmlMessage);
                //sc.setAttribute("messages", messages);
            } else {
                String currentMessages = (String) sc.getAttribute("messages");
                //List<String> currentMessages = (List<String>) sc.getAttribute("messages");
                //currentMessages.add(message);
                //sc.setAttribute("messages", messages);
                sc.setAttribute("messages", htmlMessage + currentMessages);
            }

            List<String> routingKeySplit = Arrays.asList(routingKey.split("[.]"));

            if (routingKeySplit.size() > 0) {
                this.userContexts.keySet().forEach(System.out::println);

                for (Integer user: this.userContexts.keySet()) {
                    AsyncContext asyncContext = this.userContexts.get(user);
                    String messageString = routingKeySplit.get(0);
                    if (asyncContext.getRequest().getAttribute(messageString) != null) {
                        boolean isRequested = (boolean) asyncContext.getRequest().getAttribute(messageString);

                        if (isRequested) {
                            try (PrintWriter writer = asyncContext.getResponse().getWriter()) {
                                writer.println(htmlMessage);
                                writer.flush();
                                asyncContext.complete();
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                }
            }
        };

        /**
         * Consumam notificarea de pe queue
         */
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int userId = (Integer) request.getSession().getAttribute("id");
        List<SkillModel> skillModelList = userSkillHibernateRepository.findByUserId(userId);

        for (SkillModel skillModel: skillModelList) {
            request.setAttribute(skillModel.getName(), true);
        }

        AsyncContext asyncContext = request.startAsync(request, response);

        asyncContext.setTimeout(10 * 60 * 1000);
        asyncContext.getRequest().setAttribute("id", userId);

        this.userContexts.put(userId, asyncContext);
        //contexts.add(asyncContext);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int userId = (Integer) request.getSession().getAttribute("id");

        List<AsyncContext> asyncContexts = new ArrayList<>(this.contexts);
        this.contexts.clear();

        ServletContext sc = request.getServletContext();

        for (AsyncContext asyncContext : asyncContexts) {
            if (asyncContext.getRequest().getAttribute("jd") != null
                    && asyncContext.getRequest().getAttribute("id").equals(userId)) {
                asyncContext.getRequest().setAttribute("seen", true);
            }
        }
    }
}
