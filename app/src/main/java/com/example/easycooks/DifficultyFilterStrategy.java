package com.example.easycooks;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import com.example.easycooks.IRecipe;

public class DifficultyFilterStrategy implements RecipeFilterStrategy {
    private Set<String> selectedDifficulties;

    public DifficultyFilterStrategy(Set<String> selectedDifficulties) {
        this.selectedDifficulties = selectedDifficulties;
    }

    @Override
    public List<? extends IRecipe> filter(List<? extends IRecipe> recipes) {
        return recipes.stream()
                .filter(recipe -> selectedDifficulties.contains(recipe.getDifficulty()))
                .collect(Collectors.toList());
    }
}