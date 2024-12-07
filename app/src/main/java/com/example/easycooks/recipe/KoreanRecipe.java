package com.example.easycooks.recipe;

import android.os.Bundle;

import com.example.easycooks.Recipe;

public class KoreanRecipe extends Recipe {
    private String spicyLevel;
    private boolean containsKimchi;

    private KoreanRecipe(Builder builder) {
        super(builder.title, builder.ingredients, builder.description,
                builder.cookingTime, builder.servings, builder.imageResourceId,
                builder.difficulty);
        this.spicyLevel = builder.spicyLevel;
        this.containsKimchi = builder.containsKimchi;
    }

    public String getSpicyLevel() { return spicyLevel; }
    public boolean containsKimchi() { return containsKimchi; }

    @Override
    public Bundle toBundle() {
        Bundle bundle = super.toBundle();
        bundle.putString("spicyLevel", spicyLevel);
        bundle.putBoolean("containsKimchi", containsKimchi);
        bundle.putString("recipeType", "korean");
        return bundle;
    }

    public static KoreanRecipe fromBundle(Bundle bundle) {
        return new KoreanRecipe.Builder(
                bundle.getString("title", ""),
                bundle.getString("ingredients", ""),
                bundle.getInt("imageResourceId", 0))
                .description(bundle.getString("description", ""))
                .cookingTime(bundle.getString("cookingTime", ""))
                .servings(bundle.getString("servings", ""))
                .difficulty(bundle.getString("difficulty", "보통"))
                .spicyLevel(bundle.getString("spicyLevel", "보통"))
                .withKimchi(bundle.getBoolean("containsKimchi", false))
                .build();
    }

    public static class Builder {
        private String title;
        private String ingredients;
        private String description = "";
        private String cookingTime = "30분";
        private String servings = "2인분";
        private int imageResourceId;
        private String difficulty = "보통";
        private String spicyLevel = "보통";
        private boolean containsKimchi = false;

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

        public Builder spicyLevel(String level) {
            this.spicyLevel = level;
            return this;
        }

        public Builder withKimchi(boolean containsKimchi) {
            this.containsKimchi = containsKimchi;
            return this;
        }

        public KoreanRecipe build() {
            return new KoreanRecipe(this);
        }
    }
}