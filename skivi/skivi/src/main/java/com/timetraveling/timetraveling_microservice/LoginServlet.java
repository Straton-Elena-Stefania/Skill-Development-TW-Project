package com.timetraveling.timetraveling_microservice;

import com.timetraveling.models.session.SessionStore;
import com.timetraveling.models.session.SessionStoreHibernateRepository;
import com.timetraveling.models.session.SessionStoreRepository;
import com.timetraveling.models.users.User;
import com.timetraveling.models.users.UserHibernateRepository;
import com.timetraveling.utils.validation.LoginValidator;
import com.timetraveling.utils.validation.SessionValidator;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Acest servlet este pentru a implementa utilitatea
 * de login pe site. Utilitatea de login poate fi accesata
 * pe pagina de index atunci cand un utilizator acceseaza
 * site-ul pentru prima oara intr-o sesiune.
 */
@WebServlet(name = "LoginServlet", value = "/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest  request, HttpServletResponse response)
            throws ServletException, IOException {

        LoginValidator loginValidator = new LoginValidator();
            /**
            * Aceasta este pentru cand cineva introduce
            * manual url-ul pentru index.jsp sau pentru /,
            * ca sa il redirectionam catre pagina de home.
            *
            * Pentru a ajunge la pagina aceasta, userul trebuie
            * ori sa apeleze explicit logout, ori sesiunea sa
            * expire.
            */
            if (SessionValidator.isLoggedIn(request)) {
                System.out.println("userul este logat: " + request.getSession().getAttribute("id"));
                response.sendRedirect(request.getContextPath() + "/home");
                return;
            }

            /**
            * Obtinem email-ul si parola din formularul de pe
            * index.jsp ca si parametri pasati in request.
            */
            String email = request.getParameter("loginEmail");
            String password = request.getParameter("password");

            /**
            * Vedem daca credentialele se potrivesc cu ce este in
            * baza de date. Pentru asta, folosim email-ul si facem
            * un query peste baza de date. Pentru email-ul gasit
            * in baza de date, vedem daca parola (dupa ce este hashed)
            * se potriveste.
            */
            if (loginValidator.isAuthenticationValid(email, password)) {
                User user = (new UserHibernateRepository()).findByEmail(email);

                /**
                * Aici setam parametrii de sesiune, precum timpul
                * maxim de inactivitate pana delogam userul si un id
                * ca sa stim cu cine avem de-a face.
                */
                request.getSession(true).setMaxInactiveInterval(3000);
                request.getSession(true).setAttribute("id", user.getId());

                SessionStoreRepository sessionStoreRepository = new SessionStoreHibernateRepository();

                SessionStore sessionStoreOfId = sessionStoreRepository.findByUserID(user.getId());

                if (sessionStoreOfId == null) {
                    sessionStoreOfId = new SessionStore();
                }

                sessionStoreOfId.setUserId(user.getId());
                sessionStoreOfId.setSessionId(request.getSession().getId());

                sessionStoreRepository.update(sessionStoreOfId);

                /**
                * Prezentam homepage cu skill-uri.
                */
                response.sendRedirect(request.getContextPath() + "/home");
        } else {
                response.sendRedirect(request.getContextPath() + "/");
        }

    }

    @Override
    protected void doGet(
            HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LoginValidator loginValidator = new LoginValidator();

        String headerEmail = request.getHeader("email");
        String headerPassword = request.getHeader("password");

        if (headerEmail != null && headerPassword != null) {
            if (loginValidator.isAuthenticationValid(headerEmail, headerPassword)) {
                User user = (new UserHibernateRepository()).findByEmail(headerEmail);
                request.getSession(true).setMaxInactiveInterval(3000);
                request.getSession(true).setAttribute("id", user.getId());

                SessionStoreRepository sessionStoreRepository = new SessionStoreHibernateRepository();

                SessionStore sessionStoreOfId = sessionStoreRepository.findByUserID(user.getId());

                if (sessionStoreOfId == null) {
                    sessionStoreOfId = new SessionStore();
                }

                sessionStoreOfId.setUserId(user.getId());
                sessionStoreOfId.setSessionId(request.getSession().getId());

                sessionStoreRepository.update(sessionStoreOfId);

                response.addHeader("Authorization", sessionStoreOfId.getSessionId());
                response.addHeader("userId", String.valueOf(sessionStoreOfId.getUserId()));

                return;
            } else {
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
                return;
            }
        }

        if (SessionValidator.isLoggedIn(request)) {
            response.sendRedirect(request.getContextPath() + "/home");
        } else {
            response.sendRedirect(request.getContextPath());
        }
    }
}