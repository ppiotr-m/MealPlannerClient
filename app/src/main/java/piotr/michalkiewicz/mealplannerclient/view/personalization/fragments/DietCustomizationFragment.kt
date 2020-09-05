package piotr.michalkiewicz.mealplannerclient.view.personalization.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import piotr.michalkiewicz.mealplannerclient.R
import piotr.michalkiewicz.mealplannerclient.view.personalization.PersonalizationFragment
import piotr.michalkiewicz.mealplannerclient.view.utils.ConstantValues.Companion.DIETS_CUSTOMIZATION_BUTTONS
import piotr.michalkiewicz.mealplannerclient.view.utils.FragmentCallback

class DietCustomizationFragment : PersonalizationFragment(), View.OnClickListener {

    private val buttonsIds = ArrayList<Int>()
    private var goBack = true

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_diet_customization, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance(shouldGoBack: Boolean) = DietCustomizationFragment().apply {
            goBack = shouldGoBack
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentCallback = context as FragmentCallback
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        val buttonsLayout = activity?.findViewById<LinearLayout>(R.id.linearButtonsDiet)
        addButtonsToLayout(buttonsLayout, DIETS_CUSTOMIZATION_BUTTONS, 1)
        initDietCustomizationButtons(buttonsLayout)

        super.onViewStateRestored(savedInstanceState)
    }

    private fun initDietCustomizationButtons(linearLayout: LinearLayout?) {
        for (i in 0 until linearLayout!!.childCount) {
            val v: View = linearLayout.getChildAt(i)
            if (v is Button) {
                buttonsIds.add(v.id)
            }
        }
        for (buttonId in buttonsIds) {
            addClick(buttonId)
        }
    }

    private fun addClick(id: Int) {
        try {
            requireView().findViewById<View>(id).setOnClickListener(this)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onClick(v: View) {
        fragmentCallback.onVariableSelect(activity?.findViewById<Button>(v.id)?.text.toString(), this)

        if (goBack) {
            closeFragment()
        } else {
            runDislikeIngredientsFragment()
        }
    }

    private fun runDislikeIngredientsFragment() {
        activity?.supportFragmentManager
                ?.beginTransaction()
                ?.replace(R.id.dietCustomizationFragment, DisIngredientsCustomizationFragment.newInstance(false))
                ?.addToBackStack(null)
                ?.commit()
    }
}