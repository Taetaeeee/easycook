package com.example.easycooks;


import android.os.Bundle;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class RecipeSearch extends AppCompatActivity {
    private RecyclerView recipeRecyclerView;
    private RecipeAdapter recipeAdapter;
    private SearchView searchView;
    private TextView searchKeyword;
    private List<? extends IRecipe> recipeList;

    // 검색 전략 인터페이스
    private interface SearchStrategy {
        List<? extends IRecipe> search(List<? extends IRecipe> recipeList, String query);
    }

    // 제목 검색 전략
    private class TitleSearchStrategy implements SearchStrategy {
        @Override
        public List<? extends IRecipe> search(List<? extends IRecipe> recipeList, String query) {
            List<IRecipe> filteredList = new ArrayList<>();
            if (query == null || query.isEmpty()) {
                filteredList.addAll((List<IRecipe>) recipeList);
            } else {
                String lowerQuery = query.toLowerCase().trim();
                for (IRecipe recipe : recipeList) {
                    if (recipe.getTitle().toLowerCase().contains(lowerQuery)) {
                        filteredList.add(recipe);
                    }
                }
            }
            return filteredList;
        }
    }

    // 재료 검색 전략
    private class IngredientSearchStrategy implements SearchStrategy {
        @Override
        public List<? extends IRecipe> search(List<? extends IRecipe> recipeList, String query) {
            List<IRecipe> filteredList = new ArrayList<>();
            if (query == null || query.isEmpty()) {
                filteredList.addAll((List<IRecipe>) recipeList);
            } else {
                String lowerQuery = query.toLowerCase().trim();
                for (IRecipe recipe : recipeList) {
                    if (recipe.getIngredients().toLowerCase().contains(lowerQuery)) {
                        filteredList.add(recipe);
                    }
                }
            }
            return filteredList;
        }
    }

    private SearchStrategy searchStrategy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.recipesearch);

        initializeViews();
        initializeData();
        setupRecyclerView();
        setupSearchView();

        // 기본 검색 전략을 제목 검색으로 설정
        searchStrategy = new TitleSearchStrategy();

        String searchQuery = getIntent().getStringExtra("search_query");
        if (searchQuery != null && !searchQuery.isEmpty()) {
            searchView.setQuery(searchQuery, true);
            searchKeyword.setText(searchQuery);
            filterRecipes(searchQuery);
        }
    }

    private void initializeViews() {
        recipeRecyclerView = findViewById(R.id.recipeRecyclerView);
        searchView = findViewById(R.id.search);
        searchKeyword = findViewById(R.id.searchKeyword);
    }

    private void initializeData() {
        recipeList = DummyRecipeData.getDummyRecipes();
    }

    private void setupRecyclerView() {
        recipeAdapter = new RecipeAdapter(recipeList);
        recipeRecyclerView.setLayoutManager(new LinearLayoutManager(this));
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
        List<? extends IRecipe> filteredList = searchStrategy.search(recipeList, query);
        recipeAdapter.updateList(filteredList);
    }

    // 검색 전략 변경 메소드
    private void setSearchStrategy(SearchStrategy strategy) {
        this.searchStrategy = strategy;
    }
}