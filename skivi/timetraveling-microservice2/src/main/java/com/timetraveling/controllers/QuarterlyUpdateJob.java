package com.timetraveling.controllers;

import jakarta.servlet.ServletContext;

public class QuarterlyUpdateJob implements Runnable {
    private ServletContext context;
    private boolean skillAvailable = false;

    private void toggleAvailability() {
        skillAvailable ^= true;
    }

    public QuarterlyUpdateJob(ServletContext context) {
        this.context = context;
    }

    @Override
    public void run() {
        toggleAvailability();
        context.setAttribute("availability", skillAvailable);
    }
}
