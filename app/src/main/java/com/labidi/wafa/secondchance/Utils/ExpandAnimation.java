package com.labidi.wafa.secondchance.Utils;

import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created by sofien on 25/11/2017.
 */
//TODO implementation animation class
public class ExpandAnimation extends Animation {

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);
    }

    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
    }

    @Override
    public boolean willChangeBounds() {
        return  true ;
    }
}
