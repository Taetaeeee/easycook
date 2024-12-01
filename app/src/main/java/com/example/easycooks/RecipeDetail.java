package com.example.easycooks;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ImageButton;
import android.widget.Button;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.app.AlertDialog;
import android.widget.Toast;

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
    private Button addStepButton;
    private List<CookingStep> cookingSteps;
    private Button saveAsNewButton;
    private Button deleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipedetail);

        // 기본 뷰 초기화
        initializeBasicViews();

        // Intent에서 레시피 정보 가져오기
        currentRecipe = getRecipeFromIntent();

        // 레시피가 null이 아닐 때만 나머지 초기화 진행
        if (currentRecipe != null) {
            // 버시피 정보 표시
            displayRecipeDetails(currentRecipe);

            // 조리 순서 설정
            setupCookingSteps();

            // 부족한 재료를 확인하고 화면에 표시
            checkMissingIngredients(currentRecipe);

            // 즐겨찾기 버튼 설정
            setupFavoriteButton();

            // 버튼 초기화 및 리스너 설정
            setupButtons();

            // 디버그 로그 추가
            System.out.println("Current Recipe: " + currentRecipe.getTitle());
            System.out.println("Is Custom: " + DummyRecipeData.isCustomRecipe(currentRecipe));
        } else {
            // 레시피 로드 실패 시 처리
            Toast.makeText(this, "레시피를 불러올 수 없습니다.", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void initializeBasicViews() {
        recipeImage = findViewById(R.id.recipeImage);
        recipeTitle = findViewById(R.id.recipeTitle);
        cookingTime = findViewById(R.id.cookingTime);
        difficulty = findViewById(R.id.difficulty);
        ingredients = findViewById(R.id.ingredients);
        nutritionInfo = findViewById(R.id.nutritionInfo);
        cookingStepsRecyclerView = findViewById(R.id.cookingStepsRecyclerView);
        missingIngredientsView = findViewById(R.id.missingIngredients);
        addStepButton = findViewById(R.id.addStepButton);
        saveAsNewButton = findViewById(R.id.saveAsNewButton);
        deleteButton = findViewById(R.id.deleteButton);
        favoriteButton = findViewById(R.id.favoriteButton);
    }

    private void setupButtons() {
        // 추가 버튼 클릭 리스너 설정
        addStepButton.setOnClickListener(v -> showAddStepDialog());
        saveAsNewButton.setOnClickListener(v -> showSaveAsNewDialog());

        // 커스텀 레시피인 경우에만 삭제 버튼 표시
        if (DummyRecipeData.isCustomRecipe(currentRecipe)) {
            deleteButton.setVisibility(View.VISIBLE);
            deleteButton.setOnClickListener(v -> showDeleteConfirmDialog());
        } else {
            deleteButton.setVisibility(View.GONE);
        }
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
        cookingStepsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        cookingSteps = DummyRecipeData.getRecipeSteps(currentRecipe);
        cookingStepsAdapter = new CookingStepsAdapter(cookingSteps);
        cookingStepsRecyclerView.setAdapter(cookingStepsAdapter);
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
            // 표로 구분하여 부족한 재료 문자열 생성
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

    private void showAddStepDialog() {
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_step, null);
        EditText stepNumberInput = dialogView.findViewById(R.id.stepNumberInput);
        EditText descriptionInput = dialogView.findViewById(R.id.descriptionInput);

        // 기본값으로 마지막 단계 + 1을 표시
        int nextStepNumber = cookingSteps.isEmpty() ? 1 : cookingSteps.size() + 1;
        stepNumberInput.setText(String.valueOf(nextStepNumber));

        new AlertDialog.Builder(this)
                .setTitle("조리 순서 추가")
                .setView(dialogView)
                .setPositiveButton("추가", (dialog, which) -> {
                    String stepNumberStr = stepNumberInput.getText().toString();
                    String description = descriptionInput.getText().toString();

                    if (!stepNumberStr.isEmpty() && !description.isEmpty()) {
                        int newStepNumber = Integer.parseInt(stepNumberStr);

                        // 입력된 단계 번호가 유효한지 확인
                        if (newStepNumber <= 0 || newStepNumber > cookingSteps.size() + 1) {
                            Toast.makeText(this, "유효하지 않은 단계 번호입니다.", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        // 새로운 단계를 삽입하고 이후 단계들의 번호를 증가
                        for (int i = cookingSteps.size() - 1; i >= newStepNumber - 1; i--) {
                            CookingStep step = cookingSteps.get(i);
                            step.setStepNumber(step.getStepNumber() + 1);
                        }

                        // 새로운 단계 추가
                        cookingSteps.add(newStepNumber - 1, new CookingStep(
                                newStepNumber,
                                description,
                                R.drawable.default_recipe_image));

                        // 단계 번호로 정렬
                        cookingSteps.sort((s1, s2) -> s1.getStepNumber() - s2.getStepNumber());

                        cookingStepsAdapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton("취소", null)
                .show();
    }

    private void showSaveAsNewDialog() {
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_save_recipe, null);
        EditText recipeNameInput = dialogView.findViewById(R.id.recipeNameInput);

        // 기존 이름을 기본값으로 설정
        recipeNameInput.setText(currentRecipe.getTitle() + " (나만의 레시피)");

        new AlertDialog.Builder(this)
                .setTitle("새 레시피로 저장")
                .setView(dialogView)
                .setPositiveButton("저장", (dialog, which) -> {
                    String newName = recipeNameInput.getText().toString().trim();
                    if (!newName.isEmpty()) {
                        saveAsNewRecipe(newName);
                    }
                })
                .setNegativeButton("취소", null)
                .show();
    }

    private void saveAsNewRecipe(String newName) {
        Recipe newRecipe = Recipe.createDetailedRecipe(
                newName,
                currentRecipe.getIngredients(),
                currentRecipe.getDescription(),
                currentRecipe.getCookingTime(),
                currentRecipe.getServings(),
                currentRecipe.getImageResourceId(),
                currentRecipe.getDifficulty());

        DummyRecipeData.addCustomRecipe(newRecipe, new ArrayList<>(cookingSteps));
        DummyRecipeData.saveCustomRecipes(this);

        Toast.makeText(this, "새로운 레시피가 저장되었습니다.", Toast.LENGTH_SHORT).show();

        // 메인 화면으로 돌아가기
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    private void showDeleteConfirmDialog() {
        new AlertDialog.Builder(this)
                .setTitle("레시피 삭제")
                .setMessage("이 레시피를 삭제하시겠습니까?")
                .setPositiveButton("삭제", (dialog, which) -> {
                    DummyRecipeData.deleteCustomRecipe(this, currentRecipe);
                    Toast.makeText(this, "레시피가 삭제되었습니다.", Toast.LENGTH_SHORT).show();

                    // 메인 화면으로 돌아가기
                    Intent intent = new Intent(this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                })
                .setNegativeButton("취소", null)
                .show();
    }
}