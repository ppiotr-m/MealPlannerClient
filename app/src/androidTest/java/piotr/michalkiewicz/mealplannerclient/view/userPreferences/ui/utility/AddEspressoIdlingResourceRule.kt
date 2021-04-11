package piotr.michalkiewicz.mealplannerclient.view.userPreferences.ui.utility

import androidx.appcompat.app.AppCompatActivity
import androidx.test.core.app.ApplicationProvider
import dagger.hilt.android.testing.HiltTestApplication
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues
import piotr.michalkiewicz.mealplannerclient.view.MainActivity

class AddEspressoIdlingResourceRule : TestRule {
    override fun apply(base: Statement?, description: Description?): MyStatement = MyStatement(base)

    class MyStatement(private val base: Statement?) : Statement() {
        @Throws(Throwable::class)
        override fun evaluate() {
            //something we do before the test
            MainActivity.MY_PREFERENCES =
                ApplicationProvider.getApplicationContext<HiltTestApplication>()
                    .getSharedPreferences(
                        ConstantValues.MY_PREFERENCE_NAME,
                        AppCompatActivity.MODE_PRIVATE
                    )
            try {
                base?.evaluate() // This executes your tests
            } finally {
                // Add something you do after test
            }
        }
    }
}