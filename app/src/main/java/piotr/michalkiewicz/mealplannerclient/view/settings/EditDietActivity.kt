package piotr.michalkiewicz.mealplannerclient.view.settings

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.fragment_diet_customization.*
import piotr.michalkiewicz.mealplannerclient.R
import piotr.michalkiewicz.mealplannerclient.user.model.UserAccount
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues
import piotr.michalkiewicz.mealplannerclient.view.utils.ConstantValues.Companion.DIETS_CUSTOMIZATION_BUTTONS

class EditDietActivity : AppCompatActivity(), View.OnClickListener {

    private val buttonsIds = ArrayList<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_diet_customization)

        initDietCustomizationButtons(linearButtonsDiet)
    }

    private fun initDietCustomizationButtons(linearLayout: LinearLayout?) {
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

    private fun addClick(id: Int) {
        try {
            findViewById<View>(id).setOnClickListener(this)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onClick(v: View) {
        setNewResultAndFinish((v as TextView).text.toString())
    }

    private fun setNewResultAndFinish(selectedDiet: String) {
        val userData = getDataFromIntent()
        if (selectedDiet.isEmpty()) {
            userData?.userSettings?.userPreference?.diet = DIETS_CUSTOMIZATION_BUTTONS[0]
        } else {
            userData?.userSettings?.userPreference?.diet = selectedDiet
        }

        setDataForParentActivity(userData)
        finish()
    }

    private fun getDataFromIntent(): UserAccount? {
        return intent.getSerializableExtra(ConstantValues.SETTINGS_DATA) as? UserAccount
    }

    private fun setDataForParentActivity(data: UserAccount?) {
        val intent = Intent()
        intent.putExtra(ConstantValues.SETTINGS_DATA, data)
        setResult(SettingsActivity.RESULT_OK, intent)
    }

}