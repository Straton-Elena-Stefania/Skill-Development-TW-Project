package com.firstaid.models.article.resources;

import com.firstaid.exceptions.DuplicateResourceException;

public interface ResourceRepository {
    Resource findByID(int id);
    Resource save(Resource resource) throws DuplicateResourceException;
}
