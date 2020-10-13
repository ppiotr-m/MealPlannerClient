package piotr.michalkiewicz.mealplannerclient.view.menu.fragments.nutrition

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_nutrition_screen.*
import piotr.michalkiewicz.mealplannerclient.R
import piotr.michalkiewicz.mealplannerclient.recipes.model.RecipeIngredient


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

    private fun init() {
        nutritionScreenViewPager.adapter = ScreenSlidePagerAdapter(this)
        nutritionMealsListView.adapter = NutritionMealsListViewAdapter(createMockMealList())
        TabLayoutMediator(nutritionTabLayout, nutritionScreenViewPager) { _, _ -> }.attach()
        initTopTabLayout()
    }

    private fun initTopTabLayout() {
        TabLayoutMediator(nutritionTopTabLayout, nutritionScreenViewPager) { _, _ -> }.attach()

        nutritionTopTabLayout.getTabAt(0)?.text = getString(R.string.general)
        nutritionTopTabLayout.getTabAt(1)?.text = getString(R.string.vitamin_targets)
        nutritionTopTabLayout.getTabAt(2)?.text = getString(R.string.mineral_targets)
    }

    private fun createMockMealList(): List<RecipeIngredient> {
        val mealList = ArrayList<RecipeIngredient>()

        mealList.add(RecipeIngredient("154.00", "g", "Fasola", "meat", "Fasola"))
        mealList.add(RecipeIngredient("432.43", "g", "Ogórki", "meat", "Ogórki"))
        mealList.add(RecipeIngredient("1", "serving", "Barszcz po ukraińsku", "meat", "Barszcz po ukraińsku"))
        mealList.add(RecipeIngredient("1", "serving", "Ryż z kurczakiem i warzywami", "meat", "Ryż z kurczakiem i warzywami"))
        mealList.add(RecipeIngredient("250.0", "ml", "Mleko", "meat", "Mleko"))
        mealList.add(RecipeIngredient("2", "cup", "Pomidory z puszki", "meat", "Pomidory z puszki"))
        mealList.add(RecipeIngredient("3", "medium banana", "Banan", "meat", "Banan"))
        mealList.add(RecipeIngredient("560", "g", "Ziemniaki", "meat", "Ziemniaki"))
        mealList.add(RecipeIngredient("67", "g", "Woda", "meat", "Woda"))
        mealList.add(RecipeIngredient("135", "g", "Śledzie", "meat", "Śledzie"))
        mealList.add(RecipeIngredient("3", "medium egg", "Jajka", "meat", "Jajka"))
        mealList.add(RecipeIngredient("5", "slice", "Ser gouda", "meat", "Ser gouda"))

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

    private inner class NutritionMealsListViewAdapter(val data: List<RecipeIngredient>) : BaseAdapter() {

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