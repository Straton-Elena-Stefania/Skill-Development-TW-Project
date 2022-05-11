package com.timetraveling.models.userachievements;

import javax.persistence.*;

@Entity
@Table(name = "user_achievements")
@NamedQueries({
        @NamedQuery(name = "UserAchievement.findByUserId",
                query = "SELECT m FROM UserAchievement m WHERE m.userId=:userId"),
        @NamedQuery(name = "UserAchievement.findByUserAchievement",
                query = "SELECT m FROM UserAchievement m WHERE m.achievementId=:achievementId and m.userId=:userId")
})
public class UserAchievement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private int id;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "achievement_id")
    private int achievementId;

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

    public int getAchievementId() {
        return achievementId;
    }

    public void setAchievementId(int achievementId) {
        this.achievementId = achievementId;
    }
}