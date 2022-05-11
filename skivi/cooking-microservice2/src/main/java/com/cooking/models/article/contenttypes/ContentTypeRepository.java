package com.cooking.models.article.contenttypes;

import com.cooking.exceptions.DuplicateResourceException;

public interface ContentTypeRepository {
    ContentType findByID(int id);
    ContentType save(ContentType contentType) throws DuplicateResourceException;
}
