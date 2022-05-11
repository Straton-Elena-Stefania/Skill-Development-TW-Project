package com.timetraveling.models.article;

import com.timetraveling.models.article.resources.Resource;
import com.timetraveling.models.article.steps.Step;
import com.timetraveling.models.article.stepsresources.StepResource;

import javax.persistence.Entity;
import java.io.Serializable;
import java.util.*;

public final class Article implements Serializable {
    private Set<Step> steps = new TreeSet<>();

    private Map<String, List<String>> stepResourceMap = new HashMap<>();
    //private Map<Integer, List<Integer>> stepResourceMap = new HashMap<>();

    public Article(Map<String, List<String>> stepResourceMap) {
        this.stepResourceMap = stepResourceMap;
    }

    public Map<String, List<String>> getStepResourceMap() {
        return stepResourceMap;
    }

    public void setStepResourceMap(Map<String, List<String>> stepResourceMap) {
        this.stepResourceMap = stepResourceMap;
    }
}
