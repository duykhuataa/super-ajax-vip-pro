package model;

import dao.ClassDAO;
import dao.CourseDAO;
import dao.UserDAO;
import java.sql.Timestamp;

public class Class {

    private int classId;
    private String className;
    private int courseId;
    private String classEnrollKey;
    private Timestamp dateCreated;
    private String classCode;
    private byte isDeleted;

    public Class() {
    }

    public Class(int classId, String className, int courseId, String classEnrollKey, Timestamp dateCreated, String classCode,byte isDeleted) {
        this.classId = classId;
        this.className = className;
        this.courseId = courseId;
        this.classEnrollKey = classEnrollKey;
        this.dateCreated = dateCreated;
        this.classCode = classCode;
        this.isDeleted = isDeleted;
    }

    public int getClassId() {
        return classId;
    }

    public String getClassName() {
        return className;
    }

    public int getCourseId() {
        return courseId;
    }

    public String getClassEnrollKey() {
        return this.classEnrollKey;
    }

    public Timestamp getDateCreated() {
        return dateCreated;
    }

    public String getDateCreatedString() {
        return dateCreated.toString();
    }

    public byte getIsDeleted() {
        return isDeleted;
    }

    public String getIsDeletedString() {
        return isDeleted == 0 ? "False" : "True";
    }
    
    public Course getCourse() {
        return new CourseDAO().getCourse(this.courseId);
    }
    
    public User getTeacher() {
        return new ClassDAO().getTeacherByClassId(classId);
    }

    public String getClassCode() {
        return classCode;
    }
    
}
