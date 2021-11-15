package in.global.agro.decor.ripple;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;

import androidx.core.content.ContextCompat;

import java.util.Arrays;

import in.global.agro.R;


public class RippleUtil {


    public static Drawable getRippleDrawable(Context context, Drawable backGround) {
        float[] innerRadius = new float[8];
        float[] outerRadius = new float[8];

        Arrays.fill(innerRadius, 20f);
        Arrays.fill(outerRadius, 20f);

        RoundRectShape roundRectShape = new RoundRectShape(innerRadius, null, outerRadius);
        ShapeDrawable mask = new ShapeDrawable(roundRectShape);
        ColorStateList colorStateList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.purple_200));

        RippleDrawable rippleDrawable = new RippleDrawable(colorStateList, backGround, mask);
        rippleDrawable.setAlpha(50);

        return rippleDrawable;
    }




}
