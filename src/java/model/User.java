package model;

import java.sql.Timestamp;

public class User {

    private int userId;
    private String username, password, oauthId, email;
    private Role role;
    private String fullName, image;

    private Timestamp dateCreated;
    private byte isDeleted, isActive;
    
    public User() {
    }

    public User(int userId, String username, String password, String oauthId, String email, Role role, String fullName, String image, Timestamp dateCreated, byte isDeleted,byte isActived) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.oauthId = oauthId;
        this.email = email;
        this.role = role;
        this.fullName = fullName;
        this.image = image;
        this.dateCreated = dateCreated;
        this.isDeleted = isDeleted;
        this.isActive = isActived;
    }

    public int getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getOauthId() {
        return oauthId;
    }

    public String getEmail() {
        try{
            return (!email.equals("null") ? this.email = email : "");
        }catch(Exception e) {
            return "";
        }
    }

    public Role getRole() {
        return role;
    }
  
    public String getFullName() {
        return fullName;
    }

    public String getImage() {
        return image;
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

    public byte getIsActive() {
        return isActive;
    }
    
    public String getIsActiveString() {
        return (isActive == 0) ? "False" : "True";
    }
}
