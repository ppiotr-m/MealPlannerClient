package piotr.michalkiewicz.mealplannerclient.view.userPreferences.utils

/**
 * Used as a wrapper for data that is exposed via a LiveData that represents an event.
 */
open class Event() {

    var hasBeenHandled = false
        private set // Allow external read but not write

    fun getContentIfNotHandled(): Unit? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
        }
    }
}