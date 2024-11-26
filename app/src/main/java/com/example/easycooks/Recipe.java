package com.example.easycooks;

public class Recipe {
    private String title;
    private String ingredients;
    private String description;
    private String cookingTime;
    private String servings;
    private int imageResourceId;
    private String difficulty;

    private Recipe(String title, String ingredients, String description, String cookingTime, String servings, int imageResourceId, String difficulty) {
        this.title = title;
        this.ingredients = ingredients;
        this.description = description;
        this.cookingTime = cookingTime;
        this.servings = servings;
        this.imageResourceId = imageResourceId;
        this.difficulty = difficulty;
    }

    //팩토리 패턴 적용.
    public static Recipe createBasicRecipe(String title, String ingredients, int imageResourceId) {
        return new Recipe(title, ingredients, "", "", "", imageResourceId, "");
    }

    public static Recipe createDetailedRecipe(String title, String ingredients, 
            String description, String cookingTime, String servings, 
            int imageResourceId, String difficulty) {
        return new Recipe(title, ingredients, description, cookingTime, 
                servings, imageResourceId, difficulty);
    }

    // Getters
    public String getTitle() {
        return title;
    }

    public String getIngredients() {
        return ingredients;
    }

    public String getDescription() {
        return description;
    }

    public String getCookingTime() {
        return cookingTime;
    }

    public String getServings() {
        return servings;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public String getDifficulty() {
        return difficulty;
    }

    // Setters
    public void setTitle(String title) {
        this.title = title;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCookingTime(String cookingTime) {
        this.cookingTime = cookingTime;
    }

    public void setServings(String servings) {
        this.servings = servings;
    }

    public void setImageResourceId(int imageResourceId) {
        this.imageResourceId = imageResourceId;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }
}

