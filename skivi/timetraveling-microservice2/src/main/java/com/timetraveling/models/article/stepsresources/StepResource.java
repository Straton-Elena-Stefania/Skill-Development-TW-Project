package com.timetraveling.models.article.stepsresources;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="steps_resources")
@NamedQueries({
        @NamedQuery(name = "StepResource.findByStepId",
                query = "SELECT m FROM StepResource m WHERE m.stepId=:stepId")})
public class StepResource implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private int id;

    @Column(name = "step_id")
    private int stepId;

    @Column(name = "resource_id")
    private int resourceId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStepId() {
        return stepId;
    }

    public void setStepId(int stepId) {
        this.stepId = stepId;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }
}
