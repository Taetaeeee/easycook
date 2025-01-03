package com.example.easycooks;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Button;
import android.widget.LinearLayout;

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

import com.example.easycooks.recipe.KoreanRecipe;
import com.example.easycooks.recipe.VeganRecipe;
import com.google.android.material.chip.Chip;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recipeRecyclerView;
    private RecyclerView ingredientFilterRecyclerView;
    private RecipeAdapter recipeAdapter;
    private IngredientFilterAdapter filterAdapter;
    private SearchView searchView;
    private TextView searchKeyword;
    private ImageButton myFridgeButton;
    private Chip ingredientFilterToggle;
    private Chip timeFilterChip;
    private Chip difficultyFilterChip;
    private List<? extends IRecipe> recipeList;
    private String currentQuery = "";

    // 필터 상태 저장
    private Set<String> selectedTimes = new HashSet<>();
    private Set<String> selectedDifficulties = new HashSet<>();

    private EditText ingredientInput;
    private Button addIngredientButton;
    private LinearLayout addIngredientLayout;
    private List<String> defaultIngredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // SmartRefrigerator 초기화 추가
        SmartRefrigerator.initialize(getApplicationContext());
        // 저장된 커스텀 레시피 로드
        DummyRecipeData.loadCustomRecipes(this);
        DummyRecipeData.printCustomRecipes();

        initializeViews();
        setupRecyclerViews();
        setupSearchView();
        setupFilters();
        setupMyFridgeButton();

        // 초기 상태에서는 필터 숨기기
        ingredientFilterRecyclerView.setVisibility(View.GONE);

        // 시스템 바 설정
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void initializeViews() {
        searchView = findViewById(R.id.search);
        searchKeyword = findViewById(R.id.searchKeyword);
        recipeRecyclerView = findViewById(R.id.recipeRecyclerView);
        ingredientFilterRecyclerView = findViewById(R.id.ingredientFilterRecyclerView);
        myFridgeButton = findViewById(R.id.myFridgeButton);

        // 필터 칩들 초기화
        ingredientFilterToggle = findViewById(R.id.ingredientFilterToggle);
        timeFilterChip = findViewById(R.id.timeFilterChip);
        difficultyFilterChip = findViewById(R.id.difficultyFilterChip);

        recipeList = DummyRecipeData.getDummyRecipes();

        ingredientInput = findViewById(R.id.ingredientInput);
        addIngredientButton = findViewById(R.id.addIngredientButton);
        addIngredientLayout = findViewById(R.id.addIngredientLayout);

        // 기본 재료 목록 초기화
        defaultIngredients = Arrays.asList(
                "김치", "돼지고기", "소고기", "닭고기", "두부",
                "양파", "대파", "마늘", "고추", "당근",
                "감자", "고구마", "버섯", "달걀", "밥");
    }

    private void setupRecyclerViews() {
        // 레시피 RecyclerView 설정
        recipeAdapter = new RecipeAdapter(this, recipeList);
        recipeRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        recipeRecyclerView.setAdapter(recipeAdapter);

        // 재료 필터 RecyclerView 설정
        ingredientFilterRecyclerView.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
    }

    private void setupSearchView() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                handleSearch(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                currentQuery = newText;
                handleSearch(newText);
                return true;
            }
        });
    }

    private void setupFilters() {
        ingredientFilterToggle.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                showIngredientFilters(defaultIngredients);
                addIngredientLayout.setVisibility(View.VISIBLE);
            } else {
                hideIngredientFilters();
                addIngredientLayout.setVisibility(View.GONE);
            }
        });

        addIngredientButton.setOnClickListener(v -> {
            String newIngredient = ingredientInput.getText().toString().trim();
            if (!newIngredient.isEmpty()) {
                List<String> currentIngredients = new ArrayList<>(defaultIngredients);
                if (!currentIngredients.contains(newIngredient)) {
                    currentIngredients.add(newIngredient);
                    showIngredientFilters(currentIngredients);
                }
                ingredientInput.setText("");
            }
        });

        setupAdditionalFilters();
    }

    private void setupAdditionalFilters() {
        timeFilterChip.setOnClickListener(v -> showTimeFilterDialog());
        difficultyFilterChip.setOnClickListener(v -> showDifficultyFilterDialog());
    }

    private void setupMyFridgeButton() {
        myFridgeButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MyFridge.class);
            startActivity(intent);
        });
    }

    private void handleSearch(String query) {
        if (query.isEmpty()) {
            hideIngredientFilters();
            recipeAdapter.updateList(recipeList);
            searchKeyword.setText("");
            return;
        }

        searchKeyword.setText(query);

        if (ingredientFilterToggle.isChecked()) {
            List<String> matchingIngredients = searchIngredients(query);
            if (!matchingIngredients.isEmpty()) {
                showIngredientFilters(matchingIngredients);
            } else {
                hideIngredientFilters();
            }
        }

        applyAllFilters();
    }

    private void showIngredientFilters(List<String> ingredients) {
        filterAdapter = new IngredientFilterAdapter(ingredients,
                new IngredientFilterAdapter.OnIngredientFilterListener() {
                    @Override
                    public void onIngredientSelected(Set<String> selectedIngredients) {
                        applyAllFilters();
                    }

                    @Override
                    public void onIngredientRemoved(String ingredient) {
                        defaultIngredients = new ArrayList<>(defaultIngredients);
                        defaultIngredients.remove(ingredient);
                    }
                });
        ingredientFilterRecyclerView.setAdapter(filterAdapter);
        ingredientFilterRecyclerView.setVisibility(View.VISIBLE);
    }

    private void hideIngredientFilters() {
        ingredientFilterRecyclerView.setVisibility(View.GONE);
        addIngredientLayout.setVisibility(View.GONE);
        if (filterAdapter != null) {
            filterAdapter.clearSelection();
        }
    }

    private void applyAllFilters() {
        List<? extends IRecipe> filteredList = recipeList;

        // 검색어 필터 적용
        if (!currentQuery.isEmpty()) {
            RecipeFilterStrategy searchStrategy = new SearchFilterStrategy(currentQuery);
            filteredList = searchStrategy.filter(filteredList);
        }

        // 재료 필터 적용
        if (ingredientFilterToggle.isChecked() && filterAdapter != null &&
                !filterAdapter.getSelectedIngredients().isEmpty()) {
            RecipeFilterStrategy ingredientStrategy = new IngredientFilterStrategy(
                    filterAdapter.getSelectedIngredients());
            filteredList = ingredientStrategy.filter(filteredList);
        }

        // 시간 필터 적용
        if (!selectedTimes.isEmpty()) {
            RecipeFilterStrategy timeStrategy = new TimeFilterStrategy(selectedTimes);
            filteredList = timeStrategy.filter(filteredList);
        }

        // 난이도 필터 적용
        if (!selectedDifficulties.isEmpty()) {
            RecipeFilterStrategy difficultyStrategy = new DifficultyFilterStrategy(selectedDifficulties);
            filteredList = difficultyStrategy.filter(filteredList);
        }

        // 레시피 타입별 필터링 추가
        if (filterAdapter != null && !filterAdapter.getSelectedIngredients().isEmpty()) {
            filteredList = filteredList.stream()
                    .filter(recipe -> {
                        if (recipe instanceof KoreanRecipe && ((KoreanRecipe) recipe).containsKimchi()) {
                            return filterAdapter.getSelectedIngredients().contains("김치");
                        }
                        if (recipe instanceof VeganRecipe) {
                            return !recipe.getIngredients().toLowerCase().contains("고기");
                        }
                        return true;
                    })
                    .collect(Collectors.toList());
        }

        recipeAdapter.updateList(filteredList);
    }

    private void showTimeFilterDialog() {
        String[] times = { "15분 이내", "30분 이내", "1시간 이내", "1시간 이상" };
        boolean[] checkedItems = new boolean[times.length];

        for (int i = 0; i < times.length; i++) {
            checkedItems[i] = selectedTimes.contains(times[i]);
        }

        new AlertDialog.Builder(this)
                .setTitle("조리시간 선택")
                .setMultiChoiceItems(times, checkedItems, (dialog, which, isChecked) -> {
                    if (isChecked) {
                        selectedTimes.add(times[which]);
                    } else {
                        selectedTimes.remove(times[which]);
                    }
                })
                .setPositiveButton("확인", (dialog, which) -> {
                    timeFilterChip.setChecked(!selectedTimes.isEmpty());
                    applyAllFilters();
                })
                .setNegativeButton("취소", null)
                .show();
    }

    private void showDifficultyFilterDialog() {
        String[] difficulties = { "초급", "중급", "고급" };
        boolean[] checkedItems = new boolean[difficulties.length];

        for (int i = 0; i < difficulties.length; i++) {
            checkedItems[i] = selectedDifficulties.contains(difficulties[i]);
        }

        new AlertDialog.Builder(this)
                .setTitle("난이도 선택")
                .setMultiChoiceItems(difficulties, checkedItems, (dialog, which, isChecked) -> {
                    if (isChecked) {
                        selectedDifficulties.add(difficulties[which]);
                    } else {
                        selectedDifficulties.remove(difficulties[which]);
                    }
                })
                .setPositiveButton("확인", (dialog, which) -> {
                    difficultyFilterChip.setChecked(!selectedDifficulties.isEmpty());
                    applyAllFilters();
                })
                .setNegativeButton("취소", null)
                .show();
    }

    private List<String> searchIngredients(String query) {
        Set<String> ingredients = new HashSet<>();
        for (IRecipe recipe : recipeList) {
            String[] recipeIngredients = recipe.getIngredients().split(",");
            for (String ingredient : recipeIngredients) {
                String cleanIngredient = ingredient.trim();
                if (cleanIngredient.toLowerCase().contains(query.toLowerCase())) {
                    ingredients.add(cleanIngredient);
                }
            }
        }
        return new ArrayList<>(ingredients);
    }

    private List<? extends IRecipe> filterRecipesByIngredients(List<? extends IRecipe> recipes,
                                                               List<String> ingredients) {
        if (ingredients.isEmpty()) {
            return recipes;
        }

        return recipes.stream()
                .filter(recipe -> ingredients.stream()
                        .anyMatch(ingredient -> recipe.getIngredients().toLowerCase()
                                .contains(ingredient.toLowerCase())))
                .collect(Collectors.toList());
    }

    private boolean matchesCookingTime(String cookingTime, Set<String> selectedTimes) {
        int minutes = parseCookingTime(cookingTime);
        for (String timeFilter : selectedTimes) {
            switch (timeFilter) {
                case "15분 이내":
                    if (minutes <= 15)
                        return true;
                    break;
                case "30분 이내":
                    if (minutes <= 30)
                        return true;
                    break;
                case "1시간 이내":
                    if (minutes <= 60)
                        return true;
                    break;
                case "1시간 이상":
                    if (minutes > 60)
                        return true;
                    break;
            }
        }
        return false;
    }

    private int parseCookingTime(String cookingTime) {
        try {
            return Integer.parseInt(cookingTime.replaceAll("[^0-9]", ""));
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private void performSearch(String query) {
        List<? extends IRecipe> searchResults = searchRecipes(query);
        recipeAdapter.updateList(searchResults);
    }

    private List<? extends IRecipe> searchRecipes(String query) {
        if (query.isEmpty()) {
            return recipeList;
        }
        return recipeList.stream()
                .filter(recipe -> recipe.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                        recipe.getIngredients().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());
    }

    private List<? extends IRecipe> filterRecipesByTime(List<? extends IRecipe> recipes) {
        return recipes.stream()
                .filter(recipe -> matchesCookingTime(recipe.getCookingTime(), selectedTimes))
                .collect(Collectors.toList());
    }

    private List<? extends IRecipe> filterRecipesByDifficulty(List<? extends IRecipe> recipes) {
        return recipes.stream()
                .filter(recipe -> selectedDifficulties.contains(recipe.getDifficulty()))
                .collect(Collectors.toList());
    }
}