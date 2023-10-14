package model;

import java.sql.Timestamp;

public class Admin extends User {

    public Admin() {
    }

    public Admin(int userId, String username, String password, String oauthId, String email, Role role, String fullName, String image, Timestamp dateCreated, byte isDeleted, byte isActived) {
        super(userId, username, password, oauthId, email, role, fullName, image, dateCreated, isDeleted, isActived);
    }

    
}
