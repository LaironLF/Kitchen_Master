package com.laironlf.kitchen_master.ui.Recipes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.laironlf.kitchen_master.DB.Recipe;
import com.laironlf.kitchen_master.R;
import com.laironlf.kitchen_master.data_provider.ReceiptListAdapter;
import com.laironlf.kitchen_master.data_provider.RecipeDataMediator;
import com.laironlf.kitchen_master.databinding.FragmentRecipesBinding;

import java.util.ArrayList;

public class ReceiptsFragment extends Fragment  implements ReceiptListAdapter.OnRecipeClickListener{

    private FragmentRecipesBinding binding;
    private RecyclerView recyclerView;
    private ReceiptsViewModel receiptsViewModel;
    private View root;
    private ArrayList<Recipe> recipes;
    private int count;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        receiptsViewModel = new ViewModelProvider(this, (ViewModelProvider.Factory) new ViewModelProvider.NewInstanceFactory()).get(ReceiptsViewModel.class);
        binding = FragmentRecipesBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        recyclerView = (RecyclerView) binding.RclVListReceipts;
        updateRecyclerView();
        setupSeekBar();

        return root;
    }

    private void setupSeekBar() {
        SeekBar seekBar = root.findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                count = progress;
                // Здесь вы можете обновить данные на основе нового значения count
//                Toast.makeText(getActivity().getApplicationContext(), String.valueOf(progress), Toast.LENGTH_SHORT).show();
                receiptsViewModel.updateRecipes(count);
                updateRecyclerView();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
//                Toast.makeText(getActivity().getApplicationContext(), "Клик", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }
    public void updateRecyclerView(){
        receiptsViewModel.getRecipes().observe(getViewLifecycleOwner(), recipes -> {
            this.recipes = recipes;
            ReceiptListAdapter receiptListAdapter = new ReceiptListAdapter(root.getContext(), recipes, this);
            recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
            recyclerView.setAdapter(receiptListAdapter);
        });
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onRecipeClick(int i, View view, int recipeID) {
//        Toast.makeText(getActivity().getApplicationContext(), "КЛИК", Toast.LENGTH_SHORT).show();
        RecipeDataMediator.setRecipe(recipeID);
        Navigation.findNavController(view).navigate(R.id.action_nav_slideshow_to_introduceRecipe);
    }
}