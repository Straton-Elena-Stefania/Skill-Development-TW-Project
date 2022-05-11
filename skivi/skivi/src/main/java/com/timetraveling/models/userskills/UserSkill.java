package com.timetraveling.models.userskills;

import javax.persistence.*;

@Entity
@Table(name = "user_skills")
@NamedQueries({
        @NamedQuery(name = "UserSkill.findByUserId",
                query = "SELECT m FROM UserSkill m WHERE m.userId=:userId"),
        @NamedQuery(name = "UserSkill.findBySkillId",
                query = "SELECT m FROM UserSkill m WHERE m.skillId=:skillId"),
        @NamedQuery(name = "UserSkill.findByUserSkill",
                query = "SELECT m FROM UserSkill m WHERE m.skillId=:skillId and m.userId=:userId")
})
public class UserSkill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private int id;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "skill_id")
    private int skillId;

    @Column(name = "score")
    private int score;

    @Column(name = "favorited")
    private boolean favorited = false;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getSkillId() {
        return skillId;
    }

    public void setSkillId(int skillId) {
        this.skillId = skillId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean isFavorited() {
        return favorited;
    }

    public void setFavorited(boolean favorited) {
        this.favorited = favorited;
    }
}
