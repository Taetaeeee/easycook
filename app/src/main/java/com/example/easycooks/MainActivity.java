package com.example.easycooks;

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
    private List<Recipe> recipeList;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

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
        recipeList = new ArrayList<>();
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
                searchRecipes(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // 실시간 검색을 원한다면 여기에 searchRecipes(newText) 추가
                return true;
            }
        });
    }

    private void searchRecipes(String query) {
        // TODO: 실제 검색 로직 구현
        // 예시: API 호출 또는 로컬 데이터베이스 검색
        // searchResults를 받아서 recipeList를 업데이트하고 어댑터에 알림

        // 임시 테스트 데이터
        recipeList.clear();
        recipeList.add(new Recipe("김치찌개 레시피", "김치, 돼지고기, 두부...", android.R.drawable.ic_menu_gallery));
        recipeList.add(new Recipe("된장찌개 레시피", "된장, 두부, 애호박...", android.R.drawable.ic_menu_gallery));
        recipeAdapter.notifyDataSetChanged();
    }
}