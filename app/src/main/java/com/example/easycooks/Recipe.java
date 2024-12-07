package com.example.easycooks;

import android.os.Bundle;

import com.example.easycooks.recipe.DietRecipe;
import com.example.easycooks.recipe.KoreanRecipe;
import com.example.easycooks.recipe.LowSaltRecipe;
import com.example.easycooks.recipe.LowSugarRecipe;
import com.example.easycooks.recipe.VeganRecipe;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Recipe implements IRecipe {
    private String title;
    private String ingredients;
    private String description;
    private String cookingTime;
    private String servings;
    private int imageResourceId;
    private String difficulty;

    public Recipe(String title, String ingredients, String description,
            String cookingTime, String servings, int imageResourceId, String difficulty) {
        this.title = title;
        this.ingredients = ingredients;
        this.description = description;
        this.cookingTime = cookingTime;
        this.servings = servings;
        this.imageResourceId = imageResourceId;
        this.difficulty = difficulty;
    }

    public static Recipe createDetailedRecipe(String newName, String ingredients, String description,
                                              String cookingTime, String servings, int imageResourceId, String difficulty) {
        return new Recipe(newName, ingredients, description, cookingTime, servings, imageResourceId, difficulty);
    }

    // Getters
    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getIngredients() {
        return ingredients;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getCookingTime() {
        return cookingTime;
    }

    @Override
    public String getServings() {
        return servings;
    }

    @Override
    public int getImageResourceId() {
        return imageResourceId;
    }

    @Override
    public String getDifficulty() {
        return difficulty;
    }

    // Setters
    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public void setCookingTime(String cookingTime) {
        this.cookingTime = cookingTime;
    }

    @Override
    public void setServings(String servings) {
        this.servings = servings;
    }

    @Override
    public void setImageResourceId(int imageResourceId) {
        this.imageResourceId = imageResourceId;
    }

    @Override
    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    // toBundle 메서드
    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putString("ingredients", ingredients);
        bundle.putString("description", description);
        bundle.putString("cookingTime", cookingTime);
        bundle.putString("servings", servings);
        bundle.putInt("imageResourceId", imageResourceId);
        bundle.putString("difficulty", difficulty);
        return bundle;
    }

    // fromBundle 메서드
    public static Recipe fromBundle(Bundle bundle) {
        String recipeType = bundle.getString("recipeType", "");

        switch (recipeType) {
            case "korean":
                return KoreanRecipe.fromBundle(bundle);
            case "diet":
                return DietRecipe.fromBundle(bundle);
            case "lowSalt":
                return LowSaltRecipe.fromBundle(bundle);
            case "vegan":
                return VeganRecipe.fromBundle(bundle);
            case "lowSugar":
                return LowSugarRecipe.fromBundle(bundle);
            default:
                return new Recipe(
                        bundle.getString("title", ""),
                        bundle.getString("ingredients", ""),
                        bundle.getString("description", ""),
                        bundle.getString("cookingTime", ""),
                        bundle.getString("servings", ""),
                        bundle.getInt("imageResourceId", 0),
                        bundle.getString("difficulty", "보통")
                );
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Recipe recipe = (Recipe) o;
        return imageResourceId == recipe.imageResourceId &&
                title.equals(recipe.title) &&
                ingredients.equals(recipe.ingredients);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, ingredients, imageResourceId);
    }
} 