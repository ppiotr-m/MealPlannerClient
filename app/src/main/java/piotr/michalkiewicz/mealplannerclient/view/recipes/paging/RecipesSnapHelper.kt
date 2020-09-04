package piotr.michalkiewicz.mealplannerclient.view.recipes.paging

import android.view.View
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView

class RecipesSnapHelper: LinearSnapHelper() {

    // TODO
    override fun calculateDistanceToFinalSnap(layoutManager: RecyclerView.LayoutManager, targetView: View): IntArray? {
        return super.calculateDistanceToFinalSnap(layoutManager, targetView)
    }

    // TODO
    override fun findSnapView(layoutManager: RecyclerView.LayoutManager?): View? {
        return super.findSnapView(layoutManager)
    }
}