package com.timetraveling.utils.async;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeoutException;

/*public class RPCServer {
    private static final String EXCHANGE_NAME = "topic_logs";
    private static final List<Command> COMMAND_LIST = Arrays.asList(new SectionsCommand("sections"));
    private static final List<String> BINDING_KEYS = Arrays.asList("sections", "timetraveling.*.subsections", "timetraveling.*.*.article");
    private static final String RPC_QUEUE_NAME = "rpc_queue";

    private void init() {

    }

    public void serveAMQP() throws IOException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername("guest");
        factory.setPassword("guest");
        factory.setHost("rabbit-mq");

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(RPC_QUEUE_NAME, false, false, false, null);
            channel.queuePurge(RPC_QUEUE_NAME);

            channel.exchangeDeclare(EXCHANGE_NAME, "topic");

            for (Command command : COMMAND_LIST) {
                channel.queueBind(RPC_QUEUE_NAME, EXCHANGE_NAME, command.getCommandName());
            }

            channel.basicQos(1);

            System.out.println(" [x] Awaiting RPC requests");

            Object lock = new Object();

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                AMQP.BasicProperties replyProps = new AMQP.BasicProperties
                        .Builder()
                        .correlationId(delivery.getProperties().getCorrelationId())
                        .build();

                String response = "";

                try {
                    String message = new String(delivery.getBody(), "UTF-8");
            */
                    /*
                     * Aici vedem cu ce routing-key a fost apelata comanda
                     * si in functie de asta servim
                     */
/*
                    String clientCommand = delivery.getEnvelope().getRoutingKey();

                    for (Command command: COMMAND_LIST) {
                        if (clientCommand.matches(command.getCommandName())) {
                           response = (String) command.run(message);
                        }
                    }
                } catch (RuntimeException e) {
                    System.out.println(" [.] " + e.toString());
                } finally {
                    channel.basicPublish("", delivery.getProperties().getReplyTo(), replyProps, response.getBytes("UTF-8"));
                    channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);

                    synchronized (lock) {
                        lock.notify();
                    }
                }
            };

            channel.basicConsume(RPC_QUEUE_NAME, false, deliverCallback, (consumerTag -> { }));
            while (true) {
                synchronized (lock) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }
}
*/