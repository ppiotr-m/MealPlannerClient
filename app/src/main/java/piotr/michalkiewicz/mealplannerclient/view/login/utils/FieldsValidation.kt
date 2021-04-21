package piotr.michalkiewicz.mealplannerclient.view.login.utils

import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues

object FieldsValidation {

    fun validateEmail(email: String): Boolean {
        if (email.length >= ConstantValues.MIN_EMAIL_LENGTH &&
            email.matches(ConstantValues.EMAIL_REGEX)
        ) return true
        return false
    }

    fun validatePassword(password: String): Boolean {
        if (password.length >= ConstantValues.MIN_PASSWORD_LENGTH ||
            password.length <= ConstantValues.MAX_PASSWORD_LENGTH
        ) return true
        return false
    }

}