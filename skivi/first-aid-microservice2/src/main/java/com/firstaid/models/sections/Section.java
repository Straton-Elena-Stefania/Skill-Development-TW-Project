package com.firstaid.models.sections;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="sections")
@NamedQueries({
        @NamedQuery(name = "Section.findByName",
                query = "SELECT m FROM Section m WHERE m.name=:name")})
public class Section implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GenericGenerator(name = "SectionCustomPKGenerator", strategy = "com.firstaid.models.SectionCustomPKGenerator")
    @GeneratedValue(generator = "SectionCustomPKGenerator")
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name="name")
    private String name;

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
}
