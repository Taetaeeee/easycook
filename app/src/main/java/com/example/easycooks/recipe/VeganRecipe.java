package com.example.easycooks.recipe;

import android.os.Bundle;

import com.example.easycooks.Recipe;

import java.util.ArrayList;
import java.util.List;

public class VeganRecipe extends Recipe {
    private List<String> veganProteinSources;
    private boolean isRawVegan;
    private boolean containsNutritionalYeast;

    private VeganRecipe(Builder builder) {
        super(builder.title, builder.ingredients, builder.description,
                builder.cookingTime, builder.servings, builder.imageResourceId,
                builder.difficulty);
        this.veganProteinSources = new ArrayList<>(builder.veganProteinSources);
        this.isRawVegan = builder.isRawVegan;
        this.containsNutritionalYeast = builder.containsNutritionalYeast;
    }

    public List<String> getVeganProteinSources() { return veganProteinSources; }
    public boolean isRawVegan() { return isRawVegan; }
    public boolean containsNutritionalYeast() { return containsNutritionalYeast; }

    @Override
    public Bundle toBundle() {
        Bundle bundle = super.toBundle();
        bundle.putStringArrayList("proteinSources", new ArrayList<>(veganProteinSources));
        bundle.putBoolean("isRawVegan", isRawVegan);
        bundle.putBoolean("containsNutritionalYeast", containsNutritionalYeast);
        bundle.putString("recipeType", "vegan");
        return bundle;
    }

    public static VeganRecipe fromBundle(Bundle bundle) {
        VeganRecipe.Builder builder = new VeganRecipe.Builder(
                bundle.getString("title", ""),
                bundle.getString("ingredients", ""),
                bundle.getInt("imageResourceId", 0))
                .description(bundle.getString("description", ""))
                .cookingTime(bundle.getString("cookingTime", ""))
                .servings(bundle.getString("servings", ""))
                .difficulty(bundle.getString("difficulty", "보통"));

        ArrayList<String> proteinSources = bundle.getStringArrayList("proteinSources");
        if (proteinSources != null) {
            for (String source : proteinSources) {
                builder.addProteinSource(source);
            }
        }

        if (bundle.getBoolean("isRawVegan", false)) {
            builder.setRawVegan();
        }

        if (bundle.getBoolean("containsNutritionalYeast", false)) {
            builder.withNutritionalYeast();
        }

        return builder.build();
    }

    public static class Builder {
        private String title;
        private String ingredients;
        private String description = "";
        private String cookingTime = "30분";
        private String servings = "2인분";
        private int imageResourceId;
        private String difficulty = "보통";
        private List<String> veganProteinSources = new ArrayList<>();
        private boolean isRawVegan = false;
        private boolean containsNutritionalYeast = false;

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

        public Builder addProteinSource(String source) {
            this.veganProteinSources.add(source);
            return this;
        }

        public Builder setRawVegan() {
            this.isRawVegan = true;
            return this;
        }

        public Builder withNutritionalYeast() {
            this.containsNutritionalYeast = true;
            return this;
        }

        public VeganRecipe build() {
            return new VeganRecipe(this);
        }
    }
}