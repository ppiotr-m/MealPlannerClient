package piotr.michalkiewicz.mealplannerclient.view.activities.authorization;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Objects;

import piotr.michalkiewicz.mealplannerclient.R;
import piotr.michalkiewicz.mealplannerclient.auth.LoginClient;
import piotr.michalkiewicz.mealplannerclient.auth.LoginListener;
import piotr.michalkiewicz.mealplannerclient.auth.MyPreference;
import piotr.michalkiewicz.mealplannerclient.utils.ConstantValues;
import piotr.michalkiewicz.mealplannerclient.view.activities.dialogs.LoadingDialog;
import piotr.michalkiewicz.mealplannerclient.view.activities.menus.MainMenuActivity;

public class LoginActivity extends AppCompatActivity {

    public static SharedPreferences myPreferences;
    private Button loginBtn;
    private EditText loginET;
    private EditText passwordET;
    private View createAccountClickableTV;
    private LoginClient loginClient = new LoginClient();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // I doesn't have idea yest where to put it it Must be Activity
        myPreferences = getApplicationContext().getSharedPreferences(ConstantValues.MY_PREFERENCE_NAME, MODE_PRIVATE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        assingUiElements();
        checkLoginState();
        setOnClickListeners();
    }

    private void checkLoginState() {
        loginClient.refreshToken(Objects.requireNonNull(new MyPreference().getRefreshToken()), new LoginListener() {
            @Override
            public void loginFailed() {

            }

            @Override
            public void loginSuccessful() {
                Intent myIntent = new Intent(LoginActivity.this, MainMenuActivity.class);
                startActivity(myIntent);
            }
        });
    }

    private void assingUiElements(){
        loginET = findViewById(R.id.emailET);
        passwordET = findViewById(R.id.passwordET);
        createAccountClickableTV = findViewById(R.id.createAccountTV);
        loginBtn = findViewById(R.id.loginBtn);
    }

    private void setOnClickListeners(){
        loginBtn.setOnClickListener(v->{
           login(loginET.getText().toString(), passwordET.getText().toString());
        });
    }

    public void startCreateAccountActivity(View v){
        Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
        startActivity(intent);
    }

    private void login(String username, String password){
        LoadingDialog dialog = new LoadingDialog(this);
        dialog.startLoadingDialog();
        loginClient.login(username, password, new LoginListener() {
            @Override
            public void loginSuccessful() {
                dialog.dismissDialog();
                Intent myIntent = new Intent(LoginActivity.this, MainMenuActivity.class);
                startActivity(myIntent);
            }

            @Override
            public void loginFailed() {
                dialog.dismissDialog();
                LoginActivity.this.runOnUiThread(()-> {
                        Toast.makeText(LoginActivity.this, R.string.login_failed, Toast.LENGTH_LONG).show();
                });
            }
        });
    }
}
