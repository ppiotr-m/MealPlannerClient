package piotr.michalkiewicz.mealplannerclient.view.recipes;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

import piotr.michalkiewicz.mealplannerclient.R;
import piotr.michalkiewicz.mealplannerclient.recipes.model.Comment;
import piotr.michalkiewicz.mealplannerclient.recipes.model.MealTimeRecipe;
import piotr.michalkiewicz.mealplannerclient.view.recipes.adapters.RecipeImgFragmentAdapter;
import piotr.michalkiewicz.mealplannerclient.view.recipes.presenters.RecipePresenter;
import piotr.michalkiewicz.mealplannerclient.view.utils.InitializableViewWithCategory;

import static piotr.michalkiewicz.mealplannerclient.utils.ConstantValues.COOKING_STEPS_DATA;
import static piotr.michalkiewicz.mealplannerclient.utils.ConstantValues.INGREDIENTS_DATA;
import static piotr.michalkiewicz.mealplannerclient.utils.ConstantValues.RECIPE_ID;
import static piotr.michalkiewicz.mealplannerclient.utils.ConstantValues.TAG;

public class RecipeActivity extends AppCompatActivity implements InitializableViewWithCategory<MealTimeRecipe> {

    private HorizontalScrollView horizontalScrollView;
    private LinearLayout commentsSectionLinearLayout;
    private RecipeImgFragmentAdapter adapter;
    private ViewPager viewPager;
    private ImageView temp;
    private Button goToIngredientsBtn;
    private Button goToCookingStepsBtn;
    private TextView aboutMealTV;
    private TextView cookingTimeTV;
    private TextView likedByTV;
    private TextView goodForTV;
    private ImageView recipeImageView;

    private RecipePresenter presenter;

    private MealTimeRecipe data;

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
        presenter = new RecipePresenter(this);
        presenter.fetchRecipe(getIntent().getStringExtra(RECIPE_ID));
    }

    private void assignUIElements(){
        horizontalScrollView = findViewById(R.id.horizontalScrollView);
        commentsSectionLinearLayout = findViewById(R.id.commentsSectionLinearLayout);
        viewPager = findViewById(R.id.viewPager);
        goToCookingStepsBtn = findViewById(R.id.goToCookingStepsBtn);
        goToIngredientsBtn = findViewById(R.id.goToIngredientsBtn);
        aboutMealTV = findViewById(R.id.aboutMealTV);
        cookingTimeTV = findViewById(R.id.cookingTimeTV);
        likedByTV = findViewById(R.id.likedByTV);
        goodForTV = findViewById(R.id.goodForTV);
        recipeImageView = findViewById(R.id.recipeImageView);
    }

    private void setOnClickListeners(){
        goToIngredientsBtn.setOnClickListener(v->{
            if(data==null) throw new RuntimeException();
                Intent myIntent = new Intent(RecipeActivity.this,
                    IngredientsActivity.class);
                myIntent.putExtra(INGREDIENTS_DATA, (ArrayList) data.getRecipeIngredients());
                startActivity(myIntent);
            });

        goToCookingStepsBtn.setOnClickListener(v->{
            if(data==null) throw new RuntimeException();
                Intent myIntent = new Intent(RecipeActivity.this,
                        CookingStepsActivity.class);
                myIntent.putExtra(COOKING_STEPS_DATA, (ArrayList) data.getInstructionSteps());
                startActivity(myIntent);
        });
    }

    @Override
    public void initWithData(MealTimeRecipe data, String category) {
        if (data == null) {
            // TODO obsluga tego nulla
            finish();
            return;
        }

        Log.i(TAG, data.toString());

        this.data = data;
        recipeImageView.setImageBitmap(data.getImage());
        aboutMealTV.setText(data.getDescription());
        cookingTimeTV.setText(String.valueOf(data.getCookTime()));
        likedByTV.setText(String.valueOf(data.getTotalLikes()));
        //  TODO Apply proper data
        goodForTV.setText(String.valueOf(data.getSuitableForDiet().get(0)));
        initCommentSection(Comment.createMockCommentList());
    }

    private void initCommentSection(List<Comment> commentList) {
        if (commentList == null || commentList.isEmpty()) return;

        LayoutInflater layoutInflater = LayoutInflater.from(this);
        for (Comment comment : commentList) {
            View commentView = layoutInflater.inflate(R.layout.comment_item_layout, null, false);

            TextView usernameTV = commentView.findViewById(R.id.commentUsernameTV);
            TextView commentTV = commentView.findViewById(R.id.commentTV);

            usernameTV.setText(comment.getUsername());
            commentTV.setText(comment.getComment());

            commentsSectionLinearLayout.addView(commentView);
        }
    }
}
