package com.cooking.models.article.steps;

import com.cooking.models.base.AbstractHibernateRepository;

import java.util.List;

public class StepRepositoryHibernate extends AbstractHibernateRepository<Step> implements StepRepository {
    /**
     * Pentru a transmite ca parametri functiei
     * find din cadrul implementarii JPA, trebuie
     * un obiect de tip Class.
     *
     * @param modelClass
     */
    public StepRepositoryHibernate(Class<Step> modelClass) {
        super(modelClass);
    }

    public List<Step> findAll() {
        return entityManager.createQuery("SELECT a FROM Step a", Step.class).getResultList();
    }

    public List<Step> findBySubsectionId(int subsectionID) {
        List<Step> resultList = entityManager.createNamedQuery("Step.findBySubsectionId")
                .setParameter("subsectionID", subsectionID).getResultList();

        return resultList;
    }
}
