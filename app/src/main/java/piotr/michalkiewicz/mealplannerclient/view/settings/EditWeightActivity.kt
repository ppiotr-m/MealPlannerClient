package piotr.michalkiewicz.mealplannerclient.view.settings

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_edit_height.*
import kotlinx.android.synthetic.main.activity_edit_height.cancelWeightBtn
import kotlinx.android.synthetic.main.activity_edit_height.confirmWeightBtn
import kotlinx.android.synthetic.main.activity_edit_weight.*
import piotr.michalkiewicz.mealplannerclient.R
import piotr.michalkiewicz.mealplannerclient.user.model.UserAccount
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues

class EditWeightActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_weight)

        setOnClicKListeners()
    }

    private fun setOnClicKListeners(){
        confirmWeightBtn.setOnClickListener {
            setNewWeightAndFinish()
        }
        cancelWeightBtn.setOnClickListener {
            finish()
        }
    }

    private fun setNewWeightAndFinish(){
        if(checkInput()) {
            val userData = getDataFromIntent()
            userData?.userSettings?.nutritionProfileSettings?.weight = weightET.text.toString().toInt()
            setDataForParentActivity(userData)
            finish()
        }
        else{
            Toast.makeText(this, R.string.weight_out_of_range, Toast.LENGTH_SHORT).show()
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
        if(weightET.text.toString().toInt() in 30..400) return true
        return false
    }
}