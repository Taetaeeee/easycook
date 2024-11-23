package com.example.easycooks;

import android.os.Bundle;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class RecipeSearch extends AppCompatActivity {
    private RecyclerView recipeRecyclerView;
    private RecipeAdapter recipeAdapter;
    private SearchView searchView;
    private TextView searchKeyword;
    private List<Recipe> recipeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.recipesearch);

        initializeViews();
        initializeData();
        setupRecyclerView();
        setupSearchView();
    }

    private void initializeViews() {
        recipeRecyclerView = findViewById(R.id.recipeRecyclerView);
        searchView = findViewById(R.id.search);
        searchKeyword = findViewById(R.id.searchKeyword);
    }

    private void initializeData() {
        recipeList = new ArrayList<>();

        // 기본 생성자를 사용한 예시
        recipeList.add(new Recipe("김치찌개", "김치, 돼지고기, 두부...", R.drawable.default_recipe_image));

        // 전체 정보를 포함한 생성자를 사용한 예시
        recipeList.add(new Recipe(
                "김치찌개",
                "김치 300g, 돼지고기 200g, 두부 1모...",
                "맛있는 김치찌개 만드는 방법",
                "30분",
                "2인분",
                R.drawable.default_recipe_image,
                "초급"));

        // 더 많은 레시피 데이터를 여기에 추가할 수 있습니다
        recipeList.add(new Recipe(
                "된장찌개",
                "된장 2큰술, 두부 1모, 애호박 1개...",
                "건강한 된장찌개 만드는 방법",
                "20분",
                "3인분",
                R.drawable.default_recipe_image,
                "초급"));
    }

    private void setupRecyclerView() {
        recipeAdapter = new RecipeAdapter(recipeList);
        recipeRecyclerView.setAdapter(recipeAdapter);
    }

    private void setupSearchView() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterRecipes(query);
                searchKeyword.setText(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterRecipes(newText);
                return false;
            }
        });
    }

    private void filterRecipes(String query) {
        List<Recipe> filteredList = new ArrayList<>();
        for (Recipe recipe : recipeList) {
            if (recipe.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                    recipe.getIngredients().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(recipe);
            }
        }
        recipeAdapter.updateList(filteredList);
    }
}