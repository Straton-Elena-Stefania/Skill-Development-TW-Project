package com.timetraveling.models;

import com.timetraveling.utils.validation.SessionValidator;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "RootServlet", value = "/")
public class RootServlet extends HttpServlet {
    @Override
    protected void doGet(
            HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        getServletContext().setAttribute("emailEnabled", true);

        if (SessionValidator.isLoggedIn(request)) {
            response.sendRedirect(request.getContextPath() + "/home");
        } else {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
        }
    }
}
