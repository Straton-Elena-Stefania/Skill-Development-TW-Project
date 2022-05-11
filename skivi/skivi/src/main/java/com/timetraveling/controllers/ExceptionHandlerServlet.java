package com.timetraveling.controllers;

import com.timetraveling.utils.validation.SessionValidator;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "exceptionHandlerServlet",
        urlPatterns = {"/error"},
        loadOnStartup = 1)
public class ExceptionHandlerServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!SessionValidator.isLoggedIn(request)) {
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }

        super.service(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Throwable throwable = (Throwable)
                request.getAttribute("jakarta.servlet.error.exception");
        String exceptionMessage = throwable.getMessage();

        Integer statusCode = (Integer)
                request.getAttribute("jakarta.servlet.error.status_code");
        String servletName = (String)
                request.getAttribute("jakarta.servlet.error.servlet_name");

        if (servletName == null) {
            servletName = "Unknown";
        }

        String requestUri = (String)
                request.getAttribute("jakarta.servlet.error.request_uri");

        if (requestUri == null) {
            requestUri = "Unknown";
        }

        request.setAttribute("statusCode", statusCode);
        request.setAttribute("description", exceptionMessage);

        getServletContext().getRequestDispatcher("/html/error404.jsp").forward(request, response);
    }
}
