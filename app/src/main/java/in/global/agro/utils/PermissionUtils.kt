package `in`.global.agro.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat


object PermissionUtils {


    fun hasContactPermission(applicationContext:Context):Boolean{

        if ((ActivityCompat.checkSelfPermission(applicationContext, Manifest.permission.WRITE_CONTACTS)
                    == PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(applicationContext, Manifest.permission.READ_CONTACTS)
                    == PackageManager.PERMISSION_GRANTED)){
            return true
        }


        return false
    }

}