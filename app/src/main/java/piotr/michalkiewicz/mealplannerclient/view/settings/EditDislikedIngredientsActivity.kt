package piotr.michalkiewicz.mealplannerclient.view.settings

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.LinearLayout
import com.google.android.material.button.MaterialButton
import kotlinx.android.synthetic.main.activity_edit_allergies.*
import piotr.michalkiewicz.mealplannerclient.R
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues.Companion.CHECKED_BUTTON_COLOR
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues.Companion.DEFAULT_BUTTON_COLOR
import piotr.michalkiewicz.mealplannerclient.view.utils.ConstantValues.Companion.DIS_LIKE_INGREDIENTS_CUSTOMIZATION_BUTTONS
import java.util.*
import kotlin.collections.ArrayList

class EditDislikedIngredientsActivity : DataPassingActivity(), View.OnClickListener {

    private val productsList = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_allergies)

        init()
    }

    private fun init() {
        initConfirmButton()
        addButtonsToLayout(rightSelectionButtonsLayout, leftSelectionButtonsLayout,
                DIS_LIKE_INGREDIENTS_CUSTOMIZATION_BUTTONS, 1)
        initIngredientsButtons(leftSelectionButtonsLayout)
        initIngredientsButtons(rightSelectionButtonsLayout)
        setAutoCompleteTextView()
    }

    private fun setAutoCompleteTextView() {
        allergySearchET.setAdapter(ArrayAdapter<String>(
                this, android.R.layout.simple_dropdown_item_1line, getProductsMock()))
    }

    private fun getProductsMock(): List<String> {
        return listOf("Sunflower", "Beer", "Bread", "Rice", "Pasta", "Orange", "Kiwi", "Banana",
                "Avocado", "Watermelon", "Citron", "Lemon", "Fish", "Grenadine", "Homars", "Imbir",
                "Jalapeno", "Mango", "Strawberry", "Turkey", "Tuna")
    }

    private fun initConfirmButton() {
        allergiesAndIngredientsEditConfirmBtn.text = resources.getString(R.string.confirm)
        allergiesAndIngredientsEditConfirmBtn.setOnClickListener {
            setNewResultAndFinish()
        }
    }

    private fun setNewResultAndFinish() {
        val userData = getDataFromIntent()
        userData.userSettings.userPreference.unlikeIngredients = productsList
        setDataForParentActivity(userData)

        finish()
    }

    private fun addButtonsToLayout(leftButtonsLayout: LinearLayout?,
                                   rightButtonsLayout: LinearLayout?,
                                   buttonsNames: MutableList<String>,
                                   idPrefix: Int) {
        removeAllChildren(leftButtonsLayout)
        removeAllChildren(rightButtonsLayout)

        val pairOfLists = divideListIntoTwoLists(buttonsNames)

        for ((index, text) in pairOfLists.first.withIndex()) {
            val button = MaterialButton(this)
            button.text = text
            button.id = ("$idPrefix$index").toInt()
            leftButtonsLayout?.addView(button)
        }

        for ((index, text) in pairOfLists.second.withIndex()) {
            val button = MaterialButton(this)
            button.text = text
            button.id = ("$idPrefix$index").toInt()
            rightButtonsLayout?.addView(button)
        }
    }

    private fun divideListIntoTwoLists(list: List<String>): Pair<List<String>, List<String>> {
        val result = Pair(LinkedList<String>(), LinkedList<String>())

        if (list.size % 2 == 0) {
            result.first.addAll(list.subList(0, (list.size / 2)))
            result.second.addAll(list.subList(list.size / 2, list.size))
        } else {
            result.first.addAll(list.subList(0, (list.size / 2) + 1))
            result.second.addAll(list.subList((list.size / 2) + 1, list.size))
        }

        return result
    }

    private fun removeAllChildren(layoutToClear: LinearLayout?) {
        for (i in layoutToClear!!.childCount - 1 downTo 0) {
            val childToRemove: View = layoutToClear.getChildAt(i)
            layoutToClear.removeView(childToRemove)
        }
    }

    override fun onClick(v: View) {
        if (v is Button) {
            val element = v.text.toString()
            markButton(element, v)
        }
    }

    private fun markButton(element: String, v: View) {
        if (!productsList.contains(element)) {
            v.setBackgroundColor(CHECKED_BUTTON_COLOR)
            productsList.add(element)
        } else {
            v.setBackgroundColor(DEFAULT_BUTTON_COLOR)
            productsList.remove(element)
        }
    }

    private fun initIngredientsButtons(linearLayout: LinearLayout?) {
        val buttonsIds = ArrayList<Int>()

        for (i in 0 until linearLayout!!.childCount) {
            val v: View = linearLayout.getChildAt(i)
            if (v is Button) {
                buttonsIds.add(v.id)
                v.setOnClickListener(this)
                val dislikedProductsList = getDataFromIntent().userSettings.userPreference.unlikeIngredients
                if (dislikedProductsList.contains(v.text.toString())) {
                    markButton(v.text.toString(), v)
                }
            }
        }
    }
}