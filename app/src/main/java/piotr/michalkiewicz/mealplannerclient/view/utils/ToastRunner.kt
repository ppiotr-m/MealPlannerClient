package piotr.michalkiewicz.mealplannerclient.view.utils

import android.os.Looper
import android.widget.Toast
import piotr.michalkiewicz.mealplannerclient.view.MainActivity

class ToastRunner {

    companion object {
        fun runLongToast(message: Int) {
            val thread = Thread {
                Looper.prepare()
                Toast.makeText(
                    MainActivity.getMainContext(),
                    message,
                    Toast.LENGTH_LONG
                ).show()
                Looper.loop()
            }
            thread.start()
        }

        fun runLongToast(message: String) {
            val thread = Thread {
                Looper.prepare()
                Toast.makeText(
                    MainActivity.getMainContext(),
                    message,
                    Toast.LENGTH_LONG
                ).show()
                Looper.loop()
            }
            thread.start()
        }

        fun runLShortToast(message: Int) {
            val thread = Thread {
                Looper.prepare()
                Toast.makeText(
                    MainActivity.getMainContext(),
                    message,
                    Toast.LENGTH_SHORT
                ).show()
                Looper.loop()
            }
            thread.start()
        }

        fun runLShortToast(message: String) {
            val thread = Thread {
                Looper.prepare()
                Toast.makeText(
                    MainActivity.getMainContext(),
                    message,
                    Toast.LENGTH_SHORT
                ).show()
                Looper.loop()
            }
            thread.start()
        }
    }
}