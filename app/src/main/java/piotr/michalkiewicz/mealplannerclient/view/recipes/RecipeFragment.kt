package piotr.michalkiewicz.mealplannerclient.view.recipes

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.fragment_recipe.*
import piotr.michalkiewicz.mealplannerclient.R
import piotr.michalkiewicz.mealplannerclient.recipes.model.Comment
import piotr.michalkiewicz.mealplannerclient.recipes.model.MealTimeRecipe
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues
import piotr.michalkiewicz.mealplannerclient.view.recipes.adapters.RecipeImgFragmentAdapter
import piotr.michalkiewicz.mealplannerclient.view.recipes.presenters.RecipePresenter
import java.util.*

class RecipeFragment : Fragment() {




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