package com.example.easycooks.recipe;

import android.os.Bundle;

import com.example.easycooks.Recipe;

public class DietRecipe extends Recipe {
    private int calories;
    private int protein;
    private boolean isVegan;

    private DietRecipe(Builder builder) {
        super(builder.title, builder.ingredients, builder.description,
                builder.cookingTime, builder.servings, builder.imageResourceId,
                builder.difficulty);
        this.calories = builder.calories;
        this.protein = builder.protein;
        this.isVegan = builder.isVegan;
    }

    public int getCalories() { return calories; }
    public int getProtein() { return protein; }
    public boolean isVegan() { return isVegan; }

    @Override
    public Bundle toBundle() {
        Bundle bundle = super.toBundle();
        bundle.putInt("calories", calories);
        bundle.putInt("protein", protein);
        bundle.putBoolean("isVegan", isVegan);
        bundle.putString("recipeType", "diet");
        return bundle;
    }

    public static DietRecipe fromBundle(Bundle bundle) {
        return new DietRecipe.Builder(
                bundle.getString("title", ""),
                bundle.getString("ingredients", ""),
                bundle.getInt("imageResourceId", 0))
                .description(bundle.getString("description", ""))
                .cookingTime(bundle.getString("cookingTime", ""))
                .servings(bundle.getString("servings", ""))
                .difficulty(bundle.getString("difficulty", "보통"))
                .calories(bundle.getInt("calories", 0))
                .protein(bundle.getInt("protein", 0))
                .veganOption()
                .build();
    }

    public static class Builder {
        private String title;
        private String ingredients;
        private String description = "";
        private String cookingTime = "20분";
        private String servings = "1인분";
        private int imageResourceId;
        private String difficulty = "쉬움";
        private int calories = 0;
        private int protein = 0;
        private boolean isVegan = false;

        public Builder(String title, String ingredients, int imageResourceId) {
            this.title = title;
            this.ingredients = ingredients;
            this.imageResourceId = imageResourceId;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder cookingTime(String cookingTime) {
            this.cookingTime = cookingTime;
            return this;
        }

        public Builder servings(String servings) {
            this.servings = servings;
            return this;
        }

        public Builder difficulty(String difficulty) {
            this.difficulty = difficulty;
            return this;
        }

        public Builder calories(int calories) {
            this.calories = calories;
            return this;
        }

        public Builder protein(int protein) {
            this.protein = protein;
            return this;
        }

        public Builder veganOption() {
            this.isVegan = isVegan;
            return this;
        }

        public DietRecipe build() {
            return new DietRecipe(this);
        }
    }
}