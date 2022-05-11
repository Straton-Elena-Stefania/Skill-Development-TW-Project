package com.timetraveling.controllers;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.timetraveling.exceptions.DuplicateResourceException;
import com.timetraveling.models.skillmodel.SkillHibernateRepository;
import com.timetraveling.models.skillmodel.SkillModel;
import com.timetraveling.models.skills.Skill;
import com.timetraveling.models.users.User;
import com.timetraveling.models.users.UserHibernateRepository;
import com.timetraveling.models.userskills.UserSkill;
import com.timetraveling.models.userskills.UserSkillHibernateRepository;
import com.timetraveling.utils.presentation.JSONErrorHandler;
import com.timetraveling.utils.presentation.JSONPresenter;
import com.timetraveling.utils.secure.AuthorizationChecker;
import com.timetraveling.utils.validation.SessionValidator;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@MultipartConfig
@WebServlet(name = "SkillServlet", urlPatterns = {"/skills", "/skills/*"})
public class SkillServlet extends HttpServlet {
    private final static String ID_PART = "(?<id>\\d+)";
    private final static String USER_SKILLS_PATH = "/.*/users";
    private final static Pattern ID_PATTERN = Pattern.compile("/" + ID_PART);
    private final static String ID_PATH = "/.*";
    private final static Pattern ALL_PATTERN = Pattern.compile("/");
    private final static int SKILL_ID = 1;

    private UserHibernateRepository userRepository = new UserHibernateRepository();
    private UserSkillHibernateRepository userSkillHibernateRepository = new UserSkillHibernateRepository();
    private SkillHibernateRepository skillRepository = new SkillHibernateRepository();
    private AuthorizationChecker authorizationChecker = new AuthorizationChecker();

    private Gson gson = new Gson();

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse resp) throws IOException, ServletException {
        int userId = request.getIntHeader("userId");
        String session = request.getHeader("Authorization");

        String path = request.getPathInfo();

        Matcher matcher = ID_PATTERN.matcher(path);

        if (matcher.matches()) {
            int id = Integer.parseInt(matcher.group("id"));

            /**
             * Trebuie sa fii admin sau sine insusi
             */
            if (!authorizationChecker.checkForAdmin(session, userId)) {
                JSONErrorHandler.sendError(request, resp, HttpServletResponse.SC_FORBIDDEN);
                return;
            }

            SkillModel skillModel = skillRepository.findByID(id);

            skillRepository.remove(skillModel);
            resp.setStatus(HttpServletResponse.SC_OK);
        }
    }

    protected void doPatch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getPathInfo();
        List<String> pathTraversal = Arrays.asList(path.split("/").clone());

        try {
            String body = request.getReader().lines()
                    .collect(Collectors.joining(System.lineSeparator()));

            //  /skills/{id}
            if (path.matches(ID_PATH)) {
                JsonObject resource = gson.fromJson(body, JsonObject.class);
                int skillId = Integer.parseInt(pathTraversal.get(SKILL_ID));

                /**
                 * Trebuie sa fii admin
                 */
                int id = request.getIntHeader("userId");
                String session = request.getHeader("Authorization");

                if (!authorizationChecker.checkForAdmin(session, id)) {
                    JSONErrorHandler.sendError(request, response, HttpServletResponse.SC_FORBIDDEN);
                    return;
                }

                int resourceId = resource.get("value").getAsInt();
                String operation = resource.get("operation").getAsString();
                String attribute = resource.get("attribute").getAsString();

                if (attribute == null || operation == null) {
                    JSONErrorHandler.sendError(request, response, HttpServletResponse.SC_BAD_REQUEST);
                    return;
                } else if (!attribute.equals("users")) {
                    JSONErrorHandler.sendError(request, response, HttpServletResponse.SC_NOT_IMPLEMENTED);
                    return;
                }

                if (operation.equals("add")) {
                    try {
                        UserSkill userSkill = new UserSkill();
                        userSkill.setUserId(resourceId);
                        userSkill.setSkillId(skillId);
                        userSkillHibernateRepository.save(userSkill);

                        /**
                         * Trimitem inapoi ceea ce s-a postat
                         */
                        response.setContentType("application/json");
                        response.setCharacterEncoding("UTF-8");
                        response.getWriter().write(body);
                    } catch (DuplicateResourceException duplicateResourceException) {
                        JSONErrorHandler.sendError(request, response, HttpServletResponse.SC_CONFLICT);
                        return;
                    }
                } else {
                    JSONErrorHandler.sendError(request, response, HttpServletResponse.SC_NOT_IMPLEMENTED);
                    return;
                }
            } else {
                JSONErrorHandler.sendError(request, response, HttpServletResponse.SC_NOT_FOUND);
                return;
            }
        } catch (NumberFormatException formatException) {
            JSONErrorHandler.sendError(request, response, HttpServletResponse.SC_NOT_FOUND);
            return;
        }
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if ("PATCH".equals(request.getMethod())) {
            doPatch(request, response);
            return;
        }

        super.service(request, response);
    }

    protected void doPut(HttpServletRequest request, HttpServletResponse resp) throws IOException, ServletException {
        /**
         * Metoda put presupune sa ai corpul resursei in body.
         * Si daca userul cu acel id nu exista, atunci este creat unul.
         * Sunt returnate status codes (de exemplu 201 created si 200 daca s-a modificat)
         */
        String body = request.getReader().lines()
                .collect(Collectors.joining(System.lineSeparator()));

        String path = request.getPathInfo();

        Matcher matcher = ID_PATTERN.matcher(path);

        if (matcher.matches()) {
            int id = Integer.parseInt(matcher.group("id"));

            /**
             * Trebuie sa fii admin sau sine insusi
             */
            int userId = request.getIntHeader("userId");
            String session = request.getHeader("Authorization");

            if (!authorizationChecker.checkForAdmin(session, userId)) {
                JSONErrorHandler.sendError(request, resp, HttpServletResponse.SC_FORBIDDEN);
                return;
            }

            SkillModel skillOfId = skillRepository.findByID(id);

            if (skillOfId == null) {
                resp.setStatus(HttpServletResponse.SC_CREATED);
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                resp.getWriter().write(body);
            }

            skillOfId = gson.fromJson(body, SkillModel.class);
            skillOfId.setId(id);
            skillRepository.update(skillOfId);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        /**
         * Construim un user nou dupa ceea ce se afla in corpul cererii
         */
        String body = req.getReader().lines()
                .collect(Collectors.joining(System.lineSeparator()));

        /**
         * Trebuie sa fii admin sau sine insusi
         */
        int userId = req.getIntHeader("userId");
        String session = req.getHeader("Authorization");

        if (!authorizationChecker.checkForAdmin(session, userId)) {
            JSONErrorHandler.sendError(req, resp, HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        SkillModel skillModel = gson.fromJson(body, SkillModel.class);
        //TODO: VALIDARE
        try {
            skillRepository.save(skillModel);
        } catch (DuplicateResourceException duplicateResourceException) {
            duplicateResourceException.printStackTrace();
        }

        /**
         * Acum transmitem inapoi userul nou creat
         */
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(body);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo();

        if (path == null) {
            List<SkillModel> skills = skillRepository.findAll();

            /**
             * Trebuie sa fii admin sau sine insusi
             */
            int userId = req.getIntHeader("userId");
            String session = req.getHeader("Authorization");

            if (!authorizationChecker.checkForAdmin(session, userId)) {
                JSONErrorHandler.sendError(req, resp, HttpServletResponse.SC_FORBIDDEN);
                return;
            }

            JSONPresenter<SkillModel> jsonPresenter = new JSONPresenter<>();
            jsonPresenter.sendAsJson(resp, skills);
            return;
        }

        List<String> pathTraversal = Arrays.asList(path.split("/").clone());

        if (path.matches(USER_SKILLS_PATH)) {
            int skillId = Integer.parseInt(pathTraversal.get(SKILL_ID));

            /**
             * Trebuie sa fii admin sau sine insusi
             */
            int id = req.getIntHeader("userId");
            String session = req.getHeader("Authorization");

            if (!authorizationChecker.checkForAdmin(session, id)) {
                JSONErrorHandler.sendError(req, resp, HttpServletResponse.SC_FORBIDDEN);
                return;
            }

            List<User> userList = userSkillHibernateRepository.findBySkillId(skillId);

            JSONPresenter<List<User>> jsonPresenter = new JSONPresenter<>();
            jsonPresenter.sendAsJson(resp, userList);
            return;
        }

        Matcher matcher = ID_PATTERN.matcher(path);

        if (matcher.matches()) {
            int id = Integer.parseInt(matcher.group("id"));

            /**
             * Trebuie sa fii admin sau sine insusi
             */
            int userId = req.getIntHeader("userId");
            String session = req.getHeader("Authorization");

            if (!authorizationChecker.checkForAdmin(session, userId)
                    && !authorizationChecker.checkForEnrolled(session, userId, id)) {
                JSONErrorHandler.sendError(req, resp, HttpServletResponse.SC_FORBIDDEN);
                return;
            }

            SkillModel skillModel = skillRepository.findByID(id);

            if (skillModel == null) {
                JSONErrorHandler.sendError(req, resp, HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            JSONPresenter<SkillModel> jsonPresenter = new JSONPresenter<>();
            jsonPresenter.sendAsJson(resp, skillModel);
            return;
        }
    }
}
