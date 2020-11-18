package piotr.michalkiewicz.mealplannerclient.view.personalization.service

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.button.MaterialButton

abstract class PersonalizationCustomFragment : Fragment() {

    protected lateinit var navController: NavController
    protected val personalizationViewModel: PersonalizationViewModel by activityViewModels()
    protected val recipeServiceValuesDownloader: RecipeServiceValuesDownloader =
        RecipeServiceValuesDownloader()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        navController = findNavController()
        initUserPreference()
        super.onViewCreated(view, savedInstanceState)
    }

    protected fun addButtonsToLayout(
        buttonsLayout: LinearLayout?,
        buttonsNames: List<String>,
        idPrefix: Int
    ) {
        removeAllChildren(buttonsLayout)

        for ((index, text) in buttonsNames.withIndex()) {
            val button = MaterialButton(requireContext())
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

    private fun initUserPreference() {
        if (personalizationViewModel.getUserPreference() == null) {
            personalizationViewModel.initUserPreference()
        }
    }
}