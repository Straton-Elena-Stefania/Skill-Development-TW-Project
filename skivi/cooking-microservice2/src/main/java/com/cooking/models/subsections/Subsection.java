package com.cooking.models.subsections;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "subsections")
@NamedQueries({
        @NamedQuery(name = "Subsection.findBySectionId",
                query = "SELECT m FROM Subsection m WHERE m.sectionId=:sectionId")})
public class Subsection implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private int id;

    @Column(name="name")
    private String name;

    @Column(name="section_id")
    private int sectionId;

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

    public int getSectionId() {
        return sectionId;
    }

    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
    }
}
