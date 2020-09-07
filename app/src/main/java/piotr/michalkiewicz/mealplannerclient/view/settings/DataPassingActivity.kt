package piotr.michalkiewicz.mealplannerclient.view.settings

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import piotr.michalkiewicz.mealplannerclient.user.model.UserAccount
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues

abstract class DataPassingActivity: AppCompatActivity() {

    fun getDataFromIntent(): UserAccount?{
        return intent.getSerializableExtra(ConstantValues.SETTINGS_DATA) as? UserAccount
    }

    fun setDataForParentActivity(data : UserAccount?){
        val intent = Intent()
        intent.putExtra(ConstantValues.SETTINGS_DATA, data)
        setResult(SettingsActivity.RESULT_OK, intent)
    }
}