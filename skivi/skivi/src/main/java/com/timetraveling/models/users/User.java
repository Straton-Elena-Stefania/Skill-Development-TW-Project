package com.timetraveling.models.users;

import com.google.gson.annotations.Expose;
import com.timetraveling.models.skills.Skill;
import com.timetraveling.models.skills.SkillStatus;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @GenericGenerator(name = "UserCustomPKGenerator", strategy = "com.timetraveling.models.UserCustomPKGenerator")
    @GeneratedValue(generator = "UserCustomPKGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private int id;

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "description")
    private String description = "";

    @Column(name = "time_travelling")
    private SkillStatus timetraveling = SkillStatus.UNACTIVATED;

    @Column(name = "cooking")
    private SkillStatus cooking = SkillStatus.UNACTIVATED;

    @Column(name = "first_aid")
    private SkillStatus firstAid = SkillStatus.UNACTIVATED;

    @Lob
    @Column(name = "profile_picture")
    private byte[] profilePicture = null;

    @Column(name = "profile_picture_type")
    private String profilePictureType = null;

    @Column(name = "admin")
    private boolean admin;

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public User() {
    }

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public SkillStatus getTimetraveling() {
        return timetraveling;
    }

    public void setTimetraveling(SkillStatus timetraveling) {
        this.timetraveling = timetraveling;
    }

    public SkillStatus getCooking() {
        return cooking;
    }

    public void setCooking(SkillStatus cooking) {
        this.cooking = cooking;
    }

    public SkillStatus getFirstAid() {
        return firstAid;
    }

    public void setFirstAid(SkillStatus firstAid) {
        this.firstAid = firstAid;
    }

    public String getProfilePicture() {
        if (profilePicture == null) {
            return "resources/images/user.png";
        }
        return "data:" + profilePictureType + ";base64," + Base64.getEncoder().encodeToString(profilePicture);
    }

    public void setProfilePicture(byte[] profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getProfilePictureType() {
        return profilePictureType;
    }

    public void setProfilePictureType(String profilePictureType) {
        this.profilePictureType = profilePictureType;
    }

    public List<String> getSkills() {
        List<String> skills = new ArrayList<>();

        if (firstAid != SkillStatus.UNACTIVATED) {
            skills.add("firstAid");
        }
        if (cooking != SkillStatus.UNACTIVATED) {
            skills.add("cooking");
        }
        if (timetraveling != SkillStatus.UNACTIVATED) {
            skills.add("timetraveling");
        }

        return skills;
    }

    public List<String> getFavouriteSkills() {
        List<String> skills = new ArrayList<>();

        if (firstAid == SkillStatus.FAVOURITE) {
            skills.add("firstAid");
        }
        if (cooking == SkillStatus.FAVOURITE) {
            skills.add("cooking");
        }
        if (timetraveling == SkillStatus.FAVOURITE) {
            skills.add("timetravelling");
        }

        return skills;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                '}';
    }
}