package piotr.michalkiewicz.mealplannerclient.nutrition.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import piotr.michalkiewicz.mealplannerclient.R

class NutritionListItemDialog(
    private val itemPosition: Int,
    val title: String,
    private val listener: NutritionDialogListener
) :
    DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater

            builder.setView(inflater.inflate(R.layout.nutrition_list_item_dialog, null))
                .setPositiveButton(
                    R.string.ok
                ) { _, _ ->
                    listener.onOkClicked()
                }
                .setNegativeButton(
                    R.string.cancel
                ) { _, _ ->
                    dialog?.cancel()
                }
                .setNeutralButton(R.string.delete_nutrition_item) { _, _ ->
                    listener.onDeleteClicked(itemPosition)
                }
                .setTitle(title)
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    companion object {
        const val TAG = "NutritionListItemDialog"
    }

    interface NutritionDialogListener {
        fun onDeleteClicked(itemPosition: Int)
        fun onOkClicked()
    }
}