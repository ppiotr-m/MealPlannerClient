package piotr.michalkiewicz.mealplannerclient.view.activities.recipes;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import piotr.michalkiewicz.mealplannerclient.R;
import piotr.michalkiewicz.mealplannerclient.recipes.model.MealTimeRecipe;
import piotr.michalkiewicz.mealplannerclient.view.adapters.RecipeImgFragmentAdapter;
import piotr.michalkiewicz.mealplannerclient.view.interfaces.InitializableViewWithCategory;
import piotr.michalkiewicz.mealplannerclient.view.presenters.RecipePresenter;

public class RecipeActivity extends AppCompatActivity implements InitializableViewWithCategory<MealTimeRecipe> {

    private HorizontalScrollView horizontalScrollView;
    private LinearLayout imageContainer;
    private RecipeImgFragmentAdapter adapter;
    private ViewPager viewPager;
    private ImageView temp;
    private Button goToIngredientsBtn;
    private Button goToCookingStepsBtn;

    private RecipePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        init();
    }

    private void init(){
        assignUIElements();
        setOnClickListeners();
//        adapter = new RecipeImgFragmentAdapter(getSupportFragmentManager(), getPhotos());
//        viewPager.setAdapter(adapter);
        presenter = new RecipePresenter(this, this);
        presenter.fetchRecipe(getIntent().getStringExtra("recipeId"));
    }

    private void assignUIElements(){
        horizontalScrollView = findViewById(R.id.horizontalScrollView);
        imageContainer = findViewById(R.id.imageContainer);
        viewPager = findViewById(R.id.viewPager);
        goToCookingStepsBtn = findViewById(R.id.goToCookingStepsBtn);
        goToIngredientsBtn = findViewById(R.id.goToIngredientsBtn);
    }

    private void setOnClickListeners(){
        goToIngredientsBtn.setOnClickListener(v->{
                Intent myIntent = new Intent(RecipeActivity.this,
                        IngredientsActivity.class);
                startActivity(myIntent);
            });

        goToCookingStepsBtn.setOnClickListener(v->{
                Intent myIntent = new Intent(RecipeActivity.this,
                        CookingStepsActivity.class);
                startActivity(myIntent);
        });
    }

    @Override
    public void initWithData(MealTimeRecipe data, String category) {

    }
}
