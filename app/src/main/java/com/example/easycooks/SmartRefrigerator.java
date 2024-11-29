package com.example.easycooks;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SmartRefrigerator {
    private static List<String> fridgeIngredients = new ArrayList<>();
    private static final String PREF_NAME = "FridgePreferences";
    private static final String KEY_INGREDIENTS = "ingredients";
    private static Context context;

    public static void initialize(Context appContext) {
        context = appContext.getApplicationContext();
        loadIngredients();
    }

    private static void loadIngredients() {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        Set<String> savedIngredients = prefs.getStringSet(KEY_INGREDIENTS, new HashSet<>());

        if (savedIngredients.isEmpty()) {
            // 초기 데이터 설정
            fridgeIngredients.add("김치");
            fridgeIngredients.add("돼지고기");
            fridgeIngredients.add("두부");
            fridgeIngredients.add("된장");
            fridgeIngredients.add("애호박");
            fridgeIngredients.add("소금");
            fridgeIngredients.add("간장");
            saveIngredients(); // 초기 데이터 저장
        } else {
            fridgeIngredients = new ArrayList<>(savedIngredients);
        }
    }

    private static void saveIngredients() {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putStringSet(KEY_INGREDIENTS, new HashSet<>(fridgeIngredients));
        editor.apply();
    }

    public static boolean hasIngredient(String ingredient) {
        return fridgeIngredients.stream()
                .anyMatch(fridgeItem -> fridgeItem.trim().equalsIgnoreCase(ingredient.trim()));
    }

    public static List<String> getFridgeIngredients() {
        return fridgeIngredients;
    }

    public static void addIngredient(String ingredient) {
        fridgeIngredients.add(ingredient.toLowerCase());
        saveIngredients();
    }

    public static void removeIngredient(String ingredient) {
        fridgeIngredients.remove(ingredient.toLowerCase());
        saveIngredients();
    }
}
