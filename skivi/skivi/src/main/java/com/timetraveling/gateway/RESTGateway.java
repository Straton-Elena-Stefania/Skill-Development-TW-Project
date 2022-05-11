package com.timetraveling.gateway;

import com.timetraveling.utils.validation.SessionValidator;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "GatewayServlet", value = "/index")
public class RESTGateway extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (SessionValidator.isLoggedIn(request)) {
            System.out.println("userul este logat: " + request.getSession().getAttribute("id"));
            response.sendRedirect(request.getContextPath() + "/home");
            return;
        }
    }
}
