package piotr.michalkiewicz.mealplannerclient.view.utils

import androidx.fragment.app.Fragment

interface FragmentCallback {

    fun onVariableSelect(variable: String, from: Fragment)
    fun onVariableSelectMulti(variable: String, from: Fragment, fieldName: String)
    fun onListSelect(variable: List<String>, from: Fragment)
}