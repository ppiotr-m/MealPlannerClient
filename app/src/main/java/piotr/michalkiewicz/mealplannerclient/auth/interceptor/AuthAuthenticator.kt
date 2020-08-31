package piotr.michalkiewicz.mealplannerclient.auth.interceptor

import android.util.Log
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues
import piotr.michalkiewicz.mealplannerclient.auth.LoginClient
import piotr.michalkiewicz.mealplannerclient.auth.MyPreference
import java.io.IOException

class AuthAuthenticator: Authenticator {

    private val myPreference: MyPreference = MyPreference()
    private val loginClient: LoginClient = LoginClient()

    /**
     * Method automatically refresh token if get response with 401, if get 500 must return null to avoid multi same queries to refresh token.
     */



    @Throws(IOException::class)
    override fun authenticate(route: Route?, response: Response): Request? {
        var requestAvailable: Request? = null
        if (response?.code == 401) {
            loginClient.refreshToken(myPreference.getRefreshToken().toString())
            try {
                myPreference.getToken()?.let {
                    requestAvailable = response.request.newBuilder().header("Authorization", "Bearer $it").build()
                }
                return requestAvailable
            } catch (ex: Exception) {
                Log.i(ConstantValues.TAG, ex.toString())
            }
        } else if (response?.code == 500) {
            return null
        }
        return requestAvailable
    }
}