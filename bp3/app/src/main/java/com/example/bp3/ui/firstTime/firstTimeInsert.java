package com.example.bp3.ui.firstTime;

import static com.example.bp3.functies.Gebruiker.insertGebruiker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.bp3.MainActivity;
import com.example.bp3.R;
import com.example.bp3.ui.home.HomeFragment;

public class firstTimeInsert extends AppCompatActivity {
    EditText Telefoon;
    EditText Naam;

    Spinner spinnerRegio;

    Spinner spinnerKamer;
    Button opslaan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_time_insert);

        Telefoon = (EditText) findViewById(R.id.telEditText);
        Naam = (EditText) findViewById(R.id.naamEditText);
        opslaan = (Button) findViewById(R.id.buttonOpslaan);

        //Spinner Regio
        spinnerRegio = (Spinner) findViewById(R.id.spinnerRegio);
        //Getting the list from a file called arrays
        ArrayAdapter<CharSequence> adapterRegio = ArrayAdapter.createFromResource(this,
                R.array.Regio, android.R.layout.simple_spinner_item);
        adapterRegio.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the spinner with the data (dropdown)
        spinnerRegio.setAdapter(adapterRegio);


        //Spinner Kamers
        spinnerKamer = (Spinner) findViewById(R.id.spinnerKamer);
        //Getting the list from a file called arrays
        ArrayAdapter<CharSequence> adapterKamer = ArrayAdapter.createFromResource(this,
                R.array.Kamers, android.R.layout.simple_spinner_item);
        adapterKamer.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the spinner with the data (dropdown)
        spinnerKamer.setAdapter(adapterKamer);


        //Pressing the button
        opslaan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Creating a new background Thread so the application doesn't freeze
                Thread th = new Thread(){
                    @Override
                    public void run(){
                        //Getting the input data from the user
                        String regio = String.valueOf(spinnerRegio.getSelectedItemPosition());
                        String kamer = String.valueOf(spinnerKamer.getSelectedItemPosition());
                        String tel = String.valueOf(Telefoon.getText());
                        String naam = String.valueOf(Naam.getText());

                        //Check if the input is empty
                        try {
                            if (naam.trim().length() < 1 || tel.trim().length() < 1 || regio.trim().length() < 1 || kamer.trim().length() < 1){
                                //Letting the user know they need to fill in all fields
                                Toast.makeText(firstTimeInsert.this, "Vul naam en telefoonnummer in", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            //Calling the real insert function with the user input as variables
                            insertGebruiker(tel, naam, regio, kamer);

                            //Editing the "FirstTime" fields in the app data to let the app know its no longer the first time the user opens the app
                            SharedPreferences preferences = getSharedPreferences("prefs", MODE_PRIVATE);

                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putBoolean("firstStart", false);
                            editor.apply();

                            //Calling the main activity so the app continues in its normal mode
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));

                        } catch (Exception e){
                            Log.e("Error", e.getMessage());
                        }

                    }
                };
                //Starting the background Thread
                th.start();
            }
        });
    }
}