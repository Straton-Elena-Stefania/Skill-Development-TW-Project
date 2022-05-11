package com.timetraveling.models.skillmodel;

import javax.persistence.*;

@Entity
@Table(name = "registered_skills")
public class SkillModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "url")
    private String url;

    @Column(name = "sideimage_link")
    private String sideimageLink;

    @Column(name = "image_presentation_link")
    private String imagePresentationLink;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSideimageLink() {
        return sideimageLink;
    }

    public void setSideimageLink(String sideimageLink) {
        this.sideimageLink = sideimageLink;
    }

    public String getImagePresentationLink() {
        return imagePresentationLink;
    }

    public void setImagePresentationLink(String imagePresentationLink) {
        this.imagePresentationLink = imagePresentationLink;
    }
}
