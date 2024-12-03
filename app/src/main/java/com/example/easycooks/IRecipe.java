package com.example.easycooks;

import android.content.Intent;
import android.os.Bundle;

public interface IRecipe {
    String getTitle();

    String getIngredients();

    String getDescription();

    String getCookingTime();

    String getServings();

    int getImageResourceId();

    String getDifficulty();

    void setTitle(String title);

    void setIngredients(String ingredients);

    void setDescription(String description);

    void setCookingTime(String cookingTime);

    void setServings(String servings);

    void setImageResourceId(int imageResourceId);

    void setDifficulty(String difficulty);

    Bundle toBundle();
}
