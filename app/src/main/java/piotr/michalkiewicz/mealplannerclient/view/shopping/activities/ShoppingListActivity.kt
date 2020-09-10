package piotr.michalkiewicz.mealplannerclient.view.shopping.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_shopping_list.*
import piotr.michalkiewicz.mealplannerclient.R
import piotr.michalkiewicz.mealplannerclient.recipes.model.RecipeIngredient
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues.Companion.INGREDIENTS_DATA
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues.Companion.SHOPPING_LIST_SHARED_PREF
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues.Companion.TAG
import piotr.michalkiewicz.mealplannerclient.view.login_and_signup.LoginActivity
import java.util.*
import kotlin.collections.ArrayList

class ShoppingListActivity : AppCompatActivity() {

    private val checkBoxes = ArrayList<View>()
    private var recipeIngredientsList = ArrayList<RecipeIngredient>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping_list)

        init()
    }

    private fun init() {
        initData()
        initView()
        setOnClickListeners()
    }

    private fun initData(){
        val storedShoppingList = getShoppingListFromSharedPrefs()
        if(storedShoppingList != null) {
            recipeIngredientsList.addAll(getShoppingListFromSharedPrefs()!!)
        }
        else{
            showEmptyShoppingList()
        }
    }

    //  TODO
    private fun showEmptyShoppingList(){

    }

    private fun initView() {
        recipeIngredientsList.forEach {
            shoppingListContainerLayout.addView(createViewFromData(it))
        }
    }

    private fun getShoppingListFromSharedPrefs(): ArrayList<RecipeIngredient>? {
        val json = LoginActivity.MY_PREFERENCSES.getString(SHOPPING_LIST_SHARED_PREF, "")

        if (json!!.isEmpty()) return null

        return try {
            Gson().fromJson(json, Array<RecipeIngredient>::class.java).toList() as ArrayList<RecipeIngredient>
        } catch (e: ClassCastException){
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

        Log.i(TAG, "Left ingredients size: " + recipeIngredientsList.size)
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
            putString(SHOPPING_LIST_SHARED_PREF, dataInJson)
            commit()
        }
    }
}