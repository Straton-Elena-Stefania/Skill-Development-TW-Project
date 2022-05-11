package com.timetraveling.controllers;

import com.google.gson.Gson;
import com.timetraveling.exceptions.ResourceNotAvailableException;
import com.timetraveling.models.article.Article;
import com.timetraveling.models.article.resources.Resource;
import com.timetraveling.models.article.resources.ResourceRepository;
import com.timetraveling.models.article.resources.ResourceRepositoryHibernate;
import com.timetraveling.models.article.steps.Step;
import com.timetraveling.models.article.steps.StepRepository;
import com.timetraveling.models.article.steps.StepRepositoryHibernate;
import com.timetraveling.models.article.stepsresources.StepResource;
import com.timetraveling.models.article.stepsresources.StepResourceRepository;
import com.timetraveling.models.article.stepsresources.StepResourceRepositoryHibernate;
import com.timetraveling.models.sections.Section;
import com.timetraveling.models.sections.SectionHibernateRepository;
import com.timetraveling.models.subsections.Subsection;
import com.timetraveling.models.subsections.SubsectionHibernateRepository;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Acest servlet pune la dispozitie resursa articol
 */
@WebServlet(name = "articlesServlet", value = "/articles/*")
public class ArticlesServlet extends HttpServlet {
    private final int SECTION_INDEX = 1;
    private final int SUBSECTION_INDEX = 2;

    private final StepResourceRepositoryHibernate stepResourceRepository = new StepResourceRepositoryHibernate(StepResource.class);
    private final StepRepositoryHibernate stepRepository = new StepRepositoryHibernate(Step.class);
    private final ResourceRepositoryHibernate resourceRepository = new ResourceRepositoryHibernate(Resource.class);

    private final SectionHibernateRepository sectionRepository = new SectionHibernateRepository(Section.class);
    private final SubsectionHibernateRepository subsectionRepository = new SubsectionHibernateRepository(Subsection.class);
    private final Gson gson = new Gson();

    private List<Subsection> getSubsections(int sectionId) {
        return subsectionRepository.findBySectionId(sectionId);
    }

    private String generateResponse(Article article) {
        return gson.toJson(article);
    }

    private String process(HttpServletRequest request, HttpServletResponse response) throws ResourceNotAvailableException {
        String path = request.getPathInfo();
        List<String> pathTraversal = Arrays.asList(path.split("/").clone());

        String section = pathTraversal.get(SECTION_INDEX);

        /**
         * Aici se face de fapt un try si se arunca un resource not available exception
         */
        List<Section> sections = sectionRepository.findByName(section);
        if (!sections.isEmpty()) {
            int sectionId = sections.get(0).getId();
            List<Subsection> subsections = getSubsections(sectionId);

            for (Subsection subsection: subsections) {
                if (pathTraversal.get(SUBSECTION_INDEX)
                        .equals(subsection.getName())) {
                    List<Step> steps = stepRepository.findBySubsectionId(subsection.getId());
                    Map<Step, List<Resource>> stepResourcesMap = new HashMap<>();

                    for (Step step: steps) {
                        List<Resource> resources = stepResourceRepository.findByStepId(step.getId());
                        stepResourcesMap.put(step, resources);
                    }

                    Article article = new Article(stepResourcesMap);
                    return generateResponse(article);
                }

                throw new ResourceNotAvailableException("This subsection does not belong to the section");
            }
        }

        throw new ResourceNotAvailableException("This section does not exist");
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();

        try {
            String subsections = process(request, response);
            out.println(subsections);
        } catch (ResourceNotAvailableException exception) {
            exception.printStackTrace();
        } finally {
            out.close();
        }
    }

    public void destroy() {
    }
}
