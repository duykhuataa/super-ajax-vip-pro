package model;

import java.sql.Timestamp;

public class Question {

    public static final int TYPE_SINGLE_CHOICE = 0;
    public static final int TYPE_MULTIPLE_CHOICE = 1;
    
    private String questionText;
    private int questionID, courseId;
    private Timestamp dateCreated;
    private byte questionType, isDeleted;

    public Question() {
    }

    public Question(String questionText, int questionID, int courseId, Timestamp dateCreated, byte questionType, byte isDeleted) {
        this.questionText = questionText;
        this.questionID = questionID;
        this.courseId = courseId;
        this.dateCreated = dateCreated;
        this.questionType = questionType;
        this.isDeleted = isDeleted;
    }

    public void setQuestionID(int questionID) {
        this.questionID = questionID;
    }
    
    public String getQuestionText() {
        return questionText;
    }

    public int getQuestionID() {
        return questionID;
    }

    public int getCourseId() {
        return courseId;
    }

    public Timestamp getDateCreated() {
        return dateCreated;
    }

    public byte getQuestionType() {
        return questionType;
    }

    public String getQuestionTypeString() {
        return (isDeleted == 0) ? "False" : "True";
    }

    public byte getIsDeleted() {
        return isDeleted;
    }

    public String getIsDeletedString() {
        return (isDeleted == 0) ? "False" : "True";
    }
}
