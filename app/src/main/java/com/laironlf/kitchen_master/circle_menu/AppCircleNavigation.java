package com.laironlf.kitchen_master.circle_menu;

import static android.content.ContentValues.TAG;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
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

    private AppCircleNavigation(DrawerLayout drawerLayout, AppCompatActivity activity, Menu menu){
        // Присваиваем нужды
        AppCircleNavigation.drawerLayout = drawerLayout;
        AppCircleNavigation.activity = activity;
        AppCircleNavigation.menu = menu;
        // Инициализируем подклассы
        AppNavigation.initNavigation(activity, R.id.nav_host_fragment_content_main);
        RadioButtonGroup.initGroup(menu.size());
        // Начинаем создавать меню
        DrawerLayoutGestures.initGestures(drawerLayout);
        DrawerLayoutMotion.initMotion(drawerLayout);

        circleMenu = drawerLayout.findViewById(R.id.circle_menu_main);
        createMenuItems();
        Animation.initAnimations(RadioButtonGroup.getRadioButtonViews());
        Animation.setStartPosition();
    }

    // Кароче чтобы не создавать объектов, можно просто статично создать меню и статично хранить
    public static void createCircleMenu(DrawerLayout drawerLayout, AppCompatActivity activity, Menu menu){
        appCircleNavigation = new AppCircleNavigation(drawerLayout, activity, menu);
    }

    // Если надо будет, я могу отдать это меню
    public static AppCircleNavigation getCircleMenu(){
        return appCircleNavigation;
    }
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
            RadioButtonGroup.addRadioButton(radioButtonCenter);
        }
        RadioButtonGroup.setCurrentRadioButton(AppNavigation.getCurrentDestination());
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

    /**
     * <p>Мой личный статичный класс радиогруппы, потому что view элемент мне не подходит</p>
     */
    public static class RadioButtonGroup {
        // Вариэблс
        @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
        private static ArrayList<RadioButtonCenter> radioButtons;
        private static RadioButtonCenter currentRadioButton;

        // Создаём группу, и передаём количество радиобаттонов
        public static void initGroup(int count){
            radioButtons = new ArrayList<>(count);
        }
        public static void setCurrentRadioButton (int idDestination){
            for(RadioButtonCenter btn : radioButtons)
                if(btn.getId() == idDestination)
                    currentRadioButton = btn;
            currentRadioButton.setChecked(true);
        }

        // Добавляем кнопки в массив кнопок
        public static void addRadioButton(RadioButtonCenter radioButtonCenter){
            radioButtons.add(radioButtonCenter);
            //и сразу прописываем логику ЧЭКЭД
            radioButtonCenter.setOnClickListener(view -> {
                if(currentRadioButton != null)
                    currentRadioButton.setChecked(false);

                radioButtonCenter.setChecked(true);

                if(radioButtonCenter != currentRadioButton){
                    currentRadioButton = radioButtonCenter;
                    AppNavigation.navigate(currentRadioButton.getId());
                }
            });
        }

        // получаем текущий РадиоБаттон
        public static RadioButtonCenter getCurrentButton(){
            return currentRadioButton;
        }
        public static ArrayList<RadioButtonCenter> getRadioButtonViews(){
            return radioButtons;
        }

    }

    /**
     * <p>Класс навигации меню, собственно больше нечего сказать</p>
     */
    public static class AppNavigation{
        //Вариэблес
        @SuppressLint("StaticFieldLeak")
        private static NavController navController;

        public static void initNavigation(Activity activity, int id){
            navController = Navigation.findNavController(activity, id);
        }

        public static int getCurrentDestination(){
            return navController.getCurrentDestination().getId();
        }

        public static void navigate(int idDestination){
            navController.popBackStack();
            navController.navigate(idDestination);
        }

    }

    /**
     * Класс с жестами меню, такие как открыть, закрыть, переключиться вверх, вниз
     */
    @SuppressLint("ClickableViewAccessibility")
    public static class DrawerLayoutGestures implements View.OnTouchListener {
        // variables
        private static DrawerLayoutGestures drawerLayoutGestures;
        private float initialX;
        private float initialY;
        private static final float xValueToReact = 20;
        private boolean mDragging;
        private static final float axisYFactor = 0.3F;
        private static final float axisXFactor = 0.5F;

        // Конструктор
        private DrawerLayoutGestures(DrawerLayout drawerLayout){
            drawerLayout.setOnTouchListener(this);
            drawerLayout.findViewById(R.id.nav_view).setOnTouchListener(this);
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
//                    if(isDrawerOpen()){
//                        drawerLayout
//                    }
//                    drawerLayout.findViewById(R.id.nav_view).setTranslationY(initialY);
                    mDragging = true;
                    return true;

                case MotionEvent.ACTION_MOVE:
                    float deltaX = motionEvent.getRawX() - initialX;
                    float deltaY = motionEvent.getRawY() - initialY;

                    // Обработка движений влево-вправо
                    if(mDragging && Math.abs(deltaX * axisYFactor) > Math.abs(deltaY)){
                        // вправо
                        if(!isDrawerOpen() && deltaX > xValueToReact){
                            openDrawer();
                            mDragging = false;
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
                        if(deltaY > xValueToReact){
                            mDragging = false;
                        }
                        // вниз
                        if(-deltaY > xValueToReact){
                            mDragging = false;
                        }
                    }
//                    Log.d(TAG, "onTouch: dx, dy" +deltaX +" " +deltaY + " " +mDragging);
                    break;

                case MotionEvent.ACTION_UP:
                    mDragging = true;
                    break;
                case MotionEvent.ACTION_CANCEL:

                    break;
            }
            return false;
        }
    }

    /**
     * Класс с движением меню, отлавливаем движения меню и можем их обрабатывать
     */
    public static class DrawerLayoutMotion implements DrawerLayout.DrawerListener{
        // Единоличник наверное избыточен
        private static DrawerLayoutMotion drawerLayoutMotion;
        //Вариэбле
        public static int state = 0;
        private boolean beenStarted;

        // Конструктор
        private DrawerLayoutMotion(DrawerLayout drawerLayout){
            drawerLayout.addDrawerListener(this);
        }
        public static void initMotion(DrawerLayout drawerLayout){
            drawerLayoutMotion = new DrawerLayoutMotion(drawerLayout);
        }

        // попрежнему можем отдать его
        public static DrawerLayoutMotion getDrawerLayoutMotion() {
            return drawerLayoutMotion;

        }

        // методы
        public static int getState() {
            return state;
        }

        @Override
        public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
            if(slideOffset > 0.5 && !beenStarted){
                Animation.startEnterAnimators();
                beenStarted = true;
            }

            Log.d(TAG, "onDrawerSlide: " + slideOffset + " " + beenStarted);


            if(slideOffset == 1.0 && DrawerLayoutGestures.drawerLayoutGestures.mDragging)
                lockDrawer();
        }

        @Override
        public void onDrawerOpened(@NonNull View drawerView) {
//
        }

        @Override
        public void onDrawerClosed(@NonNull View drawerView) {
            Animation.setStartPosition();
            beenStarted = false;
//            Log.d(TAG, "onDrawerClosed: close)");w
//            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }

        @Override
        public void onDrawerStateChanged(int newState) {
            state = newState;
            Log.d(TAG, "onDrawerStateChanged: " + newState);
        }
    }

    /**
     * Класс, хранящий в себе анимации
     */
    public static class Animation {

        private static ArrayList<ConstraintLayout.LayoutParams> reservedIconParams;
        private static ArrayList<RadioButtonCenter> buttonCenters;
        private static ArrayList<ValueAnimator> enterIconAnimators;
        private static ValueAnimator enterBackCircleAnimator;
        private static ConstraintLayout.LayoutParams reservedBackCircleParams;
        private static ConstraintLayout navBack;

        public static void startEnterAnimators(){
            for (ValueAnimator enterAnimator: enterIconAnimators)
                enterAnimator.start();
            enterBackCircleAnimator.start();
        }

        public static void setStartPosition(){
            //Отменяем анимации, если они были
            enterBackCircleAnimator.cancel();
            for(ValueAnimator animator: enterIconAnimators)
                animator.cancel();

            //выставляем стартовые позицыии
            for (RadioButtonCenter buttonCenter: buttonCenters){
                ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) buttonCenter.getLayoutParams();
                params.circleAngle = -90f;
                buttonCenter.setLayoutParams(params);
            }
            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) navBack.getLayoutParams();
            params.topMargin = 0;
            navBack.setLayoutParams(params);
        }

        public static void initAnimations(ArrayList<RadioButtonCenter> buttonCenters){
            Animation.buttonCenters = buttonCenters;

            Log.d(TAG, "initIconAnimations: " + buttonCenters.size());

            // инициализация параметров для иконок
            reservedIconParams = new ArrayList<>(buttonCenters.size());
            enterIconAnimators = new ArrayList<>(buttonCenters.size());

            for (RadioButtonCenter btn: buttonCenters)
                reservedIconParams.add((ConstraintLayout.LayoutParams) btn.getLayoutParams());
            // инициализация параметров для заднего круга
            navBack = drawerLayout.findViewById(R.id.circle_menu_back);
            reservedBackCircleParams = (ConstraintLayout.LayoutParams) navBack.getLayoutParams();

            initEnterAnimators();
        }

        private static void initEnterAnimators(){
            for (int i = 0; i < buttonCenters.size(); i++){
                ValueAnimator valueAnimator = ValueAnimator.ofFloat(-90f, reservedIconParams.get(i).circleAngle);
                valueAnimator.setDuration(500);
                valueAnimator.setStartDelay((buttonCenters.size() -1 - i)* 50L);
                valueAnimator.setInterpolator(new OvershootInterpolator(0.65f));
                int finalI = i;
                valueAnimator.addUpdateListener(animator -> {
                    float value = (float) animator.getAnimatedValue();
                    ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) buttonCenters.get(finalI).getLayoutParams();
                    params.circleAngle = value;
                    buttonCenters.get(finalI).setLayoutParams(params);
                });
                enterIconAnimators.add(valueAnimator);
            }
            // Задний круг

            enterBackCircleAnimator = ValueAnimator.ofInt(0, reservedBackCircleParams.topMargin);
            enterBackCircleAnimator.setDuration(350);
            enterBackCircleAnimator.setInterpolator(new DecelerateInterpolator());
            enterBackCircleAnimator.addUpdateListener(animator -> {
                int value = (int) animator.getAnimatedValue();
                ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) navBack.getLayoutParams();
                params.topMargin = value;
                navBack.setLayoutParams(params);
            });

        }

    }

//    public static class drawerToggle
}
