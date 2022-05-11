package com.timetraveling.models.session;

import javax.persistence.*;

@Entity
@Table(name = "session_store")
public class SessionStore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private int id;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "session_id")
    private String sessionId;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
