package `in`.global.agro.data.model

class PayList : ArrayList<PayList.PayListItem>(){

    data class PayListItem(
        val id: String,
        val name: String
    )

}