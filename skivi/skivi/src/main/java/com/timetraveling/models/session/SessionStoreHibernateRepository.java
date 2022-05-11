package com.timetraveling.models.session;

import com.timetraveling.exceptions.AlreadyExistsException;
import com.timetraveling.exceptions.DuplicateResourceException;
import com.timetraveling.models.management.EntityManagerFactoryHandle;
import com.timetraveling.models.users.User;

import javax.persistence.*;
import javax.persistence.criteria.AbstractQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class SessionStoreHibernateRepository implements SessionStoreRepository {
    @Override
    public SessionStore findByUserID(int id) {
        EntityManagerFactory entityManagerFactory = EntityManagerFactoryHandle.getInstance()
                .getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getMetamodel().entity(SessionStore.class);

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        AbstractQuery<SessionStore> abstractQuery = criteriaBuilder.createQuery(SessionStore.class);

        Root<SessionStore> sessionStoreRoot = abstractQuery.from(SessionStore.class);

        abstractQuery.where(criteriaBuilder.equal(sessionStoreRoot.get("userId"), id));

        CriteriaQuery<SessionStore> select = ((CriteriaQuery<SessionStore>) abstractQuery).select(sessionStoreRoot);
        TypedQuery<SessionStore> typedQuery = entityManager.createQuery(select);
        try {
            return typedQuery.getSingleResult();
        } catch (NoResultException noResultException) {
            return null;
        }
    }

    @Override
    public void save(SessionStore sessionStore) throws DuplicateResourceException {
        EntityManagerFactory entityManagerFactory = EntityManagerFactoryHandle.getInstance()
                .getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();
        try {
            entityManager.persist(sessionStore);
            entityManager.getTransaction().commit();
        } catch (PersistenceException constraintViolationException) {
            entityManager.getTransaction().rollback();
            throw new DuplicateResourceException("This resource already exists");
        }
    }

    @Override
    public void update(SessionStore sessionStore) throws AlreadyExistsException {
        EntityManagerFactory entityManagerFactory = EntityManagerFactoryHandle.getInstance()
                .getEntityManagerFactory();
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();

        try {
            em.merge(sessionStore);
            em.getTransaction().commit();
        } catch (Exception persistenceException) {
            System.err.println(persistenceException.getMessage());
            em.getTransaction().rollback();
        }
    }

    @Override
    public void remove(SessionStore sessionStore) {
        EntityManagerFactory entityManagerFactory = EntityManagerFactoryHandle.getInstance()
                .getEntityManagerFactory();
        EntityManager em = entityManagerFactory.createEntityManager();

        em.getTransaction().begin();
        em.remove(em.contains(sessionStore) ? sessionStore : em.merge(sessionStore));

        em.getTransaction().commit();
        em.close();
    }
}
