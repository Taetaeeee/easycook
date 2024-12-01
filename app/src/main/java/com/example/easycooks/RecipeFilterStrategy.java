package com.example.easycooks;

import java.util.List;
import com.example.easycooks.IRecipe;

public interface RecipeFilterStrategy {
    List<? extends IRecipe> filter(List<? extends IRecipe> recipes);
}