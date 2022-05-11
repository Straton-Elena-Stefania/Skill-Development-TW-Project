package com.timetraveling.models.email;

public class Email {
    private String subject;
    private String body;
    private String updateType;
    private String skill;

    public Email() {
    }

    public Email(String subject, String body, String updateType, String skill) {
        this.subject = subject;
        this.body = body;
        this.updateType = updateType;
        this.skill = skill;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getUpdateType() {
        return updateType;
    }

    public void setUpdateType(String updateType) {
        System.out.println("upda");
        this.updateType = updateType;
        System.out.println("te");
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        System.out.println("set skill");
        this.skill = skill;
        System.out.println("sunt doar un setter");
    }

    @Override
    public String toString() {
        return "Email{" +
                "subject='" + subject + '\'' +
                ", body='" + body + '\'' +
                ", updateType='" + updateType + '\'' +
                ", skill='" + skill + '\'' +
                '}';
    }
}


