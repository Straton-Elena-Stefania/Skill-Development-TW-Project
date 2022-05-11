package com.firstaid.controllers.configuration;

public interface Authorization {
    String SKILL_NAME = "firstAid";
    String ADMIN_ROLE = "admin";
    String ENROLLED_ROLE = "enrolled";
    String AUTHORIZATION_SERVER_LOCAL = "http://localhost:8088/skivi_war_exploded/authorization";
    String AUTHORIZATION_SERVER_DOCKER = "http://app-web:8080/authorization";
}
