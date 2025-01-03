package com.example.easycooks;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ClickableSpan;
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

// Recipe 관련 import 추가
import com.example.easycooks.Recipe;
import com.example.easycooks.IRecipe;
import com.example.easycooks.recipe.*;

public class RecipeDetail extends BaseObserverActivity {
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
            // 레시피 정보 표시
            displayRecipeDetails(currentRecipe);

            // 조리 순서 설정
            setupCookingSteps();

            // 부족한 재료를 확인하고 화면에 표시
            checkMissingIngredients(currentRecipe);

            // 즐겨찾기 버튼 설정, 초기 상태 설정
            setupFavoriteButton();
            updateFavoriteButtonState();

            // 버튼 초기화 및 리스너 설정
            setupButtons();
        } else {
            // 레시피 로드 실패 시 처리
            Toast.makeText(this, "레시피를 불러올 수 없습니다.", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    public void onFridgeContentsChanged() {
        // 냉장고 내용이 변경되면 부족한 재료 목록 업데이트
        if (currentRecipe != null) {
            checkMissingIngredients(currentRecipe);
        }
    }

    @Override
    public void onFavoritesChanged() {
        // 즐겨찾기 상태가 변경되면 버튼 상태 업데이트
        updateFavoriteButtonState();
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
            String recipeType = extras.getString("recipeType", "").toLowerCase();

            // RecipeFactory를 사용해 레시피 객체 생성
            try {
                return RecipeFactory.createRecipe(recipeType, title, ingredients, imageResourceId);
            } catch (IllegalArgumentException e) {
                Toast.makeText(this, "알 수 없는 레시피 유형입니다: " + recipeType, Toast.LENGTH_SHORT).show();
                return null;
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

        // 레시피 유형별 추가 정보 표시
        if (recipe instanceof KoreanRecipe) {
            KoreanRecipe koreanRecipe = (KoreanRecipe) recipe;
            nutritionInfo.setText("매운 정도: " + koreanRecipe.getSpicyLevel());
        } else if (recipe instanceof DietRecipe) {
            DietRecipe dietRecipe = (DietRecipe) recipe;
            nutritionInfo.setText("칼로리: " + dietRecipe.getCalories() + "kcal\n단백질: " + dietRecipe.getProtein() + "g");
        } else if (recipe instanceof LowSaltRecipe) {
            LowSaltRecipe lowSaltRecipe = (LowSaltRecipe) recipe;
            nutritionInfo.setText("나트륨 함량: " + lowSaltRecipe.getSodiumContent() + "mg\n대체 소금: " + lowSaltRecipe.getSaltAlternative());
        } else if (recipe instanceof VeganRecipe) {
            VeganRecipe veganRecipe = (VeganRecipe) recipe;
            nutritionInfo.setText("단백질 공급원: " + TextUtils.join(", ", veganRecipe.getVeganProteinSources()));
        } else if (recipe instanceof LowSugarRecipe) {
            LowSugarRecipe lowSugarRecipe = (LowSugarRecipe) recipe;
            nutritionInfo.setText("저당 함량: " + lowSugarRecipe.getSugarContent() + "g\n감미료: " + lowSugarRecipe.getSweetener());
        } else {
            nutritionInfo.setText("영양 정보가 제공되지 않습니다.");
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
        List<String> missingIngredients = SmartRefrigerator.getMissingIngredients(recipe.getIngredients());
        if (missingIngredients.isEmpty()) {
            missingIngredientsView.setText("모든 재료가 냉장고에 있습니다.");
        } else {
            displayMissingIngredients(missingIngredients);
        }
    }

    private void displayMissingIngredients(List<String> missingIngredients) {
        SpannableString spannableString = new SpannableString("부족한 재료: " + TextUtils.join(", ", missingIngredients));
        int startIndex = "부족한 재료: ".length();

        for (String ingredient : missingIngredients) {
            int endIndex = startIndex + ingredient.length();
            spannableString.setSpan(createClickableSpan(ingredient), startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            startIndex = endIndex + 2; // ", " 길이만큼 추가
        }

        missingIngredientsView.setText(spannableString);
        missingIngredientsView.setMovementMethod(android.text.method.LinkMovementMethod.getInstance());
    }

    private ClickableSpan createClickableSpan(String ingredient) {
        return new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                String searchQuery = Uri.encode(ingredient);
                String url = "https://www.coupang.com/np/search?component=&q=" + searchQuery;
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            }

            @Override
            public void updateDrawState(android.text.TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(getResources().getColor(android.R.color.holo_red_dark));
            }
        };
    }

    private void setupFavoriteButton() {
        // 초기 상태 업데이트
        updateFavoriteButtonState();

        favoriteButton.setOnClickListener(v -> {
            if (currentRecipe != null) {
                SmartRefrigerator.toggleFavorite(currentRecipe.getTitle());
                // 버튼 상태 즉시 업데이트
                updateFavoriteButtonState();
            }
        });
    }

    private void updateFavoriteButtonState() {
        if (currentRecipe != null) {
            // 현재 레시피가 즐겨찾기에 있는지 확인
            boolean isFavorite = SmartRefrigerator.isFavorite(currentRecipe.getTitle());

            // 버튼 상태 업데이트
            favoriteButton.setImageResource(
                    isFavorite ? R.drawable.ic_favorite : R.drawable.ic_favorite_border
            );
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
