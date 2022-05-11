package com.timetraveling.models.article;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

public class Resource {
    private static final long serialVersionUID = 1L;

    private int id;

    private String resourceLink;

    private String resourceImageLink;

    private String resourceDescription;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getResourceLink() {
        return resourceLink;
    }

    public void setResourceLink(String resourceLink) {
        this.resourceLink = resourceLink;
    }

    public String getResourceImageLink() {
        return resourceImageLink;
    }

    public void setResourceImageLink(String resourceImageLink) {
        this.resourceImageLink = resourceImageLink;
    }

    public String getResourceDescription() {
        return resourceDescription;
    }

    public void setResourceDescription(String resourceDescription) {
        this.resourceDescription = resourceDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Resource)) return false;
        Resource resource = (Resource) o;
        return getId() == resource.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
