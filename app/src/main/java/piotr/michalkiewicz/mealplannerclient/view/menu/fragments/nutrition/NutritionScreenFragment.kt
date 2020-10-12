package piotr.michalkiewicz.mealplannerclient.view.menu.fragments.nutrition

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_nutrition_screen.*
import piotr.michalkiewicz.mealplannerclient.R

class NutritionScreenFragment : Fragment() {

    private val pagesCount = 3

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_nutrition_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        nutritionScreenViewPager.adapter = ScreenSlidePagerAdapter(this)

        TabLayoutMediator(nutritionTabLayout, nutritionScreenViewPager) { _, _ -> }.attach()
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
}