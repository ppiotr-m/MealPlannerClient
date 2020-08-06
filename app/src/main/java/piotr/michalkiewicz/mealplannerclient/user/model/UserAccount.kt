package piotr.michalkiewicz.mealplannerclient.user.model

import java.io.Serializable

class UserAccount : Serializable {

    var id: String? = null
    var email: String? = null
    var name: String? = null
    var username: String? = null
    var password: String? = null
    var location: String? = null
    var userSettings: UserSettings? = null

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