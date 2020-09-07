package piotr.michalkiewicz.mealplannerclient.view.settings

import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_edit_height.cancelWeightBtn
import kotlinx.android.synthetic.main.activity_edit_height.confirmWeightBtn
import kotlinx.android.synthetic.main.activity_edit_weight.*
import piotr.michalkiewicz.mealplannerclient.R

class EditWeightActivity : DataPassingActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_weight)

        setOnClickListeners()
    }

    private fun setOnClickListeners(){
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
            userData.userSettings.nutritionProfileSettings.weight = weightET.text.toString().toInt()
            setDataForParentActivity(userData)
            finish()
        }
        else{
            Toast.makeText(this, R.string.weight_out_of_range, Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkInput(): Boolean{
        if(weightET.text.toString().toInt() in 30..400) return true
        return false
    }
}