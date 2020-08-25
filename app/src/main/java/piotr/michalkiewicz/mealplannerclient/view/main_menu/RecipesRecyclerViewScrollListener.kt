package piotr.michalkiewicz.mealplannerclient.view.main_menu

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import piotr.michalkiewicz.mealplannerclient.recipes.service_generator.RecipeServiceGenerator

abstract class RecipesRecyclerViewScrollListener : RecyclerView.OnScrollListener {
    private var layoutManager : LinearLayoutManager
    private val visibleThreshold = 10
    private var currentPage = 0
    private var previousTotalItemCount = 0
    private var loading = true
    private val startingPageIndex = 0
    private var totalItemCount = 0
    private var lastVisibleItemPosition = 0

    private lateinit var recipeServiceGenerator: RecipeServiceGenerator

    constructor(context: Context){
        this.layoutManager = LinearLayoutManager(context)
        recipeServiceGenerator = RecipeServiceGenerator(context)
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        totalItemCount = layoutManager.itemCount
        lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()

        if(totalItemCount < previousTotalItemCount){
            this.currentPage = this.startingPageIndex
            this.previousTotalItemCount = totalItemCount
            if(totalItemCount == 0) {this.loading = true}
        }

        if (loading && (totalItemCount > previousTotalItemCount)){
            loading = false;
            previousTotalItemCount = totalItemCount
        }

        if (!loading && (lastVisibleItemPosition + visibleThreshold) > totalItemCount){
            currentPage++;
            onLoadMore(currentPage, recyclerView);
            loading = true;
        }
    }

    fun reset() {
        currentPage = startingPageIndex
        previousTotalItemCount = 0
        loading = true
    }

    abstract fun onLoadMore(pageNumber : Int, recyclerView: RecyclerView)
}