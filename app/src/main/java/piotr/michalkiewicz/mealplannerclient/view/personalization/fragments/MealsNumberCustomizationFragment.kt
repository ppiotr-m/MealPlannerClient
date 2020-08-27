package piotr.michalkiewicz.mealplannerclient.view.personalization.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import piotr.michalkiewicz.mealplannerclient.R
import piotr.michalkiewicz.mealplannerclient.view.utils.FragmentCallback

class MealsNumberCustomizationFragment : Fragment(), View.OnClickListener {

    private lateinit var fragmentCallback: FragmentCallback
    private lateinit var confirmBtn: Button
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

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        val portionAmountButtonsLayout = activity?.findViewById<LinearLayout>(R.id.portionAmountLayout)
        val portionPreferenceButtonsLayout = activity?.findViewById<LinearLayout>(R.id.portionPreferenceLayout)
        val mealsAmountButtonsLayout = activity?.findViewById<LinearLayout>(R.id.mealPerPlan)

        initConfirmButton()
        initCustomizationButtons(listOf(portionAmountButtonsLayout, portionPreferenceButtonsLayout, mealsAmountButtonsLayout))

        super.onViewStateRestored(savedInstanceState)
    }

    private fun initCustomizationButtons(linearLayouts: List<LinearLayout?>) {
        val buttonsIds = ArrayList<Int>()

        linearLayouts.forEach {
            for (i in 0 until it!!.childCount) {
                val v: View = it.getChildAt(i)
                if (v is Button) {
                    buttonsIds.add(v.id)
                }
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
        val element = activity?.findViewById<Button>(v.id)?.text.toString()
        Log.i("ELELELE", element)
    }

    private fun initConfirmButton() {
        confirmBtn = activity?.findViewById(R.id.confirmButton)!!

        confirmBtn.setOnClickListener {
//            fragmentCallback.onListSelect(productsList, this)
            if (goBack) {
                closeFragment()
            } else {
//                runMealsNumberCustomizationFragment()
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentCallback = context as FragmentCallback
    }

    private fun closeFragment() {
        activity?.supportFragmentManager?.beginTransaction()?.remove(this)?.commit()
    }
}