package piotr.michalkiewicz.mealplannerclient.view.personalization.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import piotr.michalkiewicz.mealplannerclient.R

class MealsNumberCustomizationFragment : Fragment() {

    private var goBack = true

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_meals_number_customization, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance(shouldGoBack: Boolean) = MealsNumberCustomizationFragment().apply {
            goBack = shouldGoBack
        }
    }
}