package model;

import java.sql.Timestamp;

public class Student extends User {

    public Student() {
    }

    public Student(int userId, String username, String password, String oauthId, String email, Role role, String fullName, String image, Timestamp dateCreated, byte isDeleted, byte isActived) {
        super(userId, username, password, oauthId, email, role, fullName, image, dateCreated, isDeleted, isActived);
    }

}
