package com.firstaid.timetraveling_microservice2;

import java.io.*;
import java.util.concurrent.TimeoutException;

import com.firstaid.models.sections.Section;
import com.firstaid.utils.async.NotificationPublisher;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    private NotificationPublisher notificationPublisher;

    public void init() {
        System.out.println("initializing servlet with publisher");
        this.notificationPublisher = new NotificationPublisher();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");

        // Hello
        PrintWriter out = response.getWriter();
        Section section = new Section();
        //section.setName("Section");
        //SectionHibernateRepository sectionHibernateRepository = new SectionHibernateRepository(Section.class);
        //sectionHibernateRepository.save(section);

        /*
        try {
            System.out.println("ATTEMPTING TO CONNECT TO " + MQConfiguration.AMQP_URL);
            ConnectionFactory factory = new ConnectionFactory();
            factory.setUsername("guest");
            factory.setPassword("guest");
            factory.setHost("rabbit-mq");
            Connection connection = factory.newConnection(MQConfiguration.AMQP_URL);
            Channel channel = connection.createChannel();

            channel.queueDeclare("Mobile", false, false, false, null);
            System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), "UTF-8");
                System.out.println(" [x] Received '" + message + "'");
            };
            channel.basicConsume("Mobile", true, deliverCallback, consumerTag -> { });
        } catch (IOException | TimeoutException exception) {
            exception.printStackTrace();
        }
        */

        /*try {
            RPCServer rpcServer = new RPCServer();
            rpcServer.serveAMQP();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }*/

        try {
            notificationPublisher.publish("timetraveling.step.update", "debug");
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

    public void destroy() {
    }
}