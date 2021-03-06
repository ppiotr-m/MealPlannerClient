package piotr.michalkiewicz.mealplannerclient.view.menu.fragments.nutrition

import android.content.Context
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
import kotlinx.android.synthetic.main.dialog_nutrition_add_product.*
import kotlinx.android.synthetic.main.fragment_nutrition_screen.*
import piotr.michalkiewicz.mealplannerclient.R
import piotr.michalkiewicz.mealplannerclient.databinding.FragmentNutritionScreenBinding
import piotr.michalkiewicz.mealplannerclient.nutrition.Injection
import piotr.michalkiewicz.mealplannerclient.nutrition.dialogs.NutritionAddEatenProductDialogFragment
import piotr.michalkiewicz.mealplannerclient.nutrition.dialogs.NutritionListItemDialog
import piotr.michalkiewicz.mealplannerclient.nutrition.model.EatableItem
import piotr.michalkiewicz.mealplannerclient.nutrition.utils.ConstantValues.Companion.ENERGY
import piotr.michalkiewicz.mealplannerclient.nutrition.viewmodel.NutritionSharedViewModel
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues.Companion.TAG
import piotr.michalkiewicz.mealplannerclient.utils.Resource
import java.util.*

class NutritionScreenFragment : Fragment(), NutritionListItemDialog.NutritionDialogListener,
    NutritionAddEatenProductDialogFragment.AddEatenProductDialogListener {

    private val pagesCount = 3
    private lateinit var nutritionSharedViewModel: NutritionSharedViewModel
    private lateinit var binding: FragmentNutritionScreenBinding
    private val mealsListViewAdapter = NutritionMealsListViewAdapter(LinkedList())
    private lateinit var addEatenProductDialogFragment: NutritionAddEatenProductDialogFragment

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNutritionScreenBinding.inflate(inflater)

        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        addEatenProductDialogFragment = NutritionAddEatenProductDialogFragment(context, this)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onResume() {
        super.onResume()
        nutritionSharedViewModel.refreshData()
    }

    override fun onPause() {
        super.onPause()
        nutritionSharedViewModel.productsList.removeObservers(viewLifecycleOwner)
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
        setViewModel()
        setCircleMenuClickListeners()

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
                    if (nutritionSharedViewModel.getEatableItemList() != null) {
                        nutritionMealsListView.adapter =
                            NutritionMealsListViewAdapter(nutritionSharedViewModel.getEatableItemList()!!)
                    } else {
                        nutritionMealsListView.adapter =
                            NutritionMealsListViewAdapter(LinkedList<EatableItem>())
                    }
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

        nutritionSharedViewModel.productsList.observe(viewLifecycleOwner, {
            addEatenProductDialogFragment.updateAutoCompleteTextViewAdapterData(it)
        })

        nutritionSharedViewModel.selectedProduct.observe(viewLifecycleOwner, {
            addEatenProductDialogFragment.showSelectedItemPortions(it)
        })
    }

    private fun setViewModel() {
        binding.viewModel = nutritionSharedViewModel
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
                listItem.findViewById<TextView>(R.id.kcalValueTV).text =
                    getItem(position).foodNutrientsSummary[ENERGY]!!.amount.toString()

                listItem.setOnClickListener {
                    showListItemDialog(position, getItem(position).name)
                }

                return listItem
            }
            return convertView
        }

        private fun showListItemDialog(position: Int, title: String) {
            NutritionListItemDialog(position, title, this@NutritionScreenFragment).show(
                activity!!.supportFragmentManager,
                "nutrition_items"
            ) // TODO Handle null
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

    private fun setCircleMenuClickListeners() {
        nutritionCircleMenu.setOnItemClickListener { buttonIndex ->
            when (buttonIndex) {
                1 -> showAddProductDialog()
            }
        }
    }

    private fun showAddProductDialog() {
        addEatenProductDialogFragment.show(
            requireActivity().supportFragmentManager,
            NutritionAddEatenProductDialogFragment.TAG
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onDeleteClicked(position: Int) {
        nutritionSharedViewModel.deleteItemWithGivenPosition(position)
    }

    override fun onOkClicked() {

    }

    override fun findProductsForName(name: String) {
        Log.i(TAG, "Product name: " + name)
        nutritionSharedViewModel.searchProduct(name)
    }

    override fun onProductSelectedWithPosition(position: Int) {
        nutritionSharedViewModel.getProductDetailData(position)
    }
}