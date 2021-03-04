package piotr.michalkiewicz.mealplannerclient.nutrition.dialogs.adapters

import android.content.Context
import android.widget.ArrayAdapter
import piotr.michalkiewicz.mealplannerclient.products.model.BasicFoodItemData

class ProductsAutoCompleteTextViewAdapter(
    private val ownerContext: Context,
    private val resource: Int,
    private val data: List<BasicFoodItemData>
) : ArrayAdapter<BasicFoodItemData>(ownerContext, resource, data) {

//    override fun getCount(): Int {
//        return data.size
//    }
//
//    override fun getItem(position: Int): BasicFoodItemData? {
//        return data[position]
//    }
//
//    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
//        var view = convertView
//        if (view == null) {
//            view = LayoutInflater.from(ownerContext)
//                .inflate(android.R.layout.simple_spinner_dropdown_item, parent)
//        }
//        val tv = view?.findViewById<TextView>(R.id.simpleListItemTV)
//        tv?.text = getItem(position).toString()
//
//        return view!!   // TODO
//    }
}