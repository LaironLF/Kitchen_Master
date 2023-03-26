package com.laironlf.kitchen_master.data_provider;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.laironlf.kitchen_master.R;

import java.util.List;

public class ReceiptListAdapter extends RecyclerView.Adapter<ReceiptListAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private List<Receipt> receipts;

    public ReceiptListAdapter(Context context, List<Receipt> receipts){
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
        Receipt receipt = receipts.get(i);
        viewHolder.ReceiptTitle.setText(receipt.getName());
        viewHolder.ReceiptType.setText(receipt.getDish_type());
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
