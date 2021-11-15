package `in`.global.agro.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethod
import android.view.inputmethod.InputMethodManager

object KeyboardHelper {

    fun openKeyboard(view:View,context:Context){
        val inputMethodManager=context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(view,InputMethodManager.SHOW_IMPLICIT)
    }

    fun hideKeyboard(view:View,context:Context){
        val inputMethodManager=context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken,0)
    }


}