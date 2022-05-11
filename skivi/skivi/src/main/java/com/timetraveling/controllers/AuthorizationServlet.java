package com.timetraveling.controllers;

import com.google.gson.Gson;
import com.timetraveling.models.session.SessionStore;
import com.timetraveling.models.session.SessionStoreHibernateRepository;
import com.timetraveling.models.session.SessionStoreRepository;
import com.timetraveling.models.skillmodel.SkillModel;
import com.timetraveling.models.users.User;
import com.timetraveling.models.users.UserHibernateRepository;
import com.timetraveling.models.users.UserRepository;
import com.timetraveling.models.userskills.UserSkillHibernateRepository;
import com.timetraveling.utils.secure.AuthorizationChecker;
import com.timetraveling.utils.validation.SessionValidator;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@WebServlet(name = "AuthorizationServlet", urlPatterns={"/authorization", "/authorization/*"})
public class AuthorizationServlet extends HttpServlet {
    private static final int SKILL_INDEX = 1;
    private static final String SKILL_AUTHORIZATION_REQ_PATH = "/.*";
    private static final String ADMIN_AUTHORIZATION_PATH = "";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    /**
     * requested role
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /**
         * Ceea ce se afla dupa maparea servletului
         */
        String path = request.getPathInfo();

        if (path == null) {
            path = ADMIN_AUTHORIZATION_PATH;
        }
        /**
         * Obtinem componentele path-ului.
         */
        List<String> pathTraversal = Arrays.asList(path.split("/").clone());

        /**
         * Obtinem corpul resursei ce se afla in cererea POST
         */
        String body = request.getReader().lines()
                .collect(Collectors.joining(System.lineSeparator()));

        if (path.matches(SKILL_AUTHORIZATION_REQ_PATH)) {
            AuthorizationChecker authorizationChecker = new AuthorizationChecker();

            /**
             * Acest URL este mai general decat cel anterior.
             * Introducem in baza de date o noua resursa, din corpul cererii
             */
            String requestedSkillRole = pathTraversal.get(SKILL_INDEX);
            String session = request.getHeader("Authorization");
            int userId = request.getIntHeader("userId");

            if (!authorizationChecker.checkForEnrolled(session, userId, requestedSkillRole)) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
                return;
            }

            response.setStatus(HttpServletResponse.SC_OK);
        } else if (path.matches(ADMIN_AUTHORIZATION_PATH)) {
            String session = request.getHeader("Authorization");
            int userId = request.getIntHeader("userId");

            AuthorizationChecker authorizationChecker = new AuthorizationChecker();
            if (!authorizationChecker.checkForAdmin(session, userId)) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
                return;
            }

            response.setStatus(HttpServletResponse.SC_OK);
        }
    }
}
