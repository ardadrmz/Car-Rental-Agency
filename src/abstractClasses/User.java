package abstractClasses;

import enums.UserRole;

public abstract class User {

    private String name;
    private String userID;
    private UserRole role;

    public User(String name, String userID, UserRole role) {
        this.name = name;
        this.userID = userID;
        this.role = role;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getUserID() { return userID; }
    public void setUserID(String userID) { this.userID = userID; }

    public UserRole getRole() { return role; }
    public void setRole(UserRole role) { this.role = role; }

    @Override
    public String toString() {
        return getUserID() + "," + getName() + "," + getRole();
    }
}