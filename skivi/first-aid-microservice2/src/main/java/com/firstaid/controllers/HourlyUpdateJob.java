package com.firstaid.controllers;

import jakarta.servlet.ServletContext;

public class HourlyUpdateJob implements Runnable {
    private ServletContext context;
    private boolean skillAvailable = false;

    private void toggleAvailability() {
        skillAvailable ^= true;
    }

    public HourlyUpdateJob(ServletContext context) {
        this.context = context;
    }

    @Override
    public void run() {
        toggleAvailability();
        context.setAttribute("availability", skillAvailable);
    }
}
