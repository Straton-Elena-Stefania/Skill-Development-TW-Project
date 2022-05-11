package com.firstaid.models.subsections;


import com.firstaid.exceptions.DuplicateResourceException;

public interface SubsectionRepository {
    Subsection findByID(int id);
    Subsection save(Subsection subsection) throws DuplicateResourceException;
}
