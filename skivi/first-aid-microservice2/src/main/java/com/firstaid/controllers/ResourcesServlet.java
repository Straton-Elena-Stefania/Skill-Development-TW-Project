package com.firstaid.controllers;

import com.firstaid.controllers.configuration.Authorization;
import com.firstaid.controllers.configuration.AuthorizationExecutor;
import com.firstaid.exceptions.DuplicateResourceException;
import com.firstaid.models.article.resources.Resource;
import com.firstaid.models.article.resources.ResourceRepositoryHibernate;
import com.firstaid.models.article.steps.Step;
import com.firstaid.models.article.steps.StepRepositoryHibernate;
import com.firstaid.models.article.stepsresources.StepResource;
import com.firstaid.models.article.stepsresources.StepResourceRepositoryHibernate;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
/**
 * Acest servlet pune la dispozitie niste produse de care este
 * nevoie la un anumit pas. Resursele sunt mapate catre pasul lor
 * corespunzator printr-o tabela de asociere.
 */
@WebServlet(name = "resourcesServlet", value = "/resources/*")
public class ResourcesServlet extends HttpServlet implements Authorization {
    /**
     * Indexul pentru de unde luam pasul din URL, dupa id
     */
    private final static int STEP_ID = 3;
    /**
     * Indexul pentru id-ul resursei, din URL
     */
    private final static int RESOURCE_ID = 1;
    /**
     *
     */
    private final static String RESOURCE_OPTION = "/.*";
    private final static String ALL_RESOURCES_OPTION = "";

    private final StepRepositoryHibernate stepRepository = new StepRepositoryHibernate(Step.class);
    private final ResourceRepositoryHibernate resourceRepository = new ResourceRepositoryHibernate(Resource.class);
    private final StepResourceRepositoryHibernate stepResourceRepository = new StepResourceRepositoryHibernate(StepResource.class);
    private Gson gson = new Gson();

    @Override
    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        /**
         * Ceea ce se afla dupa maparea URL-ului
         */
        String path = request.getPathInfo();

        if (path == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        /**
         * Cuvintele din path
         */
        List<String> pathTraversal = Arrays.asList(path.split("/").clone());

        try {
            PrintWriter out = response.getWriter();

            if (path.matches(RESOURCE_OPTION)) {
                /**
                 * Altfel, a fost ceruta resursa in sine.
                 */
                ResourceRepositoryHibernate newRepository = new ResourceRepositoryHibernate(Resource.class);
                Resource resource = newRepository.findByID(Integer.parseInt(pathTraversal.get(RESOURCE_ID)));

                if (resource == null) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                    return;
                }

                newRepository.remove(resource);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
        } catch (NumberFormatException formatException) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AuthorizationExecutor authorizationExecutor = new AuthorizationExecutor();
        int status;

        if ("POST".equals(request.getMethod())) {
            status = authorizationExecutor.checkAuthorizationStatus(request, response,
                    ADMIN_ROLE);
        } else {
            status = authorizationExecutor.checkAuthorizationStatus(request, response,
                    ENROLLED_ROLE);
        }

        if (status != HttpServletResponse.SC_OK) {
            response.sendError(status);
            return;
        }

        super.service(request, response);
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        /**
         * Ceea ce se afla dupa maparea servletului
         */
        String path = request.getPathInfo();

        if (path == null) {
            path = ALL_RESOURCES_OPTION;
        }

        /**
         * Obtinem componentele path-ului.
         */
        List<String> pathTraversal = Arrays.asList(path.split("/").clone());

        PrintWriter out = response.getWriter();

        try {
            /**
             * Obtinem corpul resursei ce se afla in cererea POST
             */
            if (path.matches(RESOURCE_OPTION)) {
                /**
                 * Altfel, a fost ceruta resursa in sine.
                 */
                ResourceRepositoryHibernate newRepository = new ResourceRepositoryHibernate(Resource.class);
                Resource resource = newRepository.findByID(Integer.parseInt(pathTraversal.get(STEP_ID)));

                if (resource == null) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                    return;
                }

                String stepJson = gson.toJson(resource);
                out.println(stepJson);
            } else if (path.matches(ALL_RESOURCES_OPTION)) {
                ResourceRepositoryHibernate newRepository = new ResourceRepositoryHibernate(Resource.class);
                List<Resource> resources = newRepository.findAll();

                String stepJson = gson.toJson(resources);
                out.println(stepJson);
            }
        } catch (NumberFormatException formatException) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        /**
         * Ceea ce se afla dupa maparea servletului
         */
        String path = request.getPathInfo();

        if (path == null) {
            path = ALL_RESOURCES_OPTION;
        }
        /**
         * Obtinem componentele path-ului.
         */
        List<String> pathTraversal = Arrays.asList(path.split("/").clone());

        try {
            /**
             * Obtinem corpul resursei ce se afla in cererea POST
             */
            String body = request.getReader().lines()
                    .collect(Collectors.joining(System.lineSeparator()));

            if (path.matches(ALL_RESOURCES_OPTION)) {
                /**
                 * Acest URL este mai general decat cel anterior.
                 * Introducem in baza de date o noua resursa, din corpul cererii
                 */
                Resource resource = gson.fromJson(body, Resource.class);
                Resource savedResource = resourceRepository.save(resource);

                /**
                 * Intoarcem inapoi corpul ce a fost trimis in cerere
                 */
                response.setStatus(HttpServletResponse.SC_CREATED);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                String stepJson = gson.toJson(savedResource);
                response.getWriter().write(stepJson);
            }

        } catch (NumberFormatException formatException) {
            throw new ServletException("Invalid URI");
        } catch (DuplicateResourceException duplicateResourceException) {
            response.sendError(HttpServletResponse.SC_CONFLICT);
        }
    }


}
