package com.example.easycooks;

import com.example.easycooks.recipe.*;

public class RecipeFactory {

    public static Recipe createRecipe(String type, String title, String ingredients, int imageResourceId) {
        switch (type.toLowerCase()) {
            case "korean":
                return new KoreanRecipe.Builder(title, ingredients, imageResourceId)
                        .spicyLevel("보통")
                        .build();

            case "diet":
                return new DietRecipe.Builder(title, ingredients, imageResourceId)
                        .calories(300)
                        .protein(20)
                        .build();

            case "lowsalt":
                return new LowSaltRecipe.Builder(title, ingredients, imageResourceId)
                        .sodiumContent(200)
                        .withSaltSubstitute("허브소금")
                        .build();

            case "vegan":
                return new VeganRecipe.Builder(title, ingredients, imageResourceId)
                        .addProteinSource("두부")
                        .addProteinSource("렌틸콩")
                        .build();

            case "lowsugar":
                return new LowSugarRecipe.Builder(title, ingredients, imageResourceId)
                        .sugarContent(5)
                        .sweetener("스테비아")
                        .build();

            default:
                throw new IllegalArgumentException("Unknown recipe type: " + type);
        }
    }

    // 구체적인 레시피 생성 메서드들
    public static KoreanRecipe createKoreanRecipe(String title, String ingredients, int imageResourceId,
                                                  String spicyLevel, boolean containsKimchi) {
        return new KoreanRecipe.Builder(title, ingredients, imageResourceId)
                .spicyLevel(spicyLevel)
                .withKimchi(containsKimchi)
                .build();
    }

    public static DietRecipe createDietRecipe(String title, String ingredients, int imageResourceId,
                                              int calories, int protein, boolean isVegan) {
        DietRecipe.Builder builder = new DietRecipe.Builder(title, ingredients, imageResourceId)
                .calories(calories)
                .protein(protein);
        if (isVegan) {
            builder.veganOption();
        }
        return builder.build();
    }

    public static LowSaltRecipe createLowSaltRecipe(String title, String ingredients, int imageResourceId,
                                                    int sodiumContent, String saltAlternative) {
        return new LowSaltRecipe.Builder(title, ingredients, imageResourceId)
                .sodiumContent(sodiumContent)
                .withSaltSubstitute(saltAlternative)
                .build();
    }

    public static VeganRecipe createVeganRecipe(String title, String ingredients, int imageResourceId,
                                                boolean isRawVegan, String... proteinSources) {
        VeganRecipe.Builder builder = new VeganRecipe.Builder(title, ingredients, imageResourceId);
        for (String source : proteinSources) {
            builder.addProteinSource(source);
        }
        if (isRawVegan) {
            builder.setRawVegan();
        }
        return builder.build();
    }

    public static LowSugarRecipe createLowSugarRecipe(String title, String ingredients, int imageResourceId,
                                                      int sugarContent, String sweetener, boolean isKetogenic) {
        LowSugarRecipe.Builder builder = new LowSugarRecipe.Builder(title, ingredients, imageResourceId)
                .sugarContent(sugarContent)
                .sweetener(sweetener);
        if (isKetogenic) {
            builder.setKetogenic();
        }
        return builder.build();
    }
}