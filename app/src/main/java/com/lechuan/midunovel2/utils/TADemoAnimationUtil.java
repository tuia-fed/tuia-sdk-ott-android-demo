package com.lechuan.midunovel2.utils;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;

/**
 * <p> File description: <p>
 * <p> Creator: Adroll   <p>
 * <p> Created date: 11/25/20 <p>
 * * * * * * * * * * * * * * * * * * * * * *
 * Thinking is more important than coding *
 * * * * * * * * * * * * * * * * * * * * * *
 */
public class TADemoAnimationUtil {

    public static void enlarge(View view, float scale, int duration){
        int width = view.getMeasuredWidth();
        int height = view.getMeasuredHeight();
        ScaleAnimation scaleAnimation = new ScaleAnimation(1, scale, 1, scale, width * 0.5f, height * 0.5f);
        scaleAnimation.setDuration(duration);
        scaleAnimation.setFillAfter(true);
        view.startAnimation(scaleAnimation);
    }

    public static void shrink(View view, float scale, int duration){
        int width = view.getMeasuredWidth();
        int height = view.getMeasuredHeight();
        ScaleAnimation scaleAnimation = new ScaleAnimation(scale, 1, scale, 1, width * 0.5f, height * 0.5f);
        scaleAnimation.setDuration(duration);
        scaleAnimation.setFillAfter(true);
        view.startAnimation(scaleAnimation);
    }
}
