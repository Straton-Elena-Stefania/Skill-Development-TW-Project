package com.timetraveling.models.article;

import java.util.Objects;

public class Step implements Comparable<Step> {
    private static final long serialVersionUID = 1L;

    private int id;

    private int subsectionID;

    private int stepNumber;

    private int contentTypeId;

    private String contentDescription;

    private String contentLink;

    private String stepHeader;

    private String stepDescription;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSubsectionID() {
        return subsectionID;
    }

    public void setSubsectionID(int subsectionID) {
        this.subsectionID = subsectionID;
    }

    public int getStepNumber() {
        return stepNumber;
    }

    public void setStepNumber(int stepNumber) {
        this.stepNumber = stepNumber;
    }

    public int getContentTypeId() {
        return contentTypeId;
    }

    public void setContentTypeId(int contentTypeId) {
        this.contentTypeId = contentTypeId;
    }

    public String getContentDescription() {
        return contentDescription;
    }

    public void setContentDescription(String contentDescription) {
        this.contentDescription = contentDescription;
    }

    public String getContentLink() {
        return contentLink;
    }

    public void setContentLink(String contentLink) {
        this.contentLink = contentLink;
    }

    public String getStepHeader() {
        return stepHeader;
    }

    public void setStepHeader(String stepHeader) {
        this.stepHeader = stepHeader;
    }

    public String getStepDescription() {
        return stepDescription;
    }

    public void setStepDescription(String stepDescription) {
        this.stepDescription = stepDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Step)) return false;
        Step step = (Step) o;
        return getId() == step.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public int compareTo(Step other) {
        return Integer.compare(stepNumber, other.getStepNumber());
    }
}
