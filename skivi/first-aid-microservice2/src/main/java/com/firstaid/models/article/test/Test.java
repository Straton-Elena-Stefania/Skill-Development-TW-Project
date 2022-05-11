package com.firstaid.models.article.test;

import com.firstaid.models.article.answer.Answer;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name="test_questions")
@NamedQueries({
        @NamedQuery(name = "Test.findBySubsectionId",
                query = "SELECT m FROM Test m WHERE m.subsectionId=:subsectionId")})
public class Test implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private int id;

    @Column(name = "subsection_id")
    private int subsectionId;

    @Column(name = "correct_answer_id")
    private int correctAnswerId;

    @Column(name = "question_text")
    private String questionText;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

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
}
