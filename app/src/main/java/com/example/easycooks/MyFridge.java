package com.example.easycooks;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class MyFridge extends AppCompatActivity {
    private EditText ingredientInput;
    private ListView ingredientsList;
    private ArrayAdapter<String> adapter;
    private Button favoriteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_fridge);

        ingredientInput = findViewById(R.id.ingredientInput);
        ingredientsList = findViewById(R.id.ingredientsList);
        favoriteButton = findViewById(R.id.favoriteButton);

        // 어댑터 초기화
        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                new ArrayList<>(SmartRefrigerator.getFridgeIngredients()));
        ingredientsList.setAdapter(adapter);

        // 즐겨찾기 버튼 클릭 리스너
        favoriteButton.setOnClickListener(v -> {
            Intent intent = new Intent(MyFridge.this, FavoriteRecipesActivity.class);
            startActivity(intent);
        });

        // 재료 추가 버튼
        Button addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(v -> {
            String ingredient = ingredientInput.getText().toString().trim();
            if (!ingredient.isEmpty()) {
                SmartRefrigerator.addIngredient(ingredient);
                adapter.clear();
                adapter.addAll(SmartRefrigerator.getFridgeIngredients());
                ingredientInput.setText("");
            }
        });

        // 재료 삭제 (길게 누르기)
        ingredientsList.setOnItemLongClickListener((parent, view, position, id) -> {
            String ingredient = adapter.getItem(position);
            SmartRefrigerator.removeIngredient(ingredient);
            adapter.clear();
            adapter.addAll(SmartRefrigerator.getFridgeIngredients());
            return true;
        });
    }
}