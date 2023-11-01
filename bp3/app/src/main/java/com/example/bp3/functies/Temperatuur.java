package com.example.bp3.functies;

import android.util.Log;

import com.example.bp3.db.DatabasePostgreSqlConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.IllegalFormatConversionException;

public class Temperatuur {
    static Connection connect;

    //function to get the tempartuur etc
    public static com.example.bp3.model.Temperatuur getTemp() {
        com.example.bp3.model.Temperatuur temp = null;
        try {
            DatabasePostgreSqlConnection db = new DatabasePostgreSqlConnection();
            //connecting to the database
            connect = db.connectionClass();
            //check if there is a connection
            if (connect != null) {
                Statement st = connect.createStatement();
                //query with a join to get the gebruikers naam and telefoonnummer also limits to 1 so there is only 1 active row
                ResultSet rs = st.executeQuery("SELECT temperaturen.gebruiker, gebruikers.naam, temperaturen.luchtkwaliteit, temperaturen.datumtijd, temperaturen.temperatuur, temperaturen.luchtvochtigheid, temperaturen.gaswaarde FROM temperaturen INNER JOIN gebruikers on gebruikers.telefoonnummer = temperaturen.gebruiker;");
                while (rs.next()) {
                    //creating a temperatuur from the model
                    temp = new com.example.bp3.model.Temperatuur(rs.getString(1), rs.getString(2), rs.getString(3), rs.getTimestamp(4), rs.getDouble(5), rs.getString(6), rs.getString(7));
                }
                rs.close();
                st.close();
                //Closing connection
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
        //returning the temperatuur etc
        return temp;
    }

}
