package com.firstaid.controllers;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebFilter
public class AvailabilityFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;

        ServletContext servletContext = req.getServletContext();

        boolean isAvailable = (boolean) servletContext.getAttribute("availability");

        if (isAvailable) {
            chain.doFilter(request, response);
        } else {
            ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
            PrintWriter printWriter = response.getWriter();
            printWriter.println("The course is under review and will be back in an hour");
            printWriter.flush();
            printWriter.close();
        }
    }
}
