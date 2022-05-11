package com.timetraveling.controllers;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.timetraveling.configuration.Authorization;
import com.timetraveling.configuration.AuthorizationExecutor;
import com.timetraveling.exceptions.DuplicateResourceException;
import com.timetraveling.models.article.resources.Resource;
import com.timetraveling.models.article.resources.ResourceRepositoryHibernate;
import com.timetraveling.models.article.steps.Step;
import com.timetraveling.models.article.steps.StepRepositoryHibernate;
import com.timetraveling.models.article.stepsresources.StepResource;
import com.timetraveling.models.article.stepsresources.StepResourceRepositoryHibernate;
import com.timetraveling.models.sections.Section;
import com.timetraveling.models.sections.SectionHibernateRepository;
import com.timetraveling.utils.async.NotificationPublisher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

/**
 * Un pas are un format bine definit pentru a fi afisat cat mai intuitiv
 * in pagina. Un pas contine informatii pentru a realiza un tutorial,
 * pentru a face articolele mai usor de citit.
 */
@WebServlet(name = "stepsServlet", value = "/steps/*")
public class StepsServlet extends HttpServlet implements Authorization {
    /**
     * Indexul pentru de unde luam pasul din URL, dupa id
     */
    private final static int STEP_ID = 1;
    /**
     * Indexul pentru id-ul resursei, din URL
     */
    private final static int RESOURCE_ID = 3;

    private final static String ALL_STEP_OPTION = "";
    /**
     * Pentru a obtine resursa propriu-zisa. De aici va fi extras id-ul.
     */
    private final static String STEP_OPTION = "/.*";
    /**
     * Pentru a obtine toate resursele ce tin de un pas
     */
    private final static String RESOURCE_PATH = "/.*/resources";
    private final static String RESOURCE_ID_PATH = "/.*/resources/.*";

    private final StepRepositoryHibernate stepRepository = new StepRepositoryHibernate(Step.class);
    private final StepResourceRepositoryHibernate stepResourceRepository = new StepResourceRepositoryHibernate(StepResource.class);
    private final ResourceRepositoryHibernate resourceRepository = new ResourceRepositoryHibernate(Resource.class);

    private Gson gson = new Gson();

    /**
     * Legatura dintre servlet si publisher.
     */
    private NotificationPublisher notificationPublisher;

    /**
     * Atunci cand este initializat servlet-ul (la cerere), este initializat
     * publisher-ul de mesaje AMQP.
     */
    public void init() {
        //System.out.println("initializing servlet with publisher");
        this.notificationPublisher = new NotificationPublisher();
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");

        if ("PATCH".equals(request.getMethod())) {
            doPatch(request, response);
            return;
        }

        super.service(request, response);
    }

    public void doPatch(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String path = request.getPathInfo();
        List<String> pathTraversal = Arrays.asList(path.split("/").clone());

        try {
            String body = request.getReader().lines()
                    .collect(Collectors.joining(System.lineSeparator()));

            AuthorizationExecutor authorizationExecutor = new AuthorizationExecutor();

            int status = authorizationExecutor.checkAuthorizationStatus(request, response,
                    ADMIN_ROLE);

            if (status != HttpServletResponse.SC_OK) {
                response.sendError(status);
                return;
            }

            //  /steps/{id}
            if (path.matches(STEP_OPTION)) {
                JsonObject resource = gson.fromJson(body, JsonObject.class);
                int stepId = Integer.parseInt(pathTraversal.get(STEP_ID));

                int resourceId = resource.get("value").getAsInt();
                String operation = resource.get("operation").getAsString();
                String attribute = resource.get("attribute").getAsString();

                if (attribute == null || operation == null) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                    return;
                } else if (!attribute.equals("resources")) {
                    response.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED);
                    return;
                }

                if (operation.equals("add")) {
                    try {
                        StepResource stepResource = new StepResource();

                        if (stepRepository.findByID(stepId) == null || resourceRepository.findByID(resourceId) == null) {
                            response.sendError(HttpServletResponse.SC_NOT_FOUND);
                            return;
                        }

                        stepResource.setResourceId(resourceId);
                        stepResource.setStepId(stepId);
                        stepResourceRepository.save(stepResource);

                        /**
                         * Trimitem inapoi ceea ce s-a postat
                         */
                        response.setContentType("application/json");
                        response.setCharacterEncoding("UTF-8");
                        response.getWriter().write(body);
                    } catch (DuplicateResourceException duplicateResourceException) {
                        response.sendError(HttpServletResponse.SC_CONFLICT);
                        return;
                    }
                } else if (operation.equals("remove")) {
                    response.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED);
                    return;
                }
            }  else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
        } catch (NumberFormatException formatException) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
    }

    /**
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String path = request.getPathInfo();

        if (path == null) {
            path = ALL_STEP_OPTION;
        }

        List<String> pathTraversal = Arrays.asList(path.split("/").clone());

        try {
            String body = request.getReader().lines()
                    .collect(Collectors.joining(System.lineSeparator()));

            AuthorizationExecutor authorizationExecutor = new AuthorizationExecutor();

            int status = authorizationExecutor.checkAuthorizationStatus(request, response,
                    ADMIN_ROLE);

            if (status != HttpServletResponse.SC_OK) {
                response.sendError(status);
                return;
            }

            //  /steps/{id}/resources
            if (path.matches(RESOURCE_PATH)) {
                Resource resource = gson.fromJson(body, Resource.class);
                int stepId = Integer.parseInt(pathTraversal.get(STEP_ID));

                try {
                    ResourceRepositoryHibernate newRepository = new ResourceRepositoryHibernate(Resource.class);
                    Resource newResource = newRepository.save(resource);
                    int resourceId = newResource.getId();

                    StepResource stepResource = new StepResource();
                    stepResource.setResourceId(resourceId);
                    stepResource.setStepId(stepId);
                    stepResourceRepository.save(stepResource);

                    /**
                     * Trimitem inapoi ceea ce s-a postat
                     */
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    response.getWriter().write(body);
                } catch (DuplicateResourceException duplicateResourceException) {
                    response.sendError(HttpServletResponse.SC_CONFLICT);
                    return;
                }
            } else if (path.matches(ALL_STEP_OPTION)) {
                //  /steps
                /**
                 * A fost postat un nou pas
                 */
                Step step = gson.fromJson(body, Step.class);
                try {
                    StepRepositoryHibernate newRepository = new StepRepositoryHibernate(Step.class);
                    newRepository.save(step);
                    /**
                     * Acum este trimisa notificarea, pentru a o ruta
                     * catre un queue
                     */
                    notificationPublisher.publish("timetraveling.step.update", "A new step has been added in timetraveling: " + step.getStepHeader());
                    notificationPublisher.publish("timetraveling.push.update", "A new step has been added in timetraveling: " + step.getStepHeader());
                } catch (DuplicateResourceException duplicateResourceException) {
                    response.sendError(HttpServletResponse.SC_CONFLICT);
                    return;
                } catch (TimeoutException e) {
                    e.printStackTrace();
                }

                /**
                 * Trimitem inapoi ceea ce s-a postat
                 */
                response.setStatus(HttpServletResponse.SC_CREATED);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(body);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                throw new ServletException("Invalid URI");
            }
        } catch (NumberFormatException numberFormatException) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
    }

    /**
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */
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

        AuthorizationExecutor authorizationExecutor = new AuthorizationExecutor();

        int status = authorizationExecutor.checkAuthorizationStatus(request, response,
                ADMIN_ROLE);

        if (status != HttpServletResponse.SC_OK) {
            response.sendError(status);
            return;
        }
        /**
         * Cuvintele din path
         */
        List<String> pathTraversal = Arrays.asList(path.split("/").clone());

        try {
            PrintWriter out = response.getWriter();

            if (path.matches(STEP_OPTION)) {
                /**
                 * Altfel, a fost ceruta resursa in sine.
                 */
                StepRepositoryHibernate newRepository = new StepRepositoryHibernate(Step.class);
                Step step = newRepository.findByID(Integer.parseInt(pathTraversal.get(STEP_ID)));

                if (step == null) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                    return;
                }

                newRepository.remove(step);
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
    public void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");

        AuthorizationExecutor authorizationExecutor = new AuthorizationExecutor();

        int status = authorizationExecutor.checkAuthorizationStatus(request, response,
                ADMIN_ROLE);

        if (status != HttpServletResponse.SC_OK) {
            response.sendError(status);
            return;
        }

        /**
         * Ceea ce se afla dupa maparea URL-ului
         */
        String path = request.getPathInfo();
        /**
         * Cuvintele din path
         */
        List<String> pathTraversal = Arrays.asList(path.split("/").clone());

        try {
            if (path.matches(STEP_OPTION)) {
                SectionHibernateRepository sectionRepository = new SectionHibernateRepository(Section.class);
                int id = Integer.parseInt(pathTraversal.get(STEP_ID));

                String body = request.getReader().lines()
                        .collect(Collectors.joining(System.lineSeparator()));

                StepRepositoryHibernate newRepository = new StepRepositoryHibernate(Step.class);
                Step step = newRepository.findByID(id);
                Step stepOfBody = gson.fromJson(body, Step.class);

                if (step == null) {
                    stepOfBody.setId(id);
                    stepRepository.update(stepOfBody);

                    response.setStatus(HttpServletResponse.SC_CREATED);
                    return;
                } else {
                    step.setContentDescription(stepOfBody.getContentDescription());
                    step.setStepHeader(stepOfBody.getStepHeader());
                    step.setContentLink(stepOfBody.getContentLink());
                    step.setStepNumber(stepOfBody.getStepNumber());

                    stepRepository.update(step);

                    response.setStatus(HttpServletResponse.SC_OK);
                    return;
                }
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
        } catch (NumberFormatException numberFormatException) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
    }

    /**
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        /**
         * Ceea ce se afla dupa maparea URL-ului
         */
        String path = request.getPathInfo();

        if (path == null) {
            path = ALL_STEP_OPTION;
        }

        String body = request.getReader().lines()
                .collect(Collectors.joining(System.lineSeparator()));

        AuthorizationExecutor authorizationExecutor = new AuthorizationExecutor();

        int status = authorizationExecutor.checkAuthorizationStatus(request, response,
                ENROLLED_ROLE);

        if (status != HttpServletResponse.SC_OK) {
            response.sendError(status);
            return;
        }
        /**
         * Cuvintele din path
         */
        List<String> pathTraversal = Arrays.asList(path.split("/").clone());

        try {
            PrintWriter out = response.getWriter();

            /**
             * Daca cererea este mai specifica, in sensul ca url-ul este
             * mai lung (similar cu firewall-urile), vedem daca match-uieste cu cererea pentru
             * relatia many-to-many.
             */
            if (path.matches(RESOURCE_ID_PATH)) {
                ResourceRepositoryHibernate newRepository = new ResourceRepositoryHibernate(Resource.class);
                Resource resource = newRepository.findByID(Integer.parseInt(pathTraversal.get(RESOURCE_ID)));

                String resJson = gson.toJson(resource);
                out.println(resJson);
            } else if (path.matches(RESOURCE_PATH)) {
                StepResourceRepositoryHibernate newRepository = new StepResourceRepositoryHibernate(StepResource.class);
                List<Resource> resources = newRepository.findByStepId(Integer.parseInt(pathTraversal
                        .get(STEP_ID)));

                /**
                 * Trimitem inapoi toate resursele ce apartin pasului
                 */
                String resJson = gson.toJson(resources);
                out.println(resJson);
            } else if (path.matches(STEP_OPTION)) {
                /**
                 * Altfel, a fost ceruta resursa in sine.
                 */
                StepRepositoryHibernate newRepository = new StepRepositoryHibernate(Step.class);
                Step step = newRepository.findByID(Integer.parseInt(pathTraversal.get(STEP_ID)));

                if (step == null) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                    return;
                }

                String stepJson = gson.toJson(step);
                out.println(stepJson);
            } else if (path.matches(ALL_STEP_OPTION)) {
                StepRepositoryHibernate newRepository = new StepRepositoryHibernate(Step.class);
                List<Step> steps = newRepository.findAll();

                String stepJson = gson.toJson(steps);
                out.println(stepJson);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
        } catch (NumberFormatException formatException) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
    }
}
