package com.timetraveling.models.article.resources;

import com.timetraveling.models.article.steps.Step;
import com.timetraveling.models.base.AbstractHibernateRepository;
import com.timetraveling.models.management.EntityManagerFactoryHandle;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.AbstractQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class ResourceRepositoryHibernate extends AbstractHibernateRepository<Resource> implements ResourceRepository {

    /**
     * Pentru a transmite ca parametri functiei
     * find din cadrul implementarii JPA, trebuie
     * un obiect de tip Class.
     *
     * @param modelClass
     */
    public ResourceRepositoryHibernate(Class<Resource> modelClass) {
        super(modelClass);
    }

    public List<Resource> findAll() {
        return entityManager.createQuery("SELECT a FROM Resource a", Resource.class).getResultList();
    }
}
