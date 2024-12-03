package com.example.easycooks;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

public class SmartRefrigerator {
    private static List<String> fridgeIngredients = new ArrayList<>();
    private static final String PREF_NAME = "FridgePreferences";
    private static final String KEY_INGREDIENTS = "ingredients";
    private static Context context;
    private static Set<String> favorites = new HashSet<>();
    private static final String KEY_FAVORITES = "favorites";
    private static List<FridgeObserver> observers = new ArrayList<>();

    public static void initialize(Context appContext) {
        context = appContext.getApplicationContext();
        loadIngredients();
        loadFavorites();
    }

    public interface FridgeObserver {
        void onFridgeContentsChanged();
        void onFavoritesChanged();
    }

    public static void addObserver(FridgeObserver observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    public static void removeObserver(FridgeObserver observer) {
        observers.remove(observer);
    }

    private static void notifyObservers(Consumer<FridgeObserver> action) {
        for (FridgeObserver observer : observers) {
            action.accept(observer);
        }
    }

    private static void notifyFridgeContentsChanged() {
        notifyObservers(FridgeObserver::onFridgeContentsChanged);
    }

    private static void notifyFavoritesChanged() {
        notifyObservers(FridgeObserver::onFavoritesChanged);
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

    private static void loadFavorites() {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        favorites = new HashSet<>(prefs.getStringSet(KEY_FAVORITES, new HashSet<>()));
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
        notifyFridgeContentsChanged();
    }

    public static void removeIngredient(String ingredient) {
        fridgeIngredients.remove(ingredient.toLowerCase());
        saveIngredients();
        notifyFridgeContentsChanged();
    }

    public static boolean isFavorite(String recipeTitle) {
        return favorites.contains(recipeTitle);
    }

    public static void toggleFavorite(String recipeTitle) {
        if (favorites.contains(recipeTitle)) {
            favorites.remove(recipeTitle);
        } else {
            favorites.add(recipeTitle);
        }
        saveFavorites();
        notifyFavoritesChanged(); // 즐겨찾기 상태 변경 알림
    }

    private static void saveFavorites() {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        prefs.edit().putStringSet(KEY_FAVORITES, favorites).apply();
    }

    public static List<String> getMissingIngredients(String recipeIngredients) {
        List<String> missingIngredients = new ArrayList<>();
        for (String ingredient : recipeIngredients.split(",")) {
            String cleanIngredient = ingredient.replaceAll("[^가-힣a-zA-Z ]", "").trim().split(" ")[0];
            if (!hasIngredient(cleanIngredient)) {
                missingIngredients.add(cleanIngredient);
            }
        }
        return missingIngredients;
    }

}
