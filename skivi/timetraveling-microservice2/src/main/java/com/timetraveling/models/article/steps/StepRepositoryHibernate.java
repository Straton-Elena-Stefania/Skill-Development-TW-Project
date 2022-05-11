package com.timetraveling.models.article.steps;

import com.timetraveling.models.article.resources.Resource;
import com.timetraveling.models.article.resources.ResourceRepository;
import com.timetraveling.models.base.AbstractHibernateRepository;
import com.timetraveling.models.management.EntityManagerFactoryHandle;
import com.timetraveling.models.sections.Section;
import com.timetraveling.models.subsections.Subsection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.AbstractQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.stream.Collectors;

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
        //List<Step> resultList = entityManager.createNamedQuery("Step.findBySubsectionId")
        //        .setParameter("subsectionID", subsectionID).getResultList();
        /*CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        AbstractQuery<Step> abstractQuery = criteriaBuilder.createQuery(Step.class);

        Root<Step> user = abstractQuery.from(Step.class);

        abstractQuery.where(criteriaBuilder.equal(user.get("subsectionID"), subsectionID));

        CriteriaQuery<Step> select = ((CriteriaQuery<Step>) abstractQuery).select(user);
        TypedQuery<Step> typedQuery = entityManager.createQuery(select);
        try {
            return typedQuery.getResultList();
        } catch (NoResultException noResultException) {
            System.err.println("Nu am gasit step in baza de date");
            return null;
        }*/

        //hibernate isi bate joc de mine
        List<Step> steps = findAll();
        List<Step> okSteps = steps.stream().filter(step -> step.getSubsectionID() == subsectionID).collect(Collectors.toList());
        return okSteps;

    }
}
