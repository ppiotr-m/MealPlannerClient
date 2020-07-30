package piotr.michalkiewicz.mealplannerclient.view.activities.menus;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import piotr.michalkiewicz.mealplannerclient.R;
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues;
import piotr.michalkiewicz.mealplannerclient.view.fragments.CookbookScreenFragment;
import piotr.michalkiewicz.mealplannerclient.view.fragments.HomeScreenFragment;
import piotr.michalkiewicz.mealplannerclient.view.fragments.MealPlansFragment;
import piotr.michalkiewicz.mealplannerclient.view.fragments.NutritionScreenFragment;

public class MainMenuActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    public static SharedPreferences myPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        // I doesn't have idea yest where to put it it Must be Activity

       myPreferences = getApplicationContext().getSharedPreferences(ConstantValues.MY_PREFERENCE_NAME, MODE_PRIVATE);

        init();
    }

    private void init(){
        assignUIElements();
        setBottomNavigationMenu();
        setFragment(new HomeScreenFragment());
    }

    private void assignUIElements(){
        bottomNavigationView = findViewById(R.id.bottom_navigation);
    }

    private void setBottomNavigationMenu(){
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
                    setFragment(selectedFragment);
                    return true;
                }
                else{
                    return false;
                }
            }
        });
    }

    private void setFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.mainMenuFragmentContainer,
                fragment).commit();
    }
}
