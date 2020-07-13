package piotr.michalkiewicz.mealplannerclient.view.activities.personalization;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import piotr.michalkiewicz.mealplannerclient.R;

public class ServingsCustomizationActivity extends AppCompatActivity {

    private Button twoServingsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servings_customization);

        twoServingsBtn = findViewById(R.id.twoServingsBtn);

        twoServingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(ServingsCustomizationActivity.this,
                        CookingTimeCustomizationActivity.class);
                startActivity(myIntent);
            }
        });
    }
}
