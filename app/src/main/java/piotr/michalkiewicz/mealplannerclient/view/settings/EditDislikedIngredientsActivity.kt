package piotr.michalkiewicz.mealplannerclient.view.settings

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import kotlinx.android.synthetic.main.fragment_disliked_ingredients_cust.*
import piotr.michalkiewicz.mealplannerclient.R
import piotr.michalkiewicz.mealplannerclient.user.model.UserAccount
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues.Companion.SETTINGS_DATA
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues.Companion.TAG
import piotr.michalkiewicz.mealplannerclient.view.utils.ConstantValues

class EditDislikedIngredientsActivity : DataPassingActivity(), View.OnClickListener {

    private val productsList = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_disliked_ingredients_cust)

        initConfirmButton()
        addButtonsToLayout(linearLayoutIngredientsButtons,
                ConstantValues.DIS_LIKE_INGREDIENTS_CUSTOMIZATION_BUTTONS, 1)
        initIngredientsButtons(linearLayoutIngredientsButtons)

    }

    private fun initConfirmButton() {
        confirmButton.text = resources.getString(R.string.confirm)
        confirmButton.setOnClickListener {
            setNewResultAndFinish()
        }
    }

    private fun setNewResultAndFinish() {
        val userData = getDataFromIntent()
        userData?.userSettings?.userPreference?.unlikeIngredients = productsList
        productsList.forEach {
            Log.d(TAG, it)
        }
        setDataForParentActivity(userData)
        finish()
    }

    private fun addButtonsToLayout(buttonsLayout: LinearLayout?, buttonsNames: MutableList<String>,
                                   idPrefix: Int) {
        removeAllChildren(buttonsLayout)

        for ((index, text) in buttonsNames.withIndex()) {
            val button = MaterialButton(this)
            button.text = text
            button.id = ("$idPrefix$index").toInt()
            Log.i(text, button.id.toString())
            buttonsLayout?.addView(button)
        }
    }

    private fun removeAllChildren(layoutToClear: LinearLayout?) {
        for (i in layoutToClear!!.childCount - 1 downTo 0) {
            val childToRemove: View = layoutToClear.getChildAt(i)
            layoutToClear.removeView(childToRemove)
        }
    }

    private fun addClick(id: Int) {
        try {
            linearLayoutIngredientsButtons.findViewById<View>(id).setOnClickListener(this)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onClick(v: View) {
        val element = findViewById<Button>(v.id)?.text.toString()
        markButton(element, v)
    }

    private fun markButton(element: String, v: View) {
        if (!productsList.contains(element)) {
            findViewById<Button>(v.id)?.setBackgroundColor(piotr.michalkiewicz.mealplannerclient.utils.ConstantValues.CHECKED_BUTTON_COLOR)
            productsList.add(element)
        } else {
            findViewById<Button>(v.id)?.setBackgroundColor(piotr.michalkiewicz.mealplannerclient.utils.ConstantValues.DEFAULT_BUTTON_COLOR)
            productsList.remove(element)
        }
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
}