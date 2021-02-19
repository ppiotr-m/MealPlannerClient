package piotr.michalkiewicz.mealplannerclient.view.recipes.paging

import android.app.AlertDialog
import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import piotr.michalkiewicz.mealplannerclient.R
import piotr.michalkiewicz.mealplannerclient.nutrition.remote.NutritionDataUpdater
import piotr.michalkiewicz.mealplannerclient.recipes.model.MealTimeRecipe
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues.Companion.RECIPE_ID
import piotr.michalkiewicz.mealplannerclient.view.recipes.RecipeActivity

class RecipeViewHolder(private val recipeCardView: View) : RecyclerView.ViewHolder(recipeCardView) {
    private val recipeTitle: TextView = recipeCardView.findViewById(R.id.recipeCardTitle)
    private val cookbookThumbnail: ImageView = recipeCardView.findViewById(R.id.recipeCardThumbnail)
    private val addMealBtn: ImageButton = recipeCardView.findViewById(R.id.addMealDialogBtn)
    private val context = recipeCardView.context

    private lateinit var recipe: MealTimeRecipe

    @RequiresApi(Build.VERSION_CODES.O)
    fun bind(recipe: MealTimeRecipe?) {
        if (recipe == null) {
            val resources = itemView.resources
            recipeTitle.text = resources.getString(R.string.loading)
            cookbookThumbnail.visibility = View.GONE
        } else {
            showData(recipe)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun addOnClickListener(recipeId: String) {
        recipeCardView.setOnClickListener {
            val intent = Intent(recipeCardView.context, RecipeActivity::class.java)
            intent.putExtra(RECIPE_ID, recipeId)
            context.startActivity(intent)
        }

        addMealBtn.setOnClickListener {
            AlertDialog.Builder(recipeCardView.context)
                .setMessage(context.getString(R.string.add_meal_dialog_msg))
                .setPositiveButton(context.getString(R.string.add_to_eaten_today)) { _, _ ->
                    addMealToTodayNutrition()
                }
                .create()
                .show()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun addMealToTodayNutrition() {
        val updater = NutritionDataUpdater()

        //  TODO Should not use GlobalScope
        GlobalScope.launch {
            updater.saveMealToTodayNutrition(recipe.toEatableItem())
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun showData(recipe: MealTimeRecipe) {
        addOnClickListener(recipe.id)
        this.recipe = recipe
        recipeTitle.text = recipe.name
        cookbookThumbnail.setImageBitmap(recipe.image)
        cookbookThumbnail.visibility = View.VISIBLE
    }

    companion object {
        fun create(parent: ViewGroup): RecipeViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.recipe_list_item, parent, false)
            return RecipeViewHolder(view)
        }
    }
}