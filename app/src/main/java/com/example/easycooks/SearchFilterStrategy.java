package com.example.easycooks;

import java.util.List;
import java.util.stream.Collectors;
import com.example.easycooks.IRecipe;

public class SearchFilterStrategy implements RecipeFilterStrategy {
    private String query;

    public SearchFilterStrategy(String query) {
        this.query = query;
    }

    @Override
    public List<? extends IRecipe> filter(List<? extends IRecipe> recipes) {
        if (query.isEmpty()) {
            return recipes;
        }

        return recipes.stream()
                .filter(recipe -> recipe.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                        recipe.getIngredients().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());
    }
}