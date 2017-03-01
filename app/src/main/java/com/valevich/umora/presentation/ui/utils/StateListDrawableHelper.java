package com.valevich.umora.presentation.ui.utils;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.StateListDrawable;
import android.util.StateSet;

public class StateListDrawableHelper {
    public static StateListDrawable getDrawable(int pressedColor, int normalColor){
        /*Creating bitmap for color which will be used at pressed state*/
        Rect rectPressed = new Rect(0, 0, 1, 1);

        Bitmap imagePressed = Bitmap.createBitmap(rectPressed.width(), rectPressed.height(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(imagePressed);
        Paint paintPressed = new Paint();
        paintPressed.setColor(pressedColor);
        canvas.drawRect(rectPressed, paintPressed);
        RectF bounds = new RectF();
        bounds.round(rectPressed);

        /*Creating bitmap for color which will be used at normal state*/
        Rect rectNormal = new Rect(0, 0, 1, 1);
        Bitmap imageNormal = Bitmap.createBitmap(rectNormal.width(), rectNormal.height(), Bitmap.Config.ARGB_8888);
        Canvas canvasNormal = new Canvas(imageNormal);
        Paint paintNormal = new Paint();
        paintNormal.setColor(normalColor);
        canvasNormal.drawRect(rectNormal, paintNormal);


        /*Now assigning states to StateListDrawable*/
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(
                new int[]{android.R.attr.state_pressed},
                new BitmapDrawable(Resources.getSystem(),imagePressed));
        stateListDrawable.addState(StateSet.WILD_CARD, new BitmapDrawable(Resources.getSystem(),imageNormal));

        return stateListDrawable;

    }
}
