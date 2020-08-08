package piotr.michalkiewicz.mealplannerclient.view.login_and_signup.auth

interface LoginListener {
    fun loginSuccessful();
    fun loginFailed();
}