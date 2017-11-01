package com.alexvs.gadgeothek;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alexvs.gadgeothek.service.Callback;
import com.alexvs.gadgeothek.service.LibraryService;
import com.alexvs.gadgeothek.service.SettingService;


public class SettingsFragment extends Fragment {


    public static String URL_SETTING_KEY = "server_url";
    private EditText mUrlView;

    public OnSaveHandler onSaveHandler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_settings, container, false);


        mUrlView = (EditText) view.findViewById(R.id.url);
        mUrlView.setText(LibraryService.getServerAddress());
        Button mSaveButton = (Button) view.findViewById(R.id.save_button);
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SettingService.setString(view.getContext(), URL_SETTING_KEY, mUrlView.getText().toString());
                LibraryService.setServerAddress(mUrlView.getText().toString());

                if(onSaveHandler !=null){
                    onSaveHandler.onSave();
                }
            }
        });

        Button mLogoutButton = (Button) view.findViewById(R.id.logout_button);

        if (SettingService.getBool(view.getContext(), LoginActivity.SETTING_LOGGED_IN_KEY, false)) {
            mLogoutButton.setVisibility(View.VISIBLE);
        } else {
            mLogoutButton.setVisibility(View.GONE);
        }

        mLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LibraryService.logout(new Callback<Boolean>() {
                    @Override
                    public void onCompletion(Boolean input) {
                        SettingService.setString(view.getContext(), LoginActivity.SETTING_EMAIL_KEY, "");
                        SettingService.setString(view.getContext(), LoginActivity.SETTING_PASSWORD_KEY, "");
                        SettingService.setBool(view.getContext(), LoginActivity.SETTING_LOGGED_IN_KEY, false);

                        Intent intent = new Intent(view.getContext(), LoginActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onError(String message) {

                    }
                });

            }
        });
        return view;
    }



}
