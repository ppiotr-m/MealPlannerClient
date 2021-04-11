package piotr.michalkiewicz.mealplannerclient.view.userPreferences.ui.utility

import androidx.test.espresso.IdlingRegistry
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import piotr.michalkiewicz.mealplannerclient.utils.EspressoIdlingResource

class InitMyPreferencesRule : TestRule {
    override fun apply(base: Statement?, description: Description?): MyStatement = MyStatement(base)

    class MyStatement(private val base: Statement?) : Statement() {
        @Throws(Throwable::class)
        override fun evaluate() {
            //something we do before the test
            IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
            try {
                base?.evaluate() // This executes your tests
            } finally {
                IdlingRegistry.getInstance()
                    .unregister(EspressoIdlingResource.countingIdlingResource)
            }
        }
    }
}
