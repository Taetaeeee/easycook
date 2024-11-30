package com.example.easycooks;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class IngredientFilterAdapter extends RecyclerView.Adapter<IngredientFilterAdapter.ViewHolder> {
    private List<String> ingredients;
    private Set<String> selectedIngredients = new HashSet<>();
    private OnIngredientFilterListener listener;

    public interface OnIngredientFilterListener {
        void onIngredientSelected(Set<String> selectedIngredients);
    }

    public IngredientFilterAdapter(List<String> ingredients, OnIngredientFilterListener listener) {
        this.ingredients = ingredients;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_ingredient_filter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String ingredient = ingredients.get(position);
        holder.ingredientChip.setText(ingredient);
        holder.ingredientChip.setChecked(selectedIngredients.contains(ingredient));

        holder.ingredientChip.setOnClickListener(v -> {
            if (selectedIngredients.contains(ingredient)) {
                selectedIngredients.remove(ingredient);
            } else {
                selectedIngredients.add(ingredient);
            }
            listener.onIngredientSelected(selectedIngredients);
        });
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public void clearSelection() {
        selectedIngredients.clear();
        notifyDataSetChanged();
    }

    public List<String> getSelectedIngredients() {
        return new ArrayList<>(selectedIngredients);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        Chip ingredientChip;

        ViewHolder(View itemView) {
            super(itemView);
            ingredientChip = itemView.findViewById(R.id.ingredientChip);
        }
    }
}