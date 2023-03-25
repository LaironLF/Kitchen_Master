package com.laironlf.kitchen_master.ui.slideshow;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.arch.lifecycle.ViewModelProvider;

import com.laironlf.kitchen_master.R;
import com.laironlf.kitchen_master.data_provider.Receipt;
import com.laironlf.kitchen_master.data_provider.ReceiptListAdapter;
import com.laironlf.kitchen_master.databinding.FragmentSlideshowBinding;

import java.util.ArrayList;

public class SlideshowFragment extends Fragment {

    private FragmentSlideshowBinding binding;

    private RecyclerView recyclerView;
    private ArrayList<Receipt> receipts = new ArrayList<Receipt>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SlideshowViewModel slideshowViewModel =
                new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(SlideshowViewModel.class);

        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        addReceipts();
        recyclerView = (RecyclerView) root.findViewById(R.id.RclV_listReceipts);
        ReceiptListAdapter receiptListAdapter = new ReceiptListAdapter(root.getContext(), receipts);

        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        recyclerView.setAdapter(receiptListAdapter);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void addReceipts(){
        receipts.add(new Receipt("Борщ", "супы"));
        receipts.add(new Receipt("Мохнатка", "супы"));
        receipts.add(new Receipt("Борщ", "супы"));
        receipts.add(new Receipt("Бурыши", "кхехе"));
        receipts.add(new Receipt("Борщ", "супы"));
    }
}