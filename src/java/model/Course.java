package model;


public class Course {

    private int courseId;
    private String courseCode, courseName;
    private String dateCreated;
    private byte isDeleted;
    
    public Course() {
    }

    public Course(int courseId, String courseCode, String courseName, String dateCreated, byte isDeleted) {
        this.courseId = courseId;
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.dateCreated = dateCreated;
        this.isDeleted = isDeleted;
    }

    public Course(int courseId, String courseCode, String courseName, String dateCreated) {
        this.courseId = courseId;
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.dateCreated = dateCreated;
    }

    public int getCourseId() {
        return courseId;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public byte getIsDeleted() {
        return isDeleted;
    }

    public String getIsDeletedString() {
        return (isDeleted == 0) ? "False" : "True";
    }

}
