package piotr.michalkiewicz.mealplannerclient.view.utils

import androidx.fragment.app.Fragment

interface FragmentCallback {

    fun onVariableSelect(variable: String, from: Fragment)
}