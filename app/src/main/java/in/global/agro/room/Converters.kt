package `in`.global.agro.room

import `in`.global.agro.data.model.Products
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class Converters {

    @TypeConverter
    fun listToJson(value: List<Products>): String = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<Products>::class.java).toList()


//    @TypeConverter
//    fun stringToMeasurements(json: String?): List<Products>? {
//        val gson = Gson()
//        val type: Type = object : TypeToken<List<Products?>?>() {}.type
//        return gson.fromJson<List<Products>>(json, type)
//    }
//
//
//    @TypeConverter
//    fun measurementsToString(list: List<Products?>?): String? {
//        val gson = Gson()
//        val type: Type = object : TypeToken<List<Products?>?>() {}.type
//        return gson.toJson(list, type)
//    }


}