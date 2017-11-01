package com.alexvs.gadgeothek;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.alexvs.gadgeothek.service.Callback;
import com.alexvs.gadgeothek.service.LibraryService;
import com.alexvs.gadgeothek.service.SettingService;

public class LoginActivity extends AppCompatActivity {
    public static String SETTING_EMAIL_KEY = "email";
    public static String SETTING_PASSWORD_KEY = "password";
    public static String SETTING_LOGGED_IN_KEY = "logged_in";

    private EditText mEmailView;
    private EditText mPasswordView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmailView = (EditText) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);


        loadSettings();

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                performLogin(true);
            }
        });

        Button mSettingButton = (Button) findViewById(R.id.settings);
        mSettingButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                showSettings();
            }
        });
    }

    private void loadSettings() {
        SettingService.setBool(getApplicationContext(), SETTING_LOGGED_IN_KEY, false);

        String serverUrl = SettingService.getString(getApplicationContext(), SettingsFragment.URL_SETTING_KEY, "http://mge7.dev.ifs.hsr.ch/public");
        LibraryService.setServerAddress(serverUrl);

        mEmailView.setText(SettingService.getString(getApplicationContext(), SETTING_EMAIL_KEY, ""));
        mPasswordView.setText(SettingService.getString(getApplicationContext(), SETTING_PASSWORD_KEY, ""));

        if(!mEmailView.getText().toString().isEmpty() && !mPasswordView.getText().toString().isEmpty()) {
            performLogin(false);
        }
    }

    private void showSettings() {
        Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
        startActivity(intent);
    }

    private void showAlert(String text) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
        builder.setMessage(text);
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.show();
    }

    private void performLogin(final Boolean alertOnFailure) {
        final String email = mEmailView.getText().toString();
        final String password = mPasswordView.getText().toString();

        LibraryService.login(email, password, new Callback<Boolean>() {
            @Override
            public void onCompletion(Boolean input) {
                if (input) {
                    SettingService.setString(getApplicationContext(), SETTING_EMAIL_KEY, email);
                    SettingService.setString(getApplicationContext(), SETTING_PASSWORD_KEY, password);
                    SettingService.setBool(getApplicationContext(), SETTING_LOGGED_IN_KEY, true);

                    Intent intent = new Intent(getApplicationContext(), TabActivity.class);
                    startActivity(intent);
                } else if(alertOnFailure) {
                    showAlert("Internal error");
                }
            }

            @Override
            public void onError(String message) {
                switch (message) {
                    case "user does not exist":
                        SettingService.setString(getApplicationContext(), SETTING_EMAIL_KEY, email);
                        SettingService.setString(getApplicationContext(), SETTING_PASSWORD_KEY, password);
                        Intent intent = new Intent(getApplicationContext(), RegistrationActivity.class);
                        startActivity(intent);
                        break;
                    case "incorrect password":
                    default:
                        if(alertOnFailure) {
                            showAlert("Incorrect password");
                        }
                        break;
                }
            }
        });
    }
}

