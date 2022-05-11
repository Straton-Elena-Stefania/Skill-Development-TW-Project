package com.firstaid.models.article;

import com.firstaid.models.article.steps.Step;

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
