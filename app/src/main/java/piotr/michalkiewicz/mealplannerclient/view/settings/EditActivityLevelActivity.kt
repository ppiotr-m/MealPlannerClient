package piotr.michalkiewicz.mealplannerclient.view.settings

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_change_location.countrySpinner
import kotlinx.android.synthetic.main.activity_edit_level.*
import piotr.michalkiewicz.mealplannerclient.R
import piotr.michalkiewicz.mealplannerclient.user.model.UserAccount
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues
import piotr.michalkiewicz.mealplannerclient.view.utils.ConstantValues.Companion.ACTIVITY_LEVEL_VALUES

class EditActivityLevelActivity : DataPassingActivity(), AdapterView.OnItemSelectedListener {

    private var selectedActivityLevel: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_level)

        init()
    }

    private fun init() {
        initSpinner()
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        countrySpinner.onItemSelectedListener = this
        cancelActivityLevelChangeBtn.setOnClickListener {
            finish()
        }
        confirmActivityLevelBtn.setOnClickListener {
            val data = getDataFromIntent()

            data.userSettings.nutritionProfileSettings!!.activityLevel = selectedActivityLevel
            setDataForParentActivity(data)
            finish()
        }
    }

    private fun initSpinner() {
        countrySpinner.adapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, ACTIVITY_LEVEL_VALUES)
        val index = ACTIVITY_LEVEL_VALUES.indexOf((intent.getSerializableExtra(ConstantValues.SETTINGS_DATA) as UserAccount)
                .userSettings
                .nutritionProfileSettings?.activityLevel)
        if (index >= 0) {
            countrySpinner.setSelection(index)
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        selectedActivityLevel = p0?.selectedItem.toString()
    }
}