package piotr.michalkiewicz.mealplannerclient.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import piotr.michalkiewicz.mealplannerclient.R;

public class MainMenuActivity extends AppCompatActivity {

    Button yourWeekBtn;
    Button yourDietBtn;
    Button recipesBtn;
    Button groceriesBtn;
    LinearLayout imageContainer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        yourWeekBtn = (Button) findViewById(R.id.yourWeekBtn);
        yourDietBtn =  (Button) findViewById(R.id.yourDietBtn);
        recipesBtn = (Button) findViewById(R.id.recipiesBtn);
        groceriesBtn = (Button) findViewById(R.id.groceriesBtn);
        imageContainer = (LinearLayout) findViewById(R.id.imageContainer);

        recipesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainMenuActivity.this, RecipesActivity.class);
                startActivity(myIntent);
            }
        });

    }

}
