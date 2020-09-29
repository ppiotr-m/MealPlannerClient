package piotr.michalkiewicz.mealplannerclient.view.settings

import android.app.Activity
import android.app.PendingIntent.getActivity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import piotr.michalkiewicz.mealplannerclient.user.model.UserAccount
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues
import piotr.michalkiewicz.mealplannerclient.view.MainActivity.Companion.RESULT_OK
import kotlin.reflect.KClass

class SettingsActivityContract: ActivityResultContract<UserAccount, UserAccount> {
    private var classObject: KClass<out Activity>? = null

    constructor(classObject: KClass<out Activity>): super(){
        this.classObject = classObject
    }

    override fun createIntent(context: Context, input: UserAccount?): Intent {
        return Intent(context, classObject?.java).apply{
            putExtra(ConstantValues.SETTINGS_DATA, input)
        }
    }

    override fun parseResult(resultCode: Int, intent: Intent?): UserAccount? {
        return if(RESULT_OK) intent?.getSerializableExtra(ConstantValues.SETTINGS_DATA) as UserAccount else
            null
    }
}