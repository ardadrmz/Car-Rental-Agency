package abstractClasses;

import enums.UserRole;

public abstract class User {
    private String name;
    private String contactNo;
    private String userID;
    private UserRole role;

    public User(String name, String contactNo, String userID, UserRole role) {
        this.name = name;
        this.contactNo = contactNo;
        this.userID = userID;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
   
    @Override
    public String toString(){
        return getUserID() + "," + getName() + "," + getContactNo() + "," + getRole();
    }
       
}
