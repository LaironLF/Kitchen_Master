package com.laironlf.kitchen_master.animations;

import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;

public class BaseAnimations  {

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
