package com.firstaid.controllers;

import com.firstaid.controllers.configuration.Authorization;
import com.firstaid.controllers.configuration.AuthorizationExecutor;
import com.firstaid.exceptions.DuplicateResourceException;
import com.firstaid.models.article.answer.Answer;
import com.firstaid.models.article.answer.AnswerRepositoryHibernate;
import com.firstaid.models.article.steps.Step;
import com.firstaid.models.article.steps.StepRepositoryHibernate;
import com.firstaid.models.article.test.Test;
import com.firstaid.models.article.test.TestRepositoryHibernate;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(name = "subsectionsServlet", value = "/subsections/*")
public class SubsectionsServlet extends HttpServlet implements Authorization {
    private final static int SECTION_INDEX = 1;
    private final static int SUBSECTION_INDEX = 2;
    private final static int SUBSECTION_ID = 1;

    private final static String GET_STEPS_OPTION = "/.*/steps";
    private final static String GET_SUBSECTION_OPTION = "/.*";
    private final static String ALL_SUBSECTIONS_OPTION = "";
    private final static String POST_SUBSECTION_OPTION = "/";
    private final static String GET_TEST_OPTION = "/.*/questions";
    private final static String GET_ANSWERS_OPTION = "/.*/answers";

    private final StepRepositoryHibernate stepRepository = new StepRepositoryHibernate(Step.class);
    private final SectionHibernateRepository sectionRepository = new SectionHibernateRepository(Section.class);
    private final SubsectionHibernateRepository subsectionRepository = new SubsectionHibernateRepository(Subsection.class);
    private final Gson gson = new Gson();

    private List<Subsection> getSubsections(int sectionId) {
        return subsectionRepository.findBySectionId(sectionId);
    }

    /**
     * Aceasta functie se ocupa cu serializarea datelor si returnarea
     * catre client cu o lista de obiecte in format JSON ce descriu
     * subsectiile unei sectii valide, cerute in path.
     * @param subsections O lista de entitati serializabile ce descrie
     *                    subsectiile unei
     */
    private String generateResponse(List<Subsection> subsections) {
        return gson.toJson(subsections);
    }

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
    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        /**
         * Ceea ce se afla dupa maparea URL-ului
         */
        String path = request.getPathInfo();

        if (path == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        /**
         * Cuvintele din path
         */
        List<String> pathTraversal = Arrays.asList(path.split("/").clone());

        try {
            PrintWriter out = response.getWriter();

            if (path.matches(GET_SUBSECTION_OPTION)) {
                /**
                 * Altfel, a fost ceruta resursa in sine.
                 */
                SubsectionHibernateRepository newRepository = new SubsectionHibernateRepository(Subsection.class);
                Subsection resource = newRepository.findByID(Integer.parseInt(pathTraversal.get(SUBSECTION_ID)));

                if (resource == null) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                    return;
                }

                newRepository.remove(resource);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
        } catch (NumberFormatException formatException) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
    }

    //bad
    /*
    private String process(HttpServletRequest request, HttpServletResponse response) throws ResourceNotAvailableException {
        String path = request.getPathInfo();
        List<String> pathTraversal = Arrays.asList(path.split("/").clone());

        String section = pathTraversal.get(SECTION_INDEX);

        List<Section> sections = sectionRepository.findByName(section);
        if (!sections.isEmpty()) {
            int sectionId = sections.get(0).getId();
            List<Subsection> subsections = getSubsections(sectionId);
            return generateResponse(subsections);
        }

        throw new ResourceNotAvailableException("This section does not exist");
    }*/

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String path = request.getPathInfo();

        if (path == null) {
            path = POST_SUBSECTION_OPTION;
        }

        if (path.matches(POST_SUBSECTION_OPTION)) {
            String body = request.getReader().lines()
                                 .collect(Collectors.joining(System.lineSeparator()));

            Subsection subsection = gson.fromJson(body, Subsection.class);
            try {
                subsectionRepository.save(subsection);
            } catch (DuplicateResourceException duplicateResourceException) {
                response.sendError(HttpServletResponse.SC_CONFLICT);
            }

            /**
             * Trimitem inapoi ceea ce s-a postat
             */
            response.setStatus(HttpServletResponse.SC_CREATED);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(body);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            throw new ServletException("Invalid URI");
        }
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();

        String path = request.getPathInfo();

        if (path == null) {
            path = ALL_SUBSECTIONS_OPTION;
        }

        List<String> pathTraversal = Arrays.asList(path.split("/").clone());

        try {
            if (path.matches(GET_ANSWERS_OPTION)) {
                Subsection subsection = subsectionRepository.findByID(Integer.parseInt(pathTraversal
                        .get(SUBSECTION_ID)));
                TestRepositoryHibernate testRepositoryHibernate = new TestRepositoryHibernate(Test.class);
                AnswerRepositoryHibernate answerRepositoryHibernate = new AnswerRepositoryHibernate(Answer.class);

                List<Test> tests = testRepositoryHibernate.findBySubsectionId(subsection.getId());
                List<Answer> answers = new ArrayList<>();

                for (Test question: tests) {
                    List<Answer> answersOfTest = answerRepositoryHibernate.findByTestId(question.getId());
                    answers.addAll(answersOfTest);
                }

                String answerJson = gson.toJson(answers);
                out.println(answerJson);
            } else if (path.matches(GET_STEPS_OPTION)) {
                //List<Step> steps = stepRepository.findBySubsectionId(Integer.parseInt(pathTraversal
                //                                                                    .get(SUBSECTION_ID)));

                //List<Step> steps2 = stepRepository.findAll();
                StepRepositoryHibernate stepRepositoryHibernate = new StepRepositoryHibernate(Step.class);
                List<Step> steps = stepRepositoryHibernate.findAll().stream().filter(step -> step.getSubsectionID() == Integer.parseInt(pathTraversal.get(SUBSECTION_ID))).collect(Collectors.toList());
                String stepJson = gson.toJson(steps);
                out.println(stepJson);
            } else if (path.matches(GET_TEST_OPTION)) {
                Subsection subsection = subsectionRepository.findByID(Integer.parseInt(pathTraversal
                        .get(SUBSECTION_ID)));
                TestRepositoryHibernate testRepositoryHibernate = new TestRepositoryHibernate(Test.class);

                List<Test> test = testRepositoryHibernate.findBySubsectionId(subsection.getId());

                String testJson = gson.toJson(test);
                out.println(testJson);
            } else if (path.matches(GET_SUBSECTION_OPTION)){
                Subsection subsection = subsectionRepository.findByID(Integer.parseInt(pathTraversal
                        .get(SUBSECTION_ID)));

                String stepJson = gson.toJson(subsection);
                out.println(stepJson);
            } else if (path.matches(ALL_SUBSECTIONS_OPTION)) {
                List<Subsection> subsections = subsectionRepository.findAll();

                String stepJson = gson.toJson(subsections);
                out.println(stepJson);
            }
        } finally {
            out.close();
        }
    }

    public void destroy() {
    }
}
