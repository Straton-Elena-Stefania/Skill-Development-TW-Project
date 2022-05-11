package com.timetraveling.models.article.resources;

import com.timetraveling.exceptions.DuplicateResourceException;

public interface ResourceRepository {
    Resource findByID(int id);
    Resource save(Resource resource) throws DuplicateResourceException;
}
