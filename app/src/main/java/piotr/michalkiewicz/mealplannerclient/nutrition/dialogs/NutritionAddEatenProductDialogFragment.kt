package piotr.michalkiewicz.mealplannerclient.nutrition.dialogs

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.dialog_nutrition_add_product.*
import piotr.michalkiewicz.mealplannerclient.R
import piotr.michalkiewicz.mealplannerclient.products.model.BasicFoodItemData
import piotr.michalkiewicz.mealplannerclient.products.usda.model.FoodPortion
import piotr.michalkiewicz.mealplannerclient.products.usda.model.UsdaFoodItem
import java.util.*

class NutritionAddEatenProductDialogFragment(
    private val ownerContext: Context,
    private val addProductListener: AddEatenProductDialogListener,
    private val portionSelectListener: PortionSelectListener
) : DialogFragment(), AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {

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
        setClickListeners()
        setTextWatcherForAmountEditText()
    }

    private fun initSpinner() {
        productPortionSpinner.adapter = ArrayAdapter(
            this.requireContext(),
            android.R.layout.simple_spinner_item,
            LinkedList<FoodPortion>()
        )
    }

    fun updateAutoCompleteTextViewAdapterData(data: List<BasicFoodItemData>) {
//          TODO Not sure if this is correct but might just be. The thing is,
//          when the view is destroyed, viewModel remains, and when we re-enter the fragment
//          the dialog is created and observing is set, then it sees change - there's no data anymore
//          however the dialog has not been shown after re-entering the fragment so the views references are nulls,
//          yet they are notified of the change to empty data,
//          thus throwing null pointer exception. Unless this sneaky statement is checked :>
        if (nutritionProductsAutoCompleteTV != null) {
            val adapter =
                ArrayAdapter(ownerContext, android.R.layout.simple_spinner_dropdown_item, data)
            nutritionProductsAutoCompleteTV.setAdapter(adapter)
            nutritionProductsAutoCompleteTV.showDropDown()
        }
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
                    addProductListener.findProductsForName(s.toString())
                }
            }
        })
    }

    private fun setTextWatcherForAmountEditText() {
        productAmountET.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val lastCharacter = s?.get(count - 1)
                if (lastCharacter != null) {
                    if (!lastCharacter.equals(".")) {
                        addProductListener.onAmountChanged(s.toString().toFloat())
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })
    }

    fun showSelectedItemPortions(selectedItem: UsdaFoodItem) {
        productPortionSpinner.adapter = ArrayAdapter(
            this.requireContext(),
            android.R.layout.simple_spinner_item,
            selectedItem.foodPortions
        )
    }

    private fun setClickListeners() {
        nutritionProductsAutoCompleteTV.onItemClickListener = this
        productPortionSpinner.onItemSelectedListener = this

        addProductBtn.setOnClickListener {
            addProductListener.addProduct()
        }
    }

    companion object {
        const val NUTRITION_DIALOG_TAG = "NutritionAddProductDialog"
    }

    interface AddEatenProductDialogListener {
        fun findProductsForName(name: String)
        fun onProductSelectedWithPosition(position: Int)
        fun onAmountChanged(amount: Float)
        fun addProduct()
    }

    interface PortionSelectListener {
        fun onPortionSelected(position: Int)
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        addProductListener.onProductSelectedWithPosition(position)
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        portionSelectListener.onPortionSelected(position)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }
}