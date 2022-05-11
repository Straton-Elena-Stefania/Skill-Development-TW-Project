package com.firstaid.models.article.steps;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name="steps")
@NamedQueries({
        @NamedQuery(name = "Step.findBySubsectionId",
                query = "SELECT m FROM Step m WHERE m.subsectionID=:subsectionID")})
public class Step implements Serializable, Comparable<Step> {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private int id;

    @Column(name="subsection_id")
    private int subsectionID;

    @Column(name="step_number")
    private int stepNumber;

    @Column(name="content_type_id")
    private int contentTypeId;

    @Column(name="content_description")
    private String contentDescription;

    @Column(name="content_link")
    private String contentLink;

    @Column(name="step_header")
    private String stepHeader;

    @Column(name="step_description")
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
        return Integer.compare(other.getStepNumber(), stepNumber);
    }
}
