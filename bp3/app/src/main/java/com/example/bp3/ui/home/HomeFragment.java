package com.example.bp3.ui.home;

import static com.example.bp3.functies.Gebruiker.insertGebruiker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.bp3.MainActivity;
import com.example.bp3.R;
import com.example.bp3.databinding.FragmentHomeBinding;
import com.example.bp3.functies.Favoriet;
import com.example.bp3.functies.Gebruiker;
import com.example.bp3.functies.Temperatuur;
import com.example.bp3.ui.firstTime.firstTimeInsert;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HomeFragment extends Fragment {

    TextView Gas;
    TextView Temp;
    TextView LuchtV;
    TextView Status;

    TextView Welkom;

    ImageButton fbtn;

    private Handler handleRefresh;
    private FragmentHomeBinding binding;

    private String Telefoonnummer;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);


        //Linking the variable to the listview
        Gas = (TextView) root.findViewById(R.id.Gas);
        Temp = (TextView) root.findViewById(R.id.Temp);
        LuchtV = (TextView) root.findViewById(R.id.LuchtV);
        Status = (TextView) root.findViewById(R.id.status);
        Welkom = (TextView) root.findViewById(R.id.welkom);
        fbtn = (ImageButton) root.findViewById(R.id.favorite_button);

        //Calling function to get the temperatuur
        getTemp();

        //Handler to refresh the app every 5 seconds
        handleRefresh = new Handler(Looper.getMainLooper());
        Runnable runRefresh = new Runnable() {
            @Override
            public void run() {
                //Calling function to get the temperatuur
                getTemp();
                //5secs
                handleRefresh.postDelayed(this, 5 * 1000);
            }
        };
        //Calling the handler
        handleRefresh.postDelayed(runRefresh, 5 * 1000);

        //Favoriet button on click
        fbtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //letting the user know what is happening
                Toast.makeText(getActivity(), "Bezig met Opslaan!", Toast.LENGTH_SHORT).show();
                try {
                    //setting the button to disabled so the user can't spam
                    fbtn.setEnabled(false);
                    new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //if the data isn't loaded the function won't do anything
                            if (Temp.getText().equals("...")){
                                fbtn.setEnabled(true);
                                return;
                            }
                            //Getting the date and time
                            Date date = new Date();
                            Timestamp DatumTijd = new Timestamp(date.getTime());

                            String temp = (String) Temp.getText();
                            //Clearing the C from the strings
                            String tempC = temp.replace("C", "");

                            //Creating a double of the string to use in the database
                            Double tempD = Double.valueOf(tempC);
                            String luchtV = (String) LuchtV.getText();
                            String gas = (String) Gas.getText();

                            //Calling the function to add favoriet
                            addFav(Telefoonnummer,DatumTijd, tempD, luchtV, gas);
                            //enable the button
                            fbtn.setEnabled(true);
                        }//Waiting 5sec before it can run again
                    }, 5000);

                } catch (Exception e) {
                    Log.e("Error", e.getMessage());
                }

            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    Handler tempHandler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {

            super.handleMessage(msg);
            //Getting variables from the message/bundle/pakkage
            Bundle objBundle = msg.getData();
            String Naam = objBundle.getString("Naam");
            String LuchtK = objBundle.getString("LuchtK");
            String Temperatuur = objBundle.getString("Temperatuur");
            String LuchtVo = objBundle.getString("LuchtV");
            String Gasw = objBundle.getString("Gas");

            //using the name and luchkwaliteit
            Welkom.setText("Welkom, " + Naam);
            Status.setText("Luchtkwaliteit: " + LuchtK);

            //Cards
            Gas.setText(Gasw);
            //adding c to the string
            Temp.setText(Temperatuur + "C");
            //adding % to the string
            LuchtV.setText(LuchtVo + "%");

        }
    };

    public void getTemp(){
        Message tempData = tempHandler.obtainMessage();
        Bundle gebBundle = new Bundle();

        //Creating a new background Thread so the application doesn't freeze
        Thread th = new Thread(){
            @Override
            public void run() {
                String Naam = null;
                String LuchtK = null;
                Double Temperatuur =null;
                String LuchtV = null;
                String Gas = null;
                try {
                    //calling the function to get the data and putting it into variables
                    com.example.bp3.model.Temperatuur T = com.example.bp3.functies.Temperatuur.getTemp();
                    Naam = T.getGebruiker();
                    LuchtK = T.getLuchtkwaliteit();
                    Temperatuur = T.getTemperatuur();
                    LuchtV = T.getLuchtvochtigheid();
                    Gas = T.getGaswaarde();
                    Telefoonnummer = T.getTelefoon();

                } catch (Exception e) {
                    Log.e("Error", e.getMessage());
                }
                //Putting the variables from the db into a message/bundle
                gebBundle.putString("Naam", Naam);
                gebBundle.putString("LuchtK", LuchtK);
                gebBundle.putString("Temperatuur", String.valueOf(Temperatuur));
                gebBundle.putString("LuchtV", LuchtV);
                gebBundle.putString("Gas", Gas);
                tempData.setData(gebBundle);

                //Sending the gebruiker data to the handler to handle the UI
                tempHandler.sendMessage(tempData);
            }
        };
        //Starting the background Thread
        th.start();
    }

    public void addFav(String tel, Timestamp datumtijd, Double Temperatuur, String luchtV, String gasW){
        //Creating a new background Thread so the application doesn't freeze
        Thread th = new Thread(){
            @Override
            public void run(){
                try {
                    Favoriet.createFavoriet(tel, datumtijd, Temperatuur, luchtV, gasW);
                    //letting the user know it was saved through the ui thread
                    getActivity().runOnUiThread(() -> {
                        Toast.makeText(getActivity(), "Opgeslagen!", Toast.LENGTH_SHORT).show();
                    });

                } catch (Exception e){
                    Log.e("Error", e.getMessage());
                }

            }
        };
        //Starting the background Thread
        th.start();
    }
}