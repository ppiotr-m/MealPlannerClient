package piotr.michalkiewicz.mealplannerclient.view.main_menu.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.core.view.children
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_shopping_list.*
import piotr.michalkiewicz.mealplannerclient.R
import piotr.michalkiewicz.mealplannerclient.recipes.model.RecipeIngredient
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues
import piotr.michalkiewicz.mealplannerclient.view.login_and_signup.LoginActivity
import java.util.*
import kotlin.collections.ArrayList

class ShoppingListFragment : Fragment() {
    private val checkBoxes = ArrayList<View>()
    private var recipeIngredientsList = ArrayList<RecipeIngredient>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_shopping_list, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    private fun init() {
        initData()
        initView()
        setOnClickListeners()
    }

    private fun initData() {
        val storedShoppingList = getShoppingListFromSharedPrefs()
        if (storedShoppingList != null) {
            recipeIngredientsList.addAll(getShoppingListFromSharedPrefs()!!)
        } else {
            showEmptyShoppingList()
        }
    }

    //  TODO
    private fun showEmptyShoppingList() {
        noItemsOnShoppingListTV.visibility = View.VISIBLE
        deleteProductsBtn.visibility = View.GONE
    }

    private fun initView() {
        recipeIngredientsList.forEach {
            shoppingListContainerLayout.addView(createViewFromData(it))
        }
    }

    private fun getShoppingListFromSharedPrefs(): ArrayList<RecipeIngredient>? {
        val json = LoginActivity.MY_PREFERENCSES.getString(ConstantValues.SHOPPING_LIST_SHARED_PREF, "")

        if (json!!.isEmpty()) return null

        return try {
            Gson().fromJson(json, Array<RecipeIngredient>::class.java).toList() as ArrayList<RecipeIngredient>
        } catch (e: ClassCastException) {
            return null
        }
    }

    private fun createViewFromData(product: RecipeIngredient?): View {
        val view = layoutInflater.inflate(R.layout.ingredient_list_item, null, false)

        view.findViewById<TextView>(R.id.ingredientListItemCB).text = product?.originalName
        view.findViewById<TextView>(R.id.ingredientListAmountTV).text = product?.amount
        view.tag = product

        return view
    }

    private fun setOnClickListeners() {
        deleteProductsBtn.setOnClickListener {
            deleteProducts()
        }
    }

    private fun deleteProducts() {
        removeProductsFromView()
        removeProductsFromStorage()

        Log.i(ConstantValues.TAG, "Left ingredients size: " + recipeIngredientsList.size)
    }

    private fun removeProductsFromView() {
        for (x in 0 until shoppingListContainerLayout.childCount) {
            val checkBox = shoppingListContainerLayout.getChildAt(x).findViewById<CheckBox>(R.id.ingredientListItemCB)
            if (checkBox.isChecked) {
                checkBoxes.add(shoppingListContainerLayout.getChildAt(x))
            }
        }
        checkBoxes.forEach {
            shoppingListContainerLayout.removeView(it)
        }
        if (shoppingListContainerLayout.childCount == 0) {
            showEmptyShoppingList()
        }
    }

    private fun removeProductsFromStorage() {
        val remainingProducts = LinkedList<RecipeIngredient>()

        shoppingListContainerLayout.children.iterator().forEach {
            remainingProducts.add(it.tag as RecipeIngredient)
        }

        saveShoppingListToSharedPrefs(remainingProducts)
    }

    private fun saveShoppingListToSharedPrefs(recipeIngredientList: List<RecipeIngredient>) {
        val dataInJson = Gson().toJson(recipeIngredientList)

        with(LoginActivity.MY_PREFERENCSES.edit()) {
            putString(ConstantValues.SHOPPING_LIST_SHARED_PREF, dataInJson)
            commit()
        }
    }
}