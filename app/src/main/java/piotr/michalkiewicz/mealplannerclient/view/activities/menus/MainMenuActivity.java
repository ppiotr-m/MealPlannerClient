package piotr.michalkiewicz.mealplannerclient.view.activities.menus;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import piotr.michalkiewicz.mealplannerclient.R;
import piotr.michalkiewicz.mealplannerclient.view.fragments.CookbookScreenFragment;
import piotr.michalkiewicz.mealplannerclient.view.fragments.HomeScreenFragment;
import piotr.michalkiewicz.mealplannerclient.view.fragments.MealPlansFragment;
import piotr.michalkiewicz.mealplannerclient.view.fragments.NutritionScreenFragment;

public class MainMenuActivity extends AppCompatActivity {

    private Button yourWeekBtn;
    private Button yourDietBtn;
    private Button recipesBtn;
    private Button groceriesBtn;
    private LinearLayout imageContainer;
    private BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;

                switch (item.getItemId()){

                    case R.id.navigation_home:
                        selectedFragment = new HomeScreenFragment();
                        break;
                    case R.id.navigation_cookbook:
                        selectedFragment = new CookbookScreenFragment();
                        break;
                    case R.id.navigation_mealplans:
                        selectedFragment = new MealPlansFragment();
                        break;
                    case R.id.navigation_nutrition:
                        selectedFragment = new NutritionScreenFragment();
                        break;
                }
                if(selectedFragment!=null) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.mainMenuFragmentContainer,
                            selectedFragment).commit();
                    return true;
                }
                else{
                    return false;
                }
            }
        });
    }

}
