package com.costular.flatsharing.util;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class LinearLayoutBackground extends LinearLayout {

    public LinearLayoutBackground(Context context) {
        super(context);
    }

    public LinearLayoutBackground(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LinearLayoutBackground(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setBackgroundColor(@ColorInt int color) {
        super.setBackgroundColor(color);
        ViewGroup parent = (ViewGroup) getParent();
        CardView card = (CardView) parent.getParent();
        card.setCardBackgroundColor(color);
    }
}