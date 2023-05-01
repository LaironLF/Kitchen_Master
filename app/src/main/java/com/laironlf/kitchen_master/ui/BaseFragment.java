package com.laironlf.kitchen_master.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.laironlf.kitchen_master.R;
import com.laironlf.kitchen_master.circle_menu.AppCircleNavigation;

public class BaseFragment extends Fragment implements View.OnTouchListener {

    public void setMenuRules(View rootView){
        rootView.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return true;
    }
}
