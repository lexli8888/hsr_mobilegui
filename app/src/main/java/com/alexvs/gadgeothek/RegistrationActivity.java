package com.alexvs.gadgeothek;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.alexvs.gadgeothek.service.Callback;
import com.alexvs.gadgeothek.service.LibraryService;
import com.alexvs.gadgeothek.service.SettingService;

public class RegistrationActivity extends AppCompatActivity {

    private EditText mStudentNoView;
    private EditText mUsernameView;
    private EditText mEmailView;
    private EditText mPasswordView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mStudentNoView = (EditText) findViewById(R.id.studentno);
        mUsernameView = (EditText) findViewById(R.id.username);
        mEmailView = (EditText) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);

        setContentView(R.layout.activity_registration);

        Button mSaveButton = (Button) findViewById(R.id.register_button);
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = mEmailView.getText().toString();
                final String username = mUsernameView.getText().toString();
                final String password = mPasswordView.getText().toString();
                final String studentNo = mStudentNoView.getText().toString();

                LibraryService.register(email, password, username, studentNo, new Callback<Boolean>() {
                    @Override
                    public void onCompletion(Boolean input) {
                        if(input) {
                            LibraryService.login(email, password, new Callback<Boolean>() {
                                @Override
                                public void onCompletion(Boolean input) {
                                    SettingService.setString(getApplicationContext(), LoginActivity.SETTING_EMAIL_KEY, email);
                                    SettingService.setString(getApplicationContext(), LoginActivity.SETTING_PASSWORD_KEY, password);
                                    SettingService.setBool(getApplicationContext(), LoginActivity.SETTING_LOGGED_IN_KEY, true);

                                    Intent intent = new Intent(getApplicationContext(), TabActivity.class);
                                    startActivity(intent);
                                }

                                @Override
                                public void onError(String message) {
                                    showAlert("login failed");
                                }
                            });
                        }
                    }

                    @Override
                    public void onError(String message) {
                        showAlert("registration failed");
                    }
                });
            }
        });
    }


    private void showAlert(String text) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
        builder.setMessage(text);
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.show();
    }

}
