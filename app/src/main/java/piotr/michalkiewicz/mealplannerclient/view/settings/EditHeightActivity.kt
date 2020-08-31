package piotr.michalkiewicz.mealplannerclient.view.settings

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_edit_height.*
import piotr.michalkiewicz.mealplannerclient.R
import piotr.michalkiewicz.mealplannerclient.user.model.UserAccount
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues

class EditHeightActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_height)

        setOnClicKListeners()
    }

    private fun setOnClicKListeners(){
        confirmWeightBtn.setOnClickListener {
            setNewHeightAndFinish()
        }
        cancelWeightBtn.setOnClickListener {
            finish()
        }
    }

    private fun setNewHeightAndFinish(){
        if(checkInput()) {
            val userData = getDataFromIntent()
            userData?.userSettings?.nutritionProfileSettings?.height = heightET.text.toString().toInt()
            Log.d(ConstantValues.TAG, "EditHeightActivity::height = " + userData?.userSettings?.nutritionProfileSettings?.height)
            setDataForParentActivity(userData)
            finish()
        }
        else{
            Toast.makeText(this, R.string.height_out_of_range, Toast.LENGTH_SHORT).show()
        }
    }

    private fun getDataFromIntent(): UserAccount?{
        return intent.getSerializableExtra(ConstantValues.SETTINGS_DATA) as? UserAccount
    }

    private fun setDataForParentActivity(data : UserAccount?){
        val intent = Intent()
        intent.putExtra(ConstantValues.SETTINGS_DATA, data)
        setResult(SettingsActivity.RESULT_OK, intent)
    }

    private fun checkInput(): Boolean{
        if(heightET.text.toString().toInt() in 100..300) return true
        return false
    }
}