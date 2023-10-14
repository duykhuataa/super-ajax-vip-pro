package model;

import java.sql.Timestamp;

public class Teacher extends User {

    public Teacher() {
    }

    public Teacher(int userId, String username, String password, String oauthId, String email, Role role, String fullName, String image, Timestamp dateCreated, byte isDeleted, byte isActived) {
        super(userId, username, password, oauthId, email, role, fullName, image, dateCreated, isDeleted, isActived);
    }

    public Teacher(User user) {
        super(user.getUserId(), user.getUsername(), 
                user.getPassword(), user.getOauthId(), user.getEmail(), 
                user.getRole(), user.getFullName(), user.getImage(), 
                user.getDateCreated(), user.getIsDeleted(), user.getIsActive());
    }
}
