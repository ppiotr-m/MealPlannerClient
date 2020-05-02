package piotr.michalkiewicz.mealplannerclient.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import piotr.michalkiewicz.mealplannerclient.R;

public class AllergyCustomizationActivity extends AppCompatActivity {

    Button allergyGlutenBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allergy_customization);

        allergyGlutenBtn = findViewById(R.id.allergyGlutenBtn);

        allergyGlutenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(AllergyCustomizationActivity.this,
                        DislikedIngredientsCustomization.class);
                startActivity(myIntent);
            }
        });
    }
}
