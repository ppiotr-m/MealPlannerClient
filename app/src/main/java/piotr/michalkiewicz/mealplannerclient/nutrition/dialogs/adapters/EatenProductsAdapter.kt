package piotr.michalkiewicz.mealplannerclient.nutrition.dialogs.adapters

import android.content.Context
import android.widget.ArrayAdapter
import android.widget.Filter
import piotr.michalkiewicz.mealplannerclient.products.model.BasicFoodItemData
import java.util.*

class EatenProductsAdapter(
    context: Context,
    resource: Int,
    val data: LinkedList<BasicFoodItemData> = LinkedList()
) :
    ArrayAdapter<BasicFoodItemData>(context, resource) {

    fun setData(data: LinkedList<BasicFoodItemData>) {
        this.data.clear()
        this.data.addAll(data)
    }

    override fun getCount(): Int {
        return data.size
    }

    override fun getItem(position: Int): BasicFoodItemData? {
        return data[position]
    }

    override fun getFilter(): Filter {
        return ProductsFilter(this, data)
    }

    private class ProductsFilter(
        val adapter: ArrayAdapter<BasicFoodItemData>,
        val data: LinkedList<BasicFoodItemData>
    ) : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filterResults = FilterResults()
            if (constraint != null) {
                filterResults.values = data
                filterResults.count = data.size
            }
            return filterResults
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            if (results != null && (results.count > 0)) {
                adapter.notifyDataSetChanged()
            } else {
                adapter.notifyDataSetInvalidated()
            }
        }
    }
}