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
import piotr.michalkiewicz.mealplannerclient.view.utils.FragmentCallback

class DietCustomizationFragment : Fragment(), View.OnClickListener {

    private lateinit var fragmentCallback: FragmentCallback
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
        val linearLayout = activity?.findViewById<LinearLayout>(R.id.linearButtonsDiet)

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

    private fun closeFragment() {
        activity?.supportFragmentManager?.beginTransaction()?.remove(this)?.commit()
    }
}