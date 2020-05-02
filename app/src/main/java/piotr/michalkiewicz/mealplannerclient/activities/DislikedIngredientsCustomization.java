package piotr.michalkiewicz.mealplannerclient.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import piotr.michalkiewicz.mealplannerclient.R;

public class DislikedIngredientsCustomization extends AppCompatActivity {

    Button avocadoBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disliked_ingredients_customization);

        avocadoBtn = findViewById(R.id.avocadoBtn);

        avocadoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(DislikedIngredientsCustomization.this,
                        MealsNumberCustomizationActivity.class);
                startActivity(myIntent);
            }
        });
    }
}
