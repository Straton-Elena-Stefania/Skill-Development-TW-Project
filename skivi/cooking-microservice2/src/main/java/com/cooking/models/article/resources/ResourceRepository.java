package com.cooking.models.article.resources;

import com.cooking.exceptions.DuplicateResourceException;

public interface ResourceRepository {
    Resource findByID(int id);
    Resource save(Resource resource) throws DuplicateResourceException;
}
