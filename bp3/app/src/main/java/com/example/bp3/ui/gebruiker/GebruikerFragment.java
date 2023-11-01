package com.example.bp3.ui.gebruiker;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.bp3.R;
import com.example.bp3.databinding.FragmentGebruikerBinding;
import com.example.bp3.functies.Gebruiker;
//import com.example.bp3.db.Con;


//Gebruiker fragment
public class GebruikerFragment extends Fragment {

    private FragmentGebruikerBinding binding;
    EditText Telefoon;
    EditText Naam;

    Spinner spinnerRegio;

    Spinner spinnerKamer;
    Button btnUpdate;

    //Creating a variable to store the old phone number in case the user changes it (To use in the query)
    String oldTel = null;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GebruikerViewModel gebruikerViewModel =
                new ViewModelProvider(this).get(GebruikerViewModel.class);

        binding = FragmentGebruikerBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textGallery;
        gebruikerViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);



        //Spinner Regio's
        spinnerRegio = (Spinner) root.findViewById(R.id.spinnerRegio);
        //Getting the list from a file called arrays
        ArrayAdapter<CharSequence> adapterRegio = ArrayAdapter.createFromResource(getActivity(),
                R.array.Regio, android.R.layout.simple_spinner_item);

        adapterRegio.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the spinner with the data (dropdown)
        spinnerRegio.setAdapter(adapterRegio);


        //Spinner Kamers
        spinnerKamer = (Spinner) root.findViewById(R.id.spinnerKamer);
        //Getting the list from a file called arrays
        ArrayAdapter<CharSequence> adapterKamer = ArrayAdapter.createFromResource(getActivity(),
                R.array.Kamers, android.R.layout.simple_spinner_item);

        adapterKamer.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the spinner with the data (dropdown)
        spinnerKamer.setAdapter(adapterKamer);



        //Getting EditText
        Telefoon = (EditText) root.findViewById(R.id.telEditText);
        Naam = (EditText) root.findViewById(R.id.naamEditText);
        btnUpdate = (Button) root.findViewById(R.id.buttonOpslaan);

        //Calling getGebruikerData
        getGebruiker();

        //Update btn on click
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Getting the users input
                String regio = String.valueOf(spinnerRegio.getSelectedItemPosition());
                String kamer = String.valueOf(spinnerKamer.getSelectedItemPosition());
                String tel = String.valueOf(Telefoon.getText());
                String naam = String.valueOf(Naam.getText());

                //Calling the update function with the user input variables and the old phone number to use in the query
                updateGebruiker(oldTel, tel, naam, regio, kamer);

            }
        });

        //Root AKA View
        return root;

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    //Making a handler to handle the ui (Getting data from the getGebruiker function)
    Handler gebruikerHandler = new Handler(Looper.getMainLooper()){
        //Function to handle the message
        @Override
        public void handleMessage(@NonNull Message msg) {
        super.handleMessage(msg);
            Bundle objBundle = msg.getData();
            //Getting variables from the message/bundle/pakkage
            String tel = objBundle.getString("Tel");
            String naam = objBundle.getString("Naam");
            //Getting the spinners/dropdown positions
            int regio = Integer.parseInt(objBundle.getString("Regio"));
            int kamer = Integer.parseInt(objBundle.getString("Kamer"));

            //setting the name and telefoon
            Naam.setText(naam);
            Telefoon.setText(tel);

            //saving old number
            oldTel = tel;

            //setting the Spinners/dropdowns
            spinnerRegio.setSelection(regio);
            spinnerKamer.setSelection(kamer);

        }
    };


    //Function that gets the gebruikers data
    public void getGebruiker(){
        //Making a message/bundle to give to the handler to handle the UI
        Message gebruikerData = gebruikerHandler.obtainMessage();
        Bundle gebBundle = new Bundle();

        //Creating a new background Thread so the application doesn't freeze
        Thread th = new Thread(){
            @Override
            public void run() {
                //making local variables
                String Tel = null;
                String Naam =null;
                String Regio = null;
                String Kamer = null;
                try {
                    //Calling the real function to get the data from the database
                    com.example.bp3.model.Gebruiker g = Gebruiker.getGebruikerData();
                    Tel = g.getTelefoonnummer();
                    Naam = g.getNaam();
                    Regio = g.getRegio();
                    Kamer = g.getKamer();

                } catch (Exception e) {
                    Log.e("Error", e.getMessage());
                }
                //Putting the variables from the db into a message/bundle
                gebBundle.putString("Tel", Tel);
                gebBundle.putString("Naam", Naam);
                gebBundle.putString("Regio", Regio);
                gebBundle.putString("Kamer", Kamer);
                gebruikerData.setData(gebBundle);

                //Sending the gebruiker data to the handler to handle the UI
                gebruikerHandler.sendMessage(gebruikerData);
            }
        };
        //Starting the background Thread
        th.start();
    }

    //Function to update the gebruiker
    public void updateGebruiker(String oldtel,String telefoon, String naam, String regio, String kamer){
        //Creating a new background Thread so the application doesn't freeze
        Thread th = new Thread() {
            @Override
            public void run() {
                //Check if the input is empty
                if (naam.trim().length() < 1 || telefoon.trim().length() < 1 || regio.trim().length() < 1 || kamer.trim().length() < 1){
                    Toast.makeText(getActivity(), "Vul naam en telefoonnummer in", Toast.LENGTH_SHORT).show();
                    return;
                }
                //Calling the real update function and giving it the variables
                Gebruiker.updateGebruiker(oldtel, telefoon, naam, regio, kamer);
                getGebruiker();
            }
        };
        //Starting the background Thread
        th.start();
    }

}
