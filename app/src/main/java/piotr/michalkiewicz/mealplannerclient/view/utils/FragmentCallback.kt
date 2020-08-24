package piotr.michalkiewicz.mealplannerclient.view.utils

import androidx.fragment.app.Fragment

interface FragmentCallback {

    fun onVariableSelect(variable: String, from: Fragment)
    fun onListSelect(variable: List<String>, from: Fragment)
}