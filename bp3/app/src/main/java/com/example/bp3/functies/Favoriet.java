package com.example.bp3.functies;

import android.util.Log;

import com.example.bp3.db.DatabasePostgreSqlConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.IllegalFormatConversionException;
import java.util.List;
import java.util.Map;

public class Favoriet {
    static Connection connect;

    //Function to create a favoriet
    public static void createFavoriet(String telefoon, Timestamp datumtijd, double temperatuur, String luchtv, String gasw){
        //making a new favoriet from the model
        com.example.bp3.model.Favoriet favoriet = new com.example.bp3.model.Favoriet(telefoon, datumtijd, temperatuur, luchtv, gasw);
        try {
            //connecting to the database
            DatabasePostgreSqlConnection db = new DatabasePostgreSqlConnection();
            connect = db.connectionClass();
            //check if there is a connection
            if (connect != null) {
                Statement st = connect.createStatement();
                //query
                ResultSet rs = st.executeQuery("insert into favorieten (gebruiker, datumtijd, temperatuur, luchtvochtigheid, gaswaarde) values ('" + favoriet.getGebruiker() + "', '" + favoriet.getDatumTijd() + "', '" + favoriet.getTemperatuur() + "', '" + favoriet.getLuchtvochtigheid() + "','" + favoriet.getGaswaarde() + "');");
                rs.close();
                st.close();
                connect.close();
            }
        } catch (IllegalFormatConversionException Ie){
            Log.e("Error", Ie.getMessage());
        }
        catch (SQLException s){
            Log.e("Error", s.getMessage());
        }
        catch (Exception e) {
            Log.e("Error", e.getMessage());
        }

    }

    //Function to show the favorieten
    public List<Map<String,String>>getList() {
        //making a new list to put the data in
        List<Map<String, String>> data = null;
        data = new ArrayList<Map<String, String>>();

        try {
            DatabasePostgreSqlConnection db = new DatabasePostgreSqlConnection();
            //connecting to the database
            connect = db.connectionClass();
            //check if there is a connection
            if (connect != null) {
                Statement st = connect.createStatement();
                //query
                ResultSet rs = st.executeQuery("Select * from favorieten");
                while (rs.next()) {
                    //while there is data comng through the function adds the data to a package to use in the listview
                    Map<String, String> favorieten = new HashMap<String, String>();
                    favorieten.put("listTijd", rs.getString("datumtijd"));
                    favorieten.put("listTemp", rs.getString("Temperatuur") + "C");
                    favorieten.put("listLuchtV", rs.getString("luchtvochtigheid"));
                    favorieten.put("listGasW", rs.getString("gaswaarde") + "G");
                    data.add(favorieten);
                }
                rs.close();
                st.close();
                connect.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //returning the package
        return data;
    }

    //function to delete a favoriet
    public void deleteFav(Timestamp before, Timestamp after){
        try {
            DatabasePostgreSqlConnection db = new DatabasePostgreSqlConnection();
            //connecting to the database
            connect = db.connectionClass();
            //check if there is a connection
            if (connect != null) {
                Statement st = connect.createStatement();
                ResultSet rs = st.executeQuery("DELETE FROM favorieten WHERE datumtijd between '" + before + "' and '"+ after + "'");
                rs.close();
                st.close();
                connect.close();
            }
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
        }
    }

}
