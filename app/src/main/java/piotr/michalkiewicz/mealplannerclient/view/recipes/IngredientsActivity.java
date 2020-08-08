package piotr.michalkiewicz.mealplannerclient.view.recipes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

import piotr.michalkiewicz.mealplannerclient.R;
import piotr.michalkiewicz.mealplannerclient.recipes.model.RecipeIngredient;

import static piotr.michalkiewicz.mealplannerclient.utils.ConstantValues.INGREDIENTS_DATA;

public class IngredientsActivity extends AppCompatActivity {

    private ArrayList<RecipeIngredient> data;
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

    private void init(){
        data = (ArrayList<RecipeIngredient>)getIntent().getSerializableExtra(INGREDIENTS_DATA);

        if(data == null) throw new RuntimeException();

        assignUIElements();
        initView();

    }

    private void initView(){
        LayoutInflater inflater = LayoutInflater.from(this);

        for(RecipeIngredient ingredient : data){
            View view = inflater.inflate(R.layout.ingredient_list_item, produceContainer);
            CheckBox checkBox = view.findViewById(R.id.ingredientListItemCB);
            TextView amountTV = view.findViewById(R.id.ingredientListAmountTV);
            checkBox.setText(ingredient.getName());
            amountTV.setText(ingredient.getAmount());

        }
    }
}
