package boot.spring.entity

data class User(var username: String = "", var password: String = "") {
    var id: Int = 0
    var address_id: Short = 0
    var email: String? = null
    var head_image: String? = null
    var last_update: String? = null
}