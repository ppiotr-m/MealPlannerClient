package piotr.michalkiewicz.mealplannerclient.view.recipes

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_cooking_mode.*
import piotr.michalkiewicz.mealplannerclient.R
import piotr.michalkiewicz.mealplannerclient.recipes.model.InstructionStep
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues
import java.util.*

class CookingModeActivity : AppCompatActivity() {

    private lateinit var data: ArrayList<InstructionStep>
    private var currentStepIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cooking_mode)

        init()
    }

    private fun init() {
        data = intent.getSerializableExtra(ConstantValues.COOKING_STEPS_DATA) as ArrayList<InstructionStep>

        setNextBtnClickListener()
        initView(data[0])
    }

    private fun initView(data: InstructionStep) {
        cookingStepNrTV.text = data.stepNumber.toString()
        cookingStepTV.text = data.stepInstruction
        currentStepIndex += 1
    }

    private fun setNextBtnClickListener() {
        nextStepBtn.setOnClickListener {
            if (currentStepIndex + 1 == data.size) {
                initView(data[currentStepIndex])
                showLastStep()
            } else {
                initView(data[currentStepIndex])
            }
        }
    }

    private fun showLastStep() {
        nextStepBtn.text = resources.getString(R.string.finish_cooking)

        nextStepBtn.setOnClickListener {
            finish()
        }
    }
}