package com.example.easycooks;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import java.util.stream.Collectors;

public class FavoriteRecipesActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecipeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_recipes);

        recyclerView = findViewById(R.id.favoriteRecipesRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 즐겨찾기한 레시피만 필터링
        List<? extends IRecipe> allRecipes = DummyRecipeData.getDummyRecipes();
        List<? extends IRecipe> favoriteRecipes = allRecipes.stream()
                .filter(recipe -> SmartRefrigerator.isFavorite(recipe.getTitle()))
                .collect(Collectors.toList());

        adapter = new RecipeAdapter(favoriteRecipes);
        recyclerView.setAdapter(adapter);
    }
}