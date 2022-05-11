package com.timetraveling.controllers;

/**
 * Aceasta interfata descrie ce moduri de deployment sunt
 * cunoscute, cu scopul interactiunii dintre microservicii
 */
public interface Communicative {
    String DOCKER_DEPLOYMENT = "docker";
    String LOCAL_DEPLOYMENT = "local";
}
