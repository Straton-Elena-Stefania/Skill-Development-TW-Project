package com.timetraveling.controllers;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.timetraveling.exceptions.InvalidDeploymentException;
import com.timetraveling.models.article.*;
import com.timetraveling.models.session.SessionStore;
import com.timetraveling.models.session.SessionStoreHibernateRepository;
import com.timetraveling.models.session.SessionStoreRepository;
import com.timetraveling.models.users.User;
import com.timetraveling.models.users.UserRepository;
import com.timetraveling.models.users.UserHibernateRepository;
import com.timetraveling.utils.configuration.Configuration;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Acest servlet se ocupa cu compunerea elementelor ce trebuie
 * asezate in pagina, si transmiterea lor mai departe catre front,
 * pentru skill-ul de timetraveling.
 */
@WebServlet(name = "TimetravelingServlet", value = "/timetraveling/*")
public class TimetravelingServlet extends HttpServlet implements Communicative {
    private static final String ARTICLE_FROM_NAVBAR = "/.*/.*";
    private static final int SECTION = 1;
    private static final int SUBSECTION = 2;

    private final Gson gson = new Gson();
    private final UserRepository userRepository = new UserHibernateRepository();
    private SessionStoreRepository sessionStoreRepository = new SessionStoreHibernateRepository();
    private final CloseableHttpClient httpClient = HttpClients.createDefault();

    private List<Resource> getStepResources(int stepId, int userId) throws InvalidDeploymentException {
        HttpGet articleRequest;

        if (Configuration.DEPLOYMENT_MODE.equals(DOCKER_DEPLOYMENT)) {
            articleRequest = new HttpGet("http://timetraveling-web:8080/steps/" + stepId + "/resources");
        } else if (Configuration.DEPLOYMENT_MODE.equals(LOCAL_DEPLOYMENT)) {
            articleRequest = new HttpGet("http://localhost:8081/timetraveling_microservice2_war_exploded/steps/" + stepId + "/resources");
        } else {
            throw new InvalidDeploymentException("Deployment mode is unknown");
        }

        User user = userRepository.findByID(userId);
        SessionStore sessionStore = sessionStoreRepository.findByUserID(userId);

        articleRequest.addHeader("userId", String.valueOf(sessionStore.getUserId()));
        articleRequest.addHeader("Authorization", String.valueOf(sessionStore.getSessionId()));
        articleRequest.addHeader("content-type", "application/json");

        try (CloseableHttpResponse articleResponse = httpClient.execute(articleRequest)) {
            System.out.println(articleResponse.getStatusLine().toString());

            HttpEntity entity = articleResponse.getEntity();

            String result = EntityUtils.toString(entity);
            JsonArray stepJson = gson.fromJson(result, JsonArray.class);

            List<Resource> resources = gson.fromJson(stepJson, new TypeToken<List<Resource>>() {}.getType());
            return resources;
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        return null;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        int userId = (Integer) session.getAttribute("id");

        String path = request.getPathInfo();
        List<String> pathTraversal = Arrays.asList(path.split("/").clone());

        String sectionName = pathTraversal.get(SECTION);
        List<Step> steps = new ArrayList<>();
        List<Subsection> subsectionList = new ArrayList<>();
        String subsectionName = null;

        try {
            subsectionList = getSubsections(sectionName, userId);

            if (path.matches(ARTICLE_FROM_NAVBAR)) {
                subsectionName = pathTraversal.get(SUBSECTION);
                int subsectionId = 0;

                for (Subsection subsection: subsectionList) {
                    if (subsection.getName().equals(subsectionName)) {
                        subsectionId = subsection.getId();
                    }
                }

                steps = getSteps(subsectionId, userId).stream().sorted()
                        .collect(Collectors.toList());
            } else {
                steps = getSteps(subsectionList.get(0).getId(), userId)
                        .stream().sorted()
                        .collect(Collectors.toList());
            }
        } catch (InvalidDeploymentException deploymentException) {
            deploymentException.printStackTrace();
        }

        /**
         * Steps nu are id-uri consecutive in baza de date.
         * POATE FI OPTIMIZAT: Lista (cautare dupa ordinea in pagina)
         */
        Map<Integer, List<Resource>> stepResources = new HashMap<>();

        /**
         * Pentru fiecare pas aflam ce resurse trebuie
         */
        for (Step step: steps) {
            List<Resource> resources = new ArrayList<>();
            try {
                resources = getStepResources(step.getId(), userId);
            } catch (InvalidDeploymentException deploymentException) {
                deploymentException.printStackTrace();
            }

            stepResources.put(step.getId(), resources);
        }

        /**
         * La inceput cand utilizatorul vine de pe pagina de home,
         * URL-ul nu contine numele subsectiunii, deci luam by default
         * prima subsectiune dupa id pe care o primeste remote. Insa daca acceseaza
         * link-urile puse la dispozitie pe o pagina a unui skill
         * URL-ul va fi rescris.
         */
        if (subsectionName == null) {
            request.setAttribute("subsectionName", subsectionList.get(0).getName());
        } else {
            request.setAttribute("subsectionName", subsectionName);
        }

        /**
         * Pentru link-urile din aside
         */
        request.setAttribute("subsectionList", subsectionList);
        request.setAttribute("sectionName", sectionName);

        /**
         * Componentele propriu-zise ale articolului
         */
        request.setAttribute("stepList", steps);

        /**
         * Ce resurse sunt necesare la un anume pas
         */
        request.setAttribute("stepResourcesMap", stepResources);

        /**
         * Numele utilizatorului
         */
        request.setAttribute("userName", userRepository.findByID(userId).getUsername());

        getServletContext().getRequestDispatcher("/html/skill-display.jsp").forward(request, response);
    }

    private List<Step> getSteps(int subsectionId, int userId) throws InvalidDeploymentException {
        HttpGet articleRequest;

        User user = userRepository.findByID(userId);
        SessionStore sessionStore = sessionStoreRepository.findByUserID(userId);

        if (Configuration.DEPLOYMENT_MODE.equals(DOCKER_DEPLOYMENT)) {
            articleRequest = new HttpGet("http://timetraveling-web:8080/subsections/" + subsectionId + "/steps");
            //articleRequest.setHeader();
        } else if (Configuration.DEPLOYMENT_MODE.equals(LOCAL_DEPLOYMENT)) {
            articleRequest = new HttpGet("http://localhost:8081/timetraveling_microservice2_war_exploded/subsections/" + subsectionId + "/steps");
        } else {
            throw new InvalidDeploymentException("Deployment mode is unknown");
        }

        articleRequest.addHeader("userId", String.valueOf(sessionStore.getUserId()));
        articleRequest.addHeader("Authorization", String.valueOf(sessionStore.getSessionId()));
        articleRequest.addHeader("content-type", "application/json");

        try (CloseableHttpResponse articleResponse = httpClient.execute(articleRequest)) {
            System.out.println(articleResponse.getStatusLine().toString());

            HttpEntity entity = articleResponse.getEntity();

            String result = EntityUtils.toString(entity);
            JsonArray stepJson = gson.fromJson(result, JsonArray.class);

            List<Step> steps = gson.fromJson(stepJson, new TypeToken<List<Step>>() {}.getType());
            return steps;
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        return null;
    }

    private List<Subsection> getSubsections(String sectionName, int userId) throws IOException, ServletException, InvalidDeploymentException {
        /**
         * aici iteram prin ce skilluri prefera userul
         */

        //  Pentru a face cerere de pe docker:
        //  HttpGet sectionsRequest = new HttpGet("http://timetraveling-web:8080/sections");

        //  Pentru a face cerere de pe localhost:
        System.out.println("cerere");
        HttpGet subsectionsRequest;

        if (Configuration.DEPLOYMENT_MODE.equals(DOCKER_DEPLOYMENT)) {
            subsectionsRequest = new HttpGet("http://timetraveling-web:8080/subsections/"  + sectionName);
        } else if (Configuration.DEPLOYMENT_MODE.equals(LOCAL_DEPLOYMENT)) {
            subsectionsRequest = new HttpGet("http://localhost:8081/timetraveling_microservice2_war_exploded/subsections/"  + sectionName);
        } else {
            throw new InvalidDeploymentException("Deployment mode is unknown");
        }

        User user = userRepository.findByID(userId);
        SessionStore sessionStore = sessionStoreRepository.findByUserID(userId);

        subsectionsRequest.addHeader("userId", String.valueOf(sessionStore.getUserId()));
        subsectionsRequest.addHeader("Authorization", String.valueOf(sessionStore.getSessionId()));
        subsectionsRequest.addHeader("content-type", "application/json");

        try (CloseableHttpResponse subsectionsResponse = httpClient.execute(subsectionsRequest)) {
            System.out.println(subsectionsResponse.getStatusLine().toString());

            HttpEntity entity = subsectionsResponse.getEntity();

            String result = EntityUtils.toString(entity);
            JsonArray subsectionsListJson = gson.fromJson(result, JsonArray.class);

            List<Subsection> subsectionsList = new ArrayList<>();

            for (int subsectionIndex = 0;
                 subsectionIndex < subsectionsListJson.size();
                 subsectionIndex++) {
                subsectionsList.add(gson.fromJson(subsectionsListJson.get(subsectionIndex), Subsection.class));
            }

            return subsectionsList;
        }
    }
}