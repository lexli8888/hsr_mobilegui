package com.alexvs.gadgeothek;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.alexvs.gadgeothek.service.Callback;
import com.alexvs.gadgeothek.service.LibraryService;
import com.alexvs.gadgeothek.service.SettingService;

public class SettingsActivity extends AppCompatActivity {
    public static String URL_SETTING_KEY = "server_url";

    private EditText mUrlView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mUrlView = (EditText) findViewById(R.id.url);

        setContentView(R.layout.activity_settings);

        Button mSaveButton = (Button) findViewById(R.id.save_button);
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            SettingService.setString(getApplicationContext(), URL_SETTING_KEY, mUrlView.getText().toString());
            }
        });

        Button mLogoutButton = (Button) findViewById(R.id.logout_button);

        if (SettingService.getBool(getApplicationContext(), LoginActivity.SETTING_LOGGED_IN_KEY, false)) {
            mLogoutButton.setVisibility(View.GONE);
        } else {
            mLogoutButton.setVisibility(View.VISIBLE);
        }

        mLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LibraryService.logout(new Callback<Boolean>() {
                    @Override
                    public void onCompletion(Boolean input) {
                        SettingService.setString(getApplicationContext(), LoginActivity.SETTING_EMAIL_KEY, "");
                        SettingService.setString(getApplicationContext(), LoginActivity.SETTING_PASSWORD_KEY, "");
                        SettingService.setBool(getApplicationContext(), LoginActivity.SETTING_LOGGED_IN_KEY, false);

                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onError(String message) {

                    }
                });
            }
        });
    }
}
