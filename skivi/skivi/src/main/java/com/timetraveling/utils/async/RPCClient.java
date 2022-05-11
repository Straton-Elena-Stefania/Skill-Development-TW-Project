package com.timetraveling.utils.async;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeoutException;

public class RPCClient implements AutoCloseable {
    private Connection connection;
    private Channel channel;
    private String requestQueueName = "rpc_queue";

    public RPCClient() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername("guest");
        factory.setPassword("guest");
        factory.setHost("rabbit-mq");

        connection = factory.newConnection();
        channel = connection.createChannel();
    }

    public void MQClientRequest() throws IOException, InterruptedException {
            for (int i = 0; i < 32; i++) {
                String i_str = Integer.toString(i);
                System.out.println(" [x] Requesting fib(" + i_str + ")");
                String response = call(i_str);
                System.out.println(" [.] Got '" + response + "'");
            }
    }

    public String call(String message) throws IOException, InterruptedException {
        final String corrId = UUID.randomUUID().toString();

        String replyQueueName = channel.queueDeclare().getQueue();
        AMQP.BasicProperties props = new AMQP.BasicProperties
                .Builder()
                .correlationId(corrId)
                .replyTo(replyQueueName)
                .build();

        channel.basicPublish("", requestQueueName, props, message.getBytes("UTF-8"));

        final BlockingQueue<String> response = new ArrayBlockingQueue<>(1);

        String ctag = channel.basicConsume(replyQueueName, true, (consumerTag, delivery) -> {
            if (delivery.getProperties().getCorrelationId().equals(corrId)) {
                response.offer(new String(delivery.getBody(), "UTF-8"));
            } }, consumerTag -> {});

        String result = response.take();
        channel.basicCancel(ctag);
        return result;
    }

    public void createExchange() throws IOException, TimeoutException {
        System.out.println("attempt to exchandge");
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername("guest");
        factory.setPassword("guest");
        factory.setHost("rabbit-mq");
        Connection connection = factory.newConnection(MQConfiguration.AMQP_URL);
        Channel channel = connection.createChannel();
        //Create an exchange
        /*channel.exchangeDeclare("my-direct-exchange", BuiltinExchangeType.DIRECT, true);
        channel.queueDeclare("MobileQ", true, false, false, null);
        channel.queueBind("MobileQ", "my-direct-exchange", "personalDevice");
        String message = "Turn on home appliances";
        // publish with (exchange, routingKey, properties, messageBody)
        channel.basicPublish("my-direct-exchange", "homeAppliance", null, message.getBytes());
        */

        channel.queueDeclare("Mobile", false, false, false, null);
        String message = "Hello World!";
        channel.basicPublish("", "Mobile", null, message.getBytes());
        System.out.println(" [x] Sent '" + message + "'");

        channel.close();
        connection.close();
    }

    public void close() throws IOException {
        connection.close();
    }
}
