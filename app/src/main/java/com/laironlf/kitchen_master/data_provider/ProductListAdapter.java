package com.laironlf.kitchen_master.data_provider;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.laironlf.kitchen_master.DB.Product;
import com.laironlf.kitchen_master.R;

import java.util.List;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<Product> products;

    public ProductListAdapter(Context context, List<Product> products){
        this.products = products;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = inflater.inflate(R.layout.product_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.productName.setText(products.get(i).name);
    }

    @Override
    public int getItemCount() { return products.size(); }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView productName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = (TextView) itemView.findViewById(R.id.productName);
        }
    }
}
