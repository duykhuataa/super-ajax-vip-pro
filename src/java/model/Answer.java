package model;

import java.sql.Timestamp;

public class Answer {
    private int answerId;
    private String answerText;
    private Timestamp dateCreated;
    private byte isDeleted;

    public Answer() {
    }

    public Answer(int answerId, String answerText, Timestamp dateCreated, byte isDeleted) {
        this.answerId = answerId;
        this.answerText = answerText;
        this.dateCreated = dateCreated;
        this.isDeleted = isDeleted;
    }

    public int getAnswerId() {
        return answerId;
    }    

    public String getAnswerText() {
        return answerText;
    }

    public Timestamp getDateCreated() {
        return dateCreated;
    }

    public byte getIsDeleted() {
        return isDeleted;
    }
    
    public String getIsDeletedString() {
        return (isDeleted == 0) ? "False" : "True";
    }
}
