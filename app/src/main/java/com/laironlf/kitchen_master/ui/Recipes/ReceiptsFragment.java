package com.laironlf.kitchen_master.ui.Recipes;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;
import com.laironlf.kitchen_master.DB.Recipe;
import com.laironlf.kitchen_master.R;
import com.laironlf.kitchen_master.circle_menu.AppCircleNavigation;
import com.laironlf.kitchen_master.data_provider.ReceiptListAdapter;
import com.laironlf.kitchen_master.data_provider.RecipeDataMediator;
import com.laironlf.kitchen_master.databinding.FragmentRecipesBinding;

import java.util.ArrayList;

public class ReceiptsFragment extends Fragment implements ReceiptListAdapter.OnRecipeClickListener{

    private FragmentRecipesBinding binding;
    private RecyclerView recyclerView;
    private ReceiptsViewModel receiptsViewModel;
    private View root;
    private ReceiptListAdapter receiptListAdapter;

    /**/
    @SuppressLint("ClickableViewAccessibility")
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        receiptsViewModel = new ViewModelProvider(this, (ViewModelProvider.Factory) new ViewModelProvider.NewInstanceFactory()).get(ReceiptsViewModel.class);
        binding = FragmentRecipesBinding.inflate(inflater, container, false);
        root = binding.getRoot();
        recyclerView = binding.RclVListReceipts;
        updateRecyclerView();
        setupTabLayout();
        updateData(10);


        return root;
    }

    public void updateRecyclerView(){
        receiptsViewModel.getRecipes().observe(getViewLifecycleOwner(), recipes -> {
            receiptListAdapter = new ReceiptListAdapter(root.getContext(), recipes, this);
            recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
            recyclerView.setAdapter(receiptListAdapter);
        });
    }

    private void updateData(int param){
        receiptsViewModel.updateRecipes(param);
        updateRecyclerView();
    }

    private void setupTabLayout(){
        TabLayout tabLayout = binding.TabLayout;
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:
                        updateData(10);
                        break;
                    case 1:
                        updateData(0);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onRecipeClick(int i, View view, int recipeID) {
//        Toast.makeText(getActivity().getApplicationContext(), "КЛИК", Toast.LENGTH_SHORT).show();
        RecipeDataMediator.setRecipe(recipeID);
        Navigation.findNavController(view).navigate(R.id.action_nav_slideshow_to_introduceRecipe);
    }

}