
package dto;


public class UserServiceDTO {
    private int index;
    private String userName,email,role, fullName, Image, dateCreated, isDeleted;

    public UserServiceDTO(int index, String userName, String email, String role, String fullName, String Image, String dateCreated, String isDeleted) {
        this.index = index;
        this.userName = userName;
        this.email = email;
        this.role = role;
        this.fullName = fullName;
        this.Image = Image;
        this.dateCreated = dateCreated;
        this.isDeleted = isDeleted;
    }

    public int getIndex() {
        return index;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    public String getFullName() {
        return fullName;
    }

    public String getImage() {
        return Image;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public String getIsDeleted() {
        return isDeleted;
    }
    
    

}
