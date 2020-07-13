package piotr.michalkiewicz.mealplannerclient.view.activities.personalization;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import piotr.michalkiewicz.mealplannerclient.R;

public class MealsNumberCustomizationActivity extends AppCompatActivity {

    private Button oneMealnBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meals_number_customization);

        oneMealnBtn = findViewById(R.id.oneMealBtn);

        oneMealnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MealsNumberCustomizationActivity.this,
                        ServingsCustomizationActivity.class);
                startActivity(myIntent);
            }
        });
    }
}
