package com.timetraveling.models.skillmodel;

import com.timetraveling.exceptions.AlreadyExistsException;
import com.timetraveling.exceptions.DuplicateResourceException;
import com.timetraveling.models.management.EntityManagerFactoryHandle;
import com.timetraveling.models.users.User;
import org.hibernate.exception.ConstraintViolationException;

import javax.persistence.*;
import javax.persistence.criteria.AbstractQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class SkillHibernateRepository {
    public List<SkillModel> findAll() {
        EntityManagerFactory entityManagerFactory = EntityManagerFactoryHandle.getInstance()
                .getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getMetamodel().entity(SkillModel.class);

        return entityManager.createQuery("SELECT a FROM SkillModel a", SkillModel.class).getResultList();
    }

    public SkillModel findByName(String name) {
        EntityManagerFactory entityManagerFactory = EntityManagerFactoryHandle.getInstance()
                .getEntityManagerFactory();

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getMetamodel().entity(SkillModel.class);

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        AbstractQuery<SkillModel> abstractQuery = criteriaBuilder.createQuery(SkillModel.class);

        Root<SkillModel> skillModelRoot = abstractQuery.from(SkillModel.class);

        abstractQuery.where(criteriaBuilder.equal(skillModelRoot.get("name"), name));

        CriteriaQuery<SkillModel> select = ((CriteriaQuery<SkillModel>) abstractQuery).select(skillModelRoot);
        TypedQuery<SkillModel> typedQuery = entityManager.createQuery(select);
        try {
            return typedQuery.getSingleResult();
        } catch (NoResultException noResultException) {
            System.err.println("Nu am gasit numele in baza de date");
            return null;
        }
    }

    public void remove(SkillModel sessionStore) {
        EntityManagerFactory entityManagerFactory = EntityManagerFactoryHandle.getInstance()
                .getEntityManagerFactory();
        EntityManager em = entityManagerFactory.createEntityManager();

        em.getTransaction().begin();
        em.remove(em.contains(sessionStore) ? sessionStore : em.merge(sessionStore));

        em.getTransaction().commit();
        em.close();
    }

    public SkillModel findByID(int id) {
        EntityManagerFactory entityManagerFactory = EntityManagerFactoryHandle.getInstance()
                .getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getMetamodel().entity(SkillModel.class);

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        AbstractQuery<SkillModel> abstractQuery = criteriaBuilder.createQuery(SkillModel.class);

        Root<SkillModel> skill = abstractQuery.from(SkillModel.class);

        abstractQuery.where(criteriaBuilder.equal(skill.get("id"), id));

        CriteriaQuery<SkillModel> select = ((CriteriaQuery<SkillModel>) abstractQuery).select(skill);
        TypedQuery<SkillModel> typedQuery = entityManager.createQuery(select);
        try {
            return typedQuery.getSingleResult();
        } catch (NoResultException noResultException) {
            return null;
        }
    }

    public void save(SkillModel skillModel) throws DuplicateResourceException {
        EntityManagerFactory entityManagerFactory = EntityManagerFactoryHandle.getInstance()
                .getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();
        try {
            entityManager.persist(skillModel);
            entityManager.getTransaction().commit();
        } catch (PersistenceException constraintViolationException) {
            entityManager.getTransaction().rollback();
            throw new DuplicateResourceException("This resource already exists");
        }
    }

    public void update(SkillModel skillModel) throws AlreadyExistsException {
        EntityManagerFactory entityManagerFactory = EntityManagerFactoryHandle.getInstance()
                .getEntityManagerFactory();
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();

        try {
            em.merge(skillModel);
            em.getTransaction().commit();
        } catch (ConstraintViolationException violationException) {
            throw new AlreadyExistsException("This resource is already taken");
        } catch (PersistenceException persistenceException) {
            System.err.println(persistenceException.getMessage());
            em.getTransaction().rollback();
        }
    }
}
