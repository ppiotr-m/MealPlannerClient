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
import piotr.michalkiewicz.mealplannerclient.nutrition.model.DailyEatenFoods
import piotr.michalkiewicz.mealplannerclient.nutrition.model.EatableItem
import piotr.michalkiewicz.mealplannerclient.nutrition.utils.ConstantValues.Companion.ENERGY
import piotr.michalkiewicz.mealplannerclient.nutrition.viewmodel.NutritionSharedViewModel
import piotr.michalkiewicz.mealplannerclient.recipes.RecipeServiceGenerator
import piotr.michalkiewicz.mealplannerclient.utils.Resource
import java.time.LocalDate
import java.util.*

class NutritionScreenFragment : Fragment() {

    private val pagesCount = 3
    private lateinit var nutritionSharedViewModel: NutritionSharedViewModel
    private lateinit var binding: FragmentNutritionScreenBinding
    private val mealsListViewAdapter = NutritionMealsListViewAdapter(LinkedList())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNutritionScreenBinding.inflate(inflater)

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner

        init()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun init() {
        initViewModel()
        setupObservers()

        nutritionScreenViewPager.adapter = ScreenSlidePagerAdapter(this)
        TabLayoutMediator(nutritionTabLayout, nutritionScreenViewPager) { _, _ -> }.attach()
        initTopTabLayout()
    }

    private fun initViewModel() {
        nutritionSharedViewModel = ViewModelProvider(
            requireActivity(),
            Injection.provideGeneralViewModelFactory()
        ).get(NutritionSharedViewModel::class.java)
    }

    private fun setupObservers() {
        nutritionSharedViewModel.uiModelLiveData.observe(viewLifecycleOwner, {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    loadingTempTV.visibility = View.INVISIBLE
                    nutritionDataContainerLinearLayout.visibility = View.VISIBLE
                    nutritionMealsListView.adapter =
                        NutritionMealsListViewAdapter(it.data!!.nutritionDailyData.eatenFoods)
                }

                Resource.Status.ERROR -> {  //  TODO Here an error is signal that there is no data on server for this date, consider handling it differently
                    loadingTempTV.visibility = View.INVISIBLE
                    nutritionDataContainerLinearLayout.visibility = View.VISIBLE
                }

                Resource.Status.LOADING -> {
                    loadingTempTV.visibility = View.VISIBLE
                    nutritionDataContainerLinearLayout.visibility = View.INVISIBLE
                    nutritionMealsListView.adapter = null
                }
            }
        })

        binding.viewModel = nutritionSharedViewModel
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun saveNutritionToUser(eatenFoods: List<EatableItem>?) {
        GlobalScope.launch {
            val api = RecipeServiceGenerator().recipeAPI
            val recipe = api.getRecipeForIdCoroutine("5fc1693a7907e07a453ddf4e")
            val recipe2 = api.getRecipeForIdCoroutine("5fc166fc7907e07a453ddf36")

            val eatenRecipes = LinkedList<EatableItem>()

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
            service.nutritionAPI.saveFoodsForDate(
                DailyEatenFoods(LocalDate.now().toString(), eatenRecipes)
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

    private inner class NutritionMealsListViewAdapter(val data: List<EatableItem>) :    //  TODO Should be var
        BaseAdapter() {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            if (convertView == null) {
                val listItem =
                    layoutInflater.inflate(R.layout.nutrition_meals_list_item, parent, false)
                listItem.findViewById<TextView>(R.id.mealNameTV).text = getItem(position).name
                listItem.findViewById<TextView>(R.id.mealAmountTV).text = getItem(position).amount
                listItem.findViewById<TextView>(R.id.mealUnitTV).text = getItem(position).unit
                getItem(position).foodNutrientsSummary.associateBy { it.nutrient.name }[ENERGY]!!.amount.toString()

                return listItem
            }
            return convertView
        }

        override fun getItem(position: Int): EatableItem {
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