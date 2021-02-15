package piotr.michalkiewicz.mealplannerclient.view.recipes.dialogs

import androidx.fragment.app.DialogFragment

sealed class DialogState<T : DialogFragment> {
    data class Ok<T : DialogFragment>(val dialog: T) : DialogState<T>()
    data class Cancel<T : DialogFragment>(val dialog: T) : DialogState<T>()
}