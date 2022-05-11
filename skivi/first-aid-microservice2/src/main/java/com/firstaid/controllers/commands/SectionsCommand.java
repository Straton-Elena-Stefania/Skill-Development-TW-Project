package com.firstaid.controllers.commands;

import com.google.gson.Gson;
import com.firstaid.models.sections.Section;
import com.firstaid.models.sections.SectionHibernateRepository;

import java.util.List;

public class SectionsCommand extends Command {
    private Gson gson = new Gson();

    public SectionsCommand(String commandName) {
        super(commandName);
    }

    @Override
    public Object run(String commandName) {
        SectionHibernateRepository seectionsRepository = new SectionHibernateRepository(Section.class);
        List<Section> availableSections = seectionsRepository.findAll();

        String jsonData = gson.toJson(availableSections);

        return jsonData;
    }
}
