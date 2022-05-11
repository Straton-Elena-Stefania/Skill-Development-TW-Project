package com.timetraveling.utils.presentation;

import com.google.gson.Gson;
import com.timetraveling.models.ErrorModel;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

public class JSONErrorHandler {
    public static void sendError(HttpServletRequest request, HttpServletResponse response, int statusCode) {
        Gson gson = new Gson();
        String message = "Unexpected error";

        response.setContentType("application/json");
        response.setStatus(statusCode);

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
        } else if (statusCode == 501) {
            message = "Not implemented";
        }

        ErrorModel errorModel = new ErrorModel(message, statusCode);
        String error = gson.toJson(errorModel);
        PrintWriter out = null;
        try {
            out = response.getWriter();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        out.println(error);
    }
}
