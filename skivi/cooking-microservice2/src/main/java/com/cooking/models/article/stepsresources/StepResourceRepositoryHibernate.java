package com.cooking.models.article.stepsresources;

import com.cooking.models.article.resources.Resource;
import com.cooking.models.article.resources.ResourceRepositoryHibernate;
import com.cooking.models.base.AbstractHibernateRepository;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class StepResourceRepositoryHibernate extends AbstractHibernateRepository<StepResource> implements StepResourceRepository {
    /**
     * Pentru a transmite ca parametri functiei
     * find din cadrul implementarii JPA, trebuie
     * un obiect de tip Class.
     *
     * @param modelClass
     */
    public StepResourceRepositoryHibernate(Class<StepResource> modelClass) {
        super(modelClass);
    }

    public List<Resource> findByStepId(int stepId) {
        Gson gson = new Gson();
        ResourceRepositoryHibernate resourceRepository = new ResourceRepositoryHibernate(Resource.class);
        List<Resource> resources = new ArrayList<>();

        List<StepResource> resultList = entityManager.createNamedQuery("StepResource.findByStepId")
                .setParameter("stepId", stepId).getResultList();

        for (StepResource stepResource: resultList) {
            Resource resource = resourceRepository.findByID(stepResource.getResourceId());
            resources.add(resource);
        }

        return resources;
    }
}
