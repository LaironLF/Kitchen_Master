package com.laironlf.kitchen_master.ui.productList;

import android.arch.lifecycle.ViewModelProvider;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.laironlf.kitchen_master.DB.Product;
import com.laironlf.kitchen_master.data_provider.ProductListAdapter;
import com.laironlf.kitchen_master.databinding.FragmentAddproductsBinding;

import java.util.ArrayList;

public class AddProductFragment extends Fragment implements ProductListAdapter.OnProductClickListener {

    private AddProductViewModel mViewModel;
    private RecyclerView recyclerView;
    private FragmentAddproductsBinding binding;
    private ProductListAdapter productListAdapter;
    private ArrayList<Product> products;
    private View root;

    public static AddProductFragment newInstance() {
        return new AddProductFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // setup VM and Binding
        mViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(AddProductViewModel.class);
        binding = FragmentAddproductsBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        // setup views
        setupRecyclerView();
        updateRecyclerView();
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onProductClick(int i) {
        Toast.makeText(getActivity().getApplicationContext(), "Продукт \""+ products.get(i).name + "\" добавлен", Toast.LENGTH_SHORT).show();
    }


    private void setupRecyclerView() {
        recyclerView = (RecyclerView) binding.productsRecycler;
    }

    private void updateRecyclerView() {
        mViewModel.getProducts().observe(getViewLifecycleOwner(), products -> {
            productListAdapter = new ProductListAdapter(root.getContext(), products, this);
            recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
            recyclerView.setAdapter(productListAdapter);
            this.products = products;
        });
    }
    


}