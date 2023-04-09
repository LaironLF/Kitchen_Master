package com.laironlf.kitchen_master.ui.productList;

import android.arch.lifecycle.ViewModelProvider;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.laironlf.kitchen_master.R;
import com.laironlf.kitchen_master.data_provider.ProductListAdapter;
import com.laironlf.kitchen_master.databinding.FragmentBlankBinding;

public class BlankFragment extends Fragment {

    private BlankViewModel mViewModel;
    private RecyclerView recyclerView;
    private FragmentBlankBinding binding;
    private ProductListAdapter productListAdapter;
    private View root;

    public static BlankFragment newInstance() {
        return new BlankFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // setup VM and Binding
        mViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(BlankViewModel.class);
        binding = FragmentBlankBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        // setup views
        setupRecyclerView();
        updateRecyclerView();
        return root;
    }



    private void setupRecyclerView() {
        recyclerView = (RecyclerView) binding.productsRecycler;
    }

    private void updateRecyclerView() {
        mViewModel.getProducts().observe(getViewLifecycleOwner(), products -> {
            productListAdapter = new ProductListAdapter(root.getContext(), products);
            recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
            recyclerView.setAdapter(productListAdapter);
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

}