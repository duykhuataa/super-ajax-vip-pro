package model;

import java.sql.Timestamp;

public class ExamAttempt {

    public static final int STATUS_SUSPENDED = -1;
    public static final int STATUS_ONGOING = 0;
    public static final int STATUS_FINISHED = 1;
    
    private int examAttemptId,userId, examId, attemptNo, status,duration;
    private float score;
    private byte isDeleted;
    private Timestamp dateStart;
    

    public ExamAttempt() {
    }

    public ExamAttempt(int examAttemptId,int userId, int examId, int status, float score,Timestamp dateStart, int duration ,byte isDeleted) {
        this.examAttemptId = examAttemptId;
        this.userId = userId;
        this.examId = examId;
        this.status = status;
        this.score = score;
        this.dateStart = dateStart;
        this.duration = duration;
        this.isDeleted = isDeleted;
    }

    public int getExamAttemptId() {
        return examAttemptId;
    }
    
    public int getUserId() {
        return userId;
    }

    public int getExamId() {
        return examId;
    }

    public int getAttemptNo() {
        return attemptNo;
    }

    public int getStatus() {
        return status;
    }

    public float getScore() {
        return score;
    }

    public byte getIsDeleted() {
        return isDeleted;
    }

    public String getIsDeletedString() {
        return (isDeleted == 0) ? "False" : "True";
    }

    public Timestamp getDateStart() {
        return dateStart;
    }

    public int getDuration() {
        return duration;
    }

    

}
