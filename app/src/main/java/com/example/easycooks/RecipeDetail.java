package com.example.easycooks;

import com.example.easycooks.data.DummyRecipeData;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecipeDetail extends AppCompatActivity {
    private ImageView recipeImage;
    private TextView recipeTitle;
    private TextView cookingTime;
    private TextView difficulty;
    private TextView ingredients;
    private TextView nutritionInfo;
    private RecyclerView cookingStepsRecyclerView;
    private CookingStepsAdapter cookingStepsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipedetail);

        // 뷰 초기화
        initializeViews();

        // Intent에서 레시피 정보 가져오기
        Recipe recipe = getRecipeFromIntent();

        // 레시피 정보 표시
        if (recipe != null) {
            displayRecipeDetails(recipe);
        }

        // 조리 순서 설정
        setupCookingSteps();
    }

    private void initializeViews() {
        recipeImage = findViewById(R.id.recipeImage);
        recipeTitle = findViewById(R.id.recipeTitle);
        cookingTime = findViewById(R.id.cookingTime);
        difficulty = findViewById(R.id.difficulty);
        ingredients = findViewById(R.id.ingredients);
        nutritionInfo = findViewById(R.id.nutritionInfo);
        cookingStepsRecyclerView = findViewById(R.id.cookingStepsRecyclerView);
    }

    private Recipe getRecipeFromIntent() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            return Recipe.createDetailedRecipe(
                extras.getString("title", ""),
                extras.getString("ingredients", ""),
                extras.getString("description", ""),
                extras.getString("cookingTime", ""),
                extras.getString("servings", "2인분"),
                extras.getInt("imageResourceId", 0),
                extras.getString("difficulty", "")
            );
        }
        return null;
    }

    private void displayRecipeDetails(Recipe recipe) {
        // 레시피 이미지 설정
        recipeImage.setImageResource(recipe.getImageResourceId());

        // 레시피 제목 설정
        recipeTitle.setText(recipe.getTitle());

        // 조리 시간 설정
        cookingTime.setText(recipe.getCookingTime());

        // 난이도 설정
        difficulty.setText(recipe.getDifficulty());

        // 재료 설정
        ingredients.setText(formatIngredients(recipe.getIngredients()));

        // 영양 정보 설정 (예시)
        nutritionInfo.setText("칼로리: 300kcal\n단백질: 20g\n탄수화물: 30g\n지방: 10g");
    }

    private String formatIngredients(String ingredients) {
        // 재료 문자열을 보기 좋게 포맷팅
        String[] ingredientList = ingredients.split(",");
        StringBuilder formattedIngredients = new StringBuilder();
        for (String ingredient : ingredientList) {
            formattedIngredients.append("• ").append(ingredient.trim()).append("\n");
        }
        return formattedIngredients.toString();
    }

    private void setupCookingSteps() {
        // 조리 순서 RecyclerView 설정
        cookingStepsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<CookingStep> cookingSteps = createCookingSteps();
        cookingStepsAdapter = new CookingStepsAdapter(cookingSteps);
        cookingStepsRecyclerView.setAdapter(cookingStepsAdapter);
    }

    private List<CookingStep> createCookingSteps() {
        List<CookingStep> steps = new ArrayList<CookingStep>();
        steps.add(new CookingStep(1, "물을 끓입니다.", R.drawable.default_recipe_image));
        steps.add(new CookingStep(2, "재료를 넣고 끓입니다.", R.drawable.default_recipe_image));
        steps.add(new CookingStep(3, "간을 맞춥니다.", R.drawable.default_recipe_image));
        return steps;
    }
}
