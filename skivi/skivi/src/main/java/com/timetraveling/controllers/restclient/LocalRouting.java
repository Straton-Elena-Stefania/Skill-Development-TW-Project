package com.timetraveling.controllers.restclient;

public interface LocalRouting extends Routing {
    String TIMETRAVELING_URL = "http://localhost:8081/timetraveling_microservice2_war_exploded";
    String COOKING_URL = "http://localhost:8083/cooking_microservice2_war_exploded";
    String FIRST_AID_URL = "http://localhost:8082/firstaid_microservice2_war_exploded";
}
