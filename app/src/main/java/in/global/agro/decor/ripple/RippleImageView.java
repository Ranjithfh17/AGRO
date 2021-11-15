package in.global.agro.decor.ripple;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

import org.jetbrains.annotations.NotNull;

public class RippleImageView extends AppCompatImageView {
    public RippleImageView(@NonNull @NotNull Context context) {
        super(context);
        setBackground();
    }

    public RippleImageView(@NonNull @NotNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs) {
        super(context, attrs);
        setBackground();

    }

    public RippleImageView(@NonNull @NotNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setBackground();

    }

    private void setBackground(){
        setBackground(RippleUtil.getRippleDrawable(getContext(),getBackground()));
        setClickable(true);
    }
}
