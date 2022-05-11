package com.firstaid.models.article.contenttypes;

import com.firstaid.exceptions.DuplicateResourceException;

public interface ContentTypeRepository {
    ContentType findByID(int id);
    ContentType save(ContentType contentType) throws DuplicateResourceException;
}
