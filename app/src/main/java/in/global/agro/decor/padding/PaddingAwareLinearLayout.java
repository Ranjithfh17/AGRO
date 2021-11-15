package in.global.agro.decor.padding;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import in.global.agro.utils.StatusBarHeight;

public class PaddingAwareLinearLayout  extends LinearLayout {

    public PaddingAwareLinearLayout(Context context) {
        super(context);
        setMargin();
    }

    public PaddingAwareLinearLayout(Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs) {
        super(context, attrs);
        setMargin();

    }

    public PaddingAwareLinearLayout(Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setMargin();

    }

    private void setMargin() {
        setPadding(getPaddingLeft(),
                StatusBarHeight.INSTANCE.getStatusBarHeight(getResources()) + getPaddingTop(),
                getPaddingRight(),
                getPaddingBottom()
        );
    }
}
