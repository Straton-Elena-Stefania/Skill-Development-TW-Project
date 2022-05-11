package com.firstaid.controllers;

import com.firstaid.controllers.configuration.Authorization;
import com.firstaid.controllers.configuration.AuthorizationExecutor;
import com.firstaid.exceptions.DuplicateResourceException;
import com.firstaid.models.sections.Section;
import com.firstaid.models.sections.SectionHibernateRepository;
import com.firstaid.models.subsections.Subsection;
import com.firstaid.models.subsections.SubsectionHibernateRepository;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
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
                int id = Integer.parseInt(pathTraversal.get(SECTION_ID));

                String body = request.getReader().lines()
                        .collect(Collectors.joining(System.lineSeparator()));

                Section section = sectionRepository.findByID(id);

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
        try {
            seectionsRepository.save(section);
        } catch (DuplicateResourceException constraintViolationException) {
            response.sendError(HttpServletResponse.SC_CONFLICT);
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
                int id = Integer.parseInt(pathTraversal.get(SECTION_ID));

                String body = request.getReader().lines()
                        .collect(Collectors.joining(System.lineSeparator()));

                Section section = sectionRepository.findByID(id);
                Section sectionOfBody = gson.fromJson(body, Section.class);

                if (section == null) {
                    sectionOfBody.setId(id);
                    sectionRepository.update(sectionOfBody);

                    /**
                     * Trimitem inapoi ceea ce s-a postat
                     */
                    response.setStatus(HttpServletResponse.SC_CREATED);
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    response.getWriter().write(body);
                    return;
                } else {
                    section.setName(sectionOfBody.getName());
                    sectionRepository.update(section);

                    response.setStatus(HttpServletResponse.SC_OK);
                    return;
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
            if (path.matches(SUBSECTIONS_OPTION)) {
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