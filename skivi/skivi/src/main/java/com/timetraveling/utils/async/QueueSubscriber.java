package com.timetraveling.utils.async;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import com.timetraveling.models.email.Email;
import com.timetraveling.utils.async.push.EmailBuilder;
import com.timetraveling.utils.async.push.EmailRouter;
import com.timetraveling.utils.async.push.EmailTask;
import com.timetraveling.utils.configuration.Configuration;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class QueueSubscriber {
    private ConnectionFactory factory;
    private Connection connection;
    private Channel channel;
    private final String QUEUE_NAME = "email_queue";
    private final String EXCHANGE_NAME = "updates";
    private EmailTask emailTask;

    public QueueSubscriber() {
        this.factory = new ConnectionFactory();
        factory.setUsername(MQConfiguration.AMQP_USER);
        factory.setPassword(MQConfiguration.AMQP_PASSWORD);
        factory.setHost(MQConfiguration.AMQP_HOST);
    }

    public QueueSubscriber(EmailTask emailTask) {
        this.factory = new ConnectionFactory();
        factory.setUsername(MQConfiguration.AMQP_USER);
        factory.setPassword(MQConfiguration.AMQP_PASSWORD);
        if (Configuration.DEPLOYMENT_MODE.equals("docker")) {
            factory.setHost(MQConfiguration.AMQP_HOST);
        } else {
            factory.setHost(MQConfiguration.AMQP_HOST_LOCAL);
        }


        this.emailTask = emailTask;
    }

    public void initializeConnection() throws IOException, TimeoutException {
        this.connection = this.factory.newConnection();
        this.channel = this.connection.createChannel();

        if (emailTask != null) {
            this.emailTask.establishSession();
        }
    }

    public void subscribeForPushNotifications(List<String> bindingKeys) throws IOException {
        channel.exchangeDeclare(EXCHANGE_NAME, "topic");

        System.out.println("Excange declared");

        for (String bindingKey: bindingKeys) {
            channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, bindingKey);
            System.out.println("RabbitMQ: Bound email subscriber to binding key: " + bindingKey);
        }
    }

    public void subscribe(List<String> bindingKeys) throws IOException {
        EmailBuilder emailBuilder = new EmailBuilder();
        EmailRouter emailRouter = new EmailRouter(this.emailTask);

        channel.exchangeDeclare(EXCHANGE_NAME, "topic");

        for (String bindingKey: bindingKeys) {
            channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, bindingKey);
            System.out.println("RabbitMQ: Bound email subscriber to binding key: " + bindingKey);
        }

        System.out.println("RabbitMQ: Waiting for messages - email");

        /**
         * Acesta este un callback care descrie ce actiune sa facem cu
         * un mesaj. Cand am primit un mesaj, am construit un email
         * pe baza informatiilor primite
         */
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" +
                    delivery.getEnvelope().getRoutingKey() + "':'" + message + "'");

            /**
             * Construim email-ul
             */
            Email email = emailBuilder.build(delivery.getEnvelope().getRoutingKey(),
                                             "Skivi", message);

            /**
             * Apoi, in functie de continutul emailului, acesta este transmis
             * mai departe catre userii potriviti.
             */
            emailRouter.route(email);
            System.out.println("Email is sent");
        };

        /**
         * Consumam email-ul de pe queue
         */
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });
    }
}
