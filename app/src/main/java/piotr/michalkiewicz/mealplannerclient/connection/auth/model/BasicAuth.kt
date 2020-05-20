package piotr.michalkiewicz.mealplannerclient.connection.auth.model

class BasicAuth @JvmOverloads constructor(
        val grant_type: String,
        val username: String,
        val password: String
)

