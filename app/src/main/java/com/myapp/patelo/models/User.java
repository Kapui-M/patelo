package com.myapp.patelo.models;

public class User {
    private String fullName;
    private String email;
    private String accountType;

    public User() {} // Needed for Firestore

    public User(String fullName, String email, String accountType) {
        this.fullName = fullName;
        this.email = email;
        this.accountType = accountType;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getAccountType() {
        return accountType;
    }
}
