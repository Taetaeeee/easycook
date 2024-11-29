package com.example.easycooks;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.graphics.Insets;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recipeRecyclerView;
    private RecipeAdapter recipeAdapter;
    private List<? extends Recipe> recipeList;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // SmartRefrigerator 초기화 추가
        SmartRefrigerator.initialize(getApplicationContext());

        // 초기화
        initializeViews();
        setupRecyclerView();
        setupSearchView();

        // 시스템 바 설정
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void initializeViews() {
        searchView = findViewById(R.id.search);
        recipeRecyclerView = findViewById(R.id.recipeRecyclerView);
        recipeList = DummyRecipeData.getDummyRecipes();

        // 냉장고 버튼 클릭 리스너 추가
        findViewById(R.id.myFridgeButton).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MyFridge.class);
            startActivity(intent);
        });
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
                // RecipeSearch 액티비티로 이동
                Intent intent = new Intent(MainActivity.this, RecipeSearch.class);
                intent.putExtra("search_query", query);
                startActivity(intent);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });
    }
}