package com.timetraveling.utils.async;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.timetraveling.configuration.Configuration;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class NotificationPublisher {
    private ConnectionFactory factory;
    private Connection connection;
    private Channel channel;
    private final String QUEUE_NAME = "email_queue";
    private final String PUSH_QUEUE_NAME = "push_queue";
    private final String EXCHANGE_NAME = "updates";

    public NotificationPublisher() {
        this.factory = new ConnectionFactory();
        factory.setUsername(MQConfiguration.AMQP_USER);
        factory.setPassword(MQConfiguration.AMQP_PASSWORD);

        if (Configuration.DEPLOYMENT_MODE.equals("docker")) {
            factory.setHost(MQConfiguration.AMQP_HOST);
        } else {
            factory.setHost(MQConfiguration.AMQP_HOST_LOCAL);
        }

        try {
            this.connection = factory.newConnection();
            this.channel = connection.createChannel();

            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            channel.queueDeclare(PUSH_QUEUE_NAME, false, false, false, null);
            channel.queuePurge(QUEUE_NAME);

            channel.basicQos(1);

            channel.exchangeDeclare(EXCHANGE_NAME, "topic");
        } catch (IOException | TimeoutException exception) {
            exception.printStackTrace();
        }
    }

    public void publish(String routingKey, String message) throws IOException, TimeoutException {
        /**
         * Functia face abstractie de routingKey, stiind ca mesajul este
         * rutat de catre exchange pe queue-ul potrivit
         */
        channel.basicPublish(EXCHANGE_NAME, routingKey, null, message.getBytes("UTF-8"));
        System.out.println(" [x] Sent '" + routingKey + "':'" + message + "'");
    }

    public void close() throws IOException {
        connection.close();
    }
}
