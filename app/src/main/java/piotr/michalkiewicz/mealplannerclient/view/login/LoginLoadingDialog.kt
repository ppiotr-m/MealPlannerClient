package piotr.michalkiewicz.mealplannerclient.view.login

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import piotr.michalkiewicz.mealplannerclient.R
import piotr.michalkiewicz.mealplannerclient.view.MainActivity

class LoginLoadingDialog {

    private lateinit var alertDialog: AlertDialog
    private val context = MainActivity.getMainContext()

    fun startLoadingDialog() {
        val builder = AlertDialog.Builder(context)
        val inflater: LayoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        builder.setView(inflater.inflate(R.layout.loading_dialog, null))
        builder.setCancelable(false)

        alertDialog = builder.create()
        alertDialog.show()
    }

    fun dismissDialog() {
        alertDialog.dismiss()
    }
}