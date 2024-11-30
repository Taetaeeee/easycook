package com.example.easycooks;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ImageButton;

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
    private TextView missingIngredientsView;
    private ImageButton favoriteButton;
    private Recipe currentRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipedetail);

        // 뷰 초기화
        initializeViews();

        // Intent에서 레시피 정보 가져오기
        currentRecipe = getRecipeFromIntent();

        // 레시피 정보 표시
        if (currentRecipe != null) {
            displayRecipeDetails(currentRecipe);
        }

        // 조리 순서 설정
        setupCookingSteps();

        // 부족한 재료를 확인하고 화면에 표시
        checkMissingIngredients(currentRecipe);

        // 즐겨찾기 버튼 설정
        setupFavoriteButton();
    }

    private void initializeViews() {
        recipeImage = findViewById(R.id.recipeImage);
        recipeTitle = findViewById(R.id.recipeTitle);
        cookingTime = findViewById(R.id.cookingTime);
        difficulty = findViewById(R.id.difficulty);
        ingredients = findViewById(R.id.ingredients);
        nutritionInfo = findViewById(R.id.nutritionInfo);
        cookingStepsRecyclerView = findViewById(R.id.cookingStepsRecyclerView);
        missingIngredientsView = findViewById(R.id.missingIngredients);
    }

    private Recipe getRecipeFromIntent() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String title = extras.getString("title", "");
            String ingredients = extras.getString("ingredients", "");
            int imageResourceId = extras.getInt("imageResourceId", 0);

            switch (extras.getString("recipeType", "")) {
                case "korean":
                    return new Recipe.KoreanRecipe.Builder(title, ingredients, imageResourceId)
                            .spicyLevel(extras.getString("spicyLevel", "보통"))
                            .build();
                case "diet":
                    return new Recipe.DietRecipe.Builder(title, ingredients, imageResourceId)
                            .calories(extras.getInt("calories", 0))
                            .protein(extras.getInt("protein", 0))
                            .build();
                case "lowSalt":
                    return new Recipe.LowSaltRecipe.Builder(title, ingredients, imageResourceId)
                            .sodiumContent(extras.getInt("sodiumContent", 0))
                            .withSaltSubstitute(extras.getString("saltAlternative", ""))
                            .build();
                case "vegan":
                    Recipe.VeganRecipe.Builder veganBuilder = new Recipe.VeganRecipe.Builder(title, ingredients,
                            imageResourceId);
                    ArrayList<String> proteinSources = extras.getStringArrayList("proteinSources");
                    if (proteinSources != null) {
                        for (String source : proteinSources) {
                            veganBuilder.addProteinSource(source);
                        }
                    }
                    if (extras.getBoolean("isRawVegan", false)) {
                        veganBuilder.setRawVegan();
                    }
                    return veganBuilder.build();
                case "lowSugar":
                    return new Recipe.LowSugarRecipe.Builder(title, ingredients, imageResourceId)
                            .sugarContent(extras.getInt("sugarContent", 0))
                            .withSweetener(extras.getString("sweetener", ""))
                            .build();
                default:
                    return Recipe.createDetailedRecipe(
                            title, ingredients, "", "30분", "2인분",
                            imageResourceId, "보통");
            }
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

        // 레시피 타입별 추가 정보 표시
        if (recipe instanceof Recipe.KoreanRecipe) {
            Recipe.KoreanRecipe koreanRecipe = (Recipe.KoreanRecipe) recipe;
            // 한식 특화 정보 표시
        } else if (recipe instanceof Recipe.DietRecipe) {
            Recipe.DietRecipe dietRecipe = (Recipe.DietRecipe) recipe;
            // 다이어트 특화 정보 표시
        }
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

    private void checkMissingIngredients(Recipe recipe) {
        List<String> missingIngredients = new ArrayList<>();
        for (String ingredient : recipe.getIngredients().split(",")) {
            // 정규식으로 재료 이름만 추출
            String cleanIngredient = ingredient.replaceAll("[^가-힣a-zA-Z ]", "").trim().split(" ")[0];
            if (!SmartRefrigerator.hasIngredient(cleanIngredient.toLowerCase())) {
                missingIngredients.add(cleanIngredient);
            }
        }

        if (missingIngredients.isEmpty()) {
            missingIngredientsView.setText("모든 재료가 냉장고에 있습니다.");
        } else {
            // 쉼표로 구분하여 부족한 재료 문자열 생성
            String missingText = String.join(", ", missingIngredients);
            missingIngredientsView.setText("부족한 재료: " + missingText);
        }
    }

    private void setupFavoriteButton() {
        favoriteButton = findViewById(R.id.favoriteButton);
        updateFavoriteButtonState();

        favoriteButton.setOnClickListener(v -> {
            SmartRefrigerator.toggleFavorite(currentRecipe.getTitle());
            updateFavoriteButtonState();
        });
    }

    private void updateFavoriteButtonState() {
        if (currentRecipe != null) {
            boolean isFavorite = SmartRefrigerator.isFavorite(currentRecipe.getTitle());
            favoriteButton.setImageResource(isFavorite ? R.drawable.ic_favorite : R.drawable.ic_favorite_border);
        }
    }
}