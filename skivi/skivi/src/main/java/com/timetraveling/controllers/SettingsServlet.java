package com.timetraveling.controllers;

import com.timetraveling.models.users.User;
import com.timetraveling.models.users.UserHibernateRepository;
import com.timetraveling.models.users.UserRepository;
import com.timetraveling.utils.secure.PasswordHandler;
import com.timetraveling.utils.validation.SessionValidator;
import com.timetraveling.utils.validation.UserValidator;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Optional;

@WebServlet(name = "SettingsServlet", value = "/settings")
public class SettingsServlet extends HttpServlet {
    private final UserRepository userRepository = new UserHibernateRepository();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!SessionValidator.isLoggedIn(request)) {
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }

        super.service(request, response);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        int userId = (Integer) session.getAttribute("id");
        User user = userRepository.findByID(userId);

        if (!SessionValidator.isLoggedIn(request)) {
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }
        /**
         * Aici verificam daca cererea vine de la front. Cum in front un <form> nu poate
         * sa implementeze metode precum put sau patch, a trebuit acest
         * workaround. Am redirectionat deci pe baza a ceea ce am apasat catre
         * metoda CRUD corespunzatoare.
         */
        if (request.getParameter("infoButton") != null) {
            String username = request.getParameter("username");
            String description = request.getParameter("description");
            String email = request.getParameter("email");

            user.setEmail(email);
            user.setUsername(username);
            user.setDescription(description);

            userRepository.update(user);
        }

        if (request.getParameter("passwordButton") != null) {
            PasswordHandler passwordHandler = new PasswordHandler();

            String password = request.getParameter("password");
            String securePassword = PasswordHandler.generateSecurePassword(password, PasswordHandler.getSalt(UserValidator.SALT_LENGTH));

            user.setPassword(securePassword);
            userRepository.update(user);
        }

        response.setHeader("Cache-Control","no-cache");
        response.setHeader("Cache-Control","no-store");
        response.setHeader("Pragma","no-cache");
        response.setDateHeader ("Expires", 0);

        request.setAttribute("userName", userRepository.findByID(userId).getUsername());

        getServletContext().getRequestDispatcher("/html/usermanagement/settings.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPut(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        int userId = (Integer) session.getAttribute("id");

        if (!SessionValidator.isLoggedIn(request)) {
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }

        response.setHeader("Cache-Control","no-cache");
        response.setHeader("Cache-Control","no-store");
        response.setHeader("Pragma","no-cache");
        response.setDateHeader ("Expires", 0);

        request.setAttribute("userName", userRepository.findByID(userId).getUsername());

        getServletContext().getRequestDispatcher("/html/usermanagement/settings.jsp").forward(request, response);
    }
}