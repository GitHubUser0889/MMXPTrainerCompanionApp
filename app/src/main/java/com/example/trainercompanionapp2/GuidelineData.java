package com.example.trainercompanionapp2;

public class GuidelineData {

    public GuidelineData(String title, String instructions) {
        Title = title;
        Instructions = instructions;
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

    private String Title;
    private String Instructions;
    public GuidelineData() {
    }
}
