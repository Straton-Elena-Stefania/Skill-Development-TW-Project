package com.timetraveling.models.subsections;

import com.timetraveling.models.base.AbstractHibernateRepository;
import com.timetraveling.models.management.EntityManagerFactoryHandle;
import com.timetraveling.models.sections.Section;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

public class SubsectionHibernateRepository extends AbstractHibernateRepository<Subsection> implements SubsectionRepository {
    /**
     * Pentru a transmite ca parametri functiei
     * find din cadrul implementarii JPA, trebuie
     * un obiect de tip Class.
     *
     * @param modelClass
     */
    public SubsectionHibernateRepository(Class<Subsection> modelClass) {
        super(modelClass);
    }

    public List<Subsection> findAll() {
        return entityManager.createQuery("SELECT a FROM Subsection a", Subsection.class).getResultList();
    }

    public List<Subsection> findBySectionId(int sectionId) {
        EntityManagerFactory entityManagerFactory = EntityManagerFactoryHandle.getInstance()
                .getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getMetamodel().entity(Section.class);

        List<Subsection> resultList = entityManager.createNamedQuery("Subsection.findBySectionId")
                .setParameter("sectionId", sectionId).getResultList();

        return resultList;
    }
}
