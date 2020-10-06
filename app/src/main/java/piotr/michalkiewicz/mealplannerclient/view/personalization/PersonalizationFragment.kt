package piotr.michalkiewicz.mealplannerclient.view.personalization

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import piotr.michalkiewicz.mealplannerclient.view.utils.FragmentCallback

abstract class PersonalizationFragment : Fragment() {

    lateinit var fragmentCallback: FragmentCallback

    override fun onAttach(context: Context) {
        super.onAttach(context)
//        fragmentCallback = context as FragmentCallback
    }

    fun addButtonsToLayout(buttonsLayout: LinearLayout?, buttonsNames: MutableList<String>, idPrefix: Int) {
        removeAllChildren(buttonsLayout)

        for ((index, text) in buttonsNames.withIndex()) {
            val button = MaterialButton(requireContext())
            button.text = text
            button.id = ("$idPrefix$index").toInt()
            Log.i(text,button.id.toString() )
            buttonsLayout?.addView(button)
        }
    }

    private fun removeAllChildren(layoutToClear: LinearLayout?) {
        for (i in layoutToClear!!.childCount - 1 downTo 0) {
            val childToRemove: View = layoutToClear.getChildAt(i)
            layoutToClear.removeView(childToRemove)
        }
    }

    fun closeFragment() {
        activity?.supportFragmentManager?.beginTransaction()?.remove(this)?.commit()
    }
}