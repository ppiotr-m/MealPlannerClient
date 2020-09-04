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
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.mealplannerclient.R
import com.mealplannerclient.databinding.FragmentCookbookScreenBinding
import kotlinx.android.synthetic.main.fragment_cookbook_screen.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import piotr.michalkiewicz.mealplannerclient.recipes.Injection
import piotr.michalkiewicz.mealplannerclient.recipes.model.MealTimeRecipe
import piotr.michalkiewicz.mealplannerclient.view.recipes.paging.RecipesAdapter
import piotr.michalkiewicz.mealplannerclient.view.recipes.paging.RecipesSearchViewModel


class CookbookScreenFragment : Fragment() {

    private lateinit var binding: FragmentCookbookScreenBinding
    private lateinit var viewModel: RecipesSearchViewModel
    private lateinit var viewModel2: RecipesSearchViewModel
    private var adapter = RecipesAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_cookbook_screen, container, false)
        val manager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        view.findViewById<RecyclerView>(R.id.temporaryRecipesRecyclerView).layoutManager = manager
        view.findViewById<RecyclerView>(R.id.temporaryRecipesRecyclerView).setHasFixedSize(true)

        initAdapter(view.findViewById(R.id.temporaryRecipesRecyclerView))
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentCookbookScreenBinding.inflate(layoutInflater)

        viewModel = ViewModelProvider(this, Injection.provideViewModelFactory())
                .get(RecipesSearchViewModel::class.java)

        attachRecipesRecyclerView("diet", "Standard")
    }

    private fun initAdapter(recyclerView: RecyclerView) {
        recyclerView.adapter = adapter
    }

    private fun launchCoroutineForRecyclerView(recyclerView: RecyclerView) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.apiData.collect {
                it.let {
                    (recyclerView.adapter as PagingDataAdapter<MealTimeRecipe, RecyclerView.ViewHolder>)
                            .submitData(it)
                }
            }
        }
    }

    private fun createHorizontalRecipesList(labelText: String): View {
        val view = layoutInflater.inflate(R.layout.horizontal_recipes_list,
                recipesByCategoriesLayoutContainer)
        view.findViewById<TextView>(R.id.recipesRecyclerViewLabel).text = labelText
        val horizontalRecyclerView = view.findViewById<RecyclerView>(R.id.recipesHorizontalRecyclerView)
        horizontalRecyclerView.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        horizontalRecyclerView.adapter = RecipesAdapter()
        horizontalRecyclerView.addItemDecoration(DividerItemDecoration(context,
                DividerItemDecoration.HORIZONTAL))

        return view
    }

    private fun attachRecipesRecyclerView(category: String, categoryValue: String) {
        val recipesHorizontalListWithLabel = createHorizontalRecipesList(categoryValue)
        launchCoroutineForRecyclerView(recipesHorizontalListWithLabel
                .findViewById(R.id.recipesHorizontalRecyclerView))
    }
}