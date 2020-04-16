package piotr.michalkiewicz.mealplannerclient.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.BulletSpan;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import java.net.HttpURLConnection;
import java.util.LinkedList;
import java.util.List;

import piotr.michalkiewicz.mealplannerclient.R;
import piotr.michalkiewicz.mealplannerclient.connection.HttpConnectionHandler;
import piotr.michalkiewicz.mealplannerclient.fragments.RecipeImgFragmentAdapter;
import piotr.michalkiewicz.mealplannerclient.model.FoodTimeRecipe;
import piotr.michalkiewicz.mealplannerclient.model.RecipeIngredient;

public class RecipeActivity extends AppCompatActivity {

    HorizontalScrollView horizontalScrollView;
    LinearLayout imageContainer;
    RecipeImgFragmentAdapter adapter;
    LinearLayout ingredientsList;
    ViewPager viewPager;
    ImageView temp;
    TextView recipeTitleTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        horizontalScrollView = (HorizontalScrollView) findViewById(R.id.horizontalScrollView);
        ingredientsList = (LinearLayout) findViewById(R.id.ingredientsList);
        imageContainer = (LinearLayout) findViewById(R.id.imageContainer);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        recipeTitleTV = (TextView) findViewById(R.id.recipeTitleTV);
        adapter = new RecipeImgFragmentAdapter(getSupportFragmentManager(), getPhotos());
        viewPager.setAdapter(adapter);

        makeView();
    }

    private void addIngredientsToList(List<RecipeIngredient> ingredients){

        List<SpannableString> spannableIngredients = new LinkedList<>();
        BulletSpan bSpan = new BulletSpan(24, getResources().getColor(R.color.colorBlack));

        for(RecipeIngredient ingredient : ingredients){
            SpannableString spannableString = new SpannableString(ingredient.toString());
            spannableString.setSpan(bSpan,0, ingredient.toString().length(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableIngredients.add(spannableString);
        }
        for(SpannableString s : spannableIngredients){
            TextView tv = (TextView)getLayoutInflater().inflate(R.layout.bullet_span_element_tv, null);
            tv.setText(s);
            ingredientsList.addView(tv);
        }
    }

    private List<Integer> getPhotos(){
        List <Integer> drawableIdList = new LinkedList<>();
        drawableIdList.add(R.mipmap.pizza1);
        drawableIdList.add(R.mipmap.pizza2);
        drawableIdList.add(R.mipmap.pizza3);
        return drawableIdList;
    }

    private void makeView(){
        String json;
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {

            json = bundle.getString("recipeJSON");
            if(json != null){
                FoodTimeRecipe recipe = new Gson().fromJson(json, FoodTimeRecipe.class);
                if (recipe != null) {
                    recipeTitleTV.setText(recipe.getName());
                }
            }
        }
    }

}
