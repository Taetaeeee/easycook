package com.example.easycooks;

public class CookingStep {
    private int stepNumber;
    private String description;
    private int imageResourceId;

    public CookingStep(int stepNumber, String description, int imageResourceId) {
        this.stepNumber = stepNumber;
        this.description = description;
        this.imageResourceId = imageResourceId;
    }

    public int getStepNumber() {
        return stepNumber;
    }

    public String getDescription() {
        return description;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}