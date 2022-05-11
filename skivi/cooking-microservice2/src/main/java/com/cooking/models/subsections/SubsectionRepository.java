package com.cooking.models.subsections;


import com.cooking.exceptions.DuplicateResourceException;

public interface SubsectionRepository {
    Subsection findByID(int id);
    Subsection save(Subsection subsection) throws DuplicateResourceException;
}
