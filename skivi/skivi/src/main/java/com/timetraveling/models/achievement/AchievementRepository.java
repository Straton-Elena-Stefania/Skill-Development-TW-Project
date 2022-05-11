package com.timetraveling.models.achievement;

import com.timetraveling.exceptions.AlreadyExistsException;
import com.timetraveling.exceptions.DuplicateResourceException;
import com.timetraveling.models.management.EntityManagerFactoryHandle;
import com.timetraveling.models.skillmodel.SkillModel;
import org.hibernate.exception.ConstraintViolationException;

import javax.persistence.*;
import javax.persistence.criteria.AbstractQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class AchievementRepository {
    public List<Achievement> findAll() {
        EntityManagerFactory entityManagerFactory = EntityManagerFactoryHandle.getInstance()
                .getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getMetamodel().entity(Achievement.class);

        return entityManager.createQuery("SELECT a FROM Achievement a", Achievement.class).getResultList();
    }

    public Achievement findByTitle(String name) {
        EntityManagerFactory entityManagerFactory = EntityManagerFactoryHandle.getInstance()
                .getEntityManagerFactory();

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getMetamodel().entity(Achievement.class);

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        AbstractQuery<Achievement> abstractQuery = criteriaBuilder.createQuery(Achievement.class);

        Root<Achievement> root = abstractQuery.from(Achievement.class);

        abstractQuery.where(criteriaBuilder.equal(root.get("title"), name));

        CriteriaQuery<Achievement> select = ((CriteriaQuery<Achievement>) abstractQuery).select(root);
        TypedQuery<Achievement> typedQuery = entityManager.createQuery(select);
        try {
            return typedQuery.getSingleResult();
        } catch (NoResultException noResultException) {
            System.err.println("Nu am gasit numele in baza de date");
            return null;
        }
    }

    /*public void remove(SkillModel sessionStore) {
        EntityManagerFactory entityManagerFactory = EntityManagerFactoryHandle.getInstance()
                .getEntityManagerFactory();
        EntityManager em = entityManagerFactory.createEntityManager();

        em.getTransaction().begin();
        em.remove(em.contains(sessionStore) ? sessionStore : em.merge(sessionStore));

        em.getTransaction().commit();
        em.close();
    }*/

    public Achievement findByID(int id) {
        EntityManagerFactory entityManagerFactory = EntityManagerFactoryHandle.getInstance()
                .getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getMetamodel().entity(Achievement.class);

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        AbstractQuery<Achievement> abstractQuery = criteriaBuilder.createQuery(Achievement.class);

        Root<Achievement> skill = abstractQuery.from(Achievement.class);

        abstractQuery.where(criteriaBuilder.equal(skill.get("id"), id));

        CriteriaQuery<Achievement> select = ((CriteriaQuery<Achievement>) abstractQuery).select(skill);
        TypedQuery<Achievement> typedQuery = entityManager.createQuery(select);
        try {
            return typedQuery.getSingleResult();
        } catch (NoResultException noResultException) {
            return null;
        }
    }

    public Achievement save(Achievement achievement) throws DuplicateResourceException {
        EntityManagerFactory entityManagerFactory = EntityManagerFactoryHandle.getInstance()
                .getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();
        try {
            entityManager.persist(achievement);
            entityManager.getTransaction().commit();
        } catch (PersistenceException constraintViolationException) {
            entityManager.getTransaction().rollback();
            throw new DuplicateResourceException("This resource already exists");
        }

        return achievement;
    }

    public void update(Achievement achievement) throws AlreadyExistsException {
        EntityManagerFactory entityManagerFactory = EntityManagerFactoryHandle.getInstance()
                .getEntityManagerFactory();
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();

        try {
            em.merge(achievement);
            em.getTransaction().commit();
        } catch (ConstraintViolationException violationException) {
            throw new AlreadyExistsException("This resource is already taken");
        } catch (PersistenceException persistenceException) {
            System.err.println(persistenceException.getMessage());
            em.getTransaction().rollback();
        }
    }
}
