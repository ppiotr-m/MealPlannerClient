package piotr.michalkiewicz.mealplannerclient.view.main_menu.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import piotr.michalkiewicz.mealplannerclient.R
import piotr.michalkiewicz.mealplannerclient.databinding.FragmentCookbookScreenBinding
import piotr.michalkiewicz.mealplannerclient.recipes.paging.ui.RecipesAdapter
import piotr.michalkiewicz.mealplannerclient.view.recipes.view_model.RecipesSearchViewModel

class CookbookScreenFragment : Fragment() {

    private lateinit var binding: FragmentCookbookScreenBinding
    private lateinit var viewModel: RecipesSearchViewModel
    private val adapter = RecipesAdapter()
    private var searchJob: Job? = null

    private fun search(query: String) {
        // Make sure we cancel the previous job before creating a new one
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            viewModel.searchRepo(query).collectLatest {
                adapter.submitData(it)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_cookbook_screen, container, false)
        init(view)
        return view
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    private fun init(root: View) {
        assignUIElements(root)
    }

    private fun assignUIElements(root: View) {
    }
}