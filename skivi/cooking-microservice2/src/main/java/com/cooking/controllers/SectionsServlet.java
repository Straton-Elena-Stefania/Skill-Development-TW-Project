package com.cooking.controllers;

import com.cooking.configuration.Authorization;
import com.cooking.configuration.AuthorizationExecutor;
import com.cooking.exceptions.DuplicateResourceException;
import com.cooking.models.sections.Section;
import com.cooking.models.sections.SectionHibernateRepository;
import com.cooking.models.subsections.Subsection;
import com.cooking.models.subsections.SubsectionHibernateRepository;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.exception.ConstraintViolationException;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

/**
 * O sectiune reprezinta cea mai high level componenta a unui skill.
 * O sectiune este compusa din mai multe subsectiuni.
 */
@WebServlet(name = "sectionsServlet", value = "/sections/*")
public class SectionsServlet extends HttpServlet implements Authorization {
    private Gson gson = new Gson();

    /**
     * Indexul pentru de unde luam pasul din URL, dupa id
     */
    private final static int SECTION_ID = 1;
    /**
     * Pentru toate obiectele
     */
    private final static String ALL_SECTION_OPTION = "";
    /**
     * Pentru a obtine resursa propriu-zisa. De aici va fi extras id-ul.
     */
    private final static String SECTION_OPTION = "/.*";

    private final static String SUBSECTIONS_OPTION = "/.*/subsections";

    private final static String SECTION_BY_NAME = "/.*/getIdByName";

    private SubsectionHibernateRepository subsectionHibernateRepository = new SubsectionHibernateRepository(Subsection.class);

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AuthorizationExecutor authorizationExecutor = new AuthorizationExecutor();
        int status;

        if ("POST".equals(request.getMethod())) {
            status = authorizationExecutor.checkAuthorizationStatus(request, response,
                    ADMIN_ROLE);
        } else {
            status = authorizationExecutor.checkAuthorizationStatus(request, response,
                    ENROLLED_ROLE);
        }

        if (status != HttpServletResponse.SC_OK) {
            response.sendError(status);
            return;
        }

        super.service(request, response);
    }

    @Override
    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        /**
         * Ceea ce se afla dupa maparea URL-ului
         */
        String path = request.getPathInfo();

        if (path == null) {
            path = ALL_SECTION_OPTION;
        }
        /**
         * Cuvintele din path
         */
        List<String> pathTraversal = Arrays.asList(path.split("/").clone());

        try {
            /**
             * /section/{id}
             */
            if (path.matches(SECTION_OPTION)) {
                SectionHibernateRepository sectionRepository = new SectionHibernateRepository(Section.class);
                String name = pathTraversal.get(SECTION_ID);

                String body = request.getReader().lines()
                        .collect(Collectors.joining(System.lineSeparator()));

                List<Section> sections = sectionRepository.findByName(name);

                if (sections.size() == 0) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                    return;
                }

                Section section = sections.get(0);

                if (section == null) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                    return;
                } else {
                    sectionRepository.remove(section);
                }
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
        } catch (NumberFormatException numberFormatException) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");

        SectionHibernateRepository seectionsRepository = new SectionHibernateRepository(Section.class);

        String body = request.getReader().lines()
                .collect(Collectors.joining(System.lineSeparator()));

        Section section = gson.fromJson(body, Section.class);

        List<Section> alreadyExistingSections = seectionsRepository.findByName(section.getName());
        if (alreadyExistingSections.size() > 0) {
            response.sendError(HttpServletResponse.SC_CONFLICT);
            return;
        }

        try {
            seectionsRepository.save(section);
        } catch (DuplicateResourceException constraintViolationException) {
            response.sendError(HttpServletResponse.SC_CONFLICT);
            return;
        }

        PrintWriter out = response.getWriter();
        try {
            out.println(body);
        } finally {
            out.close();
        }

        response.setStatus(HttpServletResponse.SC_CREATED);
    }

    @Override
    public void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");

        /**
         * Ceea ce se afla dupa maparea URL-ului
         */
        String path = request.getPathInfo();

        if (path == null) {
            path = ALL_SECTION_OPTION;
        }
        /**
         * Cuvintele din path
         */
        List<String> pathTraversal = Arrays.asList(path.split("/").clone());

        try {
            if (path.matches(SECTION_OPTION)) {
                SectionHibernateRepository sectionRepository = new SectionHibernateRepository(Section.class);
                String id = pathTraversal.get(SECTION_ID);

                String body = request.getReader().lines()
                        .collect(Collectors.joining(System.lineSeparator()));

                List<Section> sections = sectionRepository.findByName(id);
                Section sectionOfBody = gson.fromJson(body, Section.class);

                List<Section> alreadyExisting = sectionRepository.findByName(sectionOfBody.getName());

                if (alreadyExisting.size() > 0) {
                    response.sendError(HttpServletResponse.SC_CONFLICT);
                    return;
                }

                if (sections.size() == 0) {
                    sectionRepository.update(sectionOfBody);

                    response.setStatus(HttpServletResponse.SC_CREATED);
                    PrintWriter out = response.getWriter();
                    try {
                        out.println(body);
                    } finally {
                        out.close();
                    }

                    return;
                }

                Section section = sections.get(0);
                section.setName(sectionOfBody.getName());
                sectionRepository.update(section);

                response.setStatus(HttpServletResponse.SC_OK);
                return;

            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
        } catch (NumberFormatException numberFormatException) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");

        /**
         * Ceea ce se afla dupa maparea URL-ului
         */
        String path = request.getPathInfo();

        if (path == null) {
            path = ALL_SECTION_OPTION;
        }
        /**
         * Cuvintele din path
         */
        List<String> pathTraversal = Arrays.asList(path.split("/").clone());

        try {
            if (path.matches(SECTION_BY_NAME)) {
                SectionHibernateRepository sectionRepository = new SectionHibernateRepository(Section.class);

                String name = pathTraversal.get(SECTION_ID);

                if (sectionRepository.findByName(name).size() != 0) {
                    Section section = sectionRepository.findByName(name).get(0);

                    String jsonData = gson.toJson(section);
                    PrintWriter out = response.getWriter();
                    try {
                        out.println(jsonData);
                    } finally {
                        out.close();
                    }
                }
            } else if (path.matches(SUBSECTIONS_OPTION)) {
                SectionHibernateRepository sectionRepository = new SectionHibernateRepository(Section.class);
                String id = pathTraversal.get(SECTION_ID);

                if (sectionRepository.findByName(id).size() != 0) {
                    Section section = sectionRepository.findByName(id).get(0);

                    List<Subsection> subsections = subsectionHibernateRepository.findBySectionId(section.getId());

                    String jsonData = gson.toJson(subsections);
                    PrintWriter out = response.getWriter();
                    try {
                        out.println(jsonData);
                    } finally {
                        out.close();
                    }
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                    return;
                }
            } if (path.matches(SECTION_OPTION)) {
                SectionHibernateRepository sectionRepository = new SectionHibernateRepository(Section.class);
                String id = pathTraversal.get(SECTION_ID);

                if (sectionRepository.findByName(id).size() != 0) {
                    Section section = sectionRepository.findByName(id).get(0);
                    String jsonData = gson.toJson(section);
                    PrintWriter out = response.getWriter();
                    try {
                        out.println(jsonData);
                    } finally {
                        out.close();
                    }
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                    return;
                }
            } else if (path.matches(ALL_SECTION_OPTION)) {
                SectionHibernateRepository seectionsRepository = new SectionHibernateRepository(Section.class);
                List<Section> availableSections = seectionsRepository.findAll();

                String jsonData = gson.toJson(availableSections);
                PrintWriter out = response.getWriter();
                try {
                    out.println(jsonData);
                } finally {
                    out.close();
                }
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
        } catch (NumberFormatException numberFormatException) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
    }

    public void destroy() {
    }
}