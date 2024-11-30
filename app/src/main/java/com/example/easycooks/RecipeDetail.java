package com.example.easycooks;


import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
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
    private List<CookingStep> cookingSteps;

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
            String recipeId = recipe.getTitle(); // 레시피 제목을 고유 ID로 사용
            loadCookingSteps(recipeId);          // 고유 ID로 저장된 데이터 로드
            displayRecipeDetails(recipe);
        }

        // 조리 순서 설정
        setupCookingSteps();

        // 부족한 재료를 확인하고 화면에 표시
        checkMissingIngredients(recipe);

        // 추가 버튼 클릭 이벤트 설정
        Button addStepButton = findViewById(R.id.addStepButton);
        addStepButton.setOnClickListener(v -> {
            showAddStepDialog();
            saveCookingSteps(recipe.getTitle()); // 고유 ID로 데이터 저장
        });
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
                    Recipe.VeganRecipe.Builder veganBuilder = new Recipe.VeganRecipe.Builder(title, ingredients, imageResourceId);
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
        if (cookingSteps == null) {
            cookingSteps = new ArrayList<>();
        }
        cookingSteps.add(new CookingStep(1, "물을 끓입니다.", R.drawable.default_recipe_image));
        cookingSteps.add(new CookingStep(2, "재료를 넣고 끓입니다.", R.drawable.default_recipe_image));
        cookingSteps.add(new CookingStep(3, "간을 맞춥니다.", R.drawable.default_recipe_image));

        cookingStepsAdapter = new CookingStepsAdapter(cookingSteps);
        cookingStepsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
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
            // 부족한 재료를 텍스트에 추가
            SpannableString spannableString = new SpannableString("부족한 재료: " + TextUtils.join(", ", missingIngredients));

            // "부족한 재료: " 길이를 계산
            int startIndex = "부족한 재료: ".length();

            for (int i = 0; i < missingIngredients.size(); i++) {
                String ingredient = missingIngredients.get(i);
                int endIndex = startIndex + ingredient.length();

                // 각 재료에 클릭 이벤트 설정
                ClickableSpan clickableSpan = new ClickableSpan() {
                    @Override
                    public void onClick(View widget) {
                        String searchQuery = Uri.encode(ingredient);
                        String url = "https://www.coupang.com/np/search?component=&q=" + searchQuery;
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        widget.getContext().startActivity(intent);
                    }

                    @Override
                    public void updateDrawState(android.text.TextPaint ds) {
                        super.updateDrawState(ds);
                        ds.setColor(getResources().getColor(android.R.color.holo_red_dark)); // 텍스트 색상
                    }
                };

                // 재료에 클릭 이벤트 추가
                spannableString.setSpan(clickableSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                startIndex = endIndex + 2; // ", " 길이만큼 추가
            }

            // TextView에 클릭 가능한 텍스트 설정
            missingIngredientsView.setText(spannableString);
            missingIngredientsView.setMovementMethod(android.text.method.LinkMovementMethod.getInstance());
        }
    }

    private void showAddStepDialog() {
        // AlertDialog.Builder를 사용하여 다이얼로그 생성
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("나만의 레시피 추가");

        // 다이얼로그 레이아웃 설정
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_step, null);
        builder.setView(dialogView);

        // 레이아웃의 입력 필드 참조
        EditText stepNumberInput = dialogView.findViewById(R.id.stepNumberInput);
        EditText descriptionInput = dialogView.findViewById(R.id.descriptionInput);

        // "추가" 버튼 동작
        builder.setPositiveButton("추가", (dialog, which) -> {
            String stepNumberText = stepNumberInput.getText().toString().trim();
            String descriptionText = descriptionInput.getText().toString().trim();

            if (!stepNumberText.isEmpty() && !descriptionText.isEmpty()) {
                int stepNumber = Integer.parseInt(stepNumberText);

                // Step 찾기 또는 추가
                boolean stepExists = false;
                for (CookingStep step : cookingSteps) {
                    if (step.getStepNumber() == stepNumber) {
                        step.setDescription(step.getDescription() + "\n" + descriptionText);
                        stepExists = true;
                        break;
                    }
                }
                if (!stepExists) {
                    cookingSteps.add(new CookingStep(stepNumber, descriptionText, R.drawable.default_recipe_image));
                }

                // RecyclerView 업데이트
                cookingStepsAdapter.notifyDataSetChanged();

                // 조리 단계 저장 (추가된 내용 반영)
                Recipe recipe = getRecipeFromIntent();
                if (recipe != null) {
                    saveCookingSteps(recipe.getTitle());
                }
            } else {
                Toast.makeText(this, "모든 항목을 입력하세요.", Toast.LENGTH_SHORT).show();
            }
        });

        // "취소" 버튼 동작
        builder.setNegativeButton("취소", (dialog, which) -> dialog.cancel());

        // 다이얼로그 표시
        builder.show();
    }

    private void saveCookingSteps(String recipeId) {
        SharedPreferences prefs = getSharedPreferences("RecipeDetailPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        // 조리 단계 데이터를 JSON 문자열로 변환
        Gson gson = new Gson();
        String jsonSteps = gson.toJson(cookingSteps);

        // 레시피 ID를 키로 사용하여 저장
        editor.putString("CookingSteps_" + recipeId, jsonSteps);
        editor.apply();
    }

    private void loadCookingSteps(String recipeId) {
        SharedPreferences prefs = getSharedPreferences("RecipeDetailPrefs", MODE_PRIVATE);

        // JSON 문자열로 저장된 조리 단계 불러오기
        String jsonSteps = prefs.getString("CookingSteps_" + recipeId, null);

        if (jsonSteps != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<CookingStep>>() {}.getType();
            cookingSteps = gson.fromJson(jsonSteps, type);
        } else {
            cookingSteps = new ArrayList<>();
        }
    }
}