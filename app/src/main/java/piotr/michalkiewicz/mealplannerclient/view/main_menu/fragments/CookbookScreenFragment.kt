package piotr.michalkiewicz.mealplannerclient.view.main_menu.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import piotr.michalkiewicz.mealplannerclient.R
import piotr.michalkiewicz.mealplannerclient.databinding.FragmentCookbookScreenBinding
import piotr.michalkiewicz.mealplannerclient.recipes.paging.Injection
import piotr.michalkiewicz.mealplannerclient.recipes.paging.ui.RecipesAdapter
import piotr.michalkiewicz.mealplannerclient.recipes.paging.ui.RecipesSearchViewModel


class CookbookScreenFragment : Fragment() {

    private lateinit var binding: FragmentCookbookScreenBinding
    private lateinit var viewModel: RecipesSearchViewModel
    private var adapter = RecipesAdapter()
    private var searchJob: Job? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_cookbook_screen, container, false)
        val manager = LinearLayoutManager(context)
        view.findViewById<RecyclerView>(R.id.temporaryRecipesRecyclerView).layoutManager = manager
        view.findViewById<RecyclerView>(R.id.temporaryRecipesRecyclerView).setHasFixedSize(true)

        initAdapter(view.findViewById<RecyclerView>(R.id.temporaryRecipesRecyclerView))
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentCookbookScreenBinding.inflate(layoutInflater)

        viewModel = ViewModelProvider(this, Injection.provideViewModelFactory())
                .get(RecipesSearchViewModel::class.java)

        val decoration = DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL)
        binding.temporaryRecipesRecyclerView.addItemDecoration(decoration)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.apiData.collect {
                it.let {
                    adapter.submitData(it)
                }
            }
        }
    }

    private fun initAdapter(recyclerView: RecyclerView) {
        recyclerView.adapter = adapter
    }

    private fun search(query: String) {
        // Make sure we cancel the previous job before creating a new one
        searchJob?.cancel()
        searchJob = viewLifecycleOwner.lifecycleScope.launch {
            viewModel.searchRecipe(query).collectLatest {
                adapter.submitData(it)
            }
        }
    }
}