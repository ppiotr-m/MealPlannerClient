package piotr.michalkiewicz.mealplannerclient.view.personalization

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import piotr.michalkiewicz.mealplannerclient.R
import piotr.michalkiewicz.mealplannerclient.view.main_menu.MainMenuActivity

class StartCustomizationActivity : AppCompatActivity() {

    private lateinit var startCustomizationBtn: Button
    private lateinit var skipCustomizationBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_customization)

        initButtons()
        implButtons()
    }

    private fun initButtons() {
        startCustomizationBtn = findViewById(R.id.startCustomizationBtn)
        skipCustomizationBtn = findViewById(R.id.skipCustomizationBtn)
    }


    private fun implButtons() {
        startCustomizationBtn.setOnClickListener(View.OnClickListener {
            //                Intent myIntent = new Intent(StartCustomizationActivity.this, DietCustomizationActivity.class);
    //                startActivity(myIntent);
            Log.i("eee", "eee")
            // TODO Start customization here
        })

        skipCustomizationBtn.setOnClickListener(View.OnClickListener {
            val myIntent = Intent(this@StartCustomizationActivity, MainMenuActivity::class.java)
            startActivity(myIntent)
        })
    }
}