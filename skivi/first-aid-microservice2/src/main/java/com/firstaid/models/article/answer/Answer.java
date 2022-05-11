package com.firstaid.models.article.answer;

import javax.persistence.*;

@Entity
@Table(name="test_answers")
@NamedQueries({
        @NamedQuery(name = "Answer.findByTestId",
                query = "SELECT m FROM Answer m WHERE m.testId=:testId")})
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private int id;

    @Column(name = "test_id")
    private int testId;

    @Column(name = "answer_text")
    private String answerText;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTestId() {
        return testId;
    }

    public void setTestId(int testId) {
        this.testId = testId;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }
}
