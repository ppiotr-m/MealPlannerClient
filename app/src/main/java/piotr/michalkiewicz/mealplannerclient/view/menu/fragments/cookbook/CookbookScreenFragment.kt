package piotr.michalkiewicz.mealplannerclient.view.menu.fragments.cookbook

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_cookbook_screen.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import piotr.michalkiewicz.mealplannerclient.R
import piotr.michalkiewicz.mealplannerclient.databinding.FragmentCookbookScreenBinding
import piotr.michalkiewicz.mealplannerclient.recipes.Injection
import piotr.michalkiewicz.mealplannerclient.recipes.model.MealTimeRecipe
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues.Companion.TAG
import piotr.michalkiewicz.mealplannerclient.view.menu.fragments.cookbook.interfaces.RecipesNavigationListener
import piotr.michalkiewicz.mealplannerclient.view.recipes.paging.CookbookViewModel
import piotr.michalkiewicz.mealplannerclient.view.recipes.paging.RecipesAdapter

class CookbookScreenFragment : Fragment(), RecipesNavigationListener {

    private lateinit var binding: FragmentCookbookScreenBinding
    private lateinit var viewModel: CookbookViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cookbook_screen, container, false)
    }

    @ExperimentalPagingApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentCookbookScreenBinding.inflate(layoutInflater)

        viewModel = ViewModelProvider(
            this,
            Injection.provideViewModelFactory(
                requireContext().applicationContext
            )
        )
            .get(CookbookViewModel::class.java)

        initRecipeRecyclerViews()
    }

    @ExperimentalPagingApi
    private fun initRecipeRecyclerViews() {
        attachRecipesRecyclerView("diet", "VEGAN")
        attachRecipesRecyclerView("diet", "STANDARD")
        attachRecipesRecyclerView("diet", "VEGETARIAN")
        attachRecipesRecyclerView("diet", "PALEO")
    }

    @ExperimentalPagingApi
    private fun launchCoroutineByDietForRecyclerView(
        recyclerView: RecyclerView,
        queryParam: String
    ) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.recipesByDietApiData(queryParam).collect {
                it.let {
                    Log.d(TAG, "Submitting data to recycler view adapter")
                    (recyclerView.adapter as PagingDataAdapter<MealTimeRecipe, RecyclerView.ViewHolder>)
                        .submitData(it)
                }
            }
        }
    }

    private fun launchCoroutineByTypeForRecyclerView(
        recyclerView: RecyclerView,
        queryParam: String
    ) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.recipesByTypeApiData(queryParam).collect {
                it.let {
                    (recyclerView.adapter as PagingDataAdapter<MealTimeRecipe, RecyclerView.ViewHolder>)
                        .submitData(it)
                }
            }
        }
    }

    private fun launchCoroutineByTagForRecyclerView(
        recyclerView: RecyclerView,
        queryParam: String
    ) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.recipesByTagApiData(queryParam).collect {
                it.let {
                    (recyclerView.adapter as PagingDataAdapter<MealTimeRecipe, RecyclerView.ViewHolder>)
                        .submitData(it)
                }
            }
        }
    }

    private fun createHorizontalRecipesList(labelText: String): View {
        val root = layoutInflater.inflate(
            R.layout.horizontal_recipes_list,
            recipesByCategoriesLayoutContainer, true
        ) as ViewGroup

        val view = root.getChildAt(root.childCount - 1)

        if (labelText.isEmpty()) {
            view.findViewById<TextView>(R.id.recipesRecyclerViewLabel).text =
                resources.getString(R.string.all)
        } else {
            view.findViewById<TextView>(R.id.recipesRecyclerViewLabel).text = labelText
        }
        val horizontalRecyclerView =
            view.findViewById<RecyclerView>(R.id.recipesHorizontalRecyclerView)
        horizontalRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        horizontalRecyclerView.adapter = RecipesAdapter(this)

        return view
    }

    @ExperimentalPagingApi
    private fun attachRecipesRecyclerView(
        category: String,
        categoryValue: String
    ) {
        val recipesHorizontalListWithLabel = createHorizontalRecipesList(categoryValue)

        when (category) {
            "diet" -> launchCoroutineByDietForRecyclerView(
                recipesHorizontalListWithLabel
                    .findViewById(R.id.recipesHorizontalRecyclerView), categoryValue
            )
            "type" -> launchCoroutineByTypeForRecyclerView(
                recipesHorizontalListWithLabel
                    .findViewById(R.id.recipesHorizontalRecyclerView), categoryValue
            )
            "tag" -> launchCoroutineByTagForRecyclerView(
                recipesHorizontalListWithLabel
                    .findViewById(R.id.recipesHorizontalRecyclerView), categoryValue
            )
        }

    }

    override fun navigateToFragmentWithRecipeId(recipeId: String) {

    }
}