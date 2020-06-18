package piotr.michalkiewicz.mealplannerclient.view.activities.customization;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import piotr.michalkiewicz.mealplannerclient.R;
import piotr.michalkiewicz.mealplannerclient.view.activities.menus.MainMenuActivity;

public class StartCustomizationActivity extends AppCompatActivity {

    private Button startCustomizationBtn;
    private Button skipCustomizationBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_customization);

        startCustomizationBtn = findViewById(R.id.startCustomizationBtn);
        skipCustomizationBtn = findViewById(R.id.skipCustomizationBtn);

        startCustomizationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(StartCustomizationActivity.this, DietCustomizationActivity.class);
                startActivity(myIntent);
            }
        });

        skipCustomizationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(StartCustomizationActivity.this, MainMenuActivity.class);
                startActivity(myIntent);
            }
        });

    }
}
