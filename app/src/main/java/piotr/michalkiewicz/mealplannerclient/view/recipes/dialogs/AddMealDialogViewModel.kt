package piotr.michalkiewicz.mealplannerclient.view.recipes.dialogs

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AddMealDialogViewModel : ViewModel() {

    val state = MutableLiveData<DialogState<AddMealDialogFragment>>()
}