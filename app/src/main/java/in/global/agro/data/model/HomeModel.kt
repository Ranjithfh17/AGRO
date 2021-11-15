package `in`.global.agro.data.model

data class HomeModel(
    val data: List<Data>,
    val page_type: Int,
    val total_column: Int
) {

    data class Data(
        val label: String,
        val value: String
    )

}