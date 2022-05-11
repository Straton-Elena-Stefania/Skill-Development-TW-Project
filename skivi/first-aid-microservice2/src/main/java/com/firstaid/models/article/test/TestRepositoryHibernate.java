package com.firstaid.models.article.test;

import com.firstaid.models.article.stepsresources.StepResource;
import com.firstaid.models.article.stepsresources.StepResourceRepository;
import com.firstaid.models.base.AbstractHibernateRepository;
import com.firstaid.models.management.EntityManagerFactoryHandle;
import com.firstaid.models.sections.Section;
import com.firstaid.models.subsections.Subsection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

public class TestRepositoryHibernate extends AbstractHibernateRepository<Test> {
    public TestRepositoryHibernate(Class<Test> modelClass) {
        super(modelClass);
    }

    public List<Test> findBySubsectionId(int subsectionId) {
        EntityManagerFactory entityManagerFactory = EntityManagerFactoryHandle.getInstance()
                .getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getMetamodel().entity(Test.class);

        List<Test> resultList = entityManager.createNamedQuery("Test.findBySubsectionId")
                .setParameter("subsectionId", subsectionId).getResultList();

        return resultList;
    }
}
