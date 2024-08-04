package com.example.trainercompanionapp2;

public class ClientData {

    private String Name;
    private String Age;
    private String Contact;
    private String Gender;

    private String Weight;
    private String Height;
    private String Workout;
    private String Guideline;
    private String Sessions;
    private String Notes;

    public ClientData(String name, String age, String contact, String gender, String weight, String height, String workout, String guideline, String sessions, String notes) {
        Name = name;
        Age = age;
        Contact = contact;
        Gender = gender;
        Weight = weight;
        Height = height;
        Workout = workout;
        Guideline = guideline;
        Sessions = sessions;
        Notes = notes;
    }



    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAge() {
        return Age;
    }

    public void setAge(String age) {
        Age = age;
    }

    public String getContact() {
        return Contact;
    }

    public void setContact(String contact) {
        Contact = contact;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getWeight() {
        return Weight;
    }

    public void setWeight(String weight) {
        Weight = weight;
    }

    public String getHeight() {
        return Height;
    }

    public void setHeight(String height) {
        Height = height;
    }

    public String getWorkout() {
        return Workout;
    }

    public void setWorkout(String workout) {
        Workout = workout;
    }

    public String getGuideline() {
        return Guideline;
    }

    public void setGuideline(String guideline) {
        Guideline = guideline;
    }

    public String getSessions() {
        return Sessions;
    }

    public void setSessions(String sessions) {
        Sessions = sessions;
    }

    public String getNotes() {
        return Notes;
    }

    public void setNotes(String notes) {
        Notes = notes;
    }



    public ClientData() {
    }
}
