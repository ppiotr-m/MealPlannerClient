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

import java.util.LinkedList;
import java.util.List;

import piotr.michalkiewicz.mealplannerclient.R;
import piotr.michalkiewicz.mealplannerclient.fragments.RecipeImgFragmentAdapter;
import piotr.michalkiewicz.mealplannerclient.model.Ingredient;

public class RecipeActivity extends AppCompatActivity {

    HorizontalScrollView horizontalScrollView;
    LinearLayout imageContainer;
    RecipeImgFragmentAdapter adapter;
    LinearLayout ingredientsList;
    ViewPager viewPager;
    ImageView temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        horizontalScrollView = (HorizontalScrollView) findViewById(R.id.horizontalScrollView);
        ingredientsList = (LinearLayout) findViewById(R.id.ingredientsList);
        imageContainer = (LinearLayout) findViewById(R.id.imageContainer);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        adapter = new RecipeImgFragmentAdapter(getSupportFragmentManager(), getPhotos());
        viewPager.setAdapter(adapter);
        addIngredientsToList(generateRandomIngredients());
    }

    private void addIngredientsToList(List<Ingredient> ingredients){

        List<SpannableString> spannableIngredients = new LinkedList<>();
        BulletSpan bSpan = new BulletSpan(24, getResources().getColor(R.color.colorBlack));

        for(Ingredient ingredient : ingredients){
            SpannableString spannableString = new SpannableString(ingredient.getIngredientName());
            spannableString.setSpan(bSpan,0, ingredient.getIngredientName().length(),
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

    private List<Ingredient> generateRandomIngredients(){
        LinkedList<Ingredient> list = new LinkedList<>();
        list.add(new Ingredient("Woda"));
        list.add(new Ingredient("Sól"));
        list.add(new Ingredient("Ziemia"));
        list.add(new Ingredient("Paznokcie"));

        return list;
    }
}
