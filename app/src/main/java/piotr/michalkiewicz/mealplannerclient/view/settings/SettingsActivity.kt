package piotr.michalkiewicz.mealplannerclient.view.settings

import android.os.Bundle
import android.util.Log
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.result.ActivityResultCaller
import androidx.activity.result.contract.ActivityResultContract
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import kotlinx.android.synthetic.main.activity_settings.*
import piotr.michalkiewicz.mealplannerclient.R
import piotr.michalkiewicz.mealplannerclient.user.model.UserAccount
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues.Companion.TAG
import piotr.michalkiewicz.mealplannerclient.view.settings.presenters.SettingsActivityPresenter
import piotr.michalkiewicz.mealplannerclient.view.utils.InitializableView

class SettingsActivity : AppCompatActivity(), InitializableView<UserAccount>, ActivityResultCaller {

    private val presenter = SettingsActivityPresenter(this)
    private val MALE = "Male"
    private val FEMALE = "Female"
    private val ABSENT_DATA = "N/A"
    private var firstOnResumeExection = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        init()
    }

    override fun onResume() {
        super.onResume()
        if(firstOnResumeExection) {
            firstOnResumeExection = false
            return
        }
        initWithData(presenter.data)

    }

    override fun onPause() {
        super.onPause()
        presenter.saveSettingsServerSide()
    }

    private fun init(){
        setOnClickListeners()
        presenter.initSettingsViewWithData()
    }

    private fun setOnClickListeners() {
        editPasswordBtn.setOnClickListener{
            startLauncherForActivityResult(SettingsActivityContract(EditPasswordActivity::class))
        }
        editLocationBtn.setOnClickListener{
            startLauncherForActivityResult(SettingsActivityContract(EditLocationActivity::class))
        }
        sexSelectionRadioGroup.setOnCheckedChangeListener { _, i ->
            if(i == R.id.maleRadioBtn){
                if(findViewById<RadioButton>(i).isChecked){
                    presenter.data?.userSettings?.sex = MALE
                }
            }
            else{
                if(findViewById<RadioButton>(i).isChecked){
                    presenter.data?.userSettings?.sex = FEMALE
                }
            }
        }
        editDietBtn.setOnClickListener {
            startLauncherForActivityResult(SettingsActivityContract(EditDietActivity::class))
        }
        editHeightBtn.setOnClickListener {
            startLauncherForActivityResult(SettingsActivityContract(EditHeightActivity::class))
        }
        editWeightBtn.setOnClickListener {
            startLauncherForActivityResult(SettingsActivityContract(EditWeightActivity::class))
        }
    }

    private fun startLauncherForActivityResult(contract: ActivityResultContract<UserAccount, UserAccount>){
        val launcher = registerForActivityResult(contract){
            if(it!=null) {
                presenter.data = it
            }
        }
        launcher.launch(presenter.data)
    }

    private fun initSexSelectionRadioGroup(userAccount: UserAccount?) {
        if (userAccount?.userSettings?.sex == null) return
        if (userAccount.userSettings?.sex == MALE) sexSelectionRadioGroup.check(R.id.maleRadioBtn)
        else sexSelectionRadioGroup.check(R.id.femaleRadioBtn)
    }

    private fun initAllergiesChipGroup(userAccount: UserAccount?) {
        var childViewCounter = 0
        allergiesChipGroup.removeAllViews()
        userAccount?.userSettings?.userPreference?.allergies?.forEach {
            val chipGroup = layoutInflater.inflate(R.layout.chip_element_layout, allergiesChipGroup) as ChipGroup
            val chip = chipGroup.getChildAt(childViewCounter) as Chip
            chip.text = it
            chip.setOnCloseIconClickListener {
                allergiesChipGroup.removeView(it)
                // TODO implement storing in data object
            }
            childViewCounter += 1
        }
        val chipGroup = layoutInflater.inflate(R.layout.add_chip_element_layout, allergiesChipGroup) as ChipGroup
        (chipGroup.getChildAt(childViewCounter) as Chip).text = resources.getString(R.string.add_chip)
        chipGroup.getChildAt(childViewCounter).setOnClickListener {
            addAllergy()
        }
    }

    private fun initAvoidedIngredientsChipGroup(userAccount: UserAccount?) {
        var childViewCounter = 0
        avoidedIngredientsChipGroup.removeAllViews()
        userAccount?.userSettings?.userPreference?.unlikeIngredients?.forEach {
            val chipGroup = layoutInflater.inflate(R.layout.chip_element_layout, avoidedIngredientsChipGroup) as ChipGroup
            (chipGroup.getChildAt(childViewCounter) as Chip).text = it
            childViewCounter += 1
        }
        val chipGroup = layoutInflater.inflate(R.layout.add_chip_element_layout, avoidedIngredientsChipGroup) as ChipGroup
        (chipGroup.getChildAt(childViewCounter) as Chip).text = resources.getString(R.string.add_chip)
        chipGroup.getChildAt(childViewCounter).setOnClickListener {
            addAvoidedIngredient()
        }
    }

    private fun addAllergy(){
        startLauncherForActivityResult(SettingsActivityContract(EditAllergiesActivity::class))
    }

    private fun addAvoidedIngredient(){
        startLauncherForActivityResult(SettingsActivityContract(EditDislikedIngredientsActivity::class))
    }

    private fun showDataNullToast(){
        Toast.makeText(this, R.string.no_settings_data_received, Toast.LENGTH_LONG).show()
    }

    override fun initWithData(data: UserAccount?) {
        if(data == null){
            showDataNullToast()
            finish()
            return
        }
        emailTV.text = data?.email
        passwordTV.text = "--------"
        locationTV.text = data?.userSettings?.location
        dietTV.text = data?.userSettings?.userPreference?.diet
        if (data?.userSettings?.nutritionProfileSettings?.height == null) {
            heightTV.text = ABSENT_DATA
        } else {
            heightTV.text = data.userSettings?.nutritionProfileSettings?.height.toString()
        }
        if (data?.userSettings?.nutritionProfileSettings?.weight == null) {
            weightTV.text = ABSENT_DATA
        } else {
            weightTV.text = data.userSettings?.nutritionProfileSettings?.weight.toString()
        }
        usernameTV.text = data?.username
        initAvoidedIngredientsChipGroup(data)
        initAllergiesChipGroup(data)
        initSexSelectionRadioGroup(data)
    }

    companion object {
        @JvmStatic val RESULT_OK : Int = 1033
    }
}
