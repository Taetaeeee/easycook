package com.example.easycooks;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import java.util.stream.Collectors;

public class FavoriteRecipesActivity extends BaseObserverActivity {
    private RecyclerView recyclerView;
    private RecipeAdapter adapter;
    private List<? extends IRecipe> favoriteRecipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_recipes);

        recyclerView = findViewById(R.id.favoriteRecipesRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 즐겨찾기한 레시피만 필터링
        loadFavoriteRecipes();
    }

    @Override
    public void onFridgeContentsChanged() {
        // 냉장고 내용 변경 이벤트는 무시
    }

    @Override
    public void onFavoritesChanged() {
        // 즐겨찾기 상태가 변경되면 목록 업데이트
        loadFavoriteRecipes();
    }

    private void loadFavoriteRecipes() {
        List<? extends IRecipe> allRecipes = DummyRecipeData.getDummyRecipes();
        favoriteRecipes = allRecipes.stream()
                .filter(recipe -> SmartRefrigerator.isFavorite(recipe.getTitle()))
                .collect(Collectors.toList());

        if (adapter == null) {
            adapter = new RecipeAdapter(this, favoriteRecipes);
            recyclerView.setAdapter(adapter);
        } else {
            adapter.updateList(favoriteRecipes); // Adapter의 데이터 갱신
        }
    }
}