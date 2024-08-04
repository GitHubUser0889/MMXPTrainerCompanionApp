package com.example.trainercompanionapp2;

public class RegisteredData {
    public RegisteredData() {
    }

    public String getDatabaseUserID() {
        return databaseUserID;
    }

    public void setDatabaseUserID(String databaseUserID) {
        this.databaseUserID = databaseUserID;
    }

    public String getDatabaseEmail() {
        return databaseEmail;
    }

    public void setDatabaseEmail(String databaseEmail) {
        this.databaseEmail = databaseEmail;
    }

    public String getDatabaseUsername() {
        return databaseUsername;
    }

    public void setDatabaseUsername(String databaseUsername) {
        this.databaseUsername = databaseUsername;
    }

    public String getDatabaseContact() {
        return databaseContact;
    }

    public void setDatabaseContact(String databaseContact) {
        this.databaseContact = databaseContact;
    }

    public RegisteredData(String databaseUserID, String databaseEmail, String databaseUsername, String databaseContact) {
        this.databaseUserID = databaseUserID;
        this.databaseEmail = databaseEmail;
        this.databaseUsername = databaseUsername;
        this.databaseContact = databaseContact;
    }

    private String databaseUserID;
    private String databaseEmail;
    private String databaseUsername;
    private String databaseContact;

}
