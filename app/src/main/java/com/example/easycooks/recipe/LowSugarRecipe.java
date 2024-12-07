package com.example.easycooks.recipe;

import android.os.Bundle;

import com.example.easycooks.Recipe;

public class LowSugarRecipe extends Recipe {
    private int sugarContent;
    private String sweetener;
    private boolean isKetogenic;

    private LowSugarRecipe(Builder builder) {
        super(builder.title, builder.ingredients, builder.description,
                builder.cookingTime, builder.servings, builder.imageResourceId,
                builder.difficulty);
        this.sugarContent = builder.sugarContent;
        this.sweetener = builder.sweetener;
        this.isKetogenic = builder.isKetogenic;
    }

    public int getSugarContent() { return sugarContent; }
    public String getSweetener() { return sweetener; }
    public boolean isKetogenic() { return isKetogenic; }

    @Override
    public Bundle toBundle() {
        Bundle bundle = super.toBundle();
        bundle.putInt("sugarContent", sugarContent);
        bundle.putString("sweetener", sweetener);
        bundle.putBoolean("isKetogenic", isKetogenic);
        bundle.putString("recipeType", "lowSugar");
        return bundle;
    }

    public static LowSugarRecipe fromBundle(Bundle bundle) {
        return new LowSugarRecipe.Builder(
                bundle.getString("title", ""),
                bundle.getString("ingredients", ""),
                bundle.getInt("imageResourceId", 0))
                .description(bundle.getString("description", ""))
                .cookingTime(bundle.getString("cookingTime", ""))
                .servings(bundle.getString("servings", ""))
                .difficulty(bundle.getString("difficulty", "보통"))
                .sugarContent(bundle.getInt("sugarContent", 0))
                .sweetener(bundle.getString("sweetener", ""))
                .setKetogenic()
                .build();
    }

    public static class Builder {
        private String title;
        private String ingredients;
        private String description = "";
        private String cookingTime = "35분";
        private String servings = "4인분";
        private int imageResourceId;
        private String difficulty = "중급";
        private int sugarContent = 0;
        private String sweetener = "";
        private boolean isKetogenic = false;

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

        public Builder sugarContent(int content) {
            this.sugarContent = content;
            return this;
        }

        public Builder sweetener(String sweetener) {
            this.sweetener = sweetener;
            return this;
        }

        public Builder setKetogenic() {
            this.isKetogenic = isKetogenic;
            return this;
        }

        public LowSugarRecipe build() {
            return new LowSugarRecipe(this);
        }
    }
}