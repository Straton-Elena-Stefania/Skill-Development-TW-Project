package com.cooking.models.article.stepsresources;

import com.cooking.exceptions.DuplicateResourceException;

public interface StepResourceRepository {
    StepResource findByID(int id);
    StepResource save(StepResource stepResource) throws DuplicateResourceException;
}
