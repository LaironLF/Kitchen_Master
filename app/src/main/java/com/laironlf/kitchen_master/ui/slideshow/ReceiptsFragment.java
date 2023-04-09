package com.laironlf.kitchen_master.ui.slideshow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.laironlf.kitchen_master.R;
import com.laironlf.kitchen_master.data_provider.Receipt;
import com.laironlf.kitchen_master.data_provider.ReceiptListAdapter;
import com.laironlf.kitchen_master.databinding.FragmentRecipesBinding;

import java.util.ArrayList;

public class ReceiptsFragment extends Fragment {

    private FragmentRecipesBinding binding;

    private RecyclerView recyclerView;
    private ArrayList<Receipt> receipts = new ArrayList<Receipt>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ReceiptsViewModel receiptsViewModel =
                new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(ReceiptsViewModel.class);

        binding = FragmentRecipesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        recyclerView = (RecyclerView) root.findViewById(R.id.RclV_listReceipts);
//        ReceiptListAdapter receiptListAdapter = new ReceiptListAdapter(root.getContext(), receip);
//
//        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
//        recyclerView.setAdapter(receiptListAdapter);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}