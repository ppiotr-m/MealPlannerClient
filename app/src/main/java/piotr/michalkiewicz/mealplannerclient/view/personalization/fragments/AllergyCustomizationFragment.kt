package piotr.michalkiewicz.mealplannerclient.view.personalization.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.core.view.children
import androidx.fragment.app.Fragment
import piotr.michalkiewicz.mealplannerclient.R
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues
import piotr.michalkiewicz.mealplannerclient.view.utils.FragmentCallback

class AllergyCustomizationFragment : Fragment(), View.OnClickListener {

    private val allergiesList = ArrayList<String>()
    private lateinit var fragmentCallback: FragmentCallback
    private var goBack = true
    private lateinit var confirmBtn: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_allergy_customization, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance(shouldGoBack: Boolean) = AllergyCustomizationFragment().apply {
            goBack = shouldGoBack
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentCallback = context as FragmentCallback
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        initAllergyButtons()
        initConfirmButton()

        super.onViewStateRestored(savedInstanceState)
    }

    private fun initAllergyButtons() {
        val allergiesLinearLayout = activity?.findViewById<LinearLayout>(R.id.allergiesLinearLayout)
        val insideLayouts: Sequence<View>? = allergiesLinearLayout?.children

        insideLayouts?.iterator()?.forEach {
            it as LinearLayout
            initButtonsFromLayout(it)
        }
    }

    private fun initButtonsFromLayout(linearLayout: LinearLayout?) {
        val buttonsIds = ArrayList<Int>()

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


    private fun initConfirmButton() {
        confirmBtn = activity?.findViewById(R.id.confirmButton)!!

        confirmBtn.setOnClickListener {
            fragmentCallback.onListSelect(allergiesList, this)
            if (goBack) {
                closeFragment()
            } else {
//                runMealsNumberCustomizationFragment()
            }
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
        markButton(element, v)
    }

    private fun markButton(element: String, v: View) {
        if (!allergiesList.contains(element)) {
            activity?.findViewById<Button>(v.id)?.setBackgroundColor(ConstantValues.CHECKED_BUTTON_COLOR)
            allergiesList.add(element)
        } else {
            activity?.findViewById<Button>(v.id)?.setBackgroundColor(ConstantValues.DEFAULT_BUTTON_COLOR)
            allergiesList.remove(element)
        }
    }

    private fun closeFragment() {
        activity?.supportFragmentManager?.beginTransaction()?.remove(this)?.commit()
    }
}