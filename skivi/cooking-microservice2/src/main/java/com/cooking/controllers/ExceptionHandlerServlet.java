package com.cooking.controllers;

import com.cooking.models.ErrorModel;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "exceptionHandlerServlet",
        urlPatterns = {"/errorHandler"},
        loadOnStartup = 1)
public class ExceptionHandlerServlet extends HttpServlet {
    @Override
    protected void doPut(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
    @Override
    protected void doDelete(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Gson gson = new Gson();
        Throwable throwable = (Throwable) request
                .getAttribute("jakarta.servlet.error.exception");
        Integer statusCode = (Integer) request
                .getAttribute("jakarta.servlet.error.status_code");
        String message = (String) request.getAttribute("jakarta.servlet.error.message");
        String requestUri = (String) request
                .getAttribute("jakarta.servlet.error.request_uri");
        if (requestUri == null) {
            requestUri = "Unknown";
        }

        response.setContentType("application/json");

        if (statusCode == 403) {
            message = "You must authenticate first as admin to the login endpoint of Skivi, or to enroll in this skill to access data about your skills.";
        } else if (statusCode == 404) {
            message = "This resource was not found";
        } else if (statusCode == 409) {
            message = "Duplicate found";
        } else if (statusCode == 500) {
            message = "Unexpected error";
        } else if (statusCode == 400) {
            message = "Bad request";
        }

        ErrorModel errorModel = new ErrorModel(message, statusCode, requestUri);
        String error = gson.toJson(errorModel);
        PrintWriter out = response.getWriter();
        out.println(error);
    }
}
