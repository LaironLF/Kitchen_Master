package com.laironlf.kitchen_master.data_provider;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.laironlf.kitchen_master.DB.Ingredient;
import com.laironlf.kitchen_master.R;

import java.util.ArrayList;

public class IngredientsListAdapter extends RecyclerView.Adapter<IngredientsListAdapter.ViewHolder> {

    private ArrayList<Ingredient> ingredients;
    LayoutInflater layoutInflater;

    public IngredientsListAdapter(Context context, ArrayList<Ingredient> ingredients){
        this.ingredients = ingredients;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.ingridient_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Ingredient ingredient = ingredients.get(position);
        holder.ingridName.setText(ingredient.productName);
        holder.ingridUnit.setText(ingredient.unitName);
        if(ingredient.count >= 0)
            holder.ingridCount.setText(String.valueOf(ingredient.count));
        else
            holder.ingridCount.setText("");
    }

    @Override
    public int getItemCount()
    {
        return ingredients.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView ingridName, ingridUnit, ingridCount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ingridName = (TextView) itemView.findViewById(R.id.ingridName);
            ingridUnit = (TextView) itemView.findViewById(R.id.ingridUnit);
            ingridCount = (TextView) itemView.findViewById(R.id.ingridCount);

        }
    }
}
