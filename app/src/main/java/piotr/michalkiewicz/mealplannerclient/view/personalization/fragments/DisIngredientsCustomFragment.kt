package piotr.michalkiewicz.mealplannerclient.view.personalization.fragments

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import piotr.michalkiewicz.mealplannerclient.R
import piotr.michalkiewicz.mealplannerclient.view.utils.FragmentCallback

class DisIngredientsCustomFragment : Fragment(), View.OnClickListener {

    private lateinit var fragmentCallback: FragmentCallback
    private val buttonsIds = ArrayList<Int>()
    private val productsList = ArrayList<String>()
    private lateinit var confirmBtn: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_disliked_ingredients_cust, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
                DisIngredientsCustomFragment().apply {}
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentCallback = context as FragmentCallback
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        initConfirmButton()
        val linearLayout = activity?.findViewById<LinearLayout>(R.id.linearLayoutIngredientsButtons)

        for (i in 0 until linearLayout!!.childCount) {
            val v: View = linearLayout.getChildAt(i)
            if (v is Button) {
                buttonsIds.add(v.id)
            }
        }
        for (buttonId in buttonsIds) {
            addClick(buttonId)
        }
        super.onViewStateRestored(savedInstanceState)
    }

    private fun initConfirmButton() {
        confirmBtn = activity?.findViewById(R.id.confirmButton)!!

        confirmBtn.setOnClickListener {
            fragmentCallback.onListSelect(productsList, this)
            runMealsNumberCustomizationFragment()
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
        if (!productsList.contains(element)) {
            activity?.findViewById<Button>(v.id)?.setBackgroundColor(Color.LTGRAY)
            productsList.add(element)
        } else {
            activity?.findViewById<Button>(v.id)?.setBackgroundColor(Color.RED)
            productsList.remove(element)
        }
    }

    private fun runMealsNumberCustomizationFragment() {
        activity?.supportFragmentManager
                ?.beginTransaction()
                ?.replace(R.id.dietCustomizationFragment, MealsNumberCustomizationFragment.newInstance())
                ?.addToBackStack(null)
                ?.commit()
    }

    private fun closeFragment() {
        activity?.supportFragmentManager?.beginTransaction()?.remove(this)?.commit()
    }
}