package com.example.easycooks.recipe;

import android.os.Bundle;

import com.example.easycooks.Recipe;

public class LowSaltRecipe extends Recipe {
    private int sodiumContent;
    private boolean hasSaltSubstitute;
    private String saltAlternative;

    private LowSaltRecipe(Builder builder) {
        super(builder.title, builder.ingredients, builder.description,
                builder.cookingTime, builder.servings, builder.imageResourceId,
                builder.difficulty);
        this.sodiumContent = builder.sodiumContent;
        this.hasSaltSubstitute = builder.hasSaltSubstitute;
        this.saltAlternative = builder.saltAlternative;
    }

    public int getSodiumContent() { return sodiumContent; }
    public String getSaltAlternative() { return saltAlternative; }
    public boolean hasSaltSubstitute() { return hasSaltSubstitute; }

    @Override
    public Bundle toBundle() {
        Bundle bundle = super.toBundle();
        bundle.putInt("sodiumContent", sodiumContent);
        bundle.putBoolean("hasSaltSubstitute", hasSaltSubstitute);
        bundle.putString("saltAlternative", saltAlternative);
        bundle.putString("recipeType", "lowSalt");
        return bundle;
    }

    public static LowSaltRecipe fromBundle(Bundle bundle) {
        return new LowSaltRecipe.Builder(
                bundle.getString("title", ""),
                bundle.getString("ingredients", ""),
                bundle.getInt("imageResourceId", 0))
                .description(bundle.getString("description", ""))
                .cookingTime(bundle.getString("cookingTime", ""))
                .servings(bundle.getString("servings", ""))
                .difficulty(bundle.getString("difficulty", "보통"))
                .sodiumContent(bundle.getInt("sodiumContent", 0))
                .withSaltSubstitute(bundle.getString("saltAlternative", ""))
                .build();
    }

    public static class Builder {
        private String title;
        private String ingredients;
        private String description = "";
        private String cookingTime = "25분";
        private String servings = "2인분";
        private int imageResourceId;
        private String difficulty = "보통";
        private int sodiumContent = 500;
        private boolean hasSaltSubstitute = false;
        private String saltAlternative = "";

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

        public Builder sodiumContent(int sodiumContent) {
            this.sodiumContent = sodiumContent;
            return this;
        }

        public Builder withSaltSubstitute(String alternative) {
            this.hasSaltSubstitute = true;
            this.saltAlternative = alternative;
            return this;
        }

        public LowSaltRecipe build() {
            return new LowSaltRecipe(this);
        }
    }
}