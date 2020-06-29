package piotr.michalkiewicz.mealplannerclient.auth

interface LoginListener {
    fun loginSuccessful();
    fun loginFailed();
}