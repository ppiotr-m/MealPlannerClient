package piotr.michalkiewicz.mealplannerclient

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues
import piotr.michalkiewicz.mealplannerclient.view.MainActivity

@AndroidEntryPoint
class HiltTestActivity : AppCompatActivity() {

    companion object {
        lateinit var MY_PREFERENCES: SharedPreferences
        private lateinit var mainContext: Context

        fun getMainContext(): Context {
            return mainContext
        }
    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        initMyPreferences()
        initContext()
    }
    private fun initMyPreferences() {
        MY_PREFERENCES = applicationContext.getSharedPreferences(
            ConstantValues.MY_PREFERENCE_NAME,
            MODE_PRIVATE
        )
    }

    private fun initContext() {
        mainContext = this
    }
}