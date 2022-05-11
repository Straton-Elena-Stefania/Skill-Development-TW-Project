package com.timetraveling.controllers;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.timetraveling.exceptions.InvalidDeploymentException;
import com.timetraveling.exceptions.InvalidStepConfigurationException;
import com.timetraveling.models.PatchBody;
import com.timetraveling.models.article.*;
import com.timetraveling.models.session.SessionStore;
import com.timetraveling.models.session.SessionStoreHibernateRepository;
import com.timetraveling.models.session.SessionStoreRepository;
import com.timetraveling.models.skillmodel.SkillHibernateRepository;
import com.timetraveling.models.skillmodel.SkillModel;
import com.timetraveling.models.skills.Skill;
import com.timetraveling.models.users.User;
import com.timetraveling.models.users.UserHibernateRepository;
import com.timetraveling.models.users.UserRepository;
import com.timetraveling.models.userskills.UserSkillHibernateRepository;
import com.timetraveling.utils.configuration.Configuration;
import com.timetraveling.utils.validation.SessionValidator;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.*;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@WebServlet(name = "CourseAssembleServlet", value = "/courses/*")
public class CourseAssembleServlet extends HttpServlet implements Communicative {
    private static final int SKILL_ID = 1;

    private static final String ARTICLE_FROM_NAVBAR = "/.*/.*/.*";
    private static final int SECTION = 2;
    private static final int SUBSECTION = 3;

    private final Gson gson = new Gson();
    private final UserRepository userRepository = new UserHibernateRepository();
    private SessionStoreRepository sessionStoreRepository = new SessionStoreHibernateRepository();
    private SkillHibernateRepository skillHibernateRepository = new SkillHibernateRepository();

    private final CloseableHttpClient httpClient = HttpClients.createDefault();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!SessionValidator.isLoggedIn(request)) {
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }

        super.service(request, response);
    }

    /**
     * Este urat codul dar atata am putut
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String errorMessage = null;

        if (request.getParameter("updateStepButton") != null) {
            int stepNumber = Integer.parseInt(request.getParameter("updateStepButton"));
            int skillId = Integer.parseInt(request.getParameter("skillIdNewStepBox" + stepNumber));
            SkillModel skillModel = skillHibernateRepository.findByID(skillId);

            int stepId = Integer.parseInt(request.getParameter("updateStepId" + stepNumber));

            String newStepDescription = request.getParameter("updateStepDescription" + stepNumber);
            String newStepHeader = request.getParameter("updateStepHeader" + stepNumber);
            String newStepContentLink = request.getParameter("newStepContentLink" + stepNumber);
            int newStepNumber = Integer.parseInt(request.getParameter("updateStepNumber" + stepNumber));

            Step updatedStep = new Step();
            updatedStep.setId(stepId);
            updatedStep.setStepHeader(newStepHeader);
            updatedStep.setContentLink(newStepContentLink);
            updatedStep.setContentDescription(newStepDescription);
            updatedStep.setStepNumber(newStepNumber);

            HttpPut httpPut = new HttpPut(skillModel.getUrl() + "/steps/" + stepId);
            String json = gson.toJson(updatedStep);
            StringEntity stringEntity = new StringEntity(json);
            httpPut.setEntity(stringEntity);

            HttpSession session = request.getSession(false);
            int userId = (Integer) session.getAttribute("id");
            SessionStore sessionStore = sessionStoreRepository.findByUserID(userId);

            httpPut.addHeader("userId", String.valueOf(sessionStore.getUserId()));
            httpPut.addHeader("Authorization", String.valueOf(sessionStore.getSessionId()));
            httpPut.addHeader("content-type", "application/json");

            try (CloseableHttpResponse sectionsResponse = httpClient.execute(httpPut)) {
                System.out.println(sectionsResponse.getStatusLine().toString());

                if (sectionsResponse.getStatusLine().getStatusCode() == 500) {
                    errorMessage = "Could not communicate with the course provider";
                } else if (sectionsResponse.getStatusLine().getStatusCode() == 409) {
                    errorMessage = "There already is a step with this number on the page";
                }
            } catch (Exception exception) {
                System.out.println(exception.getMessage());
                errorMessage = "Could not communicate with the course provider";
            }
        } else if(request.getParameter("removeSubsectionButton") != null) {
            int skillId = Integer.parseInt(request.getParameter("subsectionRemoveSkillId"));
            SkillModel skillModel = skillHibernateRepository.findByID(skillId);

            int subsectionId = Integer.parseInt(request.getParameter("removeSubsectionButton"));

            HttpDelete httpDelete = new HttpDelete(skillModel.getUrl() + "/subsections/" + subsectionId);

            HttpSession session = request.getSession(false);
            int userId = (Integer) session.getAttribute("id");
            SessionStore sessionStore = sessionStoreRepository.findByUserID(userId);

            httpDelete.addHeader("userId", String.valueOf(sessionStore.getUserId()));
            httpDelete.addHeader("Authorization", String.valueOf(sessionStore.getSessionId()));
            httpDelete.addHeader("content-type", "application/json");

            try (CloseableHttpResponse sectionsResponse = httpClient.execute(httpDelete)) {
                System.out.println(sectionsResponse.getStatusLine().toString());
                if (sectionsResponse.getStatusLine().getStatusCode() != 200) {
                    errorMessage = "Could not communicate with the course provider";
                }
            } catch (Exception exception) {
                System.out.println(exception.getMessage());
                errorMessage = "Could not communicate with the course provider";
            }
        } else if (request.getParameter("newStepButton") != null) {
            int skillId = Integer.parseInt(request.getParameter("skillIdNewStepBox"));
            SkillModel skillModel = skillHibernateRepository.findByID(skillId);

            String newStepDescription = request.getParameter("newStepDescriptionBox");
            String newStepHeader = request.getParameter("newStepHeaderBox");
            String newStepContentLink = request.getParameter("newStepContentLinkBox");
            int newStepNumber = Integer.parseInt(request.getParameter("newStepNumberBox"));
            int subsectionId = Integer.parseInt(request.getParameter("subsectionIdNewStepBox"));

            Step updatedStep = new Step();
            updatedStep.setSubsectionID(subsectionId);
            updatedStep.setStepHeader(newStepHeader);
            updatedStep.setContentLink(newStepContentLink);
            updatedStep.setStepDescription(newStepDescription);
            updatedStep.setContentDescription(newStepHeader);
            updatedStep.setStepNumber(newStepNumber);
            updatedStep.setContentTypeId(2);

            HttpPost httpPost = new HttpPost(skillModel.getUrl() + "/steps");
            String json = gson.toJson(updatedStep);
            StringEntity stringEntity = new StringEntity(json);
            httpPost.setEntity(stringEntity);

            HttpSession session = request.getSession(false);
            int userId = (Integer) session.getAttribute("id");
            SessionStore sessionStore = sessionStoreRepository.findByUserID(userId);

            httpPost.addHeader("userId", String.valueOf(sessionStore.getUserId()));
            httpPost.addHeader("Authorization", String.valueOf(sessionStore.getSessionId()));
            httpPost.addHeader("content-type", "application/json");

            try (CloseableHttpResponse sectionsResponse = httpClient.execute(httpPost)) {
                System.out.println(sectionsResponse.getStatusLine().toString());

                if (sectionsResponse.getStatusLine().getStatusCode() != 201) {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

                    if (sectionsResponse.getStatusLine().getStatusCode() == 500) {
                        errorMessage = "Could not communicate with the course provider";
                    } else if (sectionsResponse.getStatusLine().getStatusCode() == 409) {
                        errorMessage = "There already is a step with this number on the page";
                    }
                }
            } catch (Exception exception) {
                System.out.println(exception.getMessage());
                errorMessage = "Could not communicate with the course provider";
            }
        } else if (request.getParameter("removeResourceButton") != null) {
            int resourceId = Integer.parseInt(request.getParameter("removeResourceButton"));
            int skillId = Integer.parseInt(request.getParameter("skillIdRemoveResourceBox"));
            SkillModel skillModel = skillHibernateRepository.findByID(skillId);

            HttpSession session = request.getSession(false);
            int userId = (Integer) session.getAttribute("id");
            SessionStore sessionStore = sessionStoreRepository.findByUserID(userId);

            HttpDelete httpDelete = new HttpDelete(skillModel.getUrl() + "/resources/" + resourceId);

            httpDelete.addHeader("userId", String.valueOf(sessionStore.getUserId()));
            httpDelete.addHeader("Authorization", String.valueOf(sessionStore.getSessionId()));
            httpDelete.addHeader("content-type", "application/json");

            try (CloseableHttpResponse sectionsResponse = httpClient.execute(httpDelete)) {
                if (sectionsResponse.getStatusLine().getStatusCode() == 500) {
                    errorMessage = "Could not communicate with the course provider";
                }
            } catch (Exception exception) {
                System.out.println(exception.getMessage());
                errorMessage = "Could not communicate with the course provider";
            }
        } else if (request.getParameter("addByIdNewResourceButton") != null) {
            int stepId = Integer.parseInt(request.getParameter("addByIdNewResourceButton"));
            int skillId = Integer.parseInt(request.getParameter("skillIdNewResourceBox" + stepId));
            SkillModel skillModel = skillHibernateRepository.findByID(skillId);

            HttpSession session = request.getSession(false);
            int userId = (Integer) session.getAttribute("id");
            SessionStore sessionStore = sessionStoreRepository.findByUserID(userId);

            int resourceId = Integer.parseInt(request.getParameter("newResourceIdBox" + stepId));

            HttpPatch httpPatch = new HttpPatch(skillModel.getUrl() + "/steps/" + stepId + "/resources");

            PatchBody patchBody = new PatchBody("add", String.valueOf(resourceId), "resources");
            String patchJson = gson.toJson(patchBody);

            StringEntity stringEntity = new StringEntity(patchJson, ContentType.APPLICATION_JSON);
            httpPatch.setEntity(stringEntity);

            httpPatch.addHeader("userId", String.valueOf(sessionStore.getUserId()));
            httpPatch.addHeader("Authorization", String.valueOf(sessionStore.getSessionId()));
            httpPatch.addHeader("content-type", "application/json");

            try (CloseableHttpResponse sectionsResponse = httpClient.execute(httpPatch)) {
                System.out.println(sectionsResponse.getStatusLine().toString());
                if (sectionsResponse.getStatusLine().getStatusCode() == 500) {
                    errorMessage = "Could not communicate with the course provider";
                } else if (sectionsResponse.getStatusLine().getStatusCode() == 409) {
                    errorMessage = "There already is a resource with this ID on the step";
                }
            } catch (Exception exception) {
                System.out.println(exception.getMessage());
                errorMessage = "Could not communicate with the course provider";
            }
            //patch
        } else if (request.getParameter("createNewResourceButton") != null) {
            int stepId = Integer.parseInt(request.getParameter("createNewResourceButton"));
            int skillId = Integer.parseInt(request.getParameter("skillIdNewResourceBox" + stepId));
            SkillModel skillModel = skillHibernateRepository.findByID(skillId);

            String resourceLink = request.getParameter("newResourceLinkBox" + stepId);
            String resourceImageLink = request.getParameter("newResourceImageLinkBox" + stepId);
            String resourceDescription = request.getParameter("newResourceDescriptionBox" + stepId);

            Resource resource = new Resource();
            resource.setResourceLink(resourceLink);
            resource.setResourceDescription(resourceDescription);
            resource.setResourceImageLink(resourceImageLink);

            HttpPost httpPost = new HttpPost(skillModel.getUrl() + "/resources");
            String json = gson.toJson(resource);
            StringEntity stringEntity = new StringEntity(json);
            httpPost.setEntity(stringEntity);

            HttpSession session = request.getSession(false);
            int userId = (Integer) session.getAttribute("id");
            SessionStore sessionStore = sessionStoreRepository.findByUserID(userId);

            httpPost.addHeader("userId", String.valueOf(sessionStore.getUserId()));
            httpPost.addHeader("Authorization", String.valueOf(sessionStore.getSessionId()));
            httpPost.addHeader("content-type", "application/json");

            String bodyOfPostResponse;
            Resource bodyResourceObject;
            int resourceId = 0;

            try (CloseableHttpResponse sectionsResponse = httpClient.execute(httpPost)) {
                System.out.println(sectionsResponse.getStatusLine().toString());

                if (sectionsResponse.getStatusLine().getStatusCode() == 500) {
                    errorMessage = "Could not communicate with the course provider";
                }
                bodyOfPostResponse = EntityUtils.toString(sectionsResponse.getEntity(), StandardCharsets.UTF_8);
                bodyResourceObject = gson.fromJson(bodyOfPostResponse, Resource.class);
                resourceId = bodyResourceObject.getId();

            } catch (Exception exception) {
                System.out.println(exception.getMessage());
                errorMessage = "Could not communicate with the course provider";
            }

            //si acum patch
            HttpPatch httpPatch = new HttpPatch(skillModel.getUrl() + "/steps/" + stepId + "/resources");

            PatchBody patchBody = new PatchBody("add", String.valueOf(resourceId), "resources");
            String patchJson = gson.toJson(patchBody);

            stringEntity = new StringEntity(patchJson, ContentType.APPLICATION_JSON);
            httpPatch.setEntity(stringEntity);

            httpPatch.addHeader("userId", String.valueOf(sessionStore.getUserId()));
            httpPatch.addHeader("Authorization", String.valueOf(sessionStore.getSessionId()));
            httpPatch.addHeader("content-type", "application/json");

            try (CloseableHttpResponse sectionsResponse = httpClient.execute(httpPatch)) {
                System.out.println(sectionsResponse.getStatusLine().toString());

                if (sectionsResponse.getStatusLine().getStatusCode() == 500) {
                    errorMessage = "Could not communicate with the course provider";
                }
            } catch (Exception exception) {
                System.out.println(exception.getMessage());
                errorMessage = "Could not communicate with the course provider";
            }
        } else if (request.getParameter("removeStepButton") != null) {
            int resourceId = Integer.parseInt(request.getParameter("removeStepButton"));
            int skillId = Integer.parseInt(request.getParameter("skillIdRemoveStepBox" + resourceId));
            SkillModel skillModel = skillHibernateRepository.findByID(skillId);

            HttpSession session = request.getSession(false);
            int userId = (Integer) session.getAttribute("id");
            SessionStore sessionStore = sessionStoreRepository.findByUserID(userId);

            HttpDelete httpDelete = new HttpDelete(skillModel.getUrl() + "/steps/" + resourceId);

            httpDelete.addHeader("userId", String.valueOf(sessionStore.getUserId()));
            httpDelete.addHeader("Authorization", String.valueOf(sessionStore.getSessionId()));
            httpDelete.addHeader("content-type", "application/json");

            try (CloseableHttpResponse sectionsResponse = httpClient.execute(httpDelete)) {
                System.out.println(sectionsResponse.getStatusLine().toString());

                if (sectionsResponse.getStatusLine().getStatusCode() != 200) {
                    errorMessage = "Could not communicate with the course provider";
                }
            }
        } else if (request.getParameter("addNewSubsectionButton") != null) {
            int skillId = Integer.parseInt(request.getParameter("subsectionCreateSkillId"));
            SkillModel skillModel = skillHibernateRepository.findByID(skillId);

            String newSubsectionName = request.getParameter("newSubsection");
            int sectionId = Integer.parseInt(request.getParameter("subsectionCreateSectionId"));

            Subsection subsection = new Subsection();
            subsection.setSectionId(sectionId);
            subsection.setName(newSubsectionName);

            HttpPost httpPost = new HttpPost(skillModel.getUrl() + "/subsections");
            String json = gson.toJson(subsection);
            StringEntity stringEntity = new StringEntity(json);
            httpPost.setEntity(stringEntity);

            HttpSession session = request.getSession(false);
            int userId = (Integer) session.getAttribute("id");
            SessionStore sessionStore = sessionStoreRepository.findByUserID(userId);

            httpPost.addHeader("userId", String.valueOf(sessionStore.getUserId()));
            httpPost.addHeader("Authorization", String.valueOf(sessionStore.getSessionId()));
            httpPost.addHeader("content-type", "application/json");

            try (CloseableHttpResponse sectionsResponse = httpClient.execute(httpPost)) {
                System.out.println(sectionsResponse.getStatusLine().toString());
                if (sectionsResponse.getStatusLine().getStatusCode() == 500) {
                    errorMessage = "Could not communicate with the course provider";
                } else if (sectionsResponse.getStatusLine().getStatusCode() == 409) {
                    errorMessage = "There already is a subsection with this name on the course";
                }
            } catch (Exception exception) {
                System.out.println(exception.getMessage());
                errorMessage = "Could not communicate with the course provider";
            }
        }

        if (errorMessage != null); {
            request.getSession(false).setAttribute("errorMessage", errorMessage);
        }
        response.sendRedirect("/skivi_war_exploded/courses/" + request.getPathInfo());
    }

    private List<Resource> getStepResources(int stepId, int userId, SkillModel skillModel) throws InvalidDeploymentException {
        HttpGet articleRequest;

        String skillUrl = skillModel.getUrl();

        if (Configuration.DEPLOYMENT_MODE.equals(DOCKER_DEPLOYMENT)) {
            articleRequest = new HttpGet(skillUrl + "/steps/" + stepId + "/resources");
        } else if (Configuration.DEPLOYMENT_MODE.equals(LOCAL_DEPLOYMENT)) {
            articleRequest = new HttpGet(skillUrl + "/steps/" + stepId + "/resources");
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
        int sectionId = 0;
        int userId = (Integer) session.getAttribute("id");

        request.setAttribute("user", userRepository.findByID(userId));

        String path = request.getPathInfo();

        if (path == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            throw new ServletException("resource not found");
        }

        List<String> pathTraversal = Arrays.asList(path.split("/").clone());

        String sectionName = pathTraversal.get(SECTION);

        int skillId = 1;

        try {
            skillId = Integer.parseInt(pathTraversal.get(SKILL_ID));
        } catch (NumberFormatException numberFormatException) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        UserSkillHibernateRepository userSkillRepository = new UserSkillHibernateRepository();
        if (userSkillRepository.findByUserSkill(userId, skillId) == null) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        SkillModel skillModel = skillHibernateRepository.findByID(skillId);

        HttpGet sectionsGet = new HttpGet(skillModel.getUrl() + "/sections/" + sectionName);
        SessionStore sessionStore = sessionStoreRepository.findByUserID(userId);

        sectionsGet.addHeader("userId", String.valueOf(sessionStore.getUserId()));
        sectionsGet.addHeader("Authorization", String.valueOf(sessionStore.getSessionId()));
        sectionsGet.addHeader("content-type", "application/json");

        try (CloseableHttpResponse sectionsResponse = httpClient.execute(sectionsGet)) {
            if (sectionsResponse.getStatusLine().getStatusCode() != 200) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            HttpEntity entity = sectionsResponse.getEntity();

            String result = EntityUtils.toString(entity);
            Section sectionJson = gson.fromJson(result, Section.class);

            sectionId = sectionJson.getId();
        }

        List<Step> steps = new ArrayList<>();
        List<Subsection> subsectionList = new ArrayList<>();
        String subsectionName = null;
        int subsectionId = 0;

        try {
            subsectionList = getSubsections(sectionName, userId, skillModel, response);
            List<Question> questions = new ArrayList<>();
            List<Answer> answers = new ArrayList<>();

            if (path.matches(ARTICLE_FROM_NAVBAR)) {
                subsectionName = pathTraversal.get(SUBSECTION);

                for (Subsection subsection: subsectionList) {
                    if (subsection.getName().equals(subsectionName)) {
                        subsectionId = subsection.getId();
                    }
                }

                steps = getSteps(subsectionId, userId, skillModel, response).stream().sorted()
                        .collect(Collectors.toList());

                Map<Question, List<Answer>> answerList = new HashMap<>();
                try {
                    questions = getQuestion(subsectionId, userId, skillModel, response)
                            .stream().sorted().collect(Collectors.toList());

                    answers = getAnswers(subsectionId, userId, skillModel, response);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }

                for (Question question: questions) {
                    answerList.put(question, answers.stream()
                            .filter(answer -> answer.getTestId() == question.getId())
                            .collect(Collectors.toList()));

                    if (!answerList.isEmpty()) {
                        request.setAttribute("questionsAnswers", answerList);
                    }
                }
            } else {
                if (subsectionList.size() > 0) {
                    steps = getSteps(subsectionList.get(0).getId(), userId, skillModel, response)
                            .stream().sorted()
                            .collect(Collectors.toList());

                    Map<Question, List<Answer>> answerList = new HashMap<>();
                    try {
                         questions = getQuestion(subsectionList.get(0).getId(), userId, skillModel, response)
                                .stream().sorted().collect(Collectors.toList());

                         answers = getAnswers(subsectionList.get(0).getId(), userId, skillModel, response);
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }

                    for (Question question: questions) {
                        answerList.put(question, answers.stream()
                                .filter(answer -> answer.getTestId() == question.getId())
                                .collect(Collectors.toList()));
                    }

                    if (!answerList.isEmpty()) {
                        request.setAttribute("questionsAnswers", answerList);
                    }
                }
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
                resources = getStepResources(step.getId(), userId, skillModel);
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
            if (subsectionList.size() > 0) {
                request.setAttribute("subsectionName", subsectionList.get(0).getName());
                request.setAttribute("subsectionId", subsectionList.get(0).getId());
                request.setAttribute("sectionId", subsectionList.get(0).getSectionId());
            } else {
                request.setAttribute("subsectionName", sectionName);
                request.setAttribute("subsectionId", 0);
                request.setAttribute("sectionId", 0);
            }
        } else {
            request.setAttribute("subsectionId", subsectionId);
            request.setAttribute("sectionId", subsectionList.get(0).getSectionId());
            request.setAttribute("subsectionName", subsectionName);
        }

        /**
         * Pentru link-urile din aside
         */
        request.setAttribute("subsectionList", subsectionList);
        request.setAttribute("sectionName", sectionName);
        request.setAttribute("sectionId", sectionId);


        /**
         * Componentele propriu-zise ale articolului
         */
        request.setAttribute("stepList", steps);

        /**
         * Ce resurse sunt necesare la un anume pas
         */
        request.setAttribute("stepResourcesMap", stepResources);

        if (request.getSession(false).getAttribute("errorMessage") != null) {
            request.setAttribute("errorMessage", request.getSession(false).getAttribute("errorMessage"));
        }

        /**
         * Numele utilizatorului
         */
        request.setAttribute("userName", userRepository.findByID(userId).getUsername());
        request.setAttribute("courseUrl", skillModel.getUrl());
        request.setAttribute("courseId", skillModel.getId());
        request.setAttribute("subsectionsNr", subsectionList.size());
        request.setAttribute("sessionId", session.getId());
        request.setAttribute("userId", userId);

        getServletContext().getRequestDispatcher("/html/skill-display.jsp").forward(request, response);
    }

    private List<Step> getSteps(int subsectionId, int userId, SkillModel skillModel, HttpServletResponse response) throws InvalidDeploymentException, ServletException, IOException {
        HttpGet articleRequest;

        String skillURL = skillModel.getUrl();

        User user = userRepository.findByID(userId);
        SessionStore sessionStore = sessionStoreRepository.findByUserID(userId);

        if (Configuration.DEPLOYMENT_MODE.equals(DOCKER_DEPLOYMENT)) {
            articleRequest = new HttpGet(skillURL + "/subsections/" + subsectionId + "/steps");
            //articleRequest.setHeader();
        } else if (Configuration.DEPLOYMENT_MODE.equals(LOCAL_DEPLOYMENT)) {
            articleRequest = new HttpGet(skillURL + "/subsections/" + subsectionId + "/steps");
        } else {
            throw new InvalidDeploymentException("Deployment mode is unknown");
        }

        articleRequest.addHeader("userId", String.valueOf(sessionStore.getUserId()));
        articleRequest.addHeader("Authorization", String.valueOf(sessionStore.getSessionId()));
        articleRequest.addHeader("content-type", "application/json");

        try (CloseableHttpResponse articleResponse = httpClient.execute(articleRequest)) {
            System.out.println(articleResponse.getStatusLine().toString());

            if (articleResponse.getStatusLine().getStatusCode() == 404) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                throw new ServletException("subsection not found");
            }

            HttpEntity entity = articleResponse.getEntity();

            String result = EntityUtils.toString(entity);
            JsonArray stepJson = gson.fromJson(result, JsonArray.class);

            List<Step> steps = gson.fromJson(stepJson, new TypeToken<List<Step>>() {}.getType());
            return steps;
        }
    }

    private List<Question> getQuestion(int subsectionId, int userId, SkillModel skillModel, HttpServletResponse response) throws IOException, ServletException, InvalidDeploymentException {
        HttpGet answersRequest;
        String skillURL = skillModel.getUrl();
        List<Question> answers = new ArrayList<>();

        answersRequest = new HttpGet(skillURL + "/subsections/" + subsectionId + "/questions");

        User user = userRepository.findByID(userId);
        SessionStore sessionStore = sessionStoreRepository.findByUserID(userId);

        answersRequest.addHeader("userId", String.valueOf(sessionStore.getUserId()));
        answersRequest.addHeader("Authorization", String.valueOf(sessionStore.getSessionId()));
        answersRequest.addHeader("content-type", "application/json");

        try (CloseableHttpResponse subsectionsResponse = httpClient.execute(answersRequest)) {
            System.out.println(subsectionsResponse.getStatusLine().toString());

            if (subsectionsResponse.getStatusLine().getStatusCode() == 404) {
                return answers;
            }

            HttpEntity entity = subsectionsResponse.getEntity();

            String result = EntityUtils.toString(entity);
            JsonArray subsectionsListJson = gson.fromJson(result, JsonArray.class);

            for (int subsectionIndex = 0;
                 subsectionIndex < subsectionsListJson.size();
                 subsectionIndex++) {
                answers.add(gson.fromJson(subsectionsListJson.get(subsectionIndex), Question.class));
            }

            return answers;
        }
    }

    private List<Answer> getAnswers(int subsectionId, int userId, SkillModel skillModel, HttpServletResponse response) throws IOException, ServletException, InvalidDeploymentException {
        HttpGet answersRequest;
        String skillURL = skillModel.getUrl();
        List<Answer> answers = new ArrayList<>();

        answersRequest = new HttpGet(skillURL + "/subsections/" + subsectionId + "/answers");

        User user = userRepository.findByID(userId);
        SessionStore sessionStore = sessionStoreRepository.findByUserID(userId);

        answersRequest.addHeader("userId", String.valueOf(sessionStore.getUserId()));
        answersRequest.addHeader("Authorization", String.valueOf(sessionStore.getSessionId()));
        answersRequest.addHeader("content-type", "application/json");

        try (CloseableHttpResponse subsectionsResponse = httpClient.execute(answersRequest)) {
            System.out.println(subsectionsResponse.getStatusLine().toString());

            if (subsectionsResponse.getStatusLine().getStatusCode() == 404) {
                return answers;
            }

            HttpEntity entity = subsectionsResponse.getEntity();

            String result = EntityUtils.toString(entity);
            JsonArray subsectionsListJson = gson.fromJson(result, JsonArray.class);

            for (int subsectionIndex = 0;
                 subsectionIndex < subsectionsListJson.size();
                 subsectionIndex++) {
                answers.add(gson.fromJson(subsectionsListJson.get(subsectionIndex), Answer.class));
            }

            return answers;
        }
    }

    private List<Subsection> getSubsections(String sectionName, int userId, SkillModel skillModel, HttpServletResponse response) throws IOException, ServletException, InvalidDeploymentException {
        HttpGet subsectionsRequest;
        String skillURL = skillModel.getUrl();

        if (Configuration.DEPLOYMENT_MODE.equals(DOCKER_DEPLOYMENT)) {
            subsectionsRequest = new HttpGet(skillURL + "/sections/" + sectionName + "/subsections");
        } else if (Configuration.DEPLOYMENT_MODE.equals(LOCAL_DEPLOYMENT)) {
            subsectionsRequest = new HttpGet(skillURL + "/sections/" + sectionName + "/subsections");
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

            if (subsectionsResponse.getStatusLine().getStatusCode() == 404) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                throw new ServletException("subsection ot found");
            }

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
