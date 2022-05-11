package com.timetraveling.models.users;

import com.timetraveling.exceptions.AlreadyExistsException;
import com.timetraveling.exceptions.DuplicateResourceException;
import com.timetraveling.models.management.EntityManagerFactoryHandle;
import com.timetraveling.models.skills.SkillStatus;
import org.hibernate.exception.ConstraintViolationException;

import javax.persistence.*;
import javax.persistence.criteria.AbstractQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class UserHibernateRepository implements UserRepository {
    public List<User> findAll() {
        EntityManagerFactory entityManagerFactory = EntityManagerFactoryHandle.getInstance()
                .getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getMetamodel().entity(User.class);

        return entityManager.createQuery("SELECT a FROM User a", User.class).getResultList();
    }

    @Override
    public User findByID(int id) {
        EntityManagerFactory entityManagerFactory = EntityManagerFactoryHandle.getInstance()
                .getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getMetamodel().entity(User.class);

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        AbstractQuery<User> abstractQuery = criteriaBuilder.createQuery(User.class);

        Root<User> user = abstractQuery.from(User.class);

        abstractQuery.where(criteriaBuilder.equal(user.get("id"), id));

        CriteriaQuery<User> select = ((CriteriaQuery<User>) abstractQuery).select(user);
        TypedQuery<User> typedQuery = entityManager.createQuery(select);
        try {
            return typedQuery.getSingleResult();
        } catch (NoResultException noResultException) {
            return null;
        }
    }

    @Override
    public User findByUsername(String username) {
        EntityManagerFactory entityManagerFactory = EntityManagerFactoryHandle.getInstance()
                .getEntityManagerFactory();

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getMetamodel().entity(User.class);

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        AbstractQuery<User> abstractQuery = criteriaBuilder.createQuery(User.class);

        Root<User> user = abstractQuery.from(User.class);

        abstractQuery.where(criteriaBuilder.equal(user.get("username"), username));

        CriteriaQuery<User> select = ((CriteriaQuery<User>) abstractQuery).select(user);
        TypedQuery<User> typedQuery = entityManager.createQuery(select);
        try {
            return typedQuery.getSingleResult();
        } catch (NoResultException noResultException) {
            System.err.println("Nu am gasit emailul in baza de date");
            return null;
        }
    }

    @Override
    public User findByEmail(String email) {
        EntityManagerFactory entityManagerFactory = EntityManagerFactoryHandle.getInstance()
                .getEntityManagerFactory();

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getMetamodel().entity(User.class);

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        AbstractQuery<User> abstractQuery = criteriaBuilder.createQuery(User.class);

        Root<User> user = abstractQuery.from(User.class);

        abstractQuery.where(criteriaBuilder.equal(user.get("email"), email));

        CriteriaQuery<User> select = ((CriteriaQuery<User>) abstractQuery).select(user);
        TypedQuery<User> typedQuery = entityManager.createQuery(select);
        try {
            return typedQuery.getSingleResult();
        } catch (NoResultException noResultException) {
            System.err.println("Nu am gasit emailul in baza de date");
            return null;
        }
    }

    @Override
    public void save(User user) throws DuplicateResourceException {
        EntityManagerFactory entityManagerFactory = EntityManagerFactoryHandle.getInstance()
                                                                              .getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();
        try {
            entityManager.persist(user);
            entityManager.getTransaction().commit();
        } catch (PersistenceException constraintViolationException) {
            entityManager.getTransaction().rollback();
            throw new DuplicateResourceException("This resource already exists");
        }
    }

    @Override
    public void update(User user) throws AlreadyExistsException {
        EntityManagerFactory entityManagerFactory = EntityManagerFactoryHandle.getInstance()
                .getEntityManagerFactory();
        EntityManager em = entityManagerFactory.createEntityManager();

        if (user.getFirstAid() == null) {
            user.setFirstAid(SkillStatus.UNACTIVATED);
        }

        if (user.getCooking() == null) {
            user.setCooking(SkillStatus.UNACTIVATED);
        }

        if (user.getTimetraveling() == null) {
            user.setTimetraveling(SkillStatus.UNACTIVATED);
        }

        em.getTransaction().begin();

        try {
            em.merge(user);
            em.getTransaction().commit();
        } catch (ConstraintViolationException violationException) {
            throw new AlreadyExistsException("This resource is already taken");
        } catch (PersistenceException persistenceException) {
            System.err.println(persistenceException.getMessage());
            em.getTransaction().rollback();
        }
    }

    public void remove(User sessionStore) {
        EntityManagerFactory entityManagerFactory = EntityManagerFactoryHandle.getInstance()
                .getEntityManagerFactory();
        EntityManager em = entityManagerFactory.createEntityManager();

        em.getTransaction().begin();
        em.remove(em.contains(sessionStore) ? sessionStore : em.merge(sessionStore));

        em.getTransaction().commit();
        em.close();
    }
}