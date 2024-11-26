package com.example.easycooks;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {
    private List<Recipe> recipes;
    private Context context;

    public RecipeAdapter(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recipe, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        Recipe recipe = recipes.get(position);
        holder.titleTextView.setText(recipe.getTitle());
        holder.ingredientsTextView.setText(recipe.getIngredients());
        holder.recipeImage.setImageResource(recipe.getImageResourceId());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), RecipeDetail.class);
            Bundle recipeData = new Bundle();
            recipeData.putString("title", recipe.getTitle());
            recipeData.putString("ingredients", recipe.getIngredients());
            recipeData.putString("description", recipe.getDescription());
            recipeData.putString("cookingTime", recipe.getCookingTime());
            recipeData.putString("servings", recipe.getServings());
            recipeData.putInt("imageResourceId", recipe.getImageResourceId());
            recipeData.putString("difficulty", recipe.getDifficulty());
            
            intent.putExtras(recipeData);
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public void updateList(List<Recipe> newList) {
        recipes = newList;
        notifyDataSetChanged();
    }

    static class RecipeViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView ingredientsTextView;
        ImageView recipeImage;

        RecipeViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.recipeName);
            ingredientsTextView = itemView.findViewById(R.id.recipeDescription);
            recipeImage = itemView.findViewById(R.id.recipeImage);
        }
    }
}