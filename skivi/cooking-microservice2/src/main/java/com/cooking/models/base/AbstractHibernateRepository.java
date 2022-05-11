package com.cooking.models.base;

import com.cooking.exceptions.DuplicateResourceException;
import com.cooking.models.management.EntityManagerFactoryHandle;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;

/**
 * Aceasta este o clasa ce ajuta in dezvoltarea de repository-uri,
 * un dezvoltator putand sa suprascrie/adauge functionalitati noi
 * unui repository. Aceasta este implementarea pentru Hibernate.
 *
 * @param <ModelType> Entitatea modelata de catre repository.
 *
 * @author Damian Teodora, 2A5
 */
public class AbstractHibernateRepository<ModelType> implements AbstractRepository<ModelType> {
    private Class<ModelType> modelClass;
    protected EntityManagerFactory entityManagerFactory;
    protected EntityManager entityManager;

    /**
     * Pentru a transmite ca parametri functiei
     * find din cadrul implementarii JPA, trebuie
     * un obiect de tip Class.
     * @param modelClass
     */
    public AbstractHibernateRepository(Class<ModelType> modelClass) {
        this.entityManagerFactory = EntityManagerFactoryHandle.getInstance()
                .getEntityManagerFactory();
        this.entityManager = entityManagerFactory.createEntityManager();
        this.modelClass = modelClass;
    }

    /**
     * Functia este un wrapper pentru functia generica find
     * din implementarea JPA, ce returneaza
     * entitatea modelata cautand-o dupa cheia sa primara.
     * @param id O cheie primara pentru baza de date, de
     *           tip intreg (presupunem chei seriale,
     *           fara semantica)
     * @return
     */
    public ModelType findByID(int id) {
        entityManager.getMetamodel().entity(modelClass);

        return entityManager.find(modelClass, id);
    }

    /**
     * Clasa este un wrapper pentru actiunea de a persista un obiect
     * ceea ce include si realizarea unei tranzactii.
     * @param model Entitatea salvata
     */
    public ModelType save(ModelType model) throws DuplicateResourceException {
        entityManager.getTransaction().begin();
        try {
            entityManager.persist(model);
            entityManager.getTransaction().commit();
        } catch (PersistenceException constraintViolationException) {
            entityManager.getTransaction().rollback();
            throw new DuplicateResourceException("This resource already exists");
        }

        return model;
    }

    public void remove(ModelType sessionStore) {
        EntityManagerFactory entityManagerFactory = EntityManagerFactoryHandle.getInstance()
                .getEntityManagerFactory();
        EntityManager em = entityManagerFactory.createEntityManager();

        em.getTransaction().begin();
        em.remove(em.contains(sessionStore) ? sessionStore : em.merge(sessionStore));

        em.getTransaction().commit();
        em.close();
    }

    public void update(ModelType modelType) {
        EntityManagerFactory entityManagerFactory = EntityManagerFactoryHandle.getInstance()
                .getEntityManagerFactory();
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();

        try {
            em.merge(modelType);
            em.getTransaction().commit();
        } catch (Exception persistenceException) {
            System.err.println(persistenceException.getMessage());
            em.getTransaction().rollback();
        }
    }
}