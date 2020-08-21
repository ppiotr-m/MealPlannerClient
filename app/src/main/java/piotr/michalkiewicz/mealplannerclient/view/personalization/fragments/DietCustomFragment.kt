package piotr.michalkiewicz.mealplannerclient.view.personalization.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import piotr.michalkiewicz.mealplannerclient.R
import piotr.michalkiewicz.mealplannerclient.view.utils.FragmentCallback

private const val VALUE = "value"

class DietCustomFragment : Fragment(), View.OnClickListener {

    private lateinit var fragmentCallback: FragmentCallback

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_diet_customization, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        if (arguments?.containsKey("value")!!) {
            Log.i("arguments", arguments.toString())
        }

        super.onCreate(savedInstanceState)
    }

    companion object {
        @JvmStatic
        fun newInstance(diet: String) = DietCustomFragment().apply {
            arguments = Bundle().apply {
                putString(VALUE, diet)
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentCallback = context as FragmentCallback
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        addClick(R.id.standartDietBtn)
        addClick(R.id.paleoDietBtn)
        addClick(R.id.vegetarianDietBtn)
        addClick(R.id.pescatarianDietBtn)
        addClick(R.id.flexitarianDietBtn)
        addClick(R.id.veganDietBtn)
        addClick(R.id.lowCarbDietBtn)
        addClick(R.id.ketoDietBtn)

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
//        Log.i("selectDiet", activity?.findViewById<Button>(v.id)?.text.toString())
        fragmentCallback.onVariableSelect(activity?.findViewById<Button>(v.id)?.text.toString(), this)
        closeFragment()
    }

    private fun closeFragment() {
        activity?.supportFragmentManager?.beginTransaction()?.remove(this)?.commit()
    }
}