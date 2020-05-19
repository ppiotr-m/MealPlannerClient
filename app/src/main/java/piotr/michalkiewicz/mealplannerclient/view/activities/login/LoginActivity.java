package piotr.michalkiewicz.mealplannerclient.view.activities.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import piotr.michalkiewicz.mealplannerclient.R;
import piotr.michalkiewicz.mealplannerclient.connection.auth.AuthTester;
import piotr.michalkiewicz.mealplannerclient.view.activities.customization.StartCustomizationActivity;

public class LoginActivity extends AppCompatActivity {

    AuthTester authTester = new AuthTester();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button loginBtn = (Button) findViewById(R.id.loginBtn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                authTester.ping();
                Intent myIntent = new Intent(LoginActivity.this, StartCustomizationActivity.class);
                LoginActivity.this.startActivity(myIntent);
            }
        });


    }

}
