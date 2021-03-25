package piotr.michalkiewicz.mealplannerclient.view.recipes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import piotr.michalkiewicz.mealplannerclient.R

class RecipeFragment : Fragment() {

//    private lateinit var binding: FragmentRecipeBinding
//    private lateinit var viewModel: RecipeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_recipe, container, false)
    }

    //    private fun setOnClickListeners() {
//        goToIngredientsBtn!!.setOnClickListener { v: View? ->
//            if (data == null) throw RuntimeException()
//            val myIntent = Intent(
//                this@RecipeFragment,
//                IngredientsActivity::class.java
//            )
//            myIntent.putExtra(
//                ConstantValues.INGREDIENTS_DATA,
//                data!!.recipeIngredients as ArrayList<*>
//            )
//            startActivity(myIntent)
//        }
//        goToCookingStepsBtn!!.setOnClickListener { v: View? ->
//            if (data == null) throw RuntimeException()
//            val myIntent = Intent(
//                this@RecipeFragment,
//                CookingStepsActivity::class.java
//            )
//            myIntent.putExtra(
//                ConstantValues.COOKING_STEPS_DATA,
//                data!!.instructionSteps as ArrayList<*>
//            )
//            startActivity(myIntent)
//        }
//    }

//    fun initWithData() {
//
//        initCommentSection(Comment.createMockCommentList())
//    }

//    private fun initCommentSection(commentList: List<Comment>?) {
//        if (commentList == null || commentList.isEmpty()) return
//        val layoutInflater = LayoutInflater.from(this)
//        for (comment in commentList) {
//            val commentView = layoutInflater.inflate(R.layout.comment_item_layout, null, false)
//            val usernameTV = commentView.findViewById<TextView>(R.id.commentUsernameTV)
//            val commentTV = commentView.findViewById<TextView>(R.id.commentTV)
//            usernameTV.text = comment.username
//            commentTV.text = comment.comment
//            commentsSectionLinearLayout!!.addView(commentView)
//        }
//    }
}