package com.laironlf.kitchen_master.circle_menu;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.laironlf.kitchen_master.R;

import java.util.ArrayList;

public class AppCircleNavigation {

    // Кароче юзаю единоличника, чтобы не создавалось 20 меню объектов
    private static AppCircleNavigation appCircleNavigation;
    // Вариэблс для объекта
    private static DrawerLayout drawerLayout;
    private static AppCompatActivity activity;
    private static Menu menu;
    // Остальное говно
    private static ConstraintLayout circleMenu;
    private float initialX;
    private final float SWIPE_THRESHOLD = 150;

    private AppCircleNavigation(DrawerLayout drawerLayout, AppCompatActivity activity, Menu menu){
        // Присваиваем нужды
        AppCircleNavigation.drawerLayout = drawerLayout;
        AppCircleNavigation.activity = activity;
        AppCircleNavigation.menu = menu;
        // Инициализируем подклассы
        RadioButtonGroup.InitGroup(menu.size());
        AppNavigation.initNavigation(activity, R.id.nav_host_fragment_content_main);
        // Начинаем создавать меню
        circleMenu = drawerLayout.findViewById(R.id.circle_menu_main);
        createMenuItems();
    }

    // Кароче чтобы не создавать объектов, можно просто статично создать меню и статично хранить
    public static void createCircleMenu(DrawerLayout drawerLayout, AppCompatActivity activity, Menu menu){
        appCircleNavigation = new AppCircleNavigation(drawerLayout, activity, menu);
    }

    // Если надо будет, я могу отдать это меню
    public static AppCircleNavigation getCircleMenu(){
        return appCircleNavigation;
    }

    @SuppressLint("ResourceType")
    private static void createMenuItems() {
        // Достаём нужные величины из ресурсов
        int diameter = (int)activity.getResources().getDimension(R.dimen.circle_nav_item_diameter);
        int radius = (int)activity.getResources().getDimension(R.dimen.circle_nav_center_radius);
        int textSize = (int) activity.getResources().getDimension(R.dimen.circle_nav_item_text_size);
        // Это для первой кнопки
        int frstBtnDiameter = (int) activity.getResources().getDimension(R.dimen.circle_nav_item_diameter_first);
        // Это для последней
        int width = (int) activity.getResources().getDimension(R.dimen.circle_nav_lastBtn_width);
        int height = (int) activity.getResources().getDimension(R.dimen.circle_nav_lastBtn_height);

        // Заполняем круг нашими радиобаттонами
        for(int i = 0; i < menu.size(); i++){
            ConstraintLayout.LayoutParams params;
            RadioButtonCenter radioButtonCenter = new RadioButtonCenter(drawerLayout.getContext());

            if(i == 0){
                params = new ConstraintLayout.LayoutParams(frstBtnDiameter, frstBtnDiameter);
                params.circleAngle = 354;
            }
            else if(i == menu.size() -1){
                params = new ConstraintLayout.LayoutParams(width, height);
                params.circleAngle = 186;
                radioButtonCenter.setBackground(activity.getResources().getDrawable(R.drawable.menu_square_shape, activity.getTheme()));
            }
            else{
                params = new ConstraintLayout.LayoutParams(diameter, diameter);
                params.circleAngle = 36 * i;
            }

            if (i != 0) radioButtonCenter.setText(menu.getItem(i).getTitle());
            if (i != menu.size()-1) radioButtonCenter.setBackground(activity.getResources().getDrawable(R.drawable.circle_nav_back_selector, activity.getTheme()));

            radioButtonCenter.setId(menu.getItem(i).getItemId());
            radioButtonCenter.setButtonDrawable(android.R.color.transparent);
            radioButtonCenter.setDrawableCenter(menu.getItem(i).getIcon());
            radioButtonCenter.setTextSize(textSize);
            radioButtonCenter.setTextColor(activity.getResources().getColorStateList(R.color.circle_nav_text_selector, activity.getTheme()));

            params.circleRadius = radius;
            params.circleConstraint = R.id.center;

            radioButtonCenter.setLayoutParams(params);
            circleMenu.addView(radioButtonCenter);
            RadioButtonGroup.addRadioButton(radioButtonCenter);
        }
    }

    /*
    | ---- ПОДКЛАССЫ ООУЕААА ---- |
     */

    public static class RadioButtonGroup {
        // Вариэблс
        @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
        private static ArrayList<RadioButtonCenter> radioButtons;
        private static RadioButtonCenter currentRadioButton;

        // Создаём группу, и передаём количество радиобаттонов
        public static void InitGroup(int count){
            radioButtons = new ArrayList<>(count);
        }

        // Добавляем кнопки в массив кнопок
        public static void addRadioButton(RadioButtonCenter radioButtonCenter){
            radioButtons.add(radioButtonCenter);
            //и сразу прописываем логику ЧЭКЭД
            radioButtonCenter.setOnClickListener(view -> {
                if(currentRadioButton != null)
                    currentRadioButton.setChecked(false);

                radioButtonCenter.setChecked(true);

                if(currentRadioButton != radioButtonCenter){
                    currentRadioButton = radioButtonCenter;
                    AppNavigation.navigate(currentRadioButton.getId());
                }
            });
        }

        // получаем текущий РадиоБаттон
        public RadioButtonCenter getCurrentButton(){
            return currentRadioButton;
        }

    }

    // Сделаю отдельный подкласс для навигации
    public static class AppNavigation{
        //Вариэблес
        @SuppressLint("StaticFieldLeak")
        private static NavController navController;

        public static void initNavigation(Activity activity, int id){
            navController = Navigation.findNavController(activity, id);
        }

        public static void navigate(int idDestination){
            navController.popBackStack();
            navController.navigate(idDestination);
        }

    }

    // Класс с жестами и обработкой кнопка
    @SuppressLint("ClickableViewAccessibility")
    public static class DrawerLayoutGestures implements View.OnTouchListener {
        // variables
        private static DrawerLayoutGestures drawerLayoutGestures;

        // Конструктор
        private DrawerLayoutGestures(DrawerLayout drawerLayout){
            drawerLayout.setOnTouchListener(this);
        }
        public static void InitGestures(DrawerLayout drawerLayout){
            drawerLayoutGestures = new DrawerLayoutGestures(drawerLayout);
        }

        // Можем отдать объект
        public static DrawerLayoutGestures getGestures(){
            return drawerLayoutGestures;
        }

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            switch(motionEvent.getAction()){
                case MotionEvent.ACTION_DOWN:
                    Log.d("circleApp", String.valueOf(motionEvent.getX()));
            }
            return false;
        }
    }

    // Также отдельный класс для анимаций
    public static class Animation {

    }
}
