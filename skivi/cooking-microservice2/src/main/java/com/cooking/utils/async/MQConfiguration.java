package com.cooking.utils.async;

public final class MQConfiguration {
    public static final String AMQP_URL = "amqp://guest:guest@rabbit-mq/";
    public static final String AMQP_USER = "guest";
    public static final String AMQP_PASSWORD = "guest";
    public static final String AMQP_HOST = "rabbit-mq";
    public static final String AMQP_URL_LOCAL = "amqp://guest:guest@localhost:5672/";
    public static final String AMQP_HOST_LOCAL = "localhost";

    private MQConfiguration() {}
}
