package com.laironlf.kitchen_master.circle_menu.modules;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;

import com.laironlf.kitchen_master.circle_menu.AppCircleNavigation;
import com.laironlf.kitchen_master.custom_elements.RadioButtonCenter;

import java.util.ArrayList;

public class RadioButtonGroup implements NavController.OnDestinationChangedListener{
    // Вариэблс
    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private ArrayList<RadioButtonCenter> radioButtons;
    private RadioButtonCenter currentRadioButton;
    private AppNavigation appNavigation;
    private Boolean buttonClicked = false;

    // Создаём группу, и передаём количество радиобаттонов
    public RadioButtonGroup (ArrayList<RadioButtonCenter> radioButtons, AppNavigation appNavigation){
        this.radioButtons = radioButtons;
        this.appNavigation = appNavigation;
        this.setCurrentRadioButton(appNavigation.getCurrentDestinationID());

        setOnClickListeners();
        appNavigation.getNavController().addOnDestinationChangedListener(this);
    }

    // Добавляем кнопки в массив кнопок и прописываем логику онклик
    private void setOnClickListeners(){
        for(RadioButtonCenter radioButtonCenter: radioButtons)
            radioButtonCenter.setOnClickListener(view -> changeRadioButton(radioButtonCenter));
    }

    // Текущий радиобаттон
    private RadioButtonCenter getCurrentButton(){
        return currentRadioButton;
    }
    private int getRadioButtonIndex(RadioButtonCenter radioButtonCenter){
        for(int i = 0; i<radioButtons.size(); i++)
            if(radioButtons.get(i) == radioButtonCenter)
                return i;
        return -1;
    }
    private void setCurrentRadioButton (int idDestination){
        for(RadioButtonCenter btn : radioButtons)
            if(btn.getId() == idDestination)
                setCurrentRadioButton(btn);
    }
    private void setCurrentRadioButton(RadioButtonCenter radioButtonCenter){
        if(currentRadioButton != null)
            currentRadioButton.setChecked(false);
        radioButtonCenter.setChecked(true);
        currentRadioButton = radioButtonCenter;
    }

    // Меняем радибаттон с анимациями и навигацией
    private void changeRadioButton(RadioButtonCenter radioButtonCenter){
        setCurrentRadioButton(radioButtonCenter);
        buttonClicked = true;
        AppCircleNavigation.Animation.startBulbAnimator(getRadioButtonIndex(radioButtonCenter));
        if(radioButtonCenter.getId() != appNavigation.getCurrentDestinationID())
            appNavigation.navigate(radioButtonCenter.getId());
    }
    public void swipeRadioButton(boolean nextButton){
        int thisButtonId = getRadioButtonIndex(currentRadioButton);
        if(thisButtonId == -1) return;
        if(nextButton)
            if(++thisButtonId > radioButtons.size()-1) thisButtonId = 0;
        if(!nextButton)
            if(--thisButtonId < 0) thisButtonId = radioButtons.size() -1;
        changeRadioButton(radioButtons.get(thisButtonId));
    }

    public ArrayList<RadioButtonCenter> getRadioButtonViews(){
        return radioButtons;
    }

    @Override
    public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
        if(!buttonClicked) setCurrentRadioButton(destination.getId());
        buttonClicked = false;
    }
}
