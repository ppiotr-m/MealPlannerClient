package piotr.michalkiewicz.mealplannerclient.view.activities.personalization;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import piotr.michalkiewicz.mealplannerclient.R;

public class DietCustomizationActivity extends AppCompatActivity {

    private Button standardDietBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet_customization);

        standardDietBtn = findViewById(R.id.dietStandardBtn);

        standardDietBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(DietCustomizationActivity.this, AllergyCustomizationActivity.class);
                startActivity(myIntent);
            }
        });
    }
}
