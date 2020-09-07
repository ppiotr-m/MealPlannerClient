package piotr.michalkiewicz.mealplannerclient.view.settings

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_change_location.*
import piotr.michalkiewicz.mealplannerclient.R
import piotr.michalkiewicz.mealplannerclient.user.model.UserAccount
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues.Companion.SETTINGS_DATA
import java.util.*
import kotlin.collections.ArrayList

class EditLocationActivity : DataPassingActivity(), AdapterView.OnItemSelectedListener {

    private var selectedLocation: String = ""
    private val countries = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_location)

        init()
    }

    private fun init(){
        initSpinner()
        setOnClickListeners()
    }

    private fun setOnClickListeners(){
        countrySpinner.onItemSelectedListener = this
        cancelLocationChangeBtn.setOnClickListener {
            finish()
        }
        confirmLocationBtn.setOnClickListener {
            val data = getDataFromIntent()

            data.userSettings.location = selectedLocation
            setDataForParentActivity(data)
            finish()
        }
    }

    private fun initSpinner(){
        val locales = Locale.getAvailableLocales()
        val countriesTemporary = ArrayList<String>()

        locales.forEach {
            if (it.displayCountry.isNotEmpty()) {
                countriesTemporary.add(it.displayCountry)
            }
        }
        countries.addAll(countriesTemporary.sorted().distinct())
        countrySpinner.adapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, countries)
        val index = countries.indexOf((intent.getSerializableExtra(SETTINGS_DATA) as UserAccount)
                .userSettings
                .location)
        if (index >= 0) {
            countrySpinner.setSelection(index)
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        selectedLocation = p0?.selectedItem.toString()
    }
}


