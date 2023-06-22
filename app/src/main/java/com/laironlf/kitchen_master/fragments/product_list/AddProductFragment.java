package com.laironlf.kitchen_master.fragments.product_list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
        mViewModel = new ViewModelProvider(this, (ViewModelProvider.Factory) new ViewModelProvider.NewInstanceFactory()).get(AddProductViewModel.class);
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
//        Toast.makeText(getActivity().getApplicationContext(), "Продукт \""+ products.get(i).name + "\" добавлен", Toast.LENGTH_SHORT).show();
        deleteProduct(i);
    }


    private void deleteProduct(int i){
        mViewModel.deleteProduct(i);
        productListAdapter.notifyItemRemoved(i);
    }

    private void setupRecyclerView() {
        recyclerView = (RecyclerView) binding.productsRecycler;
    }

    private void updateRecyclerView() {
        mViewModel.getProducts().observe(getViewLifecycleOwner(), products -> {

//            this.products = products;
            productListAdapter = new ProductListAdapter(root.getContext(), products, this);
            recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
            recyclerView.setAdapter(productListAdapter);
        });
    }
    


}