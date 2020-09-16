package piotr.michalkiewicz.mealplannerclient.view.main_menu.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_cookbook_screen.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import piotr.michalkiewicz.mealplannerclient.R
import piotr.michalkiewicz.mealplannerclient.databinding.FragmentCookbookScreenBinding
import piotr.michalkiewicz.mealplannerclient.recipes.Injection
import piotr.michalkiewicz.mealplannerclient.recipes.model.MealTimeRecipeMiniatureData
import piotr.michalkiewicz.mealplannerclient.recipes.model.RecipeParamsPair
import piotr.michalkiewicz.mealplannerclient.view.recipes.paging.OnPrependDataLoadedListener
import piotr.michalkiewicz.mealplannerclient.view.recipes.paging.RecipesAdapter
import piotr.michalkiewicz.mealplannerclient.view.recipes.paging.RecipesSearchViewModel
import java.util.*


class CookbookScreenFragment : Fragment() {

    private lateinit var binding: FragmentCookbookScreenBinding
    private lateinit var viewModel: RecipesSearchViewModel

    private var recipeListsInitParams = LinkedList<RecipeParamsPair>()

    private val onPrependDataLoadedListener = object : OnPrependDataLoadedListener {
        override fun onPrependDataLoaded() {
            val nextRecipeListParameters = recipeListsInitParams.poll()
            if (nextRecipeListParameters != null) {
                attachRecipesRecyclerView(nextRecipeListParameters.category, nextRecipeListParameters.value)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_cookbook_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentCookbookScreenBinding.inflate(layoutInflater)

        viewModel = ViewModelProvider(this, Injection.provideViewModelFactory(onPrependDataLoadedListener))
                .get(RecipesSearchViewModel::class.java)

        initRecipeRecyclerViews()
    }

    private fun launchCoroutineByDietForRecyclerView(recyclerView: RecyclerView, queryParam: String) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.recipesByDietApiData(queryParam).collect {
                it.let {
                    (recyclerView.adapter as PagingDataAdapter<MealTimeRecipeMiniatureData, RecyclerView.ViewHolder>)
                            .submitData(it)
                }
            }
        }
    }

    private fun initListsParamsWithMockValues() {
        recipeListsInitParams.add(RecipeParamsPair("diet", "STANDARD"))
        recipeListsInitParams.add(RecipeParamsPair("diet", "VEGETARIAN"))
        recipeListsInitParams.add(RecipeParamsPair("diet", "PALEO"))
    }

    private fun initRecipeRecyclerViews() {
        initListsParamsWithMockValues()
        attachRecipesRecyclerView("", "")
    }

    private fun launchCoroutineByTypeForRecyclerView(recyclerView: RecyclerView, queryParam: String) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.recipesByTypeApiData(queryParam).collect {
                it.let {
                    (recyclerView.adapter as PagingDataAdapter<MealTimeRecipeMiniatureData, RecyclerView.ViewHolder>)
                            .submitData(it)
                }
            }
        }
    }

    private fun launchCoroutineByTagForRecyclerView(recyclerView: RecyclerView, queryParam: String) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.recipesByTagApiData(queryParam).collect {
                it.let {
                    (recyclerView.adapter as PagingDataAdapter<MealTimeRecipeMiniatureData, RecyclerView.ViewHolder>)
                            .submitData(it)
                }
            }
        }
    }

    private fun launchCoroutineAllRecipesForRecyclerView(recyclerView: RecyclerView) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.allRecipesApiData().collect {
                it.let {
                    (recyclerView.adapter as PagingDataAdapter<MealTimeRecipeMiniatureData, RecyclerView.ViewHolder>)
                            .submitData(it)
                }
            }
        }
    }

    private fun createHorizontalRecipesList(labelText: String): View {
        val root = layoutInflater.inflate(R.layout.horizontal_recipes_list,
                recipesByCategoriesLayoutContainer, true) as ViewGroup

        val view = root.getChildAt(root.childCount - 1)

        if (labelText.isEmpty()) {
            view.findViewById<TextView>(R.id.recipesRecyclerViewLabel).text = resources.getString(R.string.all)
        } else {
            view.findViewById<TextView>(R.id.recipesRecyclerViewLabel).text = labelText
        }
        val horizontalRecyclerView = view.findViewById<RecyclerView>(R.id.recipesHorizontalRecyclerView)
        horizontalRecyclerView.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        horizontalRecyclerView.adapter = RecipesAdapter()

        return view
    }

    private fun attachRecipesRecyclerView(category: String, categoryValue: String) {
        val recipesHorizontalListWithLabel = createHorizontalRecipesList(categoryValue)

        if (category.isEmpty() || categoryValue.isEmpty()) {
            launchCoroutineAllRecipesForRecyclerView(recipesHorizontalListWithLabel
                    .findViewById(R.id.recipesHorizontalRecyclerView))
        }

        when (category) {
            "diet" -> launchCoroutineByDietForRecyclerView(recipesHorizontalListWithLabel
                    .findViewById(R.id.recipesHorizontalRecyclerView), categoryValue)
            "type" -> launchCoroutineByTypeForRecyclerView(recipesHorizontalListWithLabel
                    .findViewById(R.id.recipesHorizontalRecyclerView), categoryValue)
            "tag" -> launchCoroutineByTagForRecyclerView(recipesHorizontalListWithLabel
                    .findViewById(R.id.recipesHorizontalRecyclerView), categoryValue)
        }
    }
}