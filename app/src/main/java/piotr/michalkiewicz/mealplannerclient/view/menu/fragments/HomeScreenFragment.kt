package piotr.michalkiewicz.mealplannerclient.view.menu.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import piotr.michalkiewicz.mealplannerclient.R
import piotr.michalkiewicz.mealplannerclient.nutrition.model.AgeNutrientRecommendations
import piotr.michalkiewicz.mealplannerclient.utils.MealTimeDatabase
import piotr.michalkiewicz.mealplannerclient.view.MainActivity
import java.io.BufferedReader

class HomeScreenFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        (activity as HomeScreenStartListener).onHomeScreenStarted()

        return inflater.inflate(R.layout.fragment_home_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        saveRecommendedToRoom(getRecommendationsFromAssets())
    }

    private fun getRecommendationsFromAssets(): List<AgeNutrientRecommendations> {
        val inputStream =
            MainActivity.getMainContext().assets.open("recommended_nutrition_intake.json")
        val reader = BufferedReader(inputStream.reader())
        val content = StringBuilder()
        try {
            var line = reader.readLine()
            while (line != null) {
                content.append(line)
                line = reader.readLine()
            }
        } finally {
            reader.close()
        }

        return Gson().fromJson(
            content.toString(),
            ArrayList<AgeNutrientRecommendations>().javaClass
        )
    }

    private fun saveRecommendedToRoom(listOfRecommended: List<AgeNutrientRecommendations>) {
        val dao = MealTimeDatabase.getInstance(requireContext()).nutritionDao()
        GlobalScope.launch {
            dao.insertRecommendations(listOfRecommended)
        }

    }

    interface HomeScreenStartListener {
        fun onHomeScreenStarted()
    }
}