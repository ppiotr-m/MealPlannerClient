package piotr.michalkiewicz.mealplannerclient.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ContextThemeWrapper;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import piotr.michalkiewicz.mealplannerclient.R;
import piotr.michalkiewicz.mealplannerclient.connection.HttpConnectionHandler;
import piotr.michalkiewicz.mealplannerclient.model.FoodTimeRecipe;
import piotr.michalkiewicz.mealplannerclient.support.ViewUpdater;

public class RecipesActivity extends AppCompatActivity implements ViewUpdater<FoodTimeRecipe> {

    LinearLayout recipesContainerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);

        recipesContainerLayout = (LinearLayout) findViewById(R.id.recipesContainerLayout);

        HttpConnectionHandler httpHandler = new HttpConnectionHandler();
        httpHandler.setView(this);
        httpHandler.getRandomRecipes();
    }

    @Override
    public void updateView(FoodTimeRecipe[] data){
        for(final FoodTimeRecipe recipe : data){
            TextView tv = new TextView(new ContextThemeWrapper(this, R.style.recipeListElement), null, 0);
            tv.setText("ID: " + recipe.getId() + "\nName: " + recipe.getName());
            tv.setClickable(true);
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent myIntent = new Intent(RecipesActivity.this, RecipeActivity.class);
                    myIntent.putExtra("recipeJSON", new Gson().toJson(recipe));
                    startActivity(myIntent);
                }
            });
            recipesContainerLayout.addView(tv);
        }
    }
}
