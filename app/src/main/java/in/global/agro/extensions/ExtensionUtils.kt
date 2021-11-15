package `in`.global.agro.extensions

import android.content.Context
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

fun Context.showToast(message: String) {
    Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
}


fun Context.showSnackBar(view: ViewGroup, message: String) {
    Snackbar.make(view, message, Snackbar.LENGTH_LONG).show()
}