package com.timetraveling.models.article.steps;

import com.timetraveling.exceptions.DuplicateResourceException;

public interface StepRepository {
    Step findByID(int id);
    Step save(Step step) throws DuplicateResourceException;
}
