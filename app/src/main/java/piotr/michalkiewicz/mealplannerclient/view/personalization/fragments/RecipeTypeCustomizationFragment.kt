package piotr.michalkiewicz.mealplannerclient.view.personalization.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import piotr.michalkiewicz.mealplannerclient.R
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues
import piotr.michalkiewicz.mealplannerclient.view.utils.FragmentCallback

class RecipeTypeCustomizationFragment : Fragment(), View.OnClickListener {

    private lateinit var fragmentCallback: FragmentCallback
    private val recipeTypeList = ArrayList<String>()
    private lateinit var confirmBtn: Button
    private var goBack = true

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_recipe_type_customization, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance(shouldGoBack: Boolean) = RecipeTypeCustomizationFragment().apply {
            goBack = shouldGoBack
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentCallback = context as FragmentCallback
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        val buttonsLayout = activity?.findViewById<LinearLayout>(R.id.linearLayoutRecipeTypesButtons)
        initConfirmButton()
        initIngredientsButtons(buttonsLayout)

        super.onViewStateRestored(savedInstanceState)
    }

    private fun initIngredientsButtons(linearLayout: LinearLayout?) {
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
            fragmentCallback.onListSelect(recipeTypeList, this)
            if (goBack) {
                closeFragment()
            } else {
                runMealsNumberCustomizationFragment()
            }
        }
    }

    private fun runMealsNumberCustomizationFragment() {
        activity?.supportFragmentManager
                ?.beginTransaction()
                ?.replace(R.id.dietCustomizationFragment, MealsNumberCustomizationFragment.newInstance(shouldGoBack = false, shouldInitBaseValues = true))
                ?.addToBackStack(null)
                ?.commit()
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
        if (!recipeTypeList.contains(element)) {
            activity?.findViewById<Button>(v.id)?.setBackgroundColor(ConstantValues.CHECKED_BUTTON_COLOR)
            recipeTypeList.add(element)
        } else {
            activity?.findViewById<Button>(v.id)?.setBackgroundColor(ConstantValues.DEFAULT_BUTTON_COLOR)
            recipeTypeList.remove(element)
        }
    }

    private fun closeFragment() {
        activity?.supportFragmentManager?.beginTransaction()?.remove(this)?.commit()
    }
}