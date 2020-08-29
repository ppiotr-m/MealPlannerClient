package piotr.michalkiewicz.mealplannerclient.user.service_generator

import piotr.michalkiewicz.mealplannerclient.auth.ServiceGenerator
import piotr.michalkiewicz.mealplannerclient.user.model.UserAccount
import retrofit2.Callback

class UserServiceGenerator: ServiceGenerator() {

    private lateinit var userService: UserService

    init {
        if (!::userService.isInitialized) {
            val retrofit = retrofitBuilder()
            userService = retrofit.create(UserService::class.java)
        }
    }

    fun getUserAccount(callback: Callback<UserAccount>) {
        val callAsync = userService.getUserAccount()
        callAsync.enqueue(callback)
    }

    fun signUp(userAccount: UserAccount, callback: Callback<UserAccount>) {
        val callAsync = userService.signUp(userAccount)
        callAsync.enqueue(callback)
    }

    fun saveUserAccountData(data: UserAccount, callback: Callback<UserAccount>) {
        val callAsync = userService.editAccountSettings(data)
        callAsync.enqueue(callback)
    }
}