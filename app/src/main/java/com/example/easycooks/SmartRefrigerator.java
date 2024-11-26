package com.example.easycooks;

import java.util.ArrayList;
import java.util.List;

public class SmartRefrigerator {
    private static List<String> fridgeIngredients = new ArrayList<>();

    static {
        // 임의 데이터로 초기화
        fridgeIngredients.add("김치");
        fridgeIngredients.add("돼지고기");
        fridgeIngredients.add("두부");
        fridgeIngredients.add("된장");
        fridgeIngredients.add("애호박");
        fridgeIngredients.add("소금");
        fridgeIngredients.add("간장");
    }

    // 냉장고에 재료가 있는지 확인
    public static boolean hasIngredient(String ingredient) {
        return fridgeIngredients.stream()
                .anyMatch(fridgeItem -> fridgeItem.trim().equalsIgnoreCase(ingredient.trim()));
    }

    // 냉장고에 있는 재료 목록 반환
    public static List<String> getFridgeIngredients() {
        return fridgeIngredients;
    }

    // 재료 추가 메서드 (필요하면 추가)
    public static void addIngredient(String ingredient) {
        fridgeIngredients.add(ingredient.toLowerCase());
    }

    // 재료 제거 메서드 (필요하면 추가)
    public static void removeIngredient(String ingredient) {
        fridgeIngredients.remove(ingredient.toLowerCase());
    }
}
