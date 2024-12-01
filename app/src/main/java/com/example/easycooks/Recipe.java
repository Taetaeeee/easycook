package com.example.easycooks;

import java.util.ArrayList;
import java.util.List;

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

    public static Recipe createDetailedRecipe(
            String title, String ingredients, String description,
            String cookingTime, String servings, int imageResourceId,
            String difficulty) {
        return new Recipe(title, ingredients, description, cookingTime,
                servings, imageResourceId, difficulty);
    }

    // 한식 레시피 내부 클래스
    public static class KoreanRecipe extends Recipe {
        private String spicyLevel;
        private boolean containsKimchi;

        private KoreanRecipe(String title, String ingredients, String description,
                String cookingTime, String servings, int imageResourceId,
                String difficulty, String spicyLevel, boolean containsKimchi) {
            super(title, ingredients, description, cookingTime, servings, imageResourceId, difficulty);
            this.spicyLevel = spicyLevel;
            this.containsKimchi = containsKimchi;
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

            public Builder spicyLevel(String level) {
                this.spicyLevel = level;
                return this;
            }

            public Builder withKimchi() {
                this.containsKimchi = true;
                return this;
            }

            public KoreanRecipe build() {
                return new KoreanRecipe(title, ingredients, description, cookingTime,
                        servings, imageResourceId, difficulty, spicyLevel, containsKimchi);
            }
        }

        public String getSpicyLevel() {
            return spicyLevel;
        }

        public boolean containsKimchi() {
            return containsKimchi;
        }
    }

    // 다이어트 레시피 내부 클래스
    public static class DietRecipe extends Recipe {
        private int calories;
        private int protein;
        private boolean isVegan;

        private DietRecipe(String title, String ingredients, String description,
                String cookingTime, String servings, int imageResourceId,
                String difficulty, int calories, int protein, boolean isVegan) {
            super(title, ingredients, description, cookingTime, servings, imageResourceId, difficulty);
            this.calories = calories;
            this.protein = protein;
            this.isVegan = isVegan;
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

            public Builder calories(int calories) {
                this.calories = calories;
                return this;
            }

            public Builder protein(int protein) {
                this.protein = protein;
                return this;
            }

            public Builder veganOption() {
                this.isVegan = true;
                return this;
            }

            public DietRecipe build() {
                return new DietRecipe(title, ingredients, description, cookingTime,
                        servings, imageResourceId, difficulty, calories, protein, isVegan);
            }
        }

        public int getCalories() {
            return calories;
        }

        public int getProtein() {
            return protein;
        }

        public boolean isVegan() {
            return isVegan;
        }
    }

    // 저염식 레시피 내부 클래스
    public static class LowSaltRecipe extends Recipe {
        private int sodiumContent;
        private boolean hasSaltSubstitute;
        private String saltAlternative;

        private LowSaltRecipe(String title, String ingredients, String description,
                String cookingTime, String servings, int imageResourceId,
                String difficulty, int sodiumContent, boolean hasSaltSubstitute,
                String saltAlternative) {
            super(title, ingredients, description, cookingTime, servings, imageResourceId, difficulty);
            this.sodiumContent = sodiumContent;
            this.hasSaltSubstitute = hasSaltSubstitute;
            this.saltAlternative = saltAlternative;
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
                return new LowSaltRecipe(title, ingredients, description, cookingTime,
                        servings, imageResourceId, difficulty, sodiumContent,
                        hasSaltSubstitute, saltAlternative);
            }
        }

        public int getSodiumContent() {
            return sodiumContent;
        }

        public String getSaltAlternative() {
            return saltAlternative;
        }
    }

    // 비건 레시피 내부 클래스
    public static class VeganRecipe extends Recipe {
        private List<String> veganProteinSources;
        private boolean isRawVegan;
        private boolean containsNutritionalYeast;

        private VeganRecipe(String title, String ingredients, String description,
                String cookingTime, String servings, int imageResourceId,
                String difficulty, List<String> veganProteinSources,
                boolean isRawVegan, boolean containsNutritionalYeast) {
            super(title, ingredients, description, cookingTime, servings, imageResourceId, difficulty);
            this.veganProteinSources = veganProteinSources;
            this.isRawVegan = isRawVegan;
            this.containsNutritionalYeast = containsNutritionalYeast;
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
                return new VeganRecipe(title, ingredients, description, cookingTime,
                        servings, imageResourceId, difficulty, veganProteinSources,
                        isRawVegan, containsNutritionalYeast);
            }
        }

        public List<String> getVeganProteinSources() {
            return veganProteinSources;
        }

        public boolean isRawVegan() {
            return isRawVegan;
        }
    }

    // 저당 레시피 내부 클래스
    public static class LowSugarRecipe extends Recipe {
        private int sugarContent;
        private String sweetener;
        private boolean usesFruitSweetness;

        private LowSugarRecipe(String title, String ingredients, String description,
                String cookingTime, String servings, int imageResourceId,
                String difficulty, int sugarContent, String sweetener,
                boolean usesFruitSweetness) {
            super(title, ingredients, description, cookingTime, servings, imageResourceId, difficulty);
            this.sugarContent = sugarContent;
            this.sweetener = sweetener;
            this.usesFruitSweetness = usesFruitSweetness;
        }

        public static class Builder {
            private String title;
            private String ingredients;
            private String description = "";
            private String cookingTime = "35분";
            private String servings = "2인분";
            private int imageResourceId;
            private String difficulty = "보통";
            private int sugarContent = 0;
            private String sweetener = "";
            private boolean usesFruitSweetness = false;

            public Builder(String title, String ingredients, int imageResourceId) {
                this.title = title;
                this.ingredients = ingredients;
                this.imageResourceId = imageResourceId;
            }

            public Builder sugarContent(int sugarContent) {
                this.sugarContent = sugarContent;
                return this;
            }

            public Builder withSweetener(String sweetener) {
                this.sweetener = sweetener;
                return this;
            }

            public Builder useFruitSweetness() {
                this.usesFruitSweetness = true;
                return this;
            }

            public LowSugarRecipe build() {
                return new LowSugarRecipe(title, ingredients, description, cookingTime,
                        servings, imageResourceId, difficulty, sugarContent,
                        sweetener, usesFruitSweetness);
            }
        }

        public int getSugarContent() {
            return sugarContent;
        }

        public String getSweetener() {
            return sweetener;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Recipe recipe = (Recipe) o;
        return title.equals(recipe.title);
    }

    @Override
    public int hashCode() {
        return title.hashCode();
    }
} 