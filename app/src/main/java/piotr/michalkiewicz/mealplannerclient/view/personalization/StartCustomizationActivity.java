package piotr.michalkiewicz.mealplannerclient.view.personalization;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.mealplannerclient.R;

import piotr.michalkiewicz.mealplannerclient.view.main_menu.MainMenuActivity;

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
                // TODO Start customization here
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
