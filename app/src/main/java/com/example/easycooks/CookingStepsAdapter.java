package com.example.easycooks;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CookingStepsAdapter extends RecyclerView.Adapter<CookingStepsAdapter.ViewHolder> {
    private List<CookingStep> cookingSteps;

    public CookingStepsAdapter(List<CookingStep> cookingSteps) {
        this.cookingSteps = cookingSteps;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cooking_step, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CookingStep step = cookingSteps.get(position);
        holder.stepNumberTextView.setText("Step " + step.getStepNumber());
        holder.descriptionTextView.setText(step.getDescription());
        holder.stepImageView.setImageResource(step.getImageResourceId());
    }

    @Override
    public int getItemCount() {
        return cookingSteps.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView stepNumberTextView;
        TextView descriptionTextView;
        ImageView stepImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            stepNumberTextView = itemView.findViewById(R.id.stepNumberTextView);
            descriptionTextView = itemView.findViewById(R.id.stepDescriptionTextView);
            stepImageView = itemView.findViewById(R.id.stepImageView);
        }
    }
}