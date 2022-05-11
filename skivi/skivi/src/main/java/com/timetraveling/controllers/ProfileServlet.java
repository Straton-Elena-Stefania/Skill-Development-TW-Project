package com.timetraveling.controllers;

import com.google.common.io.ByteStreams;
import com.timetraveling.exceptions.DuplicateResourceException;
import com.timetraveling.exceptions.InvalidSkillException;
import com.timetraveling.models.achievement.Achievement;
import com.timetraveling.models.skillmodel.SkillHibernateRepository;
import com.timetraveling.models.skillmodel.SkillModel;
import com.timetraveling.models.skills.SkillManipulation;
import com.timetraveling.models.skills.SkillStatus;
import com.timetraveling.models.userachievements.UserAchievementRepository;
import com.timetraveling.models.users.User;
import com.timetraveling.models.users.UserHibernateRepository;
import com.timetraveling.models.users.UserRepository;
import com.timetraveling.models.userskills.UserSkill;
import com.timetraveling.models.userskills.UserSkillHibernateRepository;
import com.timetraveling.utils.validation.SessionValidator;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@MultipartConfig
@WebServlet(name = "ProfileServlet", value = "/profile")
public class ProfileServlet extends HttpServlet implements SkillManipulation {
    private final UserRepository userRepository = new UserHibernateRepository();
    private final SkillHibernateRepository skillHibernateRepository = new SkillHibernateRepository();
    private final UserSkillHibernateRepository userSkillHibernateRepository = new UserSkillHibernateRepository();

    private void profilePictureUpdate(HttpServletRequest request, HttpServletResponse response, User user) throws ServletException, IOException {
        Part file = request.getPart("file");

        if (file == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        byte[] profilePicture = ByteStreams.toByteArray(file.getInputStream());
        user.setProfilePictureType(file.getContentType());
        user.setProfilePicture(profilePicture);

        userRepository.update(user);
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!SessionValidator.isLoggedIn(request)) {
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }

        super.service(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = (Integer) request.getSession(false).getAttribute("id");
        User user = userRepository.findByID(userId);

        if (request.getParameter("submitPicture") != null) {
            profilePictureUpdate(request, response, user);
        }

        if (request.getParameter("skillAddButton") != null) {
            SkillModel skillModel = skillHibernateRepository.findByName(request.getParameter("skillModel"));

            if (skillModel == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }

            int skillToAdd = skillModel.getId();
            UserSkill userSkill = new UserSkill();
            userSkill.setUserId(userId);
            userSkill.setSkillId(skillToAdd);
            try {
                userSkillHibernateRepository.save(userSkill);
            } catch (DuplicateResourceException duplicateResourceException) {
                duplicateResourceException.printStackTrace();
            }

            userRepository.update(user);
        }

        UserAchievementRepository userAchievementRepository = new UserAchievementRepository();

        List<SkillModel> skillModelList = skillHibernateRepository.findAll();
        List<Achievement> achievements = userAchievementRepository.findByUserId(userId);
        List<UserSkill> userSkills = userSkillHibernateRepository.findAll();
        List<SkillModel> favouritedSkills = new ArrayList<>();

        for (UserSkill userSkill: userSkills) {
            if (userSkill.isFavorited() && userSkill.getUserId() == userId) {
                favouritedSkills.add(skillHibernateRepository.findByID(userSkill.getSkillId()));
            }
        }

        request.setAttribute("favoritedSkills", favouritedSkills);
        request.setAttribute("userName", userRepository.findByID(userId).getUsername());
        request.setAttribute("userAchievements", userAchievementRepository.findByUserId(userId));
        request.setAttribute("userName", userRepository.findByID(userId).getUsername());
        request.setAttribute("userSkills", userSkillHibernateRepository.findByUserId(userId));
        request.setAttribute("skills", skillModelList);

        getServletContext().getRequestDispatcher("/html/usermanagement/profile.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        int userId = (Integer) session.getAttribute("id");

        if (!SessionValidator.isLoggedIn(request)) {
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }

        UserAchievementRepository userAchievementRepository = new UserAchievementRepository();

        List<SkillModel> skillModelList = skillHibernateRepository.findAll();
        List<UserSkill> userSkills = userSkillHibernateRepository.findAll();
        List<SkillModel> favouritedSkills = new ArrayList<>();

        for (UserSkill userSkill: userSkills) {
            if (userSkill.isFavorited() && userSkill.getUserId() == userId) {
                favouritedSkills.add(skillHibernateRepository.findByID(userSkill.getSkillId()));
            }
        }

        request.setAttribute("favoritedSkills", favouritedSkills);

        response.setHeader("Cache-Control","no-cache");
        response.setHeader("Cache-Control","no-store");
        response.setHeader("Pragma","no-cache");
        response.setDateHeader ("Expires", 0);

        request.setAttribute("userAchievements", userAchievementRepository.findByUserId(userId));
        request.setAttribute("userName", userRepository.findByID(userId).getUsername());
        request.setAttribute("userSkills", userSkillHibernateRepository.findByUserId(userId));
        request.setAttribute("skills", skillModelList);

        getServletContext().getRequestDispatcher("/html/usermanagement/profile.jsp").forward(request, response);
    }
}
