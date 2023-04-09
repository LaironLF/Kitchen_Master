package com.laironlf.kitchen_master.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.laironlf.kitchen_master.R;
import com.laironlf.kitchen_master.databinding.FragmentFridgeBinding;

public class FridgeFragment extends Fragment implements View.OnClickListener{

    private FragmentFridgeBinding binding;
    private LinearLayout addProductBtn;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        FridgeViewModel fridgeViewModel =
                new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(FridgeViewModel.class);

        binding = FragmentFridgeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        addProductBtn = (LinearLayout) binding.addProductBtn;
        addProductBtn.setOnClickListener(this);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.addProductBtn:
                Navigation.findNavController(view).navigate(R.id.action_nav_gallery_to_blankFragment);
                break;
        }
    }
}