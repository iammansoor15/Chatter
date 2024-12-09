package com.example.chatter.modal;

public class User {
    String profileImg, name, email, password, cnfpassword, userId, lastMsg;
    long timestamp;


    public User(String profileImg, String name, String email, String password, String cnfpassword, String userId, String lastMsg, long timestamp) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.cnfpassword = cnfpassword;
        this.userId = userId;
        this.lastMsg = lastMsg;
        this.profileImg = profileImg;
        this.timestamp = timestamp;
    }

    public User(String name, String email, String password, String cnfpassword) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.cnfpassword = cnfpassword;
    }

    public User() {
    }

    public String getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCnfpassword() {
        return cnfpassword;
    }

    public void setCnfpassword(String cnfpassword) {
        this.cnfpassword = cnfpassword;
    }

    public String getuserId() {
        return userId;
    }

    public void setuserId(String userId) {
        this.userId = userId;
    }

    public String getLastMsg() {
        return lastMsg;
    }

    public void setLastMsg(String lastMsg) {
        this.lastMsg = lastMsg;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}

