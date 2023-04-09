package com.laironlf.kitchen_master.data_provider;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.laironlf.kitchen_master.DB.Recipe;
import com.laironlf.kitchen_master.R;

import java.util.List;

public class ReceiptListAdapter extends RecyclerView.Adapter<ReceiptListAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private List<Recipe> receipts;

    public ReceiptListAdapter(Context context, List<Recipe> receipts){
        this.receipts = receipts;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.list_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Recipe receipt = receipts.get(i);
        viewHolder.ReceiptType.setText(receipt.recipeName);
        viewHolder.ReceiptType.setText(receipt.typeName);
    }

    @Override
    public int getItemCount() {
        return receipts.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView ReceiptTitle, ReceiptType;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ReceiptTitle = (TextView) itemView.findViewById(R.id.tv_receiptTitle);
            ReceiptType = (TextView) itemView.findViewById(R.id.tv_receiptType);
        }
    }
}
