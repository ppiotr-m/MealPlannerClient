package piotr.michalkiewicz.mealplannerclient.view.activities.authorization;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import piotr.michalkiewicz.mealplannerclient.R;
import piotr.michalkiewicz.mealplannerclient.auth.LoginClient;
import piotr.michalkiewicz.mealplannerclient.auth.LoginListener;
import piotr.michalkiewicz.mealplannerclient.support.Constants;
import piotr.michalkiewicz.mealplannerclient.view.activities.menus.MainMenuActivity;

public class LoginActivity extends AppCompatActivity {

    private Button loginBtn;
    private EditText loginET;
    private EditText passwordET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        loginET = findViewById(R.id.loginEt);
        passwordET = findViewById(R.id.passwordEt);

        loginBtn = findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(v->{
            login(loginET.getText().toString(), passwordET.getText().toString());
        });
    }

    private void login(String username, String password){
        LoginClient client = new LoginClient();
        client.login(this, username, password, new LoginListener() {
            @Override
            public void loginSuccessful() {
                Intent myIntent = new Intent(LoginActivity.this, MainMenuActivity.class);
                startActivity(myIntent);
            }

            @Override
            public void loginFailed() {
                Log.i(Constants.TAG, "Login failed");
            }
        });
    }
}
