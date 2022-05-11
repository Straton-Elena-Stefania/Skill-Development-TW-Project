package com.timetraveling.models.article.contenttypes;

import com.timetraveling.exceptions.DuplicateResourceException;
import com.timetraveling.models.article.resources.Resource;

public interface ContentTypeRepository {
    ContentType findByID(int id);
    ContentType save(ContentType contentType) throws DuplicateResourceException;
}
