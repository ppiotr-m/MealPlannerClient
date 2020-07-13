package piotr.michalkiewicz.mealplannerclient.view.activities.settings

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_settings.*
import piotr.michalkiewicz.mealplannerclient.R

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        editPasswordBtn.setOnClickListener{
            startActivity(Intent(this@SettingsActivity, EditPasswordActivity::class.java))
        }
        editLocationBtn.setOnClickListener{
            startActivity(Intent(this@SettingsActivity, EditLocationActivity::class.java))
        }
    }
}
