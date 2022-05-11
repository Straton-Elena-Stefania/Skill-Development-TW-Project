package com.cooking.models.sections;

import com.cooking.exceptions.DuplicateResourceException;

public interface SectionRepository {
    Section findByID(int id);
    Section save(Section section) throws DuplicateResourceException;
}
