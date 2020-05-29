package piotr.michalkiewicz.mealplannerclient.connection.auth.model

import com.google.gson.annotations.SerializedName

class Token constructor(
        @SerializedName("access_token") var accessToken: String,
        @SerializedName("token_type") var tokenType: String,
        @SerializedName("refresh_token") var refreshToken: String,
        @SerializedName("scope") var scope: String,
        @SerializedName("expires_in") var expiresIn: Int
)
