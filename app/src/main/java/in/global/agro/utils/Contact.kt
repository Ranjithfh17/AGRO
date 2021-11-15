package `in`.global.agro.utils

import `in`.global.agro.utils.Constants.DISPLAY_NAME
import `in`.global.agro.utils.Constants.MOBILE_NO
import android.annotation.SuppressLint
import android.content.ContentProviderOperation
import android.content.Context
import android.net.Uri
import android.provider.ContactsContract
import android.provider.ContactsContract.PhoneLookup
import android.util.Log


object Contact {

    @SuppressLint("Recycle")
    fun ifContactExists(context: Context, number:String):Boolean{

        val uri=Uri.withAppendedPath(PhoneLookup.CONTENT_FILTER_URI,Uri.encode(number))
        val phoneNoProjection = arrayOf(PhoneLookup._ID, PhoneLookup.NUMBER, PhoneLookup.DISPLAY_NAME)
        val cursor=context.contentResolver.query(uri,phoneNoProjection,null,null,null)

        try {
            if (cursor != null) {
                if (cursor.moveToFirst()){
                    return true

                }
            }

        }catch (exception:Exception){
            Log.i("TAG", "ifContactExists: ${exception.message}")
        }

        finally {
            cursor?.close()
        }

        return false


    }




    fun saveContact(context: Context){
        val ops = ArrayList<ContentProviderOperation>()

        var op: ContentProviderOperation.Builder =
            ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
                .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
        ops.add(op.build())


        op = ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
            .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
            .withValue(
                ContactsContract.Data.MIMETYPE,
                ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE
            )
            .withValue(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME,DISPLAY_NAME)
        ops.add(op.build())


        op = ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
            .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
            .withValue(
                ContactsContract.Data.MIMETYPE,
                ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE
            )
            .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, MOBILE_NO)
            .withValue(
                ContactsContract.CommonDataKinds.Phone.TYPE,
                ContactsContract.CommonDataKinds.Phone.TYPE_HOME
            )
        ops.add(op.build())


        try {
           context.contentResolver.applyBatch(ContactsContract.AUTHORITY, ops)
            Log.i("TAG", "addNewContact: contact saved")

        } catch (exception: Exception) {

            Log.i("TAG", "addNewContact: $exception")
        }

    }
}