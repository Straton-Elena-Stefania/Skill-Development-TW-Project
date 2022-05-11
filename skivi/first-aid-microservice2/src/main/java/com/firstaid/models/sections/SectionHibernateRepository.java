package com.firstaid.models.sections;

import com.firstaid.models.base.AbstractHibernateRepository;
import com.firstaid.models.management.EntityManagerFactoryHandle;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

public class SectionHibernateRepository extends AbstractHibernateRepository<Section> implements SectionRepository {
    /**
     * Pentru a transmite ca parametri functiei
     * find din cadrul implementarii JPA, trebuie
     * un obiect de tip Class.
     *
     * @param modelClass
     */
    public SectionHibernateRepository(Class<Section> modelClass) {
        super(modelClass);
    }

    public List<Section> findAll() {
        return entityManager.createQuery("SELECT a FROM Section a", Section.class).getResultList();
    }

    public List<Section> findByName(String name) {
        EntityManagerFactory entityManagerFactory = EntityManagerFactoryHandle.getInstance()
                .getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getMetamodel().entity(Section.class);

        List<Section> resultList = entityManager.createNamedQuery("Section.findByName")
                .setParameter("name", name).getResultList();

        return resultList;
    }
}
