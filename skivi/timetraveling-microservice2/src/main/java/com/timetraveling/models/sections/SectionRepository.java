package com.timetraveling.models.sections;

import com.timetraveling.exceptions.DuplicateResourceException;
import com.timetraveling.models.article.stepsresources.StepResource;

public interface SectionRepository {
    Section findByID(int id);
    Section save(Section section) throws DuplicateResourceException;
}
