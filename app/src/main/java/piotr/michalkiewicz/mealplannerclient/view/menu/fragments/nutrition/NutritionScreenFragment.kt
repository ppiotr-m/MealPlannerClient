package piotr.michalkiewicz.mealplannerclient.view.menu.fragments.nutrition

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_nutrition_screen.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import piotr.michalkiewicz.mealplannerclient.R
import piotr.michalkiewicz.mealplannerclient.nutrition.NutritionServiceGenerator
import piotr.michalkiewicz.mealplannerclient.recipes.RecipeServiceGenerator
import piotr.michalkiewicz.mealplannerclient.recipes.model.EatableItem
import piotr.michalkiewicz.mealplannerclient.recipes.model.MealTimeRecipe
import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList


class NutritionScreenFragment : Fragment() {

    private val pagesCount = 3

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_nutrition_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun init() {
        nutritionScreenViewPager.adapter = ScreenSlidePagerAdapter(this)
        nutritionMealsListView.adapter =
            NutritionMealsListViewAdapter(LinkedList<RecipeIngredient2>())
        TabLayoutMediator(nutritionTabLayout, nutritionScreenViewPager) { _, _ -> }.attach()
        initTopTabLayout()

        saveNutritionToUser(null)
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun saveNutritionToUser(eatenFoods: List<EatableItem>?) {
        GlobalScope.launch {
            val api = RecipeServiceGenerator().recipeAPI
            val recipe = api.getRecipeForId2("5e9ca4fc0f218f4d5b5b4d81")
            val recipe2 = api.getRecipeForId2("5fc166fc7907e07a453ddf36")

            val eatenRecipes = LinkedList<MealTimeRecipe>()
            eatenRecipes.add(recipe)
            eatenRecipes.add(recipe2)

            val service = NutritionServiceGenerator()
            service.nutritionAPI.saveNutritionForDate(
                NutritionDiaryData(
                    LocalDate.now().toString(),
                    LinkedList(),
                    eatenRecipes
                )
            )
        }
    }


    private fun initTopTabLayout() {
        TabLayoutMediator(nutritionTopTabLayout, nutritionScreenViewPager) { _, _ -> }.attach()

        nutritionTopTabLayout.getTabAt(0)?.text = getString(R.string.general)
        nutritionTopTabLayout.getTabAt(1)?.text = getString(R.string.vitamin_targets)
        nutritionTopTabLayout.getTabAt(2)?.text = getString(R.string.mineral_targets)
    }

    private fun createMockMealList(): List<RecipeIngredient2> {
        val mealList = ArrayList<RecipeIngredient2>()

        mealList.add(
            RecipeIngredient2(
                "154.00",
                "g",
                "Fasola",
                "meat",
                "Fasola"
            )
        )
        mealList.add(
            RecipeIngredient2(
                "432.43",
                "g",
                "Ogórki",
                "meat",
                "Ogórki"
            )
        )
        mealList.add(
            RecipeIngredient2(
                "1",
                "serving",
                "Barszcz po ukraińsku",
                "meat",
                "Barszcz po ukraińsku"
            )
        )
        mealList.add(
            RecipeIngredient2(
                "1",
                "serving",
                "Ryż z kurczakiem i warzywami",
                "meat",
                "Ryż z kurczakiem i warzywami"
            )
        )
        mealList.add(
            RecipeIngredient2(
                "250.0",
                "ml",
                "Mleko",
                "meat",
                "Mleko"
            )
        )
        mealList.add(
            RecipeIngredient2(
                "2",
                "cup",
                "Pomidory z puszki",
                "meat",
                "Pomidory z puszki"
            )
        )
        mealList.add(
            RecipeIngredient2(
                "3",
                "medium banana",
                "Banan",
                "meat",
                "Banan"
            )
        )
        mealList.add(
            RecipeIngredient2(
                "560",
                "g",
                "Ziemniaki",
                "meat",
                "Ziemniaki"
            )
        )
        mealList.add(
            RecipeIngredient2(
                "67",
                "g",
                "Woda",
                "meat",
                "Woda"
            )
        )
        mealList.add(
            RecipeIngredient2(
                "135",
                "g",
                "Śledzie",
                "meat",
                "Śledzie"
            )
        )
        mealList.add(
            RecipeIngredient2(
                "3",
                "medium egg",
                "Jajka",
                "meat",
                "Jajka"
            )
        )
        mealList.add(
            RecipeIngredient2(
                "5",
                "slice",
                "Ser gouda",
                "meat",
                "Ser gouda"
            )
        )

        return mealList
    }

    private inner class ScreenSlidePagerAdapter(fa: Fragment) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = pagesCount

        override fun createFragment(position: Int): Fragment {
            when (position) {
                0 -> return NutritionGeneralTabFragment()
                1 -> return NutritionVitaminTargetsTabFragment()
                2 -> return NutritionMineralsTabFragment()
            }
            return Fragment()
        }
    }

    private inner class NutritionMealsListViewAdapter(val data: List<RecipeIngredient2>) : BaseAdapter() {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            if (convertView == null) {
                val listItem = layoutInflater.inflate(R.layout.nutrition_meals_list_item, parent, false)
                listItem.findViewById<TextView>(R.id.mealNameTV).text = getItem(position).name
                listItem.findViewById<TextView>(R.id.mealAmountTV).text = getItem(position).amount
                listItem.findViewById<TextView>(R.id.mealUnitTV).text = getItem(position).unit
                val mockKcalValue = (getItem(position).amount.toFloat() * 4).toString()
                if (mockKcalValue.length > 7) {
                    listItem.findViewById<TextView>(R.id.kcalValueTV).text = mockKcalValue.subSequence(0, 7)
                } else {
                    listItem.findViewById<TextView>(R.id.kcalValueTV).text = mockKcalValue
                }
                return listItem
            }
            return convertView
        }

        override fun getItem(position: Int): RecipeIngredient2 {
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