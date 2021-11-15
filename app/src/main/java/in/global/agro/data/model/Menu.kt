package `in`.global.agro.data.model

data class Menu(
    val title: String,
    val image: Int,
    val list: ArrayList<SubMenu>? = null,
    var isSubMenu: Boolean = false,
)
