package org.example.models;

public class Users {

    private int id;
    private String userName;
    private String passwordHash;
    private int userTypesId;

    public Users(int id, String userName, String passwordHash, int userTypesId) {
        this.id = id;
        this.userName = userName;
        this.passwordHash = passwordHash;
        this.userTypesId = userTypesId;
    }

    public Users() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public int getUserTypesId() {
        return userTypesId;
    }

    public void setUserTypesId(int userTypesId) {
        this.userTypesId = userTypesId;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + userName + '\'' +
                ", passwordHash='" + passwordHash + '\'' +
                ", username='" + userTypesId + '\'' +
                '}';
    }
}
