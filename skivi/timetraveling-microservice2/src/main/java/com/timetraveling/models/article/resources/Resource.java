package com.timetraveling.models.article.resources;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name="resources")
public class Resource implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private int id;

    @Column(name = "resource_link")
    private String resourceLink;

    @Column(name = "resource_image_link")
    private String resourceImageLink;

    @Column(name = "resource_description")
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
