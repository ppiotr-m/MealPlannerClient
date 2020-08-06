package piotr.michalkiewicz.mealplannerclient.view.activities.dialogs

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.height_setting_dialog.*
import piotr.michalkiewicz.mealplannerclient.R

class HeightPickerDialog(){
    var dialog : AlertDialog?= null

    constructor(activity: Activity, currentHeight: Int ) : this() {
        dialog = AlertDialog.Builder(activity).create()
        val dialogView = activity.layoutInflater.inflate(R.layout.height_setting_dialog, null)

   //     activity.heightDialogET.text = currentHeight.toString()

    }

}