package com.timetraveling.models.base;

import com.timetraveling.exceptions.DuplicateResourceException;

/**
 * Aceasta interfata specifica ce functii CRUD
 * trebuie sa fie implementate de un Repository
 * @param <ModelType> Entitatea modelata de un Repository
 */
public interface AbstractRepository<ModelType> {
    ModelType findByID(int id);
    ModelType save(ModelType model) throws DuplicateResourceException;
}