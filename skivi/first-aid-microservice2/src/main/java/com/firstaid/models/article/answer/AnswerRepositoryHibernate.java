package com.firstaid.models.article.answer;

import com.firstaid.models.article.steps.Step;
import com.firstaid.models.article.test.Test;
import com.firstaid.models.base.AbstractHibernateRepository;
import com.firstaid.models.management.EntityManagerFactoryHandle;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

public class AnswerRepositoryHibernate extends AbstractHibernateRepository<Answer> {

    /**
     * Pentru a transmite ca parametri functiei
     * find din cadrul implementarii JPA, trebuie
     * un obiect de tip Class.
     *
     * @param modelClass
     */
    public AnswerRepositoryHibernate(Class<Answer> modelClass) {
        super(modelClass);
    }

    public List<Answer> findByTestId(int testId) {
        EntityManagerFactory entityManagerFactory = EntityManagerFactoryHandle.getInstance()
                .getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getMetamodel().entity(Test.class);

        List<Answer> resultList = entityManager.createNamedQuery("Answer.findByTestId")
                .setParameter("testId", testId).getResultList();

        return resultList;
    }
}
