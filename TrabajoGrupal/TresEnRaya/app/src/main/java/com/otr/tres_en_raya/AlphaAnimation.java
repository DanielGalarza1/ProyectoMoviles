package com.otr.tres_en_raya;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public class AlphaAnimation extends Animation {
    final int startWidth;
    final int targetWidth;
    View view;

    public AlphaAnimation(View view, int targetWidth) {
        this.view = view;
        this.targetWidth = targetWidth;
        startWidth = (int)view.getAlpha();
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        int newWidth = (int) (startWidth + (targetWidth - startWidth) * interpolatedTime);
        view.setAlpha(newWidth);
        view.requestLayout();
    }

    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
    }

    @Override
    public boolean willChangeBounds() {
        return true;
    }
}