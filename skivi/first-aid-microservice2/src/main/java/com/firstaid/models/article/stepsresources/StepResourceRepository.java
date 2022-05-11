package com.firstaid.models.article.stepsresources;

import com.firstaid.exceptions.DuplicateResourceException;

public interface StepResourceRepository {
    StepResource findByID(int id);
    StepResource save(StepResource stepResource) throws DuplicateResourceException;
}
