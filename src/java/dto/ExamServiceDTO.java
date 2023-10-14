package dto;

import java.util.ArrayList;

public class ExamServiceDTO {

    private int examId;
    private ArrayList<QuestionDTO> questions;
    private int examDuration;

    public ExamServiceDTO() {
    }

    public ExamServiceDTO(int examId, ArrayList<QuestionDTO> questions, int durationExam) {
        this.examId = examId;
        this.questions = questions;
        this.examDuration = durationExam;
    }

    public int getExamId() {
        return examId;
    }

    public void setExamId(int examId) {
        this.examId = examId;
    }

    public ArrayList<QuestionDTO> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<QuestionDTO> questions) {
        this.questions = questions;
    }

    public double getExamDuration() {
        return examDuration;
    }

    public void setExamDuration(int examDuration) {
        this.examDuration = examDuration;
    }

}
