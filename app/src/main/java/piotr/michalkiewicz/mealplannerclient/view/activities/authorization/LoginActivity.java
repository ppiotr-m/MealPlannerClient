package piotr.michalkiewicz.mealplannerclient.view.activities.authorization;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import piotr.michalkiewicz.mealplannerclient.R;
import piotr.michalkiewicz.mealplannerclient.auth.LoginClient;
import piotr.michalkiewicz.mealplannerclient.auth.LoginListener;
import piotr.michalkiewicz.mealplannerclient.view.activities.dialogs.LoadingDialog;
import piotr.michalkiewicz.mealplannerclient.view.activities.settings.SettingsActivity;
import piotr.michalkiewicz.mealplannerclient.view.activities.menus.MainMenuActivity;

public class LoginActivity extends AppCompatActivity {

    private Button loginBtn;
    private Button settingsTempBtn;
    private EditText loginET;
    private EditText passwordET;
    private View createAccountClickableTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        assingUiElements();
        setOnClickListeners();

    }

    private void assingUiElements(){
        loginET = findViewById(R.id.emailET);
        passwordET = findViewById(R.id.passwordET);
        createAccountClickableTV = findViewById(R.id.createAccountTV);
        loginBtn = findViewById(R.id.loginBtn);
        settingsTempBtn = findViewById(R.id.settingsBtn);
    }

    private void setOnClickListeners(){
        loginBtn.setOnClickListener(v->{
           login(loginET.getText().toString(), passwordET.getText().toString());
        });

        settingsTempBtn.setOnClickListener(v->{
            startActivity(new Intent(LoginActivity.this, SettingsActivity.class));
        });
    }

    public void startCreateAccountActivity(View v){
        Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
        startActivity(intent);
    }

    private void login(String username, String password){
        LoadingDialog dialog = new LoadingDialog(this);
        dialog.startLoadingDialog();
        LoginClient client = new LoginClient();
        client.login(this, username, password, new LoginListener() {
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
