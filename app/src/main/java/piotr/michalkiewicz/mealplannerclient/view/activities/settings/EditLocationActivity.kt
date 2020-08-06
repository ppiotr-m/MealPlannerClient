package piotr.michalkiewicz.mealplannerclient.view.activities.settings

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_change_location.*
import piotr.michalkiewicz.mealplannerclient.R
import piotr.michalkiewicz.mealplannerclient.user.model.UserAccount
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues
import java.util.*
import kotlin.collections.ArrayList

class EditLocationActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private var selectedLocation : String? = ""
    private val countries = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_location)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

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
            val data = intent.getSerializableExtra(ConstantValues.SETTINGS_DATA) as? UserAccount
            data?.location = selectedLocation
            setDataForParentActivity(data)
            finish()
        }
    }

    private fun setDataForParentActivity(data : UserAccount?){
        val intent = Intent()
        intent.putExtra(ConstantValues.SETTINGS_DATA, data)
        setResult(SettingsActivity.RESULT_OK, intent)
    }

    private fun initSpinner(){
        val locales = Locale.getAvailableLocales()
        val countriesTemporary = ArrayList<String>()

        locales.forEach {
            if(it.displayCountry.isNotEmpty()) {
                countriesTemporary.add(it.displayCountry)
            }
        }
        countries.addAll(countriesTemporary.sorted().distinct())
        countrySpinner.adapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, countries)
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        selectedLocation = p0?.selectedItem.toString()
        Log.i(ConstantValues.TAG, "Location: " + selectedLocation)
    }
}


