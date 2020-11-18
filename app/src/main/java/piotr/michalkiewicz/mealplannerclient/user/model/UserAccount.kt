package piotr.michalkiewicz.mealplannerclient.user.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class UserAccount(
    @SerializedName("email") var email: String,
    @SerializedName("username") var username: String,
    @SerializedName("password") var password: String,
    @SerializedName("roles") val roles: Set<Role>,
    @SerializedName("accountType") val accountType: AccountType,
    @SerializedName("userSettings") var userSettings: UserSettings
) : Serializable

data class SingUpUserAccount(
    @SerializedName("email") var email: String,
    @SerializedName("username") var username: String,
    @SerializedName("password") var password: String
) : Serializable

enum class AccountType(val value: String) {
    TEMP("Temp"),
    NORMAL("Normal");
}

data class Role(@SerializedName("role") val role: String)