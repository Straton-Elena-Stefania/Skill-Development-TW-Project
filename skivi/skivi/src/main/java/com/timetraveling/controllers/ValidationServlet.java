package com.timetraveling.controllers;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.timetraveling.exceptions.DuplicateResourceException;
import com.timetraveling.models.RegistrationQueryResponse;
import com.timetraveling.models.session.SessionStore;
import com.timetraveling.models.session.SessionStoreHibernateRepository;
import com.timetraveling.models.session.SessionStoreRepository;
import com.timetraveling.models.users.User;
import com.timetraveling.models.users.UserHibernateRepository;
import com.timetraveling.models.users.UserRepository;
import com.timetraveling.utils.secure.PasswordHandler;
import com.timetraveling.utils.validation.SignUpValidator;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.stream.Collectors;

@WebServlet(name = "ValidationServlet", value = "/validation")
public class ValidationServlet extends HttpServlet {
    private static final int PASSWORD_LEN = 8;
    private UserHibernateRepository userDAO = new UserHibernateRepository();

    private Gson gson = new Gson();
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String body = request.getReader().lines()
                .collect(Collectors.joining(System.lineSeparator()));

        /**
         * Acest URL este mai general decat cel anterior.
         * Introducem in baza de date o noua resursa, din corpul cererii
         */
        JsonObject validationJson = gson.fromJson(body, JsonObject.class);
        String email = validationJson.get("email").getAsString();
        String username = validationJson.get("username").getAsString();
        String password = validationJson.get("password").getAsString();

        boolean everythingOk = true;

        RegistrationQueryResponse registrationQueryResponse = new RegistrationQueryResponse();

        UserRepository userRepository = new UserHibernateRepository();
        if (userRepository.findByEmail(email) != null) {
            registrationQueryResponse.setEmailError("Email is not available");
            everythingOk = false;
        }

        if (userRepository.findByUsername(username) != null) {
            registrationQueryResponse.setUsernameError("Username is not available");
            everythingOk = false;
        }

        if (password.length() < PASSWORD_LEN) {
            registrationQueryResponse.setPasswordError("Password is not at least 8 characters");
            everythingOk = false;
        }

        if (everythingOk) {
                try {
                String salt = PasswordHandler.getSalt(SignUpValidator.SALT_LENGTH);

                String securePassword = PasswordHandler.generateSecurePassword(password, salt);

                securePassword = securePassword + salt;

                User user = new User(username,
                        email,
                        securePassword);

                SignUpValidator signUpValidator = new SignUpValidator();
                if (signUpValidator.isRegistrationValid(user)) {
                    userDAO.save(user);
                    System.err.println("registration valid");
                    /**
                     * Aici setam parametrii de sesiune, precum timpul
                     * maxim de inactivitate pana delogam userul si un id
                     * ca sa stim cu cine avem de-a face.
                     */
                    request.getSession(true).setMaxInactiveInterval(3000);
                    request.getSession(true).setAttribute("id", user.getId());

                    SessionStoreRepository sessionStoreRepository = new SessionStoreHibernateRepository();

                    SessionStore sessionStore = new SessionStore();
                    sessionStore.setUserId(user.getId());
                    sessionStore.setSessionId(request.getSession().getId());

                    sessionStoreRepository.update(sessionStore);

                    //response.sendRedirect(request.getContextPath() + "/home");
                } else {
                    //response.sendRedirect(request.getContextPath() + "/");
                }

            } catch (DuplicateResourceException duplicateResourceException) {
                duplicateResourceException.printStackTrace();
            } finally {
                System.out.println("finally");
            }
        }

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String stepJson = gson.toJson(registrationQueryResponse);
        response.getWriter().write(stepJson);

        /*if (everythingOk) {
            String salt = PasswordHandler.getSalt(SignUpValidator.SALT_LENGTH);

            String securePassword = PasswordHandler.generateSecurePassword(password, salt);

            securePassword = securePassword + salt;

            User user = new User(username,
                    email,
                    securePassword);

            SignUpValidator signUpValidator = new SignUpValidator();
            if (signUpValidator.isRegistrationValid(user)) {
                try {
                    userDAO.save(user);
                } catch (DuplicateResourceException duplicateResourceException) {
                    duplicateResourceException.printStackTrace();
                }
                request.getSession(true).setMaxInactiveInterval(3000);
                request.getSession(true).setAttribute("id", user.getId());

                SessionStoreRepository sessionStoreRepository = new SessionStoreHibernateRepository();

                SessionStore sessionStore = new SessionStore();
                sessionStore.setUserId(user.getId());
                sessionStore.setSessionId(request.getSession().getId());

                sessionStoreRepository.update(sessionStore);

                response.sendRedirect(request.getContextPath() + "/home");
            } else {
                response.sendRedirect(request.getContextPath() + "/");
            }
        }*/
    }
}
