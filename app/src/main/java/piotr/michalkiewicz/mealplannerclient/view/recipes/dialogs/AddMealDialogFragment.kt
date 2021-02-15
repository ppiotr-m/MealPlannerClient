package piotr.michalkiewicz.mealplannerclient.view.recipes.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import piotr.michalkiewicz.mealplannerclient.R

class AddMealDialogFragment : DialogFragment() {

    companion object {
        private const val BUNDLE_KEY_MESSAGE = "Add to:"    //  Change to R.string...

        private fun newInstance() = AddMealDialogFragment()

        private fun newInstance(message: String): AddMealDialogFragment {
            return newInstance().apply {
                arguments = bundleOf(
                    Pair(BUNDLE_KEY_MESSAGE, message)
                )
            }
        }

        fun show(message: String, fragmentManager: FragmentManager, tag: String) {
            newInstance(message).run {
                show(fragmentManager, tag)
            }
        }
    }

    lateinit var message: String

    private val viewModel: AddMealDialogViewModel by viewModels({ requireActivity() })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireArguments().run {
            message = getString(BUNDLE_KEY_MESSAGE)!!
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireActivity())
            .setMessage(message)
            .setPositiveButton(resources.getString(R.string.add_to_eaten_today)) { _, _ ->
                viewModel.state.value = DialogState.Ok(this@AddMealDialogFragment)
            }
            .create()
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        viewModel.state.value = DialogState.Cancel(this@AddMealDialogFragment)
    }
}