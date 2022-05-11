package com.timetraveling.models.subsections;

import com.timetraveling.exceptions.DuplicateResourceException;
import com.timetraveling.models.article.stepsresources.StepResource;

public interface SubsectionRepository {
    Subsection findByID(int id);
    Subsection save(Subsection subsection) throws DuplicateResourceException;
}
