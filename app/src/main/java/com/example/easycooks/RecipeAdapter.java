package com.example.easycooks;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {
    //private List<Recipe> recipeList;
    private List<? extends IRecipe> recipeList;
    private final Context context;

    public RecipeAdapter(Context context, List<? extends IRecipe> recipeList) {
        this.context = context;
        this.recipeList = recipeList;
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
        IRecipe recipe = recipeList.get(position);
        holder.titleTextView.setText(recipe.getTitle());
        holder.ingredientsTextView.setText(recipe.getIngredients());
        holder.recipeImage.setImageResource(recipe.getImageResourceId());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, RecipeDetail.class);
            intent.putExtras(recipe.toBundle());
            context.startActivity(intent);
/*        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), RecipeDetail.class);
            Bundle recipeData = new Bundle();
            recipeData.putString("title", recipe.getTitle());
            recipeData.putString("ingredients", recipe.getIngredients());
            recipeData.putInt("imageResourceId", recipe.getImageResourceId());

            if (recipe instanceof Recipe.KoreanRecipe) {
                Recipe.KoreanRecipe koreanRecipe = (Recipe.KoreanRecipe) recipe;
                recipeData.putString("recipeType", "korean");
                recipeData.putString("spicyLevel", koreanRecipe.getSpicyLevel());
            } else if (recipe instanceof Recipe.DietRecipe) {
                Recipe.DietRecipe dietRecipe = (Recipe.DietRecipe) recipe;
                recipeData.putString("recipeType", "diet");
                recipeData.putInt("calories", dietRecipe.getCalories());
                recipeData.putInt("protein", dietRecipe.getProtein());
            } else if (recipe instanceof Recipe.LowSaltRecipe) {
                Recipe.LowSaltRecipe lowSaltRecipe = (Recipe.LowSaltRecipe) recipe;
                recipeData.putString("recipeType", "lowSalt");
                recipeData.putInt("sodiumContent", lowSaltRecipe.getSodiumContent());
                recipeData.putString("saltAlternative", lowSaltRecipe.getSaltAlternative());
            } else if (recipe instanceof Recipe.VeganRecipe) {
                Recipe.VeganRecipe veganRecipe = (Recipe.VeganRecipe) recipe;
                recipeData.putString("recipeType", "vegan");
                recipeData.putStringArrayList("proteinSources",
                        new ArrayList<>(veganRecipe.getVeganProteinSources()));
                recipeData.putBoolean("isRawVegan", veganRecipe.isRawVegan());
            } else if (recipe instanceof Recipe.LowSugarRecipe) {
                Recipe.LowSugarRecipe lowSugarRecipe = (Recipe.LowSugarRecipe) recipe;
                recipeData.putString("recipeType", "lowSugar");
                recipeData.putInt("sugarContent", lowSugarRecipe.getSugarContent());
                recipeData.putString("sweetener", lowSugarRecipe.getSweetener());
            }

            intent.putExtras(recipeData);
            v.getContext().startActivity(intent);*/
        });
    }

    @Override
    public int getItemCount() {
        return recipeList != null ? recipeList.size() : 0;
        //return recipeList.size();
    }

    public void updateList(List<? extends IRecipe> newList) {
        this.recipeList = newList;
/*        this.recipeList.clear();
        this.recipeList.addAll((Collection<? extends Recipe>) newList);*/
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