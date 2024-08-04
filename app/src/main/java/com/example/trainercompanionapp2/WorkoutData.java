package com.example.trainercompanionapp2;

public class WorkoutData {
    public WorkoutData() {
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getInstructions() {
        return Instructions;
    }

    public void setInstructions(String instructions) {
        Instructions = instructions;
    }

    public WorkoutData(String title, String instructions) {
        Title = title;
        Instructions = instructions;
    }

    private String Title;
    private String Instructions;
}
