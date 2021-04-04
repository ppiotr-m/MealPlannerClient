package piotr.michalkiewicz.mealplannerclient.view.recipes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_recipe.*
import piotr.michalkiewicz.mealplannerclient.R
import piotr.michalkiewicz.mealplannerclient.databinding.FragmentRecipeBinding
import piotr.michalkiewicz.mealplannerclient.recipes.Injection
import piotr.michalkiewicz.mealplannerclient.recipes.model.Comment
import piotr.michalkiewicz.mealplannerclient.view.recipes.viewmodel.RecipeViewModel

class RecipeFragment : Fragment() {

    private lateinit var recipeViewModel: RecipeViewModel
    private lateinit var binding: FragmentRecipeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRecipeBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner
        init()
    }

    private fun init() {
        initViewModel()
        setupObservers()
    }

    private fun initViewModel() {
        recipeViewModel = ViewModelProvider(
            requireActivity(),
            Injection.provideViewModelFactory(requireContext())
        ).get(RecipeViewModel::class.java)

        val recipeId = retriveRecipeIdPassedWithNavigation()

        binding.viewModel = recipeViewModel

        if (recipeId != null) {
            recipeViewModel.initialize(recipeId)
        } else {
            handleNullRecipeId()
        }
    }

    private fun retriveRecipeIdPassedWithNavigation(): String? {
        val bundle = arguments ?: return null
        return RecipeFragmentArgs.fromBundle(bundle).recipeId
    }

    private fun handleNullRecipeId() {
        Toast.makeText(requireContext(), R.string.general_unexpected_error, Toast.LENGTH_LONG)
            .show()
        findNavController().popBackStack()
    }

    //  TODO Implement specific logic
    private fun handleRecipeFetchingError() {
        Toast.makeText(requireContext(), R.string.recipe_fetch_error, Toast.LENGTH_LONG)
            .show()
        findNavController().popBackStack()
    }

    private fun setupObservers() {
        recipeViewModel.recipeData.observe(viewLifecycleOwner, {

        })

        recipeViewModel.recipeFeatchErrorOccurred.observe(viewLifecycleOwner, {
            if (it) {
                handleRecipeFetchingError()
            }
        })
    }

    private fun initCommentSection(commentList: List<Comment>?) {
        if (commentList == null || commentList.isEmpty()) return
        val layoutInflater = LayoutInflater.from(requireContext())
        for (comment in commentList) {
            val commentView = layoutInflater.inflate(R.layout.comment_item_layout, null, false)
            val usernameTV = commentView.findViewById<TextView>(R.id.commentUsernameTV)
            val commentTV = commentView.findViewById<TextView>(R.id.commentTV)
            usernameTV.text = comment.username
            commentTV.text = comment.comment
            commentsSectionLinearLayout!!.addView(commentView)
        }
    }
}