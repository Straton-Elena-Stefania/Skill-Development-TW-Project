package com.timetraveling.utils.async;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class RPCClient2 {
    private static final String EXCHANGE_NAME = "topic_logs";
    private Connection connection;
    private Channel channel;
    private String requestQueueName = "rpc_queue";

    public String request(String key) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();

        factory.setUsername(MQConfiguration.AMQP_USER);
        factory.setPassword(MQConfiguration.AMQP_PASSWORD);
        factory.setHost(MQConfiguration.AMQP_HOST);

        connection = factory.newConnection();
        channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, "topic");

        String message = "get";

        return call(message, key);
    }


    public String call(String message, String routingKey) throws IOException, InterruptedException {
        final String corrId = UUID.randomUUID().toString();

        String replyQueueName = channel.queueDeclare().getQueue();
        AMQP.BasicProperties props = new AMQP.BasicProperties
                .Builder()
                .correlationId(corrId)
                .replyTo(replyQueueName)
                .build();

        channel.basicPublish(EXCHANGE_NAME, routingKey, props, message.getBytes("UTF-8"));

        final BlockingQueue<String> response = new ArrayBlockingQueue<>(1);

        String ctag = channel.basicConsume(replyQueueName, true, (consumerTag, delivery) -> {
            if (delivery.getProperties().getCorrelationId().equals(corrId)) {
                response.offer(new String(delivery.getBody(), "UTF-8"));
            } }, consumerTag -> {});

        String result = response.take();
        channel.basicCancel(ctag);
        return result;
    }
}
