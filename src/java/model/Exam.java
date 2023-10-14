package model;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class Exam {

    public static final int STATUS_NOT_STARTED = -1;
    public static final int STATUS_OPENING = 0;
    public static final int STATUS_CLOSED = 1;

    private int examId, status, maxAttempts, classId, duration;
    private String examName;
    private byte isVisible, isDisplayDetails, isDeleted;
    private Timestamp dateStart, dateEnd, dateCreated;

    public Exam() {
    }

    public Exam(int examId, String examName, int classId, byte isVisible, int status, Timestamp dateStart, Timestamp dateEnd, int duration, byte isDisplayDetails, int maxAttempts, Timestamp dateCreated, byte isDeleted) {
        this.examId = examId;
        this.duration = duration;
        this.status = status;
        this.maxAttempts = maxAttempts;
        this.classId = classId;
        this.examName = examName;
        this.isVisible = isVisible;
        this.isDisplayDetails = isDisplayDetails;
        this.isDeleted = isDeleted;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.dateCreated = dateCreated;
    }

    public int getExamId() {
        return examId;
    }

    public int getStatus() {
        return status;
    }

    public String getStatusString() {
        switch (status) {
            case STATUS_NOT_STARTED:
                return "Not started";
            case STATUS_OPENING:
                return "Opening";
            case STATUS_CLOSED:
                return "Closed";
            default:
                return "Error";
        }
    }

    public int getMaxAttempts() {
        return maxAttempts;
    }

    public String getExamName() {
        return examName;
    }

    public int getClassId() {
        return classId;
    }

    public byte getIsVisible() {
        return isVisible;
    }

    public String getIsVisibleString() {
        return (isVisible == 0) ? "Private" : "Public";
    }

    public byte getIsDisplayDetails() {
        return isDisplayDetails;
    }

    public String getIsDisplayString() {
        return (isDeleted == 0) ? "False" : "True";
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

    public String getDateStartString() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(dateStart.getTime());
    }

    public Timestamp getDateEnd() {
        return dateEnd;
    }

    public String getDateEndString() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(dateEnd.getTime());
    }
    
    public int getDuration() {
        return duration;
    }

    public String getDurationString() {
        int seconds = this.duration;

        int hours = seconds / 3600;
        seconds %= 3600;

        int minutes = seconds / 60;
        seconds %= 60;

        StringBuilder result = new StringBuilder();
        if (hours > 0) {
            result.append(hours).append("h ");
        }

        if (minutes > 0) {
            result.append(minutes).append("m ");
        }

        if (seconds > 0) {
            result.append(seconds).append("s ");
        }

        return result.toString();
    }

    public Timestamp getDateCreated() {
        return dateCreated;
    }

}
