package piotr.michalkiewicz.mealplannerclient.view.settings

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.widget.RadioButton
import androidx.activity.result.ActivityResultCaller
import androidx.activity.result.contract.ActivityResultContract
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.mealplannerclient.R
import kotlinx.android.synthetic.main.activity_settings.*
import piotr.michalkiewicz.mealplannerclient.user.model.UserAccount
import piotr.michalkiewicz.mealplannerclient.user.model.UserSettings

class SettingsActivity : AppCompatActivity(), piotr.michalkiewicz.mealplannerclient.view.utils.InitializableView<UserAccount>, ActivityResultCaller {

    private val presenter = piotr.michalkiewicz.mealplannerclient.view.settings.presenters.SettingsActivityPresenter(this)
    private val MALE = "Male"
    private val FEMALE = "Female"
    private val ABSENT_DATA = "N/A"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        init()
    }

    override fun onResume() {
        super.onResume()
        initWithData(presenter.data)
    }

    override fun onPause() {
        Log.i(ContentValues.TAG, "onPause")
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

    /*
    private fun initAllergiesChipGroup(userSettings: UserSettings?){
        var childViewCounter = 0
        userSettings?.allergies?.forEach {
            val chipGroup = layoutInflater.inflate(R.layout.chip_element_layout, allergiesChipGroup) as ChipGroup
            val chip = chipGroup.getChildAt(childViewCounter) as Chip
            chip.text = it
            chip.setOnCloseIconClickListener {
                allergiesChipGroup.removeView(it)
                // TODO implement storing in data object
            }
            childViewCounter +=1
        }
        val chipGroup = layoutInflater.inflate(R.layout.add_chip_element_layout, allergiesChipGroup) as ChipGroup
        (chipGroup.getChildAt(childViewCounter) as Chip).text = resources.getString(R.string.add_chip)
        chipGroup.getChildAt(childViewCounter).setOnClickListener {
            addAllergy()
        }
    }
     */

    private fun initAvoidedIngredientsChipGroup(userSettings: UserSettings?){
        var childViewCounter = 0
        userSettings?.unlikeIngredients?.forEach {
            val chipGroup = layoutInflater.inflate(R.layout.chip_element_layout, avoidedIngredientsChipGroup) as ChipGroup
            (chipGroup.getChildAt(childViewCounter) as Chip).text = it
            childViewCounter +=1
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
        startLauncherForActivityResult(SettingsActivityContract(EditAvoidedIngredientsActivity::class))
    }

    override fun initWithData(data: UserAccount?) {
        emailTV.text = data?.email
        passwordTV.text = data?.password
        locationTV.text = data?.location
        if (data?.userSettings?.nutritionProfileSettings?.height == null) {
            heightTV.text = ABSENT_DATA
        } else {
            heightTV.text = data.userSettings?.nutritionProfileSettings?.height.toString()
        }
        if (data?.userSettings?.nutritionProfileSettings?.weight == null) {
            weightTV.text = ABSENT_DATA
        }
        else {
            weightTV.text = data.userSettings?.nutritionProfileSettings?.weight.toString()
        }
        usernameTV.text = data?.username
        initAvoidedIngredientsChipGroup(data?.userSettings)
  //      initAllergiesChipGroup(data?.userSettings)
        initSexSelectionRadioGroup(data)
    }

    companion object {
        @JvmStatic val RESULT_OK : Int = 1033
    }
}
