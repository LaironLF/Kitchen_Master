package com.laironlf.kitchen_master.data_provider;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.laironlf.kitchen_master.DB.Product;
import com.laironlf.kitchen_master.R;

import java.util.ArrayList;

public class UserProductListAdapter extends ProductListAdapter{

    OnProductClickListener onProductClickListener;

    public UserProductListAdapter(Context context, ArrayList<Product> products, OnProductClickListener mOnProductClickListener) {
        super(context, products, mOnProductClickListener);
        this.onProductClickListener = mOnProductClickListener;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.product_item_delete, viewGroup, false);
        return new ViewHolder(view, this.onProductClickListener);
    }
}
