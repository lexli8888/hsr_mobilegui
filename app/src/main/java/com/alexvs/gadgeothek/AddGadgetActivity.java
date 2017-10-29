package com.alexvs.gadgeothek;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.alexvs.gadgeothek.domain.Gadget;
import com.alexvs.gadgeothek.service.Callback;
import com.alexvs.gadgeothek.service.LibraryService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.alexvs.gadgeothek.R.layout.support_simple_spinner_dropdown_item;

/**
 * Created by Alexander on 27.10.2017.
 */

public class AddGadgetActivity extends AppCompatActivity implements View.OnClickListener {
    Spinner spinner;
    HashMap hashMap;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addgadget);

        Button button = (Button) findViewById(R.id.addGadgetButton);
        spinner = (Spinner) findViewById(R.id.spinner);
        button.setOnClickListener(this);

        LibraryService.getGadgets(new Callback<List<Gadget>>() {
            @Override
            public void onCompletion(List<Gadget> input) {
                hashMap = new HashMap();
                ArrayList<String> list = new ArrayList<String>();
                for(Gadget gadget:input){

                    list.add(gadget.getName());
                    hashMap.put(gadget.getName(), gadget);
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), support_simple_spinner_dropdown_item, list);
                spinner.setAdapter(adapter);
            }

            @Override
            public void onError(String message) {
                System.out.println(message);
            }
        });



    }

    @Override
    public void onClick(View v) {
        LibraryService.reserveGadget((Gadget) hashMap.get(spinner.getSelectedItem().toString()), new Callback<Boolean>() {
            @Override
            public void onCompletion(Boolean input) {
                System.out.println("Gadget successfully reserved");
            }

            @Override
            public void onError(String message) {
                System.out.println(message);
            }
        });

        Intent intent = new Intent(getApplicationContext(), TabActivity.class);
        startActivity(intent);
    }
}
