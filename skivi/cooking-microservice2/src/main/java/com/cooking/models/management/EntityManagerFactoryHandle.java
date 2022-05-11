package com.cooking.models.management;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Aceasta clasa este pentru a implementa si gestiona singleton pentru
 * EntityManager.
 */
public class EntityManagerFactoryHandle {
    public static final EntityManagerFactoryHandle singleton = new EntityManagerFactoryHandle();

    protected EntityManagerFactory entityManagerFactory;

    private EntityManagerFactoryHandle() {}

    public static EntityManagerFactoryHandle getInstance() {
        return singleton;
    }

    public EntityManagerFactory getEntityManagerFactory() {
        if (entityManagerFactory == null)
            createEntityManagerFactory();
        return entityManagerFactory;
    }

    protected void createEntityManagerFactory() {
        this.entityManagerFactory = Persistence.createEntityManagerFactory("persist_3");
    }
}
