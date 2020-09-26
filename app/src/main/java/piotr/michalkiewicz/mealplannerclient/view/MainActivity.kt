package piotr.michalkiewicz.mealplannerclient.view

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import piotr.michalkiewicz.mealplannerclient.R
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues

class MainActivity : AppCompatActivity() {

    companion object {
        lateinit var MY_PREFERENCSES: SharedPreferences
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("MainActivity", "I started!")
        MY_PREFERENCSES = applicationContext.getSharedPreferences(ConstantValues.MY_PREFERENCE_NAME, AppCompatActivity.MODE_PRIVATE)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

    }

}