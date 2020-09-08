package piotr.michalkiewicz.mealplannerclient.view.recipes

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.activity_cooking_mode.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import piotr.michalkiewicz.mealplannerclient.R
import piotr.michalkiewicz.mealplannerclient.recipes.model.InstructionStep
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues.Companion.RECORD_AUDIO_REQUEST_CODE
import java.util.*

class CookingModeActivity : AppCompatActivity() {

    private lateinit var data: ArrayList<InstructionStep>
    private var currentStepIndex = 0
    private var micImageBlinkOn = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cooking_mode)

        init()
    }

    private fun init() {
        data = intent.getSerializableExtra(ConstantValues.COOKING_STEPS_DATA) as ArrayList<InstructionStep>

        checkRecordAudioPermission()
        onClickListeners()
        initView(data[0])
    }

    private fun initView(data: InstructionStep) {
        cookingStepNrTV.text = data.stepNumber.toString()
        cookingStepTV.text = data.stepInstruction
    }

    private fun onClickListeners() {
        nextStepBtn.setOnClickListener {
            showNextStep()
        }
        previousStepBtn.setOnClickListener {
            showPreviousStep()
        }
        micImgView.setOnClickListener {
            blinkMicImg()
        }
    }

    private fun blinkMicImg() {
        lifecycleScope.launch {
            repeat(6) {
                delay(200L)
                changeMicImageForBlink()
            }
        }
    }

    private fun changeMicImageForBlink() {
        if (micImageBlinkOn) {
            micImgView.setImageDrawable(resources.getDrawable(R.drawable.ic_baseline_mic_140,
                    this@CookingModeActivity.theme))
            micImageBlinkOn = false
        } else {
            micImgView.setImageDrawable(resources.getDrawable(R.drawable.ic_mic_on_140,
                    this@CookingModeActivity.theme))
            micImageBlinkOn = true
        }
    }

    private fun showPreviousStep() {
        nextStepBtn.text = resources.getString(R.string.next_step)
        nextStepBtn.setOnClickListener {
            showNextStep()
        }

        currentStepIndex -= 1
        if (currentStepIndex == 0) {
            initView(data[currentStepIndex])
            previousStepBtn.visibility = View.INVISIBLE
        } else {
            initView(data[currentStepIndex])
        }
    }

    private fun showNextStep() {
        previousStepBtn.visibility = View.VISIBLE
        currentStepIndex += 1
        if (currentStepIndex == data.size - 1) {
            initView(data[currentStepIndex])
            showLastStep()
        } else {
            initView(data[currentStepIndex])
        }
    }

    private fun showLastStep() {
        nextStepBtn.text = resources.getString(R.string.finish_cooking)

        nextStepBtn.setOnClickListener {
            finish()
        }
    }

    private fun checkRecordAudioPermission() {
        if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.RECORD_AUDIO), RECORD_AUDIO_REQUEST_CODE)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == RECORD_AUDIO_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, R.string.voice_control_instruction, Toast.LENGTH_LONG).show()
                setMicIcon(true)
            } else {
                setMicIcon(false)
                Toast.makeText(this, R.string.voice_control_inactive, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun setMicIcon(enable: Boolean) {
        if (enable) {
            micImgView.setImageDrawable(resources.getDrawable(R.drawable.ic_baseline_mic_140, this.theme))
        } else {
            micImgView.setImageDrawable(resources.getDrawable(R.drawable.ic_baseline_mic_off_140, this.theme))
        }
    }
}