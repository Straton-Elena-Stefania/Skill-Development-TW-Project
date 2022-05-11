package com.firstaid.models.article.resources;

import com.firstaid.models.base.AbstractHibernateRepository;

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
