package com.laironlf.kitchen_master.circle_menu;

import static android.content.ContentValues.TAG;

import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.laironlf.kitchen_master.R;
import com.laironlf.kitchen_master.circle_menu.modules.AppNavigation;
import com.laironlf.kitchen_master.circle_menu.modules.KMToolbar;
import com.laironlf.kitchen_master.circle_menu.modules.RadioButtonGroup;
import com.laironlf.kitchen_master.custom_elements.RadioButtonCenter;

import java.util.ArrayList;

public class AppCircleNavigation {

    private static AppCircleNavigation appCircleNavigation;
    private static DrawerLayout drawerLayout;
    private static AppCompatActivity activity;
    private static Menu menu;
    private static AppNavigation appNavigation;
    private static RadioButtonGroup radioButtonGroup;

    // ---------------------------------------------------------

    // Кароче чтобы не создавать объектов, можно просто статично создать меню и статично хранить
    public static void createCircleMenu(DrawerLayout drawerLayout, AppCompatActivity activity, Menu menu, View toolbar){
        appCircleNavigation = new AppCircleNavigation(drawerLayout, activity, menu, toolbar);
    }
    public static AppCircleNavigation getAppCircleNavigation(){
        return appCircleNavigation;
    }
    private AppCircleNavigation(DrawerLayout drawerLayout, AppCompatActivity activity, Menu menu, View toolbar){
        // Присваиваем нужды
        AppCircleNavigation.drawerLayout = drawerLayout;
        AppCircleNavigation.activity = activity;
        AppCircleNavigation.menu = menu;
        // Инициализируем подклассы
        appNavigation = new AppNavigation(activity, menu, R.id.nav_host_fragment_content_main);
        createMenuItems();

        DrawerLayoutGestures.initGestures(drawerLayout);
        DrawerLayoutMotion.initMotion(drawerLayout);
        new KMToolbar(toolbar, activity, appNavigation);

        Animation.initAnimations(radioButtonGroup.getRadioButtonViews());
//        Animation.setStartPosition();
    }


    private static void createMenuItems() {
        ConstraintLayout circleMenu = drawerLayout.findViewById(R.id.circle_menu_main);
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
        ArrayList<RadioButtonCenter> radioButtonCenters = new ArrayList<>(menu.size());
        for(int i = 0; i < menu.size(); i++){
            ConstraintLayout.LayoutParams params;
            RadioButtonCenter radioButtonCenter = new RadioButtonCenter(drawerLayout.getContext());

            if(i == 0){
                params = new ConstraintLayout.LayoutParams(frstBtnDiameter, frstBtnDiameter);
                params.circleAngle = -6;
            }
            else if(i == menu.size() -1){
                params = new ConstraintLayout.LayoutParams(width, height);
                params.circleAngle = 186;
                radioButtonCenter.setBackground(activity.getResources().getDrawable(R.drawable.circle_nav_square_selector, activity.getTheme()));
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

            radioButtonCenters.add(radioButtonCenter);
        }

        radioButtonGroup = new RadioButtonGroup(radioButtonCenters, appNavigation);
    }

    // --------------------------------------------------------

    public static Menu getMenu(){
        return menu;
    }
    public static void openDrawer(){
        drawerLayout.openDrawer(GravityCompat.START);
    }
    public static void closeDrawer() {drawerLayout.closeDrawer(GravityCompat.START);}
    public static boolean isDrawerOpen(){
        return drawerLayout.isDrawerOpen(GravityCompat.START);
    }
    public static void lockDrawer() {
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN, GravityCompat.START);
    }
    public static void unlockDrawer() { drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED, GravityCompat.START);}

    // ----------------------| Подклассы |----------------------------------

    /**
     * Класс с жестами меню, такие как открыть, закрыть, переключиться вверх, вниз
     */
    @SuppressLint("ClickableViewAccessibility")
    public static class DrawerLayoutGestures implements View.OnTouchListener {
        // variables
        private static DrawerLayoutGestures drawerLayoutGestures;

        private int windowWidth;
        private float circleCenterX;
        private float circleCenterY;
        private float circleRadius;
        private float initialX;
        private float initialY;
        private float deltaX;
        private float deltaY;
        private boolean mDragging;

        private static final float windowPercent = 0.12F;
        private static final float xValueToReact = 20;
        private static final float yValueToReact = 20;
        private static final float axisYFactor = 0.3F;
        private static final float axisXFactor = 0.5F;

        // Конструктор
        private DrawerLayoutGestures(DrawerLayout drawerLayout){
            drawerLayout.setOnTouchListener(this);
            drawerLayout.findViewById(R.id.nav_view).setOnTouchListener(this);
            DisplayMetrics displayMetrics = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            windowWidth = displayMetrics.widthPixels;

            float circleDiameter = activity.getResources().getDimension(R.dimen.circle_menu_diameter);
            float circleMarginTop = activity.getResources().getDimension(R.dimen.stndTopMargin);
            float circleMarginLeft = activity.getResources().getDimension(R.dimen.circle_menu_leftMargin);

            circleRadius = circleDiameter/2;
            circleCenterX = (circleRadius + circleMarginLeft);
            circleCenterY = (circleRadius + circleMarginTop);

        }
        public static void initGestures(DrawerLayout drawerLayout){
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
                    initialX = motionEvent.getRawX();
                    initialY = motionEvent.getRawY();
                    deltaX = 0;
                    deltaY = 0;

                    if (initialX < windowWidth * windowPercent)
                        mDragging = true;
                    if(isDrawerOpen()){
                        mDragging = true;
                        return true;
                    }

                    break;

                case MotionEvent.ACTION_MOVE:
                    deltaX = motionEvent.getRawX() - initialX;
                    deltaY = motionEvent.getRawY() - initialY;

                    // Обработка движений влево-вправо
                    if(mDragging && Math.abs(deltaX * axisYFactor) > Math.abs(deltaY)){
                        // вправо
                        if(!isDrawerOpen() && deltaX > xValueToReact){
                            openDrawer();
                            mDragging = false;
                            return true;
                        }
                        // влево
                        if(isDrawerOpen() && -deltaX > xValueToReact){
                            unlockDrawer();
                            mDragging = false;
                        }
                    }
                    // Обработка движений вверх вниз, при открытой менюшке
                    if(mDragging && isDrawerOpen() && Math.abs(deltaX) < Math.abs(deltaY * axisXFactor)){
                        // вверх
                        if(deltaY > yValueToReact){
                            radioButtonGroup.swipeRadioButton(true);
                            mDragging = false;
                        }
                        // вниз
                        if(-deltaY > yValueToReact){
                            radioButtonGroup.swipeRadioButton(false);
                            mDragging = false;
                        }
                    }
                    Log.d(TAG, "onTouch: dx, dy" +deltaX +" " +deltaY + " " +mDragging);
                    break;

                case MotionEvent.ACTION_UP:
                    if(deltaY < yValueToReact && deltaX < xValueToReact && mDragging) {
                        float distanceFromCenter = (float) Math.sqrt(Math.pow(initialX + deltaX - circleCenterX, 2) + Math.pow(initialY + deltaY - circleCenterY, 2));
                        if(distanceFromCenter > circleRadius)
                            closeDrawer();
                    }
                    mDragging = false;
                    break;
                case MotionEvent.ACTION_CANCEL:

                    break;
            }
            return false;
        }
        public boolean onTouch(MotionEvent event){
            return onTouch(drawerLayout, event);
        }
    }

    /**
     * Класс с движением меню, отлавливаем движения меню и можем их обрабатывать
     */
    public static class DrawerLayoutMotion implements DrawerLayout.DrawerListener{

        private static DrawerLayoutMotion drawerLayoutMotion;
        public static int state = 0;
        private boolean beenStarted;
        private float[] iconPos;
        private ArrayList<ConstraintLayout.LayoutParams> params;

        // Конструктор
        private DrawerLayoutMotion(DrawerLayout drawerLayout){
            drawerLayout.addDrawerListener(this);
            iconPos = new float[menu.size()];
            params = new ArrayList<>(menu.size());
            for(RadioButtonCenter radioButton: radioButtonGroup.getRadioButtonViews())
                params.add((ConstraintLayout.LayoutParams)radioButton.getLayoutParams());
            for(int i =0; i<iconPos.length; i++)
                iconPos[i] = params.get(i).circleAngle;
        }
        public static void initMotion(DrawerLayout drawerLayout){
            drawerLayoutMotion = new DrawerLayoutMotion(drawerLayout);
        }
        public static DrawerLayoutMotion getDrawerLayoutMotion() {
            return drawerLayoutMotion;

        }

        // методы
        public static int getState() {
            return state;
        }

        @Override
        public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
//            if(slideOffset > 0.5 && !beenStarted){
//                Animation.startEnterAnimators();
//                beenStarted = true;
//            }

            for(int i = 0; i < params.size(); i++){


                params.get(i).circleAngle = iconPos[i] - ((270 - iconPos[i])*(1 - slideOffset));
                radioButtonGroup.getRadioButtonViews().get(i).setLayoutParams(params.get(i));
            }

            if(slideOffset == 1.0)
                lockDrawer();
        }
        @Override
        public void onDrawerOpened(@NonNull View drawerView) {

        }
        @Override
        public void onDrawerClosed(@NonNull View drawerView) {
//            Animation.setStartPosition();
            beenStarted = false;
        }
        @Override
        public void onDrawerStateChanged(int newState) {
            state = newState;
        }
    }

    /**
     * Класс, хранящий в себе анимации
     */
    public static class Animation {

        private static ArrayList<ConstraintLayout.LayoutParams> reservedIconParams;
        private static ArrayList<RadioButtonCenter> buttonCenters;
        private static ArrayList<ValueAnimator> enterIconAnimators;
        private static ArrayList<ValueAnimator> bulbAnimators;

        private static ConstraintLayout.LayoutParams reservedBackCircleParams;
        private static ConstraintLayout navBack;
        private static ValueAnimator enterBackCircleAnimator;


        // Общая иницализация анимаций
        public static void initAnimations(ArrayList<RadioButtonCenter> buttonCenters){
            Animation.buttonCenters = buttonCenters;

            Log.d(TAG, "initIconAnimations: " + buttonCenters.size());

            // инициализация массивов
            reservedIconParams = new ArrayList<>(buttonCenters.size());
            enterIconAnimators = new ArrayList<>(buttonCenters.size());
            bulbAnimators = new ArrayList<>(buttonCenters.size());

            for (RadioButtonCenter btn: buttonCenters)
                reservedIconParams.add((ConstraintLayout.LayoutParams) btn.getLayoutParams());
            // инициализация параметров для заднего круга
            navBack = drawerLayout.findViewById(R.id.circle_menu_back);
            reservedBackCircleParams = (ConstraintLayout.LayoutParams) navBack.getLayoutParams();

//            initEnterAnimators();
            initBulbAnimators();
        }

        // инициализация анимации открывания меню
//        private static void initEnterAnimators(){
//            OvershootInterpolator interpolator = new OvershootInterpolator(0.65f);
//            for (int i = 0; i < buttonCenters.size(); i++){
//                ValueAnimator valueAnimator = createNewFloatAnimator(
//                        350,
//                        30L * (menu.size() - i -1),
//                        interpolator,
//                        -90f, reservedIconParams.get(i).circleAngle);
//                int finalI = i;
//                valueAnimator.addUpdateListener(animator -> {
//                    float value = (float) animator.getAnimatedValue();
//                    ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) buttonCenters.get(finalI).getLayoutParams();
//                    params.circleAngle = value;
//                    buttonCenters.get(finalI).setLayoutParams(params);
//                });
//                enterIconAnimators.add(valueAnimator);
//            }
//
//            // Задний круг
//            enterBackCircleAnimator = createNewIntAnimator(
//                    350,
//                    50,
//                    new DecelerateInterpolator(),
//                    0, reservedBackCircleParams.topMargin
//            );
//            enterBackCircleAnimator.addUpdateListener(animator -> {
//                int value = (int) animator.getAnimatedValue();
//                ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) navBack.getLayoutParams();
//                params.topMargin = value;
//                navBack.setLayoutParams(params);
//            });
//
//        }
//        public static void startEnterAnimators(){
//            for (ValueAnimator enterAnimator: enterIconAnimators)
//                enterAnimator.start();
//            enterBackCircleAnimator.start();
//        }

        // инициализация анимации прыжка кнопки
        private static void initBulbAnimators(){
            for (int i = 0; i < buttonCenters.size(); i++){
                ValueAnimator valueAnimator = createNewFloatAnimator(
                        200,
                        0,
                        new BounceInterpolator(),
                        1, 0.9F, 1.1F, 1
                );
                int finalI = i;
                valueAnimator.addUpdateListener(animator -> {
                    float value = (float) animator.getAnimatedValue();
                    buttonCenters.get(finalI).setScaleX(value);
                    buttonCenters.get(finalI).setScaleY(value);
                });
                bulbAnimators.add(valueAnimator);
            }
        }
        public static void startBulbAnimator(int index){
            //если был запущен, то отменяем
            bulbAnimators.get(index).cancel();
            bulbAnimators.get(index).start();
        }

        public static void setStartPosition(){
            //Отменяем анимации, если они были
            enterBackCircleAnimator.cancel();
            for(ValueAnimator animator: enterIconAnimators)
                animator.cancel();

            //выставляем стартовые позиции
            for (RadioButtonCenter buttonCenter: buttonCenters){
                ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) buttonCenter.getLayoutParams();
                params.circleAngle = -90f;
                buttonCenter.setLayoutParams(params);
            }
            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) navBack.getLayoutParams();
            params.topMargin = 0;
            navBack.setLayoutParams(params);
        }

        // Выносим создание аниматоров для удобства
        public static ValueAnimator createNewFloatAnimator(long duration, long startDelay, TimeInterpolator interpolator, float... values){
            ValueAnimator valueAnimator = ValueAnimator.ofFloat(values);
            return setBaseParameters(valueAnimator, duration, startDelay, interpolator);
        }
        public static ValueAnimator createNewIntAnimator(long duration, long startDelay, TimeInterpolator interpolator, int... values){
            ValueAnimator valueAnimator = ValueAnimator.ofInt(values);
            return setBaseParameters(valueAnimator, duration, startDelay, interpolator);
        }
        private static ValueAnimator setBaseParameters(ValueAnimator valueAnimator, long duration, long startDelay, TimeInterpolator interpolator){
            valueAnimator.setDuration(duration);
            valueAnimator.setStartDelay(startDelay);
            valueAnimator.setInterpolator(interpolator);
            return valueAnimator;
        }

    }



}
