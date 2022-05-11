package com.firstaid.models.article.steps;

import com.firstaid.exceptions.DuplicateResourceException;

public interface StepRepository {
    Step findByID(int id);
    Step save(Step step) throws DuplicateResourceException;
}
