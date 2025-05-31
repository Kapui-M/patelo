package com.myapp.patelo.models;

public class User {
    private String uid;
    private String fullName;
    private String email;
    private String accountType; // optional

    public User() {
        // Required for Firestore deserialization
    }

    public User(String uid, String fullName, String email, String accountType) {
        this.uid = uid;
        this.fullName = fullName;
        this.email = email;
        this.accountType = accountType;
    }

    // Getters and setters
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }
}
