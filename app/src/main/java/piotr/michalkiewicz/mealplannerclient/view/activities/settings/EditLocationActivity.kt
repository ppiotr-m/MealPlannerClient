package piotr.michalkiewicz.mealplannerclient.view.activities.settings

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_change_location.*
import piotr.michalkiewicz.mealplannerclient.R
import java.util.*

class EditLocationActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_location)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        init()
    }

    private fun init(){
        val locales = Locale.getAvailableLocales()
        val countries = ArrayList<String>()

        locales.forEach {
            if(it.displayCountry.isNotEmpty()) {
                countries.add(it.displayCountry)
            }
        }

        countrySpinner.adapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, countries.sorted())
    }
}


