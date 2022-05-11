package com.timetraveling.controllers;

import com.google.common.io.ByteStreams;
import com.google.gson.*;
import com.timetraveling.exceptions.DuplicateResourceException;
import com.timetraveling.models.achievement.Achievement;
import com.timetraveling.models.achievement.AchievementRepository;
import com.timetraveling.models.skillmodel.SkillHibernateRepository;
import com.timetraveling.models.skillmodel.SkillModel;
import com.timetraveling.models.skills.SkillStatus;
import com.timetraveling.models.userachievements.UserAchievement;
import com.timetraveling.models.userachievements.UserAchievementRepository;
import com.timetraveling.models.users.User;
import com.timetraveling.models.users.UserHibernateRepository;
import com.timetraveling.models.userskills.UserSkill;
import com.timetraveling.models.userskills.UserSkillHibernateRepository;
import com.timetraveling.utils.presentation.JSONErrorHandler;
import com.timetraveling.utils.presentation.JSONPresenter;
import com.timetraveling.utils.presentation.JSONUserExclusionStrategy;
import com.timetraveling.utils.secure.AuthorizationChecker;
import io.swagger.util.Json;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@MultipartConfig
@WebServlet(name = "UserServlet", urlPatterns={"/users", "/users/*"})
public class UserServlet extends HttpServlet {
    private final static String ID_PART = "(?<id>\\d+)";
    private final static String USER_SKILLS_PATH = "/.*/skills";
    private final static Pattern ID_PATTERN = Pattern.compile("/" + ID_PART);
    private final static String ID_PATH = "/.*";
    private final static Pattern ALL_PATTERN = Pattern.compile("/");
    private final static int USER_ID = 1;

    private UserHibernateRepository userRepository = new UserHibernateRepository();
    private UserSkillHibernateRepository userSkillHibernateRepository = new UserSkillHibernateRepository();
    private AuthorizationChecker authorizationChecker = new AuthorizationChecker();

    private Gson gson = new Gson();
    private Gson userExclusionGson = new GsonBuilder()
                                            .setFieldNamingStrategy(FieldNamingPolicy.IDENTITY)
                                            .setPrettyPrinting()
                                            .addSerializationExclusionStrategy(new JSONUserExclusionStrategy())
                                            .create();

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
            if (!authorizationChecker.checkForAdmin(session, userId)
                    && !authorizationChecker.checkIsSelf(session, userId, id)) {
                JSONErrorHandler.sendError(request, resp, HttpServletResponse.SC_FORBIDDEN);
                return;
            }

            User user = userRepository.findByID(id);

            userRepository.remove(user);
            resp.setStatus(HttpServletResponse.SC_OK);
        }
    }

    protected void doPatch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getPathInfo();
        List<String> pathTraversal = Arrays.asList(path.split("/").clone());

        try {
            String body = request.getReader().lines()
                    .collect(Collectors.joining(System.lineSeparator()));

            //  /steps/{id}
            if (path.matches(ID_PATH)) {
                JsonObject resource = gson.fromJson(body, JsonObject.class);
                int userId = Integer.parseInt(pathTraversal.get(USER_ID));

                /**
                 * Trebuie sa fii admin sau sine insusi
                 */
                int id = request.getIntHeader("userId");
                String session = request.getHeader("Authorization");

                User user = userRepository.findByID(userId);
                if (user == null) {
                    JSONErrorHandler.sendError(request, response, HttpServletResponse.SC_NOT_FOUND);
                    return;
                }

                if (!authorizationChecker.checkForAdmin(session, id)
                        && !authorizationChecker.checkIsSelf(session, id, userId)) {
                    JSONErrorHandler.sendError(request, response, HttpServletResponse.SC_FORBIDDEN);
                    return;
                }

                String operation = resource.get("operation").getAsString();
                String attribute = resource.get("attribute").getAsString();

                if (attribute == null || operation == null) {
                    JSONErrorHandler.sendError(request, response, HttpServletResponse.SC_BAD_REQUEST);
                    return;
                } else if (!attribute.equals("skills") && !attribute.equals("achievements") && !attribute.equals("favorites")) {
                    JSONErrorHandler.sendError(request, response, HttpServletResponse.SC_NOT_IMPLEMENTED);
                    return;
                }

                if (attribute.equals("skills")) {
                    int resourceId = resource.get("value").getAsInt();
                    UserSkill userSkill = new UserSkill();
                    userSkill.setUserId(userId);
                    userSkill.setSkillId(resourceId);

                    SkillModel skillModel = (new SkillHibernateRepository().findByID(resourceId));
                    if (skillModel == null) {
                        JSONErrorHandler.sendError(request, response, HttpServletResponse.SC_NOT_FOUND);
                        return;
                    }

                    if (operation.equals("add")) {
                        try {
                            userSkillHibernateRepository.save(userSkill);
                        } catch (DuplicateResourceException duplicateResourceException) {
                            JSONErrorHandler.sendError(request, response, HttpServletResponse.SC_CONFLICT);
                            return;
                        }
                    } else if (operation.equals("remove")) {
                        userSkillHibernateRepository.remove(userSkill);
                    } else {
                        JSONErrorHandler.sendError(request, response, HttpServletResponse.SC_BAD_REQUEST);
                        return;
                    }
                } else if (attribute.equals("achievements")) {
                    UserAchievementRepository userAchievementRepository = new UserAchievementRepository();
                    AchievementRepository achievementRepository = new AchievementRepository();

                    String achievementTitle = resource.get("value").getAsString();
                    List<String> achievList = Arrays.asList(achievementTitle.split("-"));
                    int skillId = Integer.parseInt(achievList.get(0));
                    Achievement achievement = achievementRepository.findByTitle(achievList.get(achievList.size() - 1));

                    if (achievement == null) {
                        achievement = new Achievement();
                        achievement.setSkillId(skillId);
                        achievement.setTitle(achievList.get(achievList.size() - 1));
                        achievement = achievementRepository.save(achievement);
                    }

                    UserAchievement userAchievement = new UserAchievement();
                    userAchievement.setUserId(userId);
                    userAchievement.setAchievementId(achievement.getId());

                    if (operation.equals("add")) {
                        try {
                            userAchievementRepository.save(userAchievement);
                        } catch (DuplicateResourceException duplicateResourceException) {
                            JSONErrorHandler.sendError(request, response, HttpServletResponse.SC_CONFLICT);
                            return;
                        }
                    } else if (operation.equals("remove")) {
                        userAchievementRepository.remove(userAchievement);
                    } else {
                        JSONErrorHandler.sendError(request, response, HttpServletResponse.SC_BAD_REQUEST);
                        return;
                    }
                } else if (attribute.equals("favorites")) {
                    SkillHibernateRepository skillHibernateRepository = new SkillHibernateRepository();

                    int skillId = resource.get("value").getAsInt();

                    if (skillHibernateRepository.findByID(skillId) == null) {
                        JSONErrorHandler.sendError(request, response, HttpServletResponse.SC_NOT_FOUND);
                        return;
                    }

                    UserSkillHibernateRepository userSkillHibernateRepository = new UserSkillHibernateRepository();

                    UserSkill userSkill = userSkillHibernateRepository.findByUserSkill(userId, skillId);

                    if (userSkill == null) {
                        JSONErrorHandler.sendError(request, response, HttpServletResponse.SC_FORBIDDEN);
                        return;
                    }

                    if (operation.equals("add")) {
                        if (userSkill.isFavorited()) {
                            JSONErrorHandler.sendError(request, response, HttpServletResponse.SC_CONFLICT);
                            return;
                        }

                        userSkill.setFavorited(true);
                        userSkillHibernateRepository.update(userSkill);
                    } else if (operation.equals("remove")) {

                    } else {
                        JSONErrorHandler.sendError(request, response, HttpServletResponse.SC_BAD_REQUEST);
                        return;
                    }
                }

            }  else {
                JSONErrorHandler.sendError(request, response, HttpServletResponse.SC_NOT_FOUND);
                return;
            }
        } catch (NumberFormatException | DuplicateResourceException formatException) {
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

    /**
     * Aceasta metoda face un update partial peste obiecte, luand
     * un user din corpul cererii
     * @param request
     * @param resp
     * @throws IOException
     * @throws ServletException
     */
    protected void doPut(HttpServletRequest request, HttpServletResponse resp) throws IOException, ServletException {
        String xyz = request.getHeader("patch");

        if (xyz != null) {
            doPatch(request, resp);
            return;
        }
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

            if (!authorizationChecker.checkForAdmin(session, userId)
                    && !authorizationChecker.checkIsSelf(session, userId, id)) {
                JSONErrorHandler.sendError(request, resp, HttpServletResponse.SC_FORBIDDEN);
                return;
            }

            User userOfId = userRepository.findByID(id);

            if (userOfId == null) {
                User user = userExclusionGson.fromJson(body, User.class);
                if (user.isAdmin()) {
                    JSONErrorHandler.sendError(request, resp, HttpServletResponse.SC_BAD_REQUEST);
                    return;
                }
                user.setId(id);
                //TODO: VALIDARE
                userRepository.update(user);
                /**
                 * Acum transmitem inapoi userul nou creat
                 */
                resp.setStatus(HttpServletResponse.SC_CREATED);
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                resp.getWriter().write(body);
                return;
            }

            userOfId = userExclusionGson.fromJson(body, User.class);
            if (userOfId.isAdmin()) {
                JSONErrorHandler.sendError(request, resp, HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            userOfId.setId(id);
            userRepository.update(userOfId);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        /**
         * Construim un user nou dupa ceea ce se afla in corpul cererii
         */
        String body = req.getReader().lines()
                .collect(Collectors.joining(System.lineSeparator()));

        User user = userExclusionGson.fromJson(body, User.class);
        if (user.isAdmin()) {
            JSONErrorHandler.sendError(req, resp, HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        //TODO: VALIDARE
        try {
            userRepository.save(user);
        } catch (DuplicateResourceException duplicateResourceException) {
            JSONErrorHandler.sendError(req, resp, HttpServletResponse.SC_CONFLICT);
            return;
        }

        /**
         * Acum transmitem inapoi userul nou creat
         */
        resp.setStatus(HttpServletResponse.SC_CREATED);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(body);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo();

        if (path == null) {
            List<User> users = userRepository.findAll();

            /**
             * Trebuie sa fii admin sau sine insusi
             */
            int userId = req.getIntHeader("userId");
            String session = req.getHeader("Authorization");

            if (!authorizationChecker.checkForAdmin(session, userId)) {
                JSONErrorHandler.sendError(req, resp, HttpServletResponse.SC_FORBIDDEN);
                return;
            }

            JSONPresenter<User> jsonPresenter = new JSONPresenter<>();
            jsonPresenter.sendAsJson(resp, users, userExclusionGson);
            return;
        }

        List<String> pathTraversal = Arrays.asList(path.split("/").clone());

        if (path.matches(USER_SKILLS_PATH)) {
            int userId = Integer.parseInt(pathTraversal.get(USER_ID));
            User user = userRepository.findByID(userId);

            if (user == null) {
                JSONErrorHandler.sendError(req, resp, HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            /**
             * Trebuie sa fii admin sau sine insusi
             */
            int id = req.getIntHeader("userId");
            String session = req.getHeader("Authorization");

            if (!authorizationChecker.checkForAdmin(session, id)
                    && !authorizationChecker.checkIsSelf(session, id, userId)) {
                JSONErrorHandler.sendError(req, resp, HttpServletResponse.SC_FORBIDDEN);
                return;
            }

            List<SkillModel> skillModelList = userSkillHibernateRepository.findByUserId(userId);

            JSONPresenter<List<SkillModel>> jsonPresenter = new JSONPresenter<>();
            jsonPresenter.sendAsJson(resp, skillModelList);
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
                    && !authorizationChecker.checkIsSelf(session, userId, id)) {
                JSONErrorHandler.sendError(req, resp, HttpServletResponse.SC_FORBIDDEN);
                return;
            }

            User user = userRepository.findByID(id);

            if (user == null) {
                JSONErrorHandler.sendError(req, resp, HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            JSONPresenter<User> jsonPresenter = new JSONPresenter<>();
            jsonPresenter.sendAsJson(resp, user, userExclusionGson);
            return;
        }
    }

    private Optional<User> getUserFromPattern(Pattern pattern, HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String path = req.getPathInfo();
        Matcher matcher = pattern.matcher(path);

        if (!matcher.matches()) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            throw new ServletException("User not found");
        }

        int id = Integer.parseInt(matcher.group("id"));
        User user = userRepository.findByID(id);

        Object sessionId = req.getSession().getAttribute("id");
        if (user == null || sessionId == null || (int) sessionId != id) {
            resp.sendRedirect(req.getContextPath() + "/");
            return Optional.empty();
        }
        return Optional.of(user);
    }
}