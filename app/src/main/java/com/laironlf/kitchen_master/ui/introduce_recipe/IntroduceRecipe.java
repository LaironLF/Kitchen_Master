package com.laironlf.kitchen_master.ui.introduce_recipe;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.laironlf.kitchen_master.R;
import com.laironlf.kitchen_master.ui.home.HomeViewModel;

public class IntroduceRecipe extends Fragment {

    private IntroduceRecipeViewModel mViewModel;

    public static IntroduceRecipe newInstance() {
        return new IntroduceRecipe();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_introduce_recipe, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(IntroduceRecipeViewModel.class);
        // TODO: Use the ViewModel
    }

}