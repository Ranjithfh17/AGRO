package in.global.agro.decor.ripple;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

public class RippleLinearLayout extends LinearLayout {

    public RippleLinearLayout(Context context) {
        super(context);
        setBackground();
    }

    public RippleLinearLayout(Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs) {
        super(context, attrs);
        setBackground();

    }

    public RippleLinearLayout(Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setBackground();

    }

    private void setBackground() {
        setClickable(true);
        setFocusable(true);
        setBackground(RippleUtil.getRippleDrawable(getContext(), getBackground()));
    }


}
