package com.example.bp3.ui.favoriet;

import android.app.LauncherActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.bp3.R;
import com.example.bp3.databinding.FragmentFavorietenBinding;
import com.example.bp3.functies.Favoriet;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//Favorieten fragment
public class FavorietFragment extends Fragment {
    ListView favListView;
    //List for filling the listview with database values
    private List<Map<String,String>> mydatalist=null;
    private FragmentFavorietenBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        FavorietViewModel favorietViewModel =
                new ViewModelProvider(this).get(FavorietViewModel.class);

        binding = FragmentFavorietenBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textSlideshow;
        favorietViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        //Linking the variable to the listview
        favListView = (ListView) root.findViewById(R.id.FavListView);

        //Calling getFav function
        GetFav();
        //Letting the user know what is happening
        Toast.makeText(getActivity(), "Favorieten worden ingeladen", Toast.LENGTH_LONG).show();

        //Listview Item on click
        favListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    //Getting the row data
                    Map<String, String> g = mydatalist.get(i);
                    //Extracting the timestamp from the row
                    String c = g.get("listTijd");

                    //Making the timestamp string into a timestamp
                    Timestamp j = Timestamp.valueOf(c);

                    //Creating a later variable that adds 1 sec and creating a before variable that subst-rect 1 sec from the timestamp
                    // This is because on the sql browser its no possible to select using the timestamp only it needs to be "Between .. and .."
                    Timestamp later = new Timestamp(j.getTime() + (1 * 1000L));
                    Timestamp before = new Timestamp(j.getTime() - (1 * 1000L));

                    //Logging to check if its works
                    Log.e("Row", String.valueOf(later));
                    Log.e("Row", String.valueOf(before));

                    //Calling the delete function with the before and after variable
                    DeleteFav(before, later);

                } catch (Exception e){
                    //Catching a error and logging it
                    Log.e("Error", e.getMessage());
                }
            }
        });
        //Returning the screen
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    //Function that calls the function to delete the favoriet
    public void DeleteFav(Timestamp before, Timestamp after){
        //Letting the user know what is happening
        Toast.makeText(getActivity(), "Verwijderen", Toast.LENGTH_LONG).show();
        //Creating a new background Thread so the application doesn't freeze
        Thread th = new Thread() {
            @Override
            public void run() {
                try {
                    Favoriet f = new Favoriet();
                    //Calling the real delete function with the before and after variable
                    f.deleteFav(before, after);

                    //Running the ui thread to let the user know it deleted the favoriet
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(), "Verwijderd!", Toast.LENGTH_SHORT).show();
                            //Calling the getFav function to update the listview
                            GetFav();
                        }
                    });

                } catch (Exception e){
                    //Catching a error and logging it
                    Log.e("Error", e.getMessage());
                }
            }

        };
        
        //Starting the background Thread
        th.start();
    }

    SimpleAdapter a;
    public void GetFav(){
        //Creating a new background Thread so the application doesn't freeze
        Thread th = new Thread() {
            @Override
            public void run() {
                try {
                    //Creating a variable for the getList() function
                    Favoriet f = new Favoriet();
                    mydatalist = f.getList();

                    //Creating a From variable to let the listview know where its coming from
                    String[] from= {"listTemp", "listLuchtV", "listGasW", "listTijd"};
                    //Creating a Tow function to let the app know where to put the data in the list view item
                    int[] Tow = {R.id.listTemp, R.id.listLuchtV, R.id.listGasW, R.id.listTijd};

                    //Putting the data into the listview
                    //Layout.list calls a new XML file that represents a listview Item
                    a = new SimpleAdapter(getActivity(), mydatalist, R.layout.list,from, Tow);

                    //Running the ui thread to put the data into the listview
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            favListView.setAdapter(a);
                        }
                    });

                } catch (Exception e){
                    //Catching a error and logging it
                    Log.e("Error", e.getMessage());
                }
            }
        };
        //Starting the background Thread
        th.start();
    }
}