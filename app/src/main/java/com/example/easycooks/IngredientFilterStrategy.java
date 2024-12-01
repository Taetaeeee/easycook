package com.example.easycooks;

import java.util.List;
import java.util.stream.Collectors;
import com.example.easycooks.IRecipe;

public class IngredientFilterStrategy implements RecipeFilterStrategy {
    private List<String> selectedIngredients;

    public IngredientFilterStrategy(List<String> selectedIngredients) {
        this.selectedIngredients = selectedIngredients;
    }

    @Override
    public List<? extends IRecipe> filter(List<? extends IRecipe> recipes) {
        if (selectedIngredients.isEmpty()) {
            return recipes;
        }

        return recipes.stream()
                .filter(recipe -> selectedIngredients.stream()
                        .anyMatch(
                                ingredient -> recipe.getIngredients().toLowerCase().contains(ingredient.toLowerCase())))
                .collect(Collectors.toList());
    }
}