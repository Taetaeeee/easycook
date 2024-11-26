package com.example.easycooks;

import com.example.easycooks.data.DummyRecipeData;

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
        List<Recipe> filteredList = new ArrayList<>();
        if (query == null || query.isEmpty()) {
            filteredList.addAll(recipeList); //검색어 비어있으면 전체표시
        } else {
            String lowerQuery = query.toLowerCase().trim(); //검색어를 소문자로 변환
            
            //제목으로만 검색
            for (Recipe recipe : recipeList) {
                if (recipe.getTitle().toLowerCase().contains(lowerQuery)) {
                    filteredList.add(recipe);
                }
            }
        }
        recipeAdapter.updateList(filteredList);
    }
}