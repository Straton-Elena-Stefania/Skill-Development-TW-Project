package com.timetraveling.controllers;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.timetraveling.controllers.restclient.SkillDisplayFactory;
import com.timetraveling.models.PatchBody;
import com.timetraveling.models.article.Resource;
import com.timetraveling.models.article.Step;
import com.timetraveling.models.article.Subsection;
import com.timetraveling.models.session.SessionStore;
import com.timetraveling.models.session.SessionStoreHibernateRepository;
import com.timetraveling.models.session.SessionStoreRepository;
import com.timetraveling.models.skillmodel.SkillHibernateRepository;
import com.timetraveling.models.skillmodel.SkillModel;
import com.timetraveling.models.skills.Skill;
import com.timetraveling.models.userskills.UserSkill;
import com.timetraveling.models.userskills.UserSkillHibernateRepository;
import com.timetraveling.utils.async.push.EmailTask;
import com.timetraveling.exceptions.InvalidDeploymentException;
import com.timetraveling.models.article.Section;
import com.timetraveling.models.users.UserRepository;
import com.timetraveling.models.users.UserHibernateRepository;
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

import javax.persistence.criteria.CriteriaBuilder;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@WebServlet(name = "HomepageServlet", value = "/home")
public class HomepageServlet extends HttpServlet implements Communicative {
    private final static int SECTION_NAME_INDEX = 1;
    private final static int COURSE_ID_INDEX = 0;

    private final Gson gson = new Gson();

    private final UserRepository userRepository = new UserHibernateRepository();
    private final UserSkillHibernateRepository userSkillHibernateRepository = new UserSkillHibernateRepository();
    private final CloseableHttpClient httpClient = HttpClients.createDefault();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Cache-Control","no-cache");
        response.setHeader("Cache-Control","no-store");
        response.setHeader("Pragma","no-cache");
        response.setDateHeader ("Expires", 0);

        if (!SessionValidator.isLoggedIn(request)) {
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }

        if (request.getParameter("addNewSectionButton") != null) {
            int skillId = Integer.parseInt(request.getParameter("sectionCreateSkillId"));
            SkillModel skillModel = new SkillHibernateRepository().findByID(skillId);

            String sectionName = request.getParameter("newSectionNameText");
            Section newSection = new Section();
            newSection.setName(sectionName);

            HttpPost httpPost = new HttpPost(skillModel.getUrl() + "/sections");
            String json = gson.toJson(newSection);
            StringEntity stringEntity = new StringEntity(json);
            httpPost.setEntity(stringEntity);

            HttpSession session = request.getSession(false);
            int userId = (Integer) session.getAttribute("id");
            SessionStore sessionStore = new SessionStoreHibernateRepository().findByUserID(userId);

            httpPost.addHeader("userId", String.valueOf(sessionStore.getUserId()));
            httpPost.addHeader("Authorization", String.valueOf(sessionStore.getSessionId()));
            httpPost.addHeader("content-type", "application/json");

            try (CloseableHttpResponse sectionsResponse = httpClient.execute(httpPost)) {
                System.out.println(sectionsResponse.getStatusLine().toString());
            } catch (Exception exception) {
                System.out.println(exception.getMessage());
            }
        }

        response.sendRedirect("/skivi_war_exploded/home");
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (!SessionValidator.isLoggedIn(request)) {
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }

        super.service(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        int userId = (Integer) request.getSession(false).getAttribute("id");

        response.setHeader("Cache-Control","no-cache");
        response.setHeader("Cache-Control","no-store");
        response.setHeader("Pragma","no-cache");
        response.setDateHeader ("Expires", 0);

        if (!SessionValidator.isLoggedIn(request)) {
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }

        String sectionName = null;
        int courseId = 0;
        /**
         * Cuvintele din path
         */
        if (request.getParameter("year") != null) {
            List<String> pathTraversal = Arrays.asList(request.getParameter("year").split("/").clone());

            courseId = Integer.parseInt(pathTraversal.get(COURSE_ID_INDEX));
            sectionName = pathTraversal.get(SECTION_NAME_INDEX);
            int sectionId = Integer.parseInt(request.getParameter("section" + sectionName));
            request.getSession().setAttribute("sectionId", sectionId);
        }

        if (request.getParameter("skillRemovalOption") != null) {
            int skillOption = Integer.parseInt(request.getParameter("skillRemovalOption"));

            UserSkill userSkill = userSkillHibernateRepository.findByUserSkill(userId, skillOption);

            if (userSkill != null) {
                userSkillHibernateRepository.remove(userSkill);
            }
        }

        if (sectionName != null) {
            request.setAttribute("skillId", courseId);
            response.sendRedirect(request.getContextPath() + "/courses/" + courseId + "/" + sectionName);

            return;
        }

        request.setAttribute("user", userRepository.findByID(userId));

        try {
            displaySections(request, response);
        } catch (InvalidDeploymentException deploymentException) {
            deploymentException.printStackTrace();
        }
    }

    private void displaySections(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, InvalidDeploymentException {
        SkillDisplayFactory skillDisplayFactory = new SkillDisplayFactory();

        HttpSession session = request.getSession(false);
        int userId = (Integer) session.getAttribute("id");

        Map<SkillModel, List<Section>> userSkillsByParticipation = skillDisplayFactory.displayModelsForUser(userId, request,
                                                                                                            response);

        request.setAttribute("skillSections", userSkillsByParticipation);
        request.setAttribute("skillSectionsMap", userSkillsByParticipation);

        request.setAttribute("userName", userRepository.findByID(userId).getUsername());
        request.setAttribute("user", userRepository.findByID(userId));
        request.setAttribute("userId", userId);
        request.setAttribute("session", request.getSession(false).getId());

        request.getRequestDispatcher("/welcome.jsp").forward(request, response);
    }
}