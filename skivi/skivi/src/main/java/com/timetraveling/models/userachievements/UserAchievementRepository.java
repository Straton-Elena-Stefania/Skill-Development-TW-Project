package com.timetraveling.models.userachievements;


import com.google.gson.Gson;
import com.timetraveling.exceptions.AlreadyExistsException;
import com.timetraveling.exceptions.DuplicateResourceException;
import com.timetraveling.models.achievement.Achievement;
import com.timetraveling.models.achievement.AchievementRepository;
import com.timetraveling.models.management.EntityManagerFactoryHandle;
import com.timetraveling.models.skillmodel.SkillHibernateRepository;
import com.timetraveling.models.skillmodel.SkillModel;
import com.timetraveling.models.users.User;
import com.timetraveling.models.users.UserHibernateRepository;
import com.timetraveling.models.userskills.UserSkill;
import org.hibernate.exception.ConstraintViolationException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.List;

public class UserAchievementRepository {
    public List<UserAchievement> findAll() {
        EntityManagerFactory entityManagerFactory = EntityManagerFactoryHandle.getInstance()
                .getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getMetamodel().entity(UserAchievement.class);

        return entityManager.createQuery("SELECT a FROM UserAchievement a", UserAchievement.class).getResultList();
    }

    public UserAchievement findByUserAchievement(int userId, int achievementId) {
        EntityManagerFactory entityManagerFactory = EntityManagerFactoryHandle.getInstance()
                .getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Gson gson = new Gson();
        UserHibernateRepository resourceRepository = new UserHibernateRepository();
        List<User> resources = new ArrayList<>();

        /**
         * Cod foarte prost
         */

        Object results = (ArrayList<UserSkill>) entityManager.createNamedQuery("UserAchievement.findByUserAchievement")
                .setParameter("achievementId", achievementId).setParameter("userId", userId).getResultList();

        if (((List) results).size() > 0) {
            UserAchievement result = (UserAchievement) ((List) results).get(0);
            List<UserAchievement> userAchievements = new ArrayList<>();

            return result;
        }

        return null;
    }

    public void remove(UserAchievement sessionStore) {
        EntityManagerFactory entityManagerFactory = EntityManagerFactoryHandle.getInstance()
                .getEntityManagerFactory();
        EntityManager em = entityManagerFactory.createEntityManager();

        em.getTransaction().begin();
        em.remove(em.contains(sessionStore) ? sessionStore : em.merge(sessionStore));

        em.getTransaction().commit();
        em.close();
    }

    public List<Achievement> findByUserId(int id) {
        EntityManagerFactory entityManagerFactory = EntityManagerFactoryHandle.getInstance()
                .getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Gson gson = new Gson();
        AchievementRepository resourceRepository = new AchievementRepository();
        List<Achievement> achievements = new ArrayList<>();

        List<UserAchievement> resultList = entityManager.createNamedQuery("UserAchievement.findByUserId")
                .setParameter("userId", id).getResultList();

        for (UserAchievement userAchievement: resultList) {
            Achievement resource = resourceRepository.findByID(userAchievement.getAchievementId());
            achievements.add(resource);
        }

        return achievements;
    }

    /**
     * Clasa este un wrapper pentru actiunea de a persista un obiect
     * ceea ce include si realizarea unei tranzactii.
     * @param model Entitatea salvata
     */
    public UserAchievement save(UserAchievement model) throws DuplicateResourceException {
        EntityManagerFactory entityManagerFactory = EntityManagerFactoryHandle.getInstance()
                .getEntityManagerFactory();
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();

        try {
            em.persist(model);
            em.getTransaction().commit();
        } catch (PersistenceException constraintViolationException) {
            em.getTransaction().rollback();
            throw new DuplicateResourceException("This resource already exists");
        }

        return model;
    }

    public void update(UserAchievement userAchievement) throws AlreadyExistsException {
        EntityManagerFactory entityManagerFactory = EntityManagerFactoryHandle.getInstance()
                .getEntityManagerFactory();
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();

        try {
            em.merge(userAchievement);
            em.getTransaction().commit();
        } catch (ConstraintViolationException violationException) {
            throw new AlreadyExistsException("This resource is already taken");
        } catch (PersistenceException persistenceException) {
            System.err.println(persistenceException.getMessage());
            em.getTransaction().rollback();
        }
    }
}
