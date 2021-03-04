package piotr.michalkiewicz.mealplannerclient.nutrition.dialogs

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.dialog_nutrition_add_product.*
import piotr.michalkiewicz.mealplannerclient.R
import piotr.michalkiewicz.mealplannerclient.products.model.BasicFoodItemData
import java.util.*

class NutritionAddEatenProductDialogFragment(
    private val ownerContext: Context,
    private val listener: AddEatenProductDialogListener
) : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_nutrition_add_product, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSpinner()
        initAutoCompleteTextView()
    }

    private fun initSpinner() {
        productAmountUnitSpinner.adapter = ArrayAdapter.createFromResource(
            this.requireContext(),
            R.array.units_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            productAmountUnitSpinner.adapter = adapter
        }
    }

    fun updateAutoCompleteTextViewAdapterData(data: List<BasicFoodItemData>) {
        val adapter =
            ArrayAdapter(ownerContext, android.R.layout.simple_spinner_dropdown_item, data)
        nutritionProductsAutoCompleteTV.setAdapter(adapter)
        nutritionProductsAutoCompleteTV.showDropDown()
    }

    private fun initAutoCompleteTextView() {
        nutritionProductsAutoCompleteTV.setAdapter(
            ArrayAdapter(
                ownerContext,
                android.R.layout.simple_spinner_dropdown_item,
                LinkedList<BasicFoodItemData>()
            )
        )

        nutritionProductsAutoCompleteTV.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if (s?.length!! > 2) {
                    listener.findProductsForName(s.toString())
                }
            }
        })
    }

    companion object {
        const val TAG = "NutritionAddProductDialog"
    }

    interface AddEatenProductDialogListener {
        fun findProductsForName(name: String)
        fun addProductToEatenList(mongoId: String)
    }
}