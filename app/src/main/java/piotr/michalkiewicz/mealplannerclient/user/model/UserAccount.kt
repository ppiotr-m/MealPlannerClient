package piotr.michalkiewicz.mealplannerclient.user.model

import java.io.Serializable

class UserAccount : Serializable {

    var id: String? = null
    var email: String? = null
    var name: String? = null
    var username: String? = "Mariola"
    var password: String? = null
    var location: String? = "Poland"
    var userSettings: UserSettings? = null
    var sex: String? = "Male"

    companion object{
        @JvmStatic
        fun createMockUserAccountWithParams(email: String?, password: String?): UserAccount{
            val account = UserAccount()
            account.email = email
            account.password = password
            return account
        }
    }
}