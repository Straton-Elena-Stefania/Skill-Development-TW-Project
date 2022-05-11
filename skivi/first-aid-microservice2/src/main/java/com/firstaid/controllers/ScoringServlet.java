package com.firstaid.controllers;

import com.firstaid.controllers.configuration.Authorization;
import com.firstaid.controllers.configuration.AuthorizationExecutor;
import com.firstaid.models.article.answer.Answer;
import com.firstaid.models.article.resources.Resource;
import com.firstaid.models.article.test.Test;
import com.firstaid.models.article.test.TestRepositoryHibernate;
import com.firstaid.models.article.test.TestResult;
import com.firstaid.models.subsections.Subsection;
import com.firstaid.models.subsections.SubsectionHibernateRepository;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@WebServlet(name = "scoringServlet", value = "/scoring/*")
public class ScoringServlet extends HttpServlet implements Authorization {
    private Gson gson = new Gson();

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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /**
         * Obtinem corpul resursei ce se afla in cererea POST
         */
        String body = request.getReader().lines()
                .collect(Collectors.joining(System.lineSeparator()));

        /**
         * Acest URL este mai general decat cel anterior.
         * Introducem in baza de date o noua resursa, din corpul cererii
         */
        JsonObject answersReceivedJson = gson.fromJson(body, JsonObject.class);
        int subsectionId = answersReceivedJson.get("subsectionID").getAsInt();

        JsonArray answersNumbersJson = answersReceivedJson.getAsJsonArray("answers");
        Set<Integer> answerIds = gson.fromJson(answersNumbersJson, new TypeToken<Set<Integer>>(){}.getType());

        TestResult testResult = new TestResult();

        if (answerIds.size() == 0) {
            testResult.setAchievement((new SubsectionHibernateRepository(Subsection.class))
                    .findByID(subsectionId).getAchievement());
        } else {
            Set<Integer> correctAnswersIds = (new TestRepositoryHibernate(Test.class)).findBySubsectionId(subsectionId)
                    .stream().map(test -> test.getCorrectAnswerId())
                    .collect(Collectors.toSet());

            int initialSize = correctAnswersIds.size();

            correctAnswersIds.addAll(answerIds);
            int correctAnswersNr = initialSize - (correctAnswersIds.size() - initialSize);

            if (correctAnswersNr == initialSize) {
                testResult.setAchievement((new SubsectionHibernateRepository(Subsection.class))
                        .findByID(subsectionId).getAchievement());
            } else {
                testResult.setTotalQuestions(initialSize);
                testResult.setCorrectAnswers(correctAnswersNr);
                testResult.setAchievement("null");
            }
        }

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String stepJson = gson.toJson(testResult);
        response.getWriter().write(stepJson);
    }
}
