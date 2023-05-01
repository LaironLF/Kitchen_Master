package com.laironlf.kitchen_master.data_provider;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.laironlf.kitchen_master.DB.Recipe;
import com.laironlf.kitchen_master.R;
import com.laironlf.kitchen_master.circle_menu.AppCircleNavigation;

import java.util.List;

public class  ReceiptListAdapter extends RecyclerView.Adapter<ReceiptListAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private List<Recipe> recipes;
    private Context context;
    private OnRecipeClickListener onRecipeClickListener;

    public ReceiptListAdapter(Context context, List<Recipe> recipes, OnRecipeClickListener onRecipeClickListener){
        this.recipes = recipes;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.onRecipeClickListener = onRecipeClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.list_item, viewGroup, false);
        return new ViewHolder(view, onRecipeClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Recipe receipt = recipes.get(i);
        viewHolder.ReceiptTitle.setText(receipt.recipeName);
        viewHolder.ReceiptType.setText(receipt.typeName);

//        RequestManager requestManager = Glide.with(context);
//        RequestBuilder requestBuilder = requestManager.load("http://developer.alexanderklimov.ru/android/images/android_cat.jpg");
//        requestBuilder.into(viewHolder.RecipeImage);

        Glide
                .with(context)
                .load(receipt.imageMainURL)
//                .placeholder(R.drawable.easy_cook)
//                .error(R.drawable.hard_cook)
                .into(viewHolder.RecipeImage);
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView ReceiptTitle, ReceiptType;
        ImageView RecipeImage;
        OnRecipeClickListener onRecipeClickListener;

        public ViewHolder(@NonNull View itemView, OnRecipeClickListener onRecipeClickListener) {
            super(itemView);
            this.onRecipeClickListener = onRecipeClickListener;
            ReceiptTitle = (TextView) itemView.findViewById(R.id.tv_receiptTitle);
            ReceiptType = (TextView) itemView.findViewById(R.id.tv_receiptType);
            RecipeImage = (ImageView) itemView.findViewById(R.id.imageReceipt);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(AppCircleNavigation.DrawerLayoutMotion.getState() == 2) return;
            onRecipeClickListener.onRecipeClick(getAdapterPosition(), view, recipes.get(getAdapterPosition()).recipeID);
        }
    }

    public interface OnRecipeClickListener{
        void onRecipeClick(int position, View view, int recipeID);
    }
}
