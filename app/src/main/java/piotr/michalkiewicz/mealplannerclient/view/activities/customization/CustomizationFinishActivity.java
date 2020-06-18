package piotr.michalkiewicz.mealplannerclient.view.activities.customization;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import piotr.michalkiewicz.mealplannerclient.R;
import piotr.michalkiewicz.mealplannerclient.view.activities.menus.MainMenuActivity;

public class CustomizationFinishActivity extends AppCompatActivity {

    private Button nextBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customization_finish);

        nextBtn = findViewById(R.id.customizationNextBtn);

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(CustomizationFinishActivity.this, MainMenuActivity.class);
                startActivity(myIntent);
            }
        });
    }
}
