package com.timetraveling.models.userskills;

import com.google.gson.Gson;
import com.timetraveling.exceptions.AlreadyExistsException;
import com.timetraveling.exceptions.DuplicateResourceException;
import com.timetraveling.models.management.EntityManagerFactoryHandle;
import com.timetraveling.models.skillmodel.SkillModel;
import com.timetraveling.models.skillmodel.SkillHibernateRepository;
import com.timetraveling.models.users.User;
import com.timetraveling.models.users.UserHibernateRepository;
import org.hibernate.exception.ConstraintViolationException;

import javax.persistence.*;
import java.util.*;

public class UserSkillHibernateRepository {
    public List<UserSkill> findAll() {
        EntityManagerFactory entityManagerFactory = EntityManagerFactoryHandle.getInstance()
                .getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getMetamodel().entity(UserSkill.class);

        return entityManager.createQuery("SELECT a FROM UserSkill a", UserSkill.class).getResultList();
    }

    public List<User> findBySkillId(int id) {
        EntityManagerFactory entityManagerFactory = EntityManagerFactoryHandle.getInstance()
                .getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Gson gson = new Gson();
        UserHibernateRepository resourceRepository = new UserHibernateRepository();
        List<User> resources = new ArrayList<>();

        List<UserSkill> resultList = entityManager.createNamedQuery("UserSkill.findBySkillId")
                .setParameter("skillId", id).getResultList();

        for (UserSkill stepResource: resultList) {
            User resource = resourceRepository.findByID(stepResource.getUserId());
            resources.add(resource);
        }

        return resources;
    }

    public UserSkill findByUserSkill(int userId, int skillId) {
        EntityManagerFactory entityManagerFactory = EntityManagerFactoryHandle.getInstance()
                .getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Gson gson = new Gson();
        UserHibernateRepository resourceRepository = new UserHibernateRepository();
        List<User> resources = new ArrayList<>();

        /**
         * Cod foarte prost
         */

        Object results = (ArrayList<UserSkill>) entityManager.createNamedQuery("UserSkill.findByUserSkill")
                .setParameter("skillId", skillId).setParameter("userId", userId).getResultList();

        if (((List) results).size() > 0) {
            UserSkill result = (UserSkill) ((List) results).get(0);
            List<UserSkill> userSkills = new ArrayList<>();

            return result;
        }

        return null;
    }

    public void remove(UserSkill sessionStore) {
        EntityManagerFactory entityManagerFactory = EntityManagerFactoryHandle.getInstance()
                .getEntityManagerFactory();
        EntityManager em = entityManagerFactory.createEntityManager();

        em.getTransaction().begin();
        em.remove(em.contains(sessionStore) ? sessionStore : em.merge(sessionStore));

        em.getTransaction().commit();
        em.close();
    }

    public List<SkillModel> findByUserId(int id) {
        EntityManagerFactory entityManagerFactory = EntityManagerFactoryHandle.getInstance()
                .getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Gson gson = new Gson();
        SkillHibernateRepository resourceRepository = new SkillHibernateRepository();
        List<SkillModel> resources = new ArrayList<>();

        List<UserSkill> resultList = entityManager.createNamedQuery("UserSkill.findByUserId")
                .setParameter("userId", id).getResultList();

        for (UserSkill stepResource: resultList) {
            SkillModel resource = resourceRepository.findByID(stepResource.getSkillId());
            resources.add(resource);
        }

        return resources;
    }

    /**
     * Clasa este un wrapper pentru actiunea de a persista un obiect
     * ceea ce include si realizarea unei tranzactii.
     * @param model Entitatea salvata
     */
    public UserSkill save(UserSkill model) throws DuplicateResourceException {
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

    public void update(UserSkill userSkill) throws AlreadyExistsException {
        EntityManagerFactory entityManagerFactory = EntityManagerFactoryHandle.getInstance()
                .getEntityManagerFactory();
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();

        try {
            em.merge(userSkill);
            em.getTransaction().commit();
        } catch (ConstraintViolationException violationException) {
            throw new AlreadyExistsException("This resource is already taken");
        } catch (PersistenceException persistenceException) {
            System.err.println(persistenceException.getMessage());
            em.getTransaction().rollback();
        }
    }
}
