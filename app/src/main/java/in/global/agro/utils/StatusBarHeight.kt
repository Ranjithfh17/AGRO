package `in`.global.agro.utils

import android.content.Context
import android.content.res.Resources
import android.graphics.Rect
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.view.Window

object StatusBarHeight {

    /*get statusBar height using window object*/
    fun getStatusBarHeight(window: Window): Int {
        val rectangle = Rect()
        window.decorView.getWindowVisibleDisplayFrame(rectangle)
        return rectangle.top - window.findViewById<View>(Window.ID_ANDROID_CONTENT).top
    }


    /*get statusBar height using resources*/
    fun getStatusBarHeight(resources: Resources): Int {
        var result = 0;
        val resourceId = resources.getIdentifier("status-bar-height", "dimen", "android")
        if (resourceId > 0) {
            result = resources.getDimensionPixelSize(resourceId)
        }
        return result
    }


    /*get toolbar height*/
    fun getToolBarHHeight(context: Context, resources: Resources): Int {
        val typedValue = TypedValue()
        var actionBarHeight = 0

        if (context.theme.resolveAttribute(android.R.attr.actionBarSize, typedValue, true)) {
            actionBarHeight =
                TypedValue.complexToDimensionPixelSize(typedValue.data, resources.displayMetrics)

        }

        return actionBarHeight
    }


}