package in.global.agro.decor.ripple;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;

import org.jetbrains.annotations.NotNull;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)

public class RippleConstraintLayout extends ConstraintLayout {

    public RippleConstraintLayout(@NonNull @NotNull Context context) {
        super(context);
        setBackground();
    }


    public RippleConstraintLayout(@NonNull @NotNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs) {
        super(context, attrs);
        setBackground();
    }


    public RippleConstraintLayout(@NonNull @NotNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setBackground();
    }


    private void setBackground(){
        setBackground(RippleUtil.getRippleDrawable(getContext(),getBackground()));
        setClickable(true);
    }



}
