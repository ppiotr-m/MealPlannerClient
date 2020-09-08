package piotr.michalkiewicz.mealplannerclient.view.recipes

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
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
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues.Companion.TAG
import java.util.*

class CookingModeActivity : AppCompatActivity(), RecognitionListener {

    private lateinit var data: ArrayList<InstructionStep>
    private var currentStepIndex = 0
    private var micImageBlinkOn = true
    private lateinit var speechRecognizer: SpeechRecognizer
    private var isActivated: Boolean = false
    private val activationKeyword: String = "next"

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
        initSpeechRecognizer()
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
            startRecognition()
        }
    }

    private fun initSpeechRecognizer() {
        if (SpeechRecognizer.isRecognitionAvailable(this)) {
            speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this)
            speechRecognizer.setRecognitionListener(this)
        } else {
            Log.i(TAG, "Speech recognition not available")
        }
    }

    private fun startRecognition() {
        speechRecognizer.startListening(createRecognizerIntent())
    }

    private fun stopRecognition() {
        speechRecognizer.stopListening()
        speechRecognizer.destroy()
    }

    override fun onPartialResults(partialResults: Bundle) {
        Log.i(TAG, "CORAZ MNIEJ BLISKO")
        val matches = partialResults.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
        if (matches != null) {
            // Handle partial matches
        }
    }

    override fun onResults(results: Bundle) {
        Log.i(TAG, "BLISKO BLISKO")
        val matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
        val scores = results.getFloatArray(SpeechRecognizer.CONFIDENCE_SCORES)
        if (matches != null) {
            if (isActivated) {
                isActivated = false
                stopRecognition()
            } else {
                matches.firstOrNull { it.contains(other = activationKeyword, ignoreCase = true) }
                        ?.let {
                            Log.i(TAG, "!!!!!!!!SIALALALALALA O KURWA OBSIADŁO!!!!!!!!")
                            isActivated = true
                        }
                startRecognition()
            }
        }
    }

    override fun onReadyForSpeech(p0: Bundle?) {
        Log.i(TAG, "onBufferReceived()")
    }

    override fun onBeginningOfSpeech() {
        Log.i(TAG, "OJ DALEKO")
    }

    override fun onRmsChanged(p0: Float) {
        Log.i(TAG, "onRmsChanged(): " + p0)
    }

    override fun onBufferReceived(p0: ByteArray?) {
        Log.i(TAG, "onBufferReceived() :" + p0?.size)
    }

    override fun onEndOfSpeech() {
        TODO("Not yet implemented")
    }

    override fun onError(p0: Int) {
        Log.i(TAG, "SpeechRecognizer error code: " + p0)
    }

    override fun onEvent(p0: Int, p1: Bundle?) {
        TODO("Not yet implemented")
    }

    private fun createRecognizerIntent(): Intent {
        return Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 3)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
            putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                putExtra(RecognizerIntent.EXTRA_PREFER_OFFLINE, true)
            }
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
                micImgView.setOnClickListener {}
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