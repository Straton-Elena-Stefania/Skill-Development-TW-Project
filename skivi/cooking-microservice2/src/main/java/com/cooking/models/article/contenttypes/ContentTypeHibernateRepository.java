package com.cooking.models.article.contenttypes;

import com.cooking.models.base.AbstractHibernateRepository;

public class ContentTypeHibernateRepository extends AbstractHibernateRepository<ContentType> implements ContentTypeRepository {
    /**
     * Pentru a transmite ca parametri functiei
     * find din cadrul implementarii JPA, trebuie
     * un obiect de tip Class.
     *
     * @param modelClass
     */
    public ContentTypeHibernateRepository(Class<ContentType> modelClass) {
        super(modelClass);
    }
}
