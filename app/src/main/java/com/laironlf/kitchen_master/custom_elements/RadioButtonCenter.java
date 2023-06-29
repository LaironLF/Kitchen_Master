package com.laironlf.kitchen_master.custom_elements;


import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatRadioButton;

import com.laironlf.kitchen_master.R;

public class RadioButtonCenter extends AppCompatRadioButton {


    public RadioButtonCenter(Context context) {
        super(context);
    }

    public RadioButtonCenter(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RadioButtonCenter(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        Drawable[] drawables = getCompoundDrawables();
        Drawable drawableCenter = drawables[1];

        if (drawableCenter != null) {
            int drawableWidth = drawableCenter.getIntrinsicWidth();
            int drawableHeight = drawableCenter.getIntrinsicHeight();

            float totalWidth = drawableWidth + getPaint().measureText(getText().toString());
            float totalHeight = drawableHeight + getPaint().getTextSize() + getCompoundPaddingBottom();

            canvas.save();

            if(getText().length() == 0) canvas.translate((getWidth() - drawableWidth) / 2f, (getHeight() - drawableHeight) / 2f);
            else canvas.translate((getWidth() - drawableWidth) / 2f, (getHeight() - totalHeight) / 2f);

            drawableCenter.setBounds(0, 0, drawableWidth, drawableHeight);
            drawableCenter.draw(canvas);

            canvas.translate((drawableWidth - getPaint().measureText(getText().toString())) / 2f, (drawableHeight * 9 >> 3) + getTextHeight());
            getPaint().setColor(getCurrentTextColor());
            canvas.drawText(getText().toString(), 0, 0, getPaint());

            canvas.restore();
        } else {
            super.onDraw(canvas);
        }
    }

    private int getTextHeight() {
        Paint paint = getPaint();
        Rect bounds = new Rect();
        paint.getTextBounds(getText().toString(), 0, getText().length(), bounds);
        return bounds.height();
    }

    public void setDrawableCenter(Drawable drawable) {
        setCompoundDrawablesRelativeWithIntrinsicBounds(null, drawable, null, null);
    }

}