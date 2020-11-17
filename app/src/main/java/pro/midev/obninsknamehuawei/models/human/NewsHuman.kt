package pro.midev.obninsknamehuawei.models.human

data class NewsHuman(
    val id: String,
    val plus: Int = 0,
    val minus: Int = 0,
    var tag: String = "",
    var title: String = "",
    var author: String = "",
    var text: String = "",
    var date: String = "",
    val reviews: Long = 0,
    val link: String = "",
    val image: String = ""
)