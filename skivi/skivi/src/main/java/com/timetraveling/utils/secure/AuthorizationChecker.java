package com.timetraveling.utils.secure;

import com.timetraveling.models.session.SessionStore;
import com.timetraveling.models.session.SessionStoreHibernateRepository;
import com.timetraveling.models.session.SessionStoreRepository;
import com.timetraveling.models.skillmodel.SkillModel;
import com.timetraveling.models.users.User;
import com.timetraveling.models.users.UserHibernateRepository;
import com.timetraveling.models.users.UserRepository;
import com.timetraveling.models.userskills.UserSkillHibernateRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.stream.Collectors;

public class AuthorizationChecker {
    private final UserRepository userRepository = new UserHibernateRepository();
    private SessionStoreRepository sessionStoreRepository = new SessionStoreHibernateRepository();
    private UserSkillHibernateRepository userSkillHibernateRepository = new UserSkillHibernateRepository();

    public boolean checkForAdmin(String session, int userId) {
        SessionStore sessionStore = sessionStoreRepository.findByUserID(userId);
        User user = userRepository.findByID(userId);

        return sessionStore != null
                && sessionStore.getSessionId().equals(session)
                && user.isAdmin();
    }

    public boolean checkForEnrolled(String session, int userId, String role) {
        SessionStore sessionStore = sessionStoreRepository.findByUserID(userId);
        User user = userRepository.findByID(userId);
        List<SkillModel> skillModelList = userSkillHibernateRepository.findByUserId(userId);
        List<String> skillNamesList = skillModelList.stream().map(SkillModel::getName)
                .collect(Collectors.toList());

        return sessionStore != null
                && sessionStore.getSessionId().equals(session)
                && (skillNamesList.contains(role)
                || user.isAdmin());
    }

    public boolean checkForEnrolled(String session, int userId, int skillId) {
        SessionStore sessionStore = sessionStoreRepository.findByUserID(userId);
        User user = userRepository.findByID(userId);
        List<SkillModel> skillModelList = userSkillHibernateRepository.findByUserId(userId);
        List<Integer> skillNamesList = skillModelList.stream().map(SkillModel::getId)
                .collect(Collectors.toList());

        return sessionStore != null
                && sessionStore.getSessionId().equals(session)
                && (skillNamesList.contains(skillId)
                || user.isAdmin());
    }

    public boolean checkIsSelf(String session, int userId, int testUserId) {
        SessionStore sessionStore = sessionStoreRepository.findByUserID(userId);
        SessionStore otherSessionStore = sessionStoreRepository.findByUserID(testUserId);

        return sessionStore != null && otherSessionStore != null
                && sessionStore.getSessionId().equals(session);
    }
}
