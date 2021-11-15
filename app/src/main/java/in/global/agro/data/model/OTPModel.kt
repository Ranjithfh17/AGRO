package `in`.global.agro.data.model

data class OTPModel(
    val cid: String,
    val com: Com,
    val error: Boolean,
    val error_msg: String,
    val mobile: String,
    val role_id: String,
    val uid: String,
    val uname: String
) {
    data class Com(
        val address: String,
        val aid: Any,
        val cid: String,
        val id: String,
        val main_id: Any,
        val name: String,
        val phone: String,
        val sname: String,
        val status: String,
        val type: String,
        val uid: String,
    )
}