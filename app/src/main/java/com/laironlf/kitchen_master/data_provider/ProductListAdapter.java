package com.laironlf.kitchen_master.data_provider;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.laironlf.kitchen_master.DB.Product;
import com.laironlf.kitchen_master.R;

import java.util.ArrayList;
import java.util.List;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private ArrayList<Product> products;
    private OnProductClickListener mOnProductClickListener;

    public void setProductList(ArrayList<Product> products){
        this.products = products;
    }

    public ProductListAdapter(Context context, ArrayList<Product> products, OnProductClickListener mOnProductClickListener){
        this.inflater = LayoutInflater.from(context);
        this.products = products;
        this.mOnProductClickListener =mOnProductClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = inflater.inflate(R.layout.product_item, viewGroup, false);
        return new ViewHolder(view, mOnProductClickListener);
    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.productName.setText(products.get(i).name);
    }

    @Override
    public int getItemCount() { return products.size(); }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView productName;
        OnProductClickListener onProductClickListener;

        public ViewHolder(@NonNull View itemView, OnProductClickListener onProductClickListener) {
            super(itemView);
            productName = (TextView) itemView.findViewById(R.id.productName);
            this.onProductClickListener = onProductClickListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onProductClickListener.onProductClick(getAdapterPosition());
        }
    }

    public interface OnProductClickListener{
        void onProductClick(int i);
    }
}
