package piotr.michalkiewicz.mealplannerclient.view.activities.settings

import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import piotr.michalkiewicz.mealplannerclient.user.model.UserAccount
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues

class EditWeightActivityContract: ActivityResultContract<UserAccount, UserAccount>() {
    override fun createIntent(context: Context, input: UserAccount?): Intent {
        return Intent(context, EditWeightActivity::class.java).apply{
            putExtra(ConstantValues.SETTINGS_DATA, input)
        }
    }

    override fun parseResult(resultCode: Int, intent: Intent?): UserAccount? {
        return if(resultCode == SettingsActivity.RESULT_OK) intent?.getSerializableExtra(ConstantValues.SETTINGS_DATA) as UserAccount else
            null
    }
}