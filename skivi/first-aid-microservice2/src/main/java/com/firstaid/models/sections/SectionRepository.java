package com.firstaid.models.sections;

import com.firstaid.exceptions.DuplicateResourceException;

public interface SectionRepository {
    Section findByID(int id);
    Section save(Section section) throws DuplicateResourceException;
}
