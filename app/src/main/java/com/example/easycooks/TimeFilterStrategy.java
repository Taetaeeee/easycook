package com.example.easycooks;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import com.example.easycooks.IRecipe;

public class TimeFilterStrategy implements RecipeFilterStrategy {
    private Set<String> selectedTimes;

    public TimeFilterStrategy(Set<String> selectedTimes) {
        this.selectedTimes = selectedTimes;
    }

    @Override
    public List<? extends IRecipe> filter(List<? extends IRecipe> recipes) {
        return recipes.stream()
                .filter(recipe -> matchesCookingTime(recipe.getCookingTime()))
                .collect(Collectors.toList());
    }

    private boolean matchesCookingTime(String cookingTime) {
        int minutes = parseCookingTime(cookingTime);
        for (String timeFilter : selectedTimes) {
            switch (timeFilter) {
                case "15분 이내":
                    if (minutes <= 15)
                        return true;
                    break;
                case "30분 이내":
                    if (minutes <= 30)
                        return true;
                    break;
                case "1시간 이내":
                    if (minutes <= 60)
                        return true;
                    break;
                case "1시간 이상":
                    if (minutes > 60)
                        return true;
                    break;
            }
        }
        return false;
    }

    private int parseCookingTime(String cookingTime) {
        try {
            return Integer.parseInt(cookingTime.replaceAll("[^0-9]", ""));
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}