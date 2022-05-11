package com.timetraveling.models.article;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Article {
    private Map<Step, List<Resource>> stepResourceMap = new HashMap<>();

    public Article(Map<Step, List<Resource>> stepResourceMap) {
        this.stepResourceMap = stepResourceMap;
    }

    public Map<Step, List<Resource>> getStepResourceMap() {
        return stepResourceMap;
    }

    public void setStepResourceMap(Map<Step, List<Resource>> stepResourceMap) {
        this.stepResourceMap = stepResourceMap;
    }
}
