package piotr.michalkiewicz.mealplannerclient.view.recipes;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import piotr.michalkiewicz.mealplannerclient.R;
import piotr.michalkiewicz.mealplannerclient.recipes.model.RecipeIngredient;

import static piotr.michalkiewicz.mealplannerclient.utils.ConstantValues.INGREDIENTS_DATA;
import static piotr.michalkiewicz.mealplannerclient.utils.ConstantValues.TAG;

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
        findViewById(R.id.addIngredientsToListBtn).setOnClickListener(v -> {
            for (RecipeIngredient ingredient : selectedIngredients) {
                Log.i(TAG, "Selected ingrdient: " + ingredient.getOriginalName());
            }
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
}
