package pro.midev.obninsknamehuawei.models.human

data class NewsHuman(
    val id: String,
    val plus: Int = 0,
    val minus: Int = 0,
    val tag: String = "",
    val title: String = "",
    val author: String = "",
    val text: String = "",
    val date: String = "",
    val reviews: Long = 0,
    val link: String = "",
    val image: String = ""
)