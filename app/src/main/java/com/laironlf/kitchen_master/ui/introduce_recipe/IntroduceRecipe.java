package com.laironlf.kitchen_master.ui.introduce_recipe;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.laironlf.kitchen_master.R;
import com.laironlf.kitchen_master.data_provider.IngredientsListAdapter;
import com.laironlf.kitchen_master.databinding.FragmentIntroduceRecipeBinding;
import com.laironlf.kitchen_master.ui.home.HomeViewModel;

public class IntroduceRecipe extends Fragment {

    private IntroduceRecipeViewModel mViewModel;
    private FragmentIntroduceRecipeBinding binding;
    private View root;
    private RecyclerView recyclerView;
    private TextView recipeName, recipeDescription;
    private ImageView imageView;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(IntroduceRecipeViewModel.class);
        binding = FragmentIntroduceRecipeBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        recyclerView = (RecyclerView) binding.RclVIngridients;
        recipeDescription = (TextView) binding.recipeDescription;
        imageView = (ImageView) binding.mainImage;

        InitializeRecipeComponents();
        InitializeIngredients();

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    private void InitializeIngredients() {
        mViewModel.getIngredients().observe(getViewLifecycleOwner(), ingredients -> {
            IngredientsListAdapter ingredientsListAdapter = new IngredientsListAdapter(root.getContext(), ingredients);
            recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
            recyclerView.setAdapter(ingredientsListAdapter);
        });
    }

    private void InitializeRecipeComponents() {
        mViewModel.getRecipe().observe(getViewLifecycleOwner(), recipe -> {
            recipeDescription.setText(recipe.description);

            Glide
                    .with(root.getContext())
                    .load(recipe.imageMainURL)
//                .placeholder(R.drawable.easy_cook)
//                .error(R.drawable.hard_cook)
                    .into(imageView);
        });
    }

}