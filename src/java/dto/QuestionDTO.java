package dto;

import java.util.ArrayList;
import model.Answer;

public class QuestionDTO {

    private int questionIndex;
    private int questionId;
    private String questionText;
    private byte questionType;
    private ArrayList<AnswerDTO> answers = new ArrayList<>();

    public QuestionDTO() {
    }

    public QuestionDTO(int questionIndex, int questionId, String questionText, byte questionType, ArrayList<Answer> answers) {
        this.questionIndex = questionIndex;
        this.questionId = questionId;
        this.questionText = questionText;
        this.questionType = questionType;
        for (Answer a : answers) {
            this.answers.add(new AnswerDTO(a));
        }
    }

    public int getQuestionIndex() {
        return questionIndex;
    }

    public void setQuestionIndex(int questionIndex) {
        this.questionIndex = questionIndex;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public byte getQuestionType() {
        return questionType;
    }

    public void setQuestionType(byte questionType) {
        this.questionType = questionType;
    }

    public ArrayList<AnswerDTO> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<AnswerDTO> answers) {
        this.answers = answers;
    }

}
