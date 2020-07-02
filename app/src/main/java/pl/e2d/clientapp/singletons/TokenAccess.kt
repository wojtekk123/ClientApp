package pl.e2d.clientapp.singletons

object TokenAccess {

    var token: String? = String()

    fun setMyStringData(str: String) {
        token = str
    }

    fun getMyStringData(): String? =
        token

}