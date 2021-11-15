package in.global.agro.decor.ripple;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import org.jetbrains.annotations.NotNull;

public class RippleTextView extends AppCompatTextView {

    public RippleTextView(@NonNull @NotNull Context context) {
        super(context);
        setBackground();
    }

    public RippleTextView(@NonNull @NotNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs) {
        super(context, attrs);
        setBackground();

    }

    public RippleTextView(@NonNull @NotNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setBackground();

    }

    private void setBackground() {
        setClickable(true);
        setBackgroundColor(Color.TRANSPARENT);
        setBackground(RippleUtil.getRippleDrawable(getContext(), getBackground()));
    }
}
