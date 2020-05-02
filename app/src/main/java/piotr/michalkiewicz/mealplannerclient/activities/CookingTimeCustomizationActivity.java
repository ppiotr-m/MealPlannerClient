package piotr.michalkiewicz.mealplannerclient.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import piotr.michalkiewicz.mealplannerclient.R;

public class CookingTimeCustomizationActivity extends AppCompatActivity {

    Button quickAndEasyBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cooking_time_customization);

        quickAndEasyBtn = findViewById(R.id.zeroThirtyBtn);

        quickAndEasyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(CookingTimeCustomizationActivity.this, CustomizationFinishActivity.class);
                startActivity(myIntent);
            }
        });
    }
}
