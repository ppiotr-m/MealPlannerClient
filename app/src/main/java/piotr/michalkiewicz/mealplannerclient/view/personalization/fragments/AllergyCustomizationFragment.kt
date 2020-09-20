package piotr.michalkiewicz.mealplannerclient.view.personalization.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import piotr.michalkiewicz.mealplannerclient.databinding.FragmentAllergyCustomizationBinding
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues
import piotr.michalkiewicz.mealplannerclient.view.personalization.PersonalizationFragment
import piotr.michalkiewicz.mealplannerclient.view.personalization.StartCustomActivity
import piotr.michalkiewicz.mealplannerclient.view.utils.ConstantValues.Companion.ALLERGIES_CUSTOMIZATION_BUTTONS
import piotr.michalkiewicz.mealplannerclient.view.utils.FragmentCallback

class AllergyCustomizationFragment : PersonalizationFragment(), View.OnClickListener {

    private val allergiesList = ArrayList<String>()
    private var goBack = true
    private lateinit var confirmBtn: Button
    private var _binding: FragmentAllergyCustomizationBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentAllergyCustomizationBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
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
        val allergiesLinearLayout = binding.allergiesLinearLayout
        addButtonsToLayout(allergiesLinearLayout, ALLERGIES_CUSTOMIZATION_BUTTONS, 1)
        initConfirmButton()
        initButtonsFromLayout(allergiesLinearLayout)

        super.onViewStateRestored(savedInstanceState)
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

        binding.confirmButton.setOnClickListener {
            fragmentCallback.onListSelect(allergiesList, this)
            if (goBack) {
                closeFragment()
            } else {
                //End of customization
                closeFragment()
                (activity as StartCustomActivity).updateUserSettings()
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}