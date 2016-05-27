package com.sparta.imenu_client.userInterface;

import android.content.Context;
import android.view.View;
import android.view.animation.AnimationUtils;

import com.sparta.imenu_client.R;

/**
 * Created by Hussein Abu Maash on 5/3/2016.
 */

public class IMenuAnimation {
    public static void slide_down(Context context, View v){
        android.view.animation.Animation animation = AnimationUtils.loadAnimation(context, R.anim.slide_down);
        v.startAnimation(animation);
    }

    public static void slide_up(Context context, View v){
        android.view.animation.Animation animation = AnimationUtils.loadAnimation(context, R.anim.slide_up);
        v.startAnimation(animation);
    }

    public static void fade_in(Context context, View v){
        android.view.animation.Animation animation = AnimationUtils.loadAnimation(context, R.anim.fade_in);
        v.startAnimation(animation);
    }

    public static void fade_out(Context context, View v){
        android.view.animation.Animation animation = AnimationUtils.loadAnimation(context, R.anim.fade_out);
        v.startAnimation(animation);
    }

}
