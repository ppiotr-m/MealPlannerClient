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
import piotr.michalkiewicz.mealplannerclient.recipes.model.MealTimeRecipe
import piotr.michalkiewicz.mealplannerclient.view.recipes.paging.RecipesAdapter
import piotr.michalkiewicz.mealplannerclient.view.recipes.paging.RecipesSearchViewModel


class CookbookScreenFragment : Fragment() {

    private lateinit var binding: FragmentCookbookScreenBinding
    private lateinit var viewModel: RecipesSearchViewModel
    private val recipesListIds = ArrayList<Int>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_cookbook_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentCookbookScreenBinding.inflate(layoutInflater)

        viewModel = ViewModelProvider(this, Injection.provideViewModelFactory())
                .get(RecipesSearchViewModel::class.java)

        initRecipeRecyclerViews()
    }

    private fun launchCoroutineByDietForRecyclerView(recyclerView: RecyclerView, queryParam: String) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.recipesByDietApiData(queryParam).collect {
                it.let {
                    (recyclerView.adapter as PagingDataAdapter<MealTimeRecipe, RecyclerView.ViewHolder>)
                            .submitData(it)
                }
            }
        }
    }

    private fun initRecipeRecyclerViews() {
        attachRecipesRecyclerView("", "")
        attachRecipesRecyclerView("diet", "STANDARD")
        attachRecipesRecyclerView("diet", "VEGETARIAN")
        attachRecipesRecyclerView("diet", "PALEO")
//        attachRecipesRecyclerView("type", "VEGETARIAN")
        //    attachRecipesRecyclerView("tag", "light")
    }

    private fun launchCoroutineByTypeForRecyclerView(recyclerView: RecyclerView, queryParam: String) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.recipesByTypeApiData(queryParam).collect {
                it.let {
                    (recyclerView.adapter as PagingDataAdapter<MealTimeRecipe, RecyclerView.ViewHolder>)
                            .submitData(it)
                }
            }
        }
    }

    private fun launchCoroutineByTagForRecyclerView(recyclerView: RecyclerView, queryParam: String) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.recipesByTagApiData(queryParam).collect {
                it.let {
                    (recyclerView.adapter as PagingDataAdapter<MealTimeRecipe, RecyclerView.ViewHolder>)
                            .submitData(it)
                }
            }
        }
    }

    private fun launchCoroutineAllRecipesForRecyclerView(recyclerView: RecyclerView) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.allRecipesApiData().collect {
                it.let {
                    (recyclerView.adapter as PagingDataAdapter<MealTimeRecipe, RecyclerView.ViewHolder>)
                            .submitData(it)
                }
            }
        }
    }

    private fun createHorizontalRecipesList(labelText: String): View {
        val root = layoutInflater.inflate(R.layout.horizontal_recipes_list,
                recipesByCategoriesLayoutContainer, true) as ViewGroup

        val view = root.getChildAt(root.childCount - 1)

        //       val view = root.findViewById<LinearLayout>(R.id.recipesRecyclerViewContainer)
        //    val view = createRecipesList(labelText)

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
        //       binding.recipesByCategoriesLayoutContainer.addView(recipesHorizontalListWithLabel)
        //       binding.recipesByCategoriesLayoutContainer.requestLayout()

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