package piotr.michalkiewicz.mealplannerclient.view.main_menu;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import piotr.michalkiewicz.mealplannerclient.R;
import piotr.michalkiewicz.mealplannerclient.view.main_menu.fragments.CookbookScreenFragment;
import piotr.michalkiewicz.mealplannerclient.view.main_menu.fragments.HomeScreenFragment;
import piotr.michalkiewicz.mealplannerclient.view.main_menu.fragments.MealPlansFragment;
import piotr.michalkiewicz.mealplannerclient.view.main_menu.fragments.NutritionScreenFragment;
import piotr.michalkiewicz.mealplannerclient.view.settings.SettingsActivity;

public class MainMenuActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        init();
    }

    private void init() {
        initTopToolbar();
        assignUIElements();
        setBottomNavigationMenu();
        setFragment(new HomeScreenFragment());
    }

    private void initTopToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toolbar.setNavigationOnClickListener(v -> {

        });
    }

    private void assignUIElements() {
        bottomNavigationView = findViewById(R.id.bottom_navigation);
    }

    private void setBottomNavigationMenu() {
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_search) {
            //          startActivity(new Intent(MainMenuActivity.this, SettingsActivity.class));
        }
        if (item.getItemId() == R.id.action_user) {
            startActivity(new Intent(MainMenuActivity.this, SettingsActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
}
