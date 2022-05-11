package com.cooking.models.article.steps;

import com.cooking.exceptions.DuplicateResourceException;

public interface StepRepository {
    Step findByID(int id);
    Step save(Step step) throws DuplicateResourceException;
}
