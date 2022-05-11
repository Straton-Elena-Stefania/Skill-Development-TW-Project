package com.timetraveling.controllers.restclient;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.timetraveling.controllers.Communicative;
import com.timetraveling.exceptions.InvalidDeploymentException;
import com.timetraveling.models.article.Section;
import com.timetraveling.models.session.SessionStore;
import com.timetraveling.models.session.SessionStoreHibernateRepository;
import com.timetraveling.models.session.SessionStoreRepository;
import com.timetraveling.models.skillmodel.SkillHibernateRepository;
import com.timetraveling.models.skillmodel.SkillModel;
import com.timetraveling.models.skills.Skill;
import com.timetraveling.models.skills.SkillStatus;
import com.timetraveling.models.users.User;
import com.timetraveling.models.users.UserHibernateRepository;
import com.timetraveling.models.userskills.UserSkillHibernateRepository;
import com.timetraveling.utils.configuration.Configuration;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import javax.xml.stream.events.Comment;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SkillDisplayFactory implements Communicative {
    private UserHibernateRepository userHibernateRepository = new UserHibernateRepository();
    private SessionStoreRepository sessionStoreRepository = new SessionStoreHibernateRepository();

    private SkillHibernateRepository skillHibernateRepository = new SkillHibernateRepository();
    private UserSkillHibernateRepository userSkillHibernateRepository = new UserSkillHibernateRepository();

    private final CloseableHttpClient httpClient = HttpClients.createDefault();

    private Gson gson = new Gson();

    private List<Section> sectionDisplay(String skillURL, SessionStore sessionStore, HttpServletRequest request) {
        HttpGet sectionsRequest = new HttpGet(skillURL);
        sectionsRequest.addHeader("userId", String.valueOf(sessionStore.getUserId()));
        sectionsRequest.addHeader("Authorization", String.valueOf(sessionStore.getSessionId()));

        sectionsRequest.addHeader("content-type", "application/json");
        List<Section> sectionsList = new ArrayList<>();

        try (CloseableHttpResponse sectionsResponse = httpClient.execute(sectionsRequest)) {
            System.out.println(sectionsResponse.getStatusLine().toString());


            HttpEntity entity = sectionsResponse.getEntity();

            String result = EntityUtils.toString(entity);
            JsonArray sectionsListJson = gson.fromJson(result, JsonArray.class);

            for (int sectionIndex = 0;
                 sectionIndex < sectionsListJson.size();
                 sectionIndex++) {
                sectionsList.add(gson.fromJson(sectionsListJson.get(sectionIndex), Section.class));
            }
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        } finally {
            return sectionsList;
        }
    }

    public Map<SkillModel, List<Section>> displayModelsForUser(int userId, HttpServletRequest request, HttpServletResponse response) throws InvalidDeploymentException {
        Map<SkillModel, List<Section>>  userSkillsByParticipation = new HashMap<>();
        User user = userHibernateRepository.findByID(userId);
        SessionStore sessionStore = sessionStoreRepository.findByUserID(userId);

        List<SkillModel> skillModelList = userSkillHibernateRepository.findByUserId(userId);

        for (SkillModel skillModel: skillModelList) {
            List<Section> sections = sectionDisplay(skillModel.getUrl() + "/sections", sessionStore, request);

            userSkillsByParticipation.put(skillModel, sections);
        }

        return userSkillsByParticipation;
    }
}
