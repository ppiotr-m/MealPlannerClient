package piotr.michalkiewicz.mealplannerclient.view.menu.fragments.nutrition

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_nutrition_screen.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import piotr.michalkiewicz.mealplannerclient.R
import piotr.michalkiewicz.mealplannerclient.databinding.FragmentNutritionScreenBinding
import piotr.michalkiewicz.mealplannerclient.nutrition.Injection
import piotr.michalkiewicz.mealplannerclient.nutrition.NutritionServiceGenerator
import piotr.michalkiewicz.mealplannerclient.nutrition.model.EatableItem
import piotr.michalkiewicz.mealplannerclient.nutrition.model.NutritionDailyData
import piotr.michalkiewicz.mealplannerclient.nutrition.model.NutritionUiModel
import piotr.michalkiewicz.mealplannerclient.nutrition.viewmodel.NutritionScreenViewModel
import piotr.michalkiewicz.mealplannerclient.recipes.RecipeServiceGenerator
import piotr.michalkiewicz.mealplannerclient.recipes.model.RecipeIngredient
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues.Companion.TAG
import java.time.LocalDate
import java.util.*

class NutritionScreenFragment : Fragment() {

    private val pagesCount = 3
    private var nutritionUiModel: NutritionUiModel? = null
    private lateinit var nutritionScreenViewModel: NutritionScreenViewModel
    private lateinit var binding: FragmentNutritionScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNutritionScreenBinding.inflate(inflater)

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        nutritionScreenViewModel = ViewModelProvider(
            this,
            Injection.provideViewModelFactory()
        ).get(NutritionScreenViewModel::class.java)

        init()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun init() {
        nutritionScreenViewPager.adapter = ScreenSlidePagerAdapter(this)
        nutritionMealsListView.adapter =
            NutritionMealsListViewAdapter(LinkedList<RecipeIngredient>())
        TabLayoutMediator(nutritionTabLayout, nutritionScreenViewPager) { _, _ -> }.attach()
        initTopTabLayout()

        saveNutritionToUser(null)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun saveNutritionToUser(eatenFoods: List<EatableItem>?) {
        GlobalScope.launch {
            val api = RecipeServiceGenerator().recipeAPI
            val recipe = api.getRecipeForIdCoroutine("5fc1693a7907e07a453ddf4e")
            val recipe2 = api.getRecipeForIdCoroutine("5fc166fc7907e07a453ddf36")

            val eatenRecipes = LinkedList<EatableItem>()

            Log.d(TAG, "Recipe1 name: " + recipe.name)

            if (recipe.foodNutrientsSummary != null && recipe2.foodNutrientsSummary != null) {
                eatenRecipes.add(
                    EatableItem(
                        recipe.name,
                        recipe.foodNutrientsSummary,
                        "1",
                        "portion"
                    )
                )
                eatenRecipes.add(
                    EatableItem(
                        recipe2.name,
                        recipe2.foodNutrientsSummary,
                        "1",
                        "portion"
                    )
                )
            }

            val service = NutritionServiceGenerator()
            service.nutritionAPI.saveNutritionForDate(
                NutritionDailyData(LocalDate.now().toString(), eatenRecipes)
            )
        }
    }

    private fun initTopTabLayout() {
        TabLayoutMediator(nutritionTopTabLayout, nutritionScreenViewPager) { _, _ -> }.attach()

        nutritionTopTabLayout.getTabAt(0)?.text = getString(R.string.general)
        nutritionTopTabLayout.getTabAt(1)?.text = getString(R.string.vitamin_targets)
        nutritionTopTabLayout.getTabAt(2)?.text = getString(R.string.mineral_targets)
    }

    private inner class ScreenSlidePagerAdapter(fa: Fragment) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = pagesCount

        override fun createFragment(position: Int): Fragment {
            when (position) {
                0 -> return NutritionGeneralTabFragment()
                1 -> return NutritionVitaminTabFragment()
                2 -> return NutritionMineralsTabFragment()
            }
            return Fragment()
        }
    }

    private inner class NutritionMealsListViewAdapter(val data: List<RecipeIngredient>) :
        BaseAdapter() {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            if (convertView == null) {
                val listItem =
                    layoutInflater.inflate(R.layout.nutrition_meals_list_item, parent, false)
                listItem.findViewById<TextView>(R.id.mealNameTV).text = getItem(position).name
                listItem.findViewById<TextView>(R.id.mealAmountTV).text = getItem(position).amount
                listItem.findViewById<TextView>(R.id.mealUnitTV).text = getItem(position).unit
                val mockKcalValue = (getItem(position).amount.toFloat() * 4).toString()
                if (mockKcalValue.length > 7) {
                    listItem.findViewById<TextView>(R.id.kcalValueTV).text =
                        mockKcalValue.subSequence(0, 7)
                } else {
                    listItem.findViewById<TextView>(R.id.kcalValueTV).text = mockKcalValue
                }
                return listItem
            }
            return convertView
        }

        override fun getItem(position: Int): RecipeIngredient {
            return data[position]
        }

        override fun getItemId(position: Int): Long {
            return -1
        }

        override fun getCount(): Int {
            return data.size
        }

    }
}