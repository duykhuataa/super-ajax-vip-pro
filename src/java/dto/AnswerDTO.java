package dto;

import model.Answer;

public class AnswerDTO {

    private int answerId;
    private String answerText;

    public AnswerDTO() {
    }

    public AnswerDTO(int answerId, String answerText) {
        this.answerId = answerId;
        this.answerText = answerText;
    }

    public AnswerDTO(Answer a) {
        this.answerId = a.getAnswerId();
        this.answerText = a.getAnswerText();
    }
    
    public int getAnswerId() {
        return answerId;
    }

    public void setAnswerId(int answerId) {
        this.answerId = answerId;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

}
