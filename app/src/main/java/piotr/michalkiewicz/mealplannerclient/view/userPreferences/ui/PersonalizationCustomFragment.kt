package piotr.michalkiewicz.mealplannerclient.view.userPreferences.ui

import android.view.View
import android.widget.LinearLayout
import androidx.annotation.VisibleForTesting
import androidx.fragment.app.Fragment
import androidx.navigation.navGraphViewModels
import com.google.android.material.button.MaterialButton
import dagger.hilt.android.AndroidEntryPoint
import piotr.michalkiewicz.mealplannerclient.R
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues.Companion.PERSONALIZATION_PROCESS
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues.Companion.USER_PREFERENCES_FRAGMENT
import piotr.michalkiewicz.mealplannerclient.view.userPreferences.data.remote.RecipeServiceValuesDownloader
import javax.inject.Inject

@AndroidEntryPoint
abstract class PersonalizationCustomFragment : Fragment() {

    @VisibleForTesting(otherwise = VisibleForTesting.PROTECTED)
    lateinit var viewModel: UserPreferencesViewModel

    @Inject protected lateinit var recipeServiceValuesDownloader: RecipeServiceValuesDownloader

    protected fun getViewModel(originOfNavigation: String): UserPreferencesViewModel {
        return when (originOfNavigation) {
            PERSONALIZATION_PROCESS -> {
                val tempViewModel: UserPreferencesViewModel by navGraphViewModels(R.id.PersonalizationGraph) {
                    defaultViewModelProviderFactory
                }
                tempViewModel
            }
            USER_PREFERENCES_FRAGMENT -> {
                val tempViewModel: UserPreferencesViewModel by navGraphViewModels(R.id.UserPreferencesGraph) {
                    defaultViewModelProviderFactory
                }
                tempViewModel
            }
            else -> {
                error("Wrong originOfNavigation")
            }
        }
    }

    protected fun addButtonsToLayout(
        buttonsLayout: LinearLayout?,
        buttonsNames: List<String>,
        idPrefix: Int
    ) {
        removeAllChildren(buttonsLayout)

        for ((index, text) in buttonsNames.withIndex()) {
            val button = MaterialButton(requireContext(),null, R.attr.materialButtonOutlinedStyle)
            button.text = text
            button.id = ("$idPrefix$index").toInt()
            buttonsLayout?.addView(button)
        }
    }

    private fun removeAllChildren(layoutToClear: LinearLayout?) {
        for (i in layoutToClear!!.childCount - 1 downTo 0) {
            val childToRemove: View = layoutToClear.getChildAt(i)
            layoutToClear.removeView(childToRemove)
        }
    }
}