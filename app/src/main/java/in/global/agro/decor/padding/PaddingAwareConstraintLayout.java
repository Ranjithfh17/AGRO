package in.global.agro.decor.padding;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import org.jetbrains.annotations.NotNull;

import in.global.agro.utils.StatusBarHeight;

public class PaddingAwareConstraintLayout extends ConstraintLayout {

    public PaddingAwareConstraintLayout(@NonNull @NotNull Context context) {
        super(context);
        setMargin();
    }

    public PaddingAwareConstraintLayout(@NonNull @NotNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs) {
        super(context, attrs);
        setMargin();

    }

    public PaddingAwareConstraintLayout(@NonNull @NotNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs, int defStyleAttr) {
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
