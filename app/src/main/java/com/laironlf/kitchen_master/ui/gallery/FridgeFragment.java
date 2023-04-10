package com.laironlf.kitchen_master.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.laironlf.kitchen_master.DB.Product;
import com.laironlf.kitchen_master.R;
import com.laironlf.kitchen_master.data_provider.ProductListAdapter;
import com.laironlf.kitchen_master.data_provider.UserProductListAdapter;
import com.laironlf.kitchen_master.databinding.FragmentFridgeBinding;

import java.util.ArrayList;

public class FridgeFragment extends Fragment implements UserProductListAdapter.OnProductClickListener {

    private FragmentFridgeBinding binding;
    private LinearLayout addProductBtn;
    private RecyclerView recyclerView;
    private FridgeViewModel fridgeViewModel;
    private UserProductListAdapter productListAdapter;
    private View root;
    private ArrayList<Product> products;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fridgeViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(FridgeViewModel.class);

        binding = FragmentFridgeBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        addProductBtn = (LinearLayout) binding.addProductBtn;
        addProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_nav_gallery_to_blankFragment);
            }
        });
        
        setupRecyclerView();
        updateRecyclerView();

        return root;
    }

    private void updateRecyclerView() {
        fridgeViewModel.getUserProducts().observe(getViewLifecycleOwner(), products -> {
            this.products = products;
            productListAdapter = new UserProductListAdapter(root.getContext(), this.products, this);
            recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
            recyclerView.setAdapter(productListAdapter);
        });
    }

    private void deleteProduct(int i){
        fridgeViewModel.deleteProduct(i);
        productListAdapter.notifyItemRemoved(i);
    }

    private void setupRecyclerView() {
        recyclerView = (RecyclerView) binding.productsRecycler;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onProductClick(int i) {
        deleteProduct(i);
    }
}