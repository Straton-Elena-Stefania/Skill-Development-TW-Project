package com.timetraveling.models.article.stepsresources;

import com.timetraveling.exceptions.DuplicateResourceException;
import com.timetraveling.models.article.steps.Step;

public interface StepResourceRepository {
    StepResource findByID(int id);
    StepResource save(StepResource stepResource) throws DuplicateResourceException;
}
