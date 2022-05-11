package com.timetraveling.models.article;

import javax.persistence.Column;

public class Question implements Comparable<Question> {
    private int id;

    private int subsectionId;

    private int correctAnswerId;

    private String questionText;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSubsectionId() {
        return subsectionId;
    }

    public void setSubsectionId(int subsectionId) {
        this.subsectionId = subsectionId;
    }

    public int getCorrectAnswerId() {
        return correctAnswerId;
    }

    public void setCorrectAnswerId(int correctAnswerId) {
        this.correctAnswerId = correctAnswerId;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    @Override
    public int compareTo(Question o) {
        if (o.getId() < this.id) {
            return -1;
        } else if (o.getId() == this.id) {
            return 0;
        }

        return 1;
    }
}
