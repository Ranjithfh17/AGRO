package `in`.global.agro.utils

import `in`.global.agro.R
import android.content.Context
import android.os.Build
import android.view.View
import android.view.WindowManager

object ViewUtils {


    fun dimBackground(view: View) {
        val container = view.rootView
        val windowManager = view.context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val layoutParams = container.layoutParams as WindowManager.LayoutParams
        layoutParams.flags = layoutParams.flags or WindowManager.LayoutParams.FLAG_DIM_BEHIND
        layoutParams.dimAmount = 0.3f
        windowManager.updateViewLayout(container, layoutParams)
    }


    fun addOutlineShadow(view: View) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            view.outlineAmbientShadowColor = view.context.getColor(R.color.lightBlue1)
            view.outlineSpotShadowColor = view.context.getColor(R.color.lightBlue1)
        }
    }



}