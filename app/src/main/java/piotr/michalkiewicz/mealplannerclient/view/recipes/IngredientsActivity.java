package piotr.michalkiewicz.mealplannerclient.view.recipes;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.facebook.login.Login;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import piotr.michalkiewicz.mealplannerclient.R;
import piotr.michalkiewicz.mealplannerclient.recipes.model.RecipeIngredient;
import piotr.michalkiewicz.mealplannerclient.view.login_and_signup.LoginActivity;
import piotr.michalkiewicz.mealplannerclient.view.shopping.activities.ShoppingListActivity;

import static piotr.michalkiewicz.mealplannerclient.utils.ConstantValues.TAG;
import static piotr.michalkiewicz.mealplannerclient.utils.ConstantValues.INGREDIENTS_DATA;
import static piotr.michalkiewicz.mealplannerclient.utils.ConstantValues.SHOPPING_LIST_SHARED_PREF;

public class IngredientsActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    private ArrayList<RecipeIngredient> data;
    private ArrayList<String> productsOriginalNames = new ArrayList<>();
    private ArrayList<RecipeIngredient> selectedIngredients = new ArrayList<>();
    private ViewGroup produceContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients);

        init();
    }

    private void assignUIElements(){
        produceContainer = findViewById(R.id.produceContainerLayout);
    }

    private void init() {
        data = (ArrayList<RecipeIngredient>) getIntent().getSerializableExtra(INGREDIENTS_DATA);
        initProductsOriginalNamesList();

        if (data == null) throw new RuntimeException();

        assignUIElements();
        initView();
        setOnClickListeners();
    }

    private void initProductsOriginalNamesList() {
        for (RecipeIngredient ingredient : data) {
            productsOriginalNames.add(ingredient.getOriginalName());
        }
    }

    private void setOnClickListeners() {
        findViewById(R.id.addProductsToShoppingListBtn).setOnClickListener(v -> {
            if(checkIfAnyIngredientSelected() == false){
                showNoItemSelectedToast();
                return;
            }

            saveIngredientsToStoredShoppingList();
            Intent intent = new Intent(this, ShoppingListActivity.class);
            startActivity(intent);
            this.finish();
        });
    }

    private void initView() {
        LayoutInflater inflater = LayoutInflater.from(this);

        for (RecipeIngredient ingredient : data) {
            View view = inflater.inflate(R.layout.ingredient_list_item, null, false);
            CheckBox checkBox = view.findViewById(R.id.ingredientListItemCB);
            TextView amountTV = view.findViewById(R.id.ingredientListAmountTV);
            checkBox.setText(ingredient.getOriginalName());
            checkBox.setOnCheckedChangeListener(this);
            amountTV.setText(ingredient.getAmount());
            checkBox.setTag(ingredient.getOriginalName());
            produceContainer.addView(view);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (b) {
            selectedIngredients.add(data.get(productsOriginalNames.indexOf(compoundButton.getTag().toString())));
        } else {
            removeItemFromSelectedList(compoundButton.getTag().toString());
        }
    }

    private void removeItemFromSelectedList(String originalName) {
        int index = 0;
        for (RecipeIngredient ingredient : selectedIngredients) {
            if (ingredient.getOriginalName().equals(originalName)) {
                selectedIngredients.remove(index);
                break;
            }
            index += 1;
        }
    }

    private void showNoItemSelectedToast(){
        IngredientsActivity.this.runOnUiThread(() ->
                Toast.makeText(IngredientsActivity.this,
                        R.string.no_ingredient_selected, Toast.LENGTH_SHORT).show());
    }

    private void saveIngredientsToStoredShoppingList(){

        Log.i(TAG, "Selected items size: " + selectedIngredients.size());

        selectedIngredients.addAll(Arrays.asList(getShoppingListFromSharedPrefs()));

        Log.i(TAG, "All items size: " + selectedIngredients.size());

        saveShoppingListToSharedPrefs(selectedIngredients);
    }



    private RecipeIngredient[] getShoppingListFromSharedPrefs(){
        String json = LoginActivity.MY_PREFERENCSES.getString(SHOPPING_LIST_SHARED_PREF, "");

        if(json.isEmpty()) return null;

        try{
             return new Gson().fromJson(json, RecipeIngredient[].class);
        } catch (ClassCastException e){
            Log.e(TAG, e.getLocalizedMessage());
            e.printStackTrace();
        }
        return null;
    }

    private void saveShoppingListToSharedPrefs(List<RecipeIngredient> recipeIngredientList) {
        String dataInJson = new Gson().toJson(recipeIngredientList);

        LoginActivity.MY_PREFERENCSES.edit().putString(SHOPPING_LIST_SHARED_PREF, dataInJson).commit();
    }

    private boolean checkIfAnyIngredientSelected(){
        if(selectedIngredients.isEmpty()) return false;
        return true;
    }
}
