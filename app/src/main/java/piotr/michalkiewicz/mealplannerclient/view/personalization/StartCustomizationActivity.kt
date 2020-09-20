package piotr.michalkiewicz.mealplannerclient.view.personalization

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import piotr.michalkiewicz.mealplannerclient.R
import piotr.michalkiewicz.mealplannerclient.databinding.ActivityLoginBinding
import piotr.michalkiewicz.mealplannerclient.databinding.ActivityStartCustomizationBinding
import piotr.michalkiewicz.mealplannerclient.view.menu.MainMenuActivity

class StartCustomizationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStartCustomizationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartCustomizationBinding.inflate(layoutInflater)

        binding.startCustomizationContent.startCustomizationBtn.setOnClickListener(View.OnClickListener {
            // TODO Start customization here
        })
        binding.startCustomizationContent.skipCustomizationBtn.setOnClickListener(View.OnClickListener {
            val myIntent = Intent(this@StartCustomizationActivity, MainMenuActivity::class.java)
            startActivity(myIntent)
        })

        setContentView(binding.root)
    }
}