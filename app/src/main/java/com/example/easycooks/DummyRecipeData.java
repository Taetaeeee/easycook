package com.example.easycooks;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.easycooks.R;
import com.example.easycooks.recipe.*;
import com.example.easycooks.RecipeFactory;
import com.example.easycooks.CookingStep;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class DummyRecipeData {
    private static List<Recipe> customRecipes = new ArrayList<>();
    private static Map<Recipe, List<CookingStep>> customRecipeSteps = new HashMap<>();
    private static final String PREF_NAME = "CustomRecipes";
    private static final String KEY_RECIPES = "recipes";
    private static final String KEY_STEPS = "steps";

    public static List<Recipe> getDefaultRecipes() {
        List<Recipe> recipeList = new ArrayList<>();

        // 한식 레시피들
        recipeList.add(RecipeFactory.createKoreanRecipe(
                "김치찌개",
                "김치 300g, 돼지고기 200g, 두부 1모, 대파 1대, 고춧가루 1큰술, 다진마늘 1큰술",
                R.drawable.kimchi_stew,
                "매움",
                true
        ));

        recipeList.add(RecipeFactory.createKoreanRecipe(
                "된장찌개",
                "된장 2큰술, 두부 1모, 애호박 1/2개, 대파 1대, 표고버섯 2개, 다진마늘 1작은술",
                R.drawable.doenjang_stew,
                "보통",
                false
        ));

        // 다이어트 레시피들
        recipeList.add(RecipeFactory.createDietRecipe(
                "닭가슴살 샐러드",
                "닭가슴살 200g, 로메인 상추 100g, 방울토마토 5개, 올리브오일 1큰술",
                R.drawable.chicken_salad,
                280,
                32,
                false
        ));

        recipeList.add(RecipeFactory.createDietRecipe(
                "퀴노아 볼",
                "퀴노아 100g, 닭가슴살 150g, 아보카도 1개, 브로콜리 100g",
                R.drawable.quinoa_bowl,
                320,
                28,
                false
        ));

        // 저염식 레시피들
        recipeList.add(RecipeFactory.createLowSaltRecipe(
                "저염 스테이크",
                "소고기 등심 200g, 로즈마리 2줄기, 마늘 3쪽, 허브솔트 1/2작은술",
                R.drawable.low_salt_steak,
                150,
                "허브솔트"
        ));

        recipeList.add(RecipeFactory.createLowSaltRecipe(
                "저염 연어구이",
                "연어 200g, 레몬 1개, 타임 2줄기, 올리브오일 1큰술",
                R.drawable.low_salt_salmon,
                120,
                "레몬즙"
        ));

        // 비건 레시피들
        recipeList.add(RecipeFactory.createVeganRecipe(
                "두부 스테이크",
                "두부 1모, 버섯 200g, 아스파라거스 100g, 올리브오일 2큰술",
                R.drawable.tofu_steak,
                false,
                "두부", "버섯"
        ));

        recipeList.add(RecipeFactory.createVeganRecipe(
                "렌틸콩 커리",
                "렌틸콩 200g, 코코넛밀크 200ml, 양파 1개, 당근 1개, 커리가루 2큰술",
                R.drawable.lentil_curry,
                false,
                "렌틸콩"
        ));

        // 저당 레시피들
        recipeList.add(RecipeFactory.createLowSugarRecipe(
                "저당 그래놀라",
                "귀리 200g, 아몬드 50g, 호두 50g, 스테비아 1작은술",
                R.drawable.low_sugar_granola,
                3,
                "스테비아",
                true
        ));

        recipeList.add(RecipeFactory.createLowSugarRecipe(
                "코코넛 치아시드 푸딩",
                "치아시드 3큰술, 코코넛밀크 200ml, 바닐라 추출액 1/4작은술",
                R.drawable.chia_pudding,
                2,
                "없음",
                true
        ));

        return recipeList;
    }

    public static List<Recipe> getDummyRecipes() {
        List<Recipe> allRecipes = new ArrayList<>();
        allRecipes.addAll(getDefaultRecipes());
        allRecipes.addAll(customRecipes);
        return allRecipes;
    }

    public static List<CookingStep> getRecipeSteps(Recipe recipe) {
        if (customRecipeSteps.containsKey(recipe)) {
            return customRecipeSteps.get(recipe);
        }
        return createDefaultSteps(recipe);
    }

    private static List<CookingStep> createDefaultSteps(Recipe recipe) {
        List<CookingStep> steps = new ArrayList<>();

        if (recipe instanceof KoreanRecipe) {
            if (recipe.getTitle().equals("김치찌개")) {
                steps.add(new CookingStep(1, "김치를 적당한 크기로 썰어주세요.", R.drawable.default_recipe_image));
                steps.add(new CookingStep(2, "돼지고기를 썰어 김치와 함께 볶아주세요.", R.drawable.default_recipe_image));
                steps.add(new CookingStep(3, "물을 넣고 끓이다가 두부를 넣어주세요.", R.drawable.default_recipe_image));
                steps.add(new CookingStep(4, "대파를 썰어 넣고 마무리해주세요.", R.drawable.default_recipe_image));
            } else if (recipe.getTitle().equals("된장찌개")) {
                steps.add(new CookingStep(1, "채소들을 손질하고 적당한 크기로 썰어주세요.", R.drawable.default_recipe_image));
                steps.add(new CookingStep(2, "물을 끓이고 된장을 풀어주세요.", R.drawable.default_recipe_image));
                steps.add(new CookingStep(3, "채소를 넣고 끓여주세요.", R.drawable.default_recipe_image));
                steps.add(new CookingStep(4, "두부를 넣고 마무리해주세요.", R.drawable.default_recipe_image));
            }
        } else if (recipe instanceof DietRecipe) {
            if (recipe.getTitle().equals("닭가슴살 샐러드")) {
                steps.add(new CookingStep(1, "닭가슴살을 삶아주세요.", R.drawable.default_recipe_image));
                steps.add(new CookingStep(2, "채소를 씻어 손질해주세요.", R.drawable.default_recipe_image));
                steps.add(new CookingStep(3, "닭가슴살을 썰어주세요.", R.drawable.default_recipe_image));
                steps.add(new CookingStep(4, "모든 재료를 섞어 드레싱을 뿌려주세요.", R.drawable.default_recipe_image));
            } else if (recipe.getTitle().equals("퀴노아 볼")) {
                steps.add(new CookingStep(1, "퀴노아를 충분한 물에 씻어주세요.", R.drawable.default_recipe_image));
                steps.add(new CookingStep(2, "퀴노아를 2배의 물과 함께 삶아주세요.", R.drawable.default_recipe_image));
                steps.add(new CookingStep(3, "닭가슴살을 구워주세요.", R.drawable.default_recipe_image));
                steps.add(new CookingStep(4, "브로콜리를 살짝 데치고 아보카도와 함께 담아주세요.", R.drawable.default_recipe_image));
            }
        } else if (recipe instanceof LowSaltRecipe) {
            if (recipe.getTitle().equals("저염 스테이크")) {
                steps.add(new CookingStep(1, "스테이크를 실온에 30분 정도 두세요.", R.drawable.default_recipe_image));
                steps.add(new CookingStep(2, "허브솔트로 살짝만 간을 해주세요.", R.drawable.default_recipe_image));
                steps.add(new CookingStep(3, "팬을 달군 후 올리브오일을 두르고 구워주세요.", R.drawable.default_recipe_image));
                steps.add(new CookingStep(4, "로즈마리와 마늘을 넣고 마무리해주세요.", R.drawable.default_recipe_image));
            } else if (recipe.getTitle().equals("저염 연어구이")) {
                steps.add(new CookingStep(1, "연어를 깨끗이 씻어 물기를 제거해주세요.", R.drawable.default_recipe_image));
                steps.add(new CookingStep(2, "레몬즙을 뿌리고 타임을 올려주세요.", R.drawable.default_recipe_image));
                steps.add(new CookingStep(3, "180도로 예열된 오븐에서 12분간 구워주세요.", R.drawable.default_recipe_image));
                steps.add(new CookingStep(4, "레몬을 곁들여 완성해주세요.", R.drawable.default_recipe_image));
            }
        } else if (recipe instanceof VeganRecipe) {
            if (recipe.getTitle().equals("두부 스테이크")) {
                steps.add(new CookingStep(1, "두부의 물기를 꼭 빼주세요.", R.drawable.default_recipe_image));
                steps.add(new CookingStep(2, "버섯과 아스파라거스를 손질해주세요.", R.drawable.default_recipe_image));
                steps.add(new CookingStep(3, "두부를 스테이크 모양으로 썰어 구워주세요.", R.drawable.default_recipe_image));
                steps.add(new CookingStep(4, "구운 채소를 곁들여 완성해주세요.", R.drawable.default_recipe_image));
            } else if (recipe.getTitle().equals("렌틸콩 커리")) {
                steps.add(new CookingStep(1, "렌틸콩을 충분히 불려주세요.", R.drawable.default_recipe_image));
                steps.add(new CookingStep(2, "양파와 당근을 다져주세요.", R.drawable.default_recipe_image));
                steps.add(new CookingStep(3, "채소를 볶다가 렌틸콩과 커리가루를 넣어주세요.", R.drawable.default_recipe_image));
                steps.add(new CookingStep(4, "코코넛밀크를 넣고 끓여 완성해주세요.", R.drawable.default_recipe_image));
            }
        } else if (recipe instanceof LowSugarRecipe) {
            if (recipe.getTitle().equals("저당 그래놀라")) {
                steps.add(new CookingStep(1, "오븐을 160도로 예열해주세요.", R.drawable.default_recipe_image));
                steps.add(new CookingStep(2, "견과류를 잘게 부숴주세요.", R.drawable.default_recipe_image));
                steps.add(new CookingStep(3, "모든 재료를 고루 섞어주세요.", R.drawable.default_recipe_image));
                steps.add(new CookingStep(4, "20분간 구워 식혀주세요.", R.drawable.default_recipe_image));
            } else if (recipe.getTitle().equals("코코넛 치아시드 푸딩")) {
                steps.add(new CookingStep(1, "치아시드를 코코넛밀크에 넣어주세요.", R.drawable.default_recipe_image));
                steps.add(new CookingStep(2, "바닐라 추출액을 넣어주세요.", R.drawable.default_recipe_image));
                steps.add(new CookingStep(3, "냉장고에서 4시간 이상 숙성해주세요.", R.drawable.default_recipe_image));
                steps.add(new CookingStep(4, "취향에 따라 과일을 토핑해주세요.", R.drawable.default_recipe_image));
            }
        }

        return steps;
    }

    public static void addCustomRecipe(Recipe recipe, List<CookingStep> steps) {
        customRecipes.add(recipe);
        if (steps != null) {
            customRecipeSteps.put(recipe, steps);
        }
    }

    public static void deleteCustomRecipe(Context context, Recipe recipe) {
        if (isCustomRecipe(recipe)) {
            customRecipes.remove(recipe);
            customRecipeSteps.remove(recipe);
            saveCustomRecipes(context);
        }
    }

    public static boolean isCustomRecipe(Recipe recipe) {
        return customRecipes.contains(recipe);
    }

    // SharedPreferences에 커스텀 레시피 저장
    public static void saveCustomRecipes(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        JSONArray recipesArray = new JSONArray();
        JSONArray stepsArray = new JSONArray();

        try {
            for (Recipe recipe : customRecipes) {
                JSONObject recipeJson = new JSONObject();
                recipeJson.put("bundle", bundleToJson(recipe.toBundle()));
                recipesArray.put(recipeJson);

                List<CookingStep> steps = customRecipeSteps.get(recipe);
                if (steps != null) {
                    JSONArray recipeSteps = new JSONArray();
                    for (CookingStep step : steps) {
                        JSONObject stepJson = new JSONObject();
                        stepJson.put("stepNumber", step.getStepNumber());
                        stepJson.put("description", step.getDescription());
                        stepJson.put("imageResourceId", step.getImageResourceId());
                        recipeSteps.put(stepJson);
                    }
                    stepsArray.put(recipeSteps);
                }
            }

            editor.putString(KEY_RECIPES, recipesArray.toString());
            editor.putString(KEY_STEPS, stepsArray.toString());
            editor.apply();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // SharedPreferences에서 커스텀 레시피 로드
    public static void loadCustomRecipes(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String recipesJson = prefs.getString(KEY_RECIPES, "[]");
        String stepsJson = prefs.getString(KEY_STEPS, "[]");

        try {
            JSONArray recipesArray = new JSONArray(recipesJson);
            JSONArray stepsArray = new JSONArray(stepsJson);

            customRecipes.clear();
            customRecipeSteps.clear();

            for (int i = 0; i < recipesArray.length(); i++) {
                JSONObject recipeJson = recipesArray.getJSONObject(i);
                Bundle bundle = jsonToBundle(recipeJson.getJSONObject("bundle"));
                Recipe recipe = Recipe.fromBundle(bundle);
                customRecipes.add(recipe);

                if (i < stepsArray.length()) {
                    JSONArray recipeSteps = stepsArray.getJSONArray(i);
                    List<CookingStep> steps = new ArrayList<>();
                    for (int j = 0; j < recipeSteps.length(); j++) {
                        JSONObject stepJson = recipeSteps.getJSONObject(j);
                        steps.add(new CookingStep(
                                stepJson.getInt("stepNumber"),
                                stepJson.getString("description"),
                                stepJson.getInt("imageResourceId")
                        ));
                    }
                    customRecipeSteps.put(recipe, steps);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void printCustomRecipes() {
        System.out.println("Custom Recipes:");
        for (Recipe recipe : customRecipes) {
            System.out.println("Recipe Title: " + recipe.getTitle());
            System.out.println("Ingredients: " + recipe.getIngredients());
            System.out.println("Description: " + recipe.getDescription());
        }
        System.out.println("Total Custom Recipes: " + customRecipes.size());
    }

    private static JSONObject bundleToJson(Bundle bundle) throws JSONException {
        JSONObject json = new JSONObject();
        for (String key : bundle.keySet()) {
            Object value = bundle.get(key);
            if (value != null) {
                json.put(key, value.toString());
            }
        }
        return json;
    }

    private static Bundle jsonToBundle(JSONObject json) throws JSONException {
        Bundle bundle = new Bundle();
        Iterator<String> keys = json.keys(); // keys() 메서드로 키 가져오기

        while (keys.hasNext()) {
            String key = keys.next();
            String value = json.getString(key); // 각 키의 값을 가져옴
            if (key.equals("imageResourceId")) {
                bundle.putInt(key, Integer.parseInt(value)); // int 값인 경우 처리
            } else {
                bundle.putString(key, value); // 일반 문자열로 처리
            }
        }

        return bundle;
    }
}