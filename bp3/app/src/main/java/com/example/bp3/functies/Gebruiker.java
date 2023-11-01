package com.example.bp3.functies;

import android.util.Log;

import com.example.bp3.db.DatabasePostgreSqlConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.IllegalFormatConversionException;

public class Gebruiker {
    static Connection connect;

    //function to get the gebruiker from the database
    public static com.example.bp3.model.Gebruiker getGebruikerData() {
        com.example.bp3.model.Gebruiker gebruiker = null;
        try {
            DatabasePostgreSqlConnection db = new DatabasePostgreSqlConnection();
            //connecting to the database
            connect = db.connectionClass();
            //check if there is a connection
            if (connect != null) {
                Statement st = connect.createStatement();
                ResultSet rs = st.executeQuery("Select * from gebruikers limit 1");
                while (rs.next()) {
                    //getting the gebruiker to use in the app
                    gebruiker = new com.example.bp3.model.Gebruiker(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4));
                }
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
        //returning the user
        return gebruiker;
    }

    //function to update the gebruiker
    public static void updateGebruiker(String oldtel, String telefoon, String naam, String regio, String kamer) {
        //creating a gebruiker from the model to use in the function
        com.example.bp3.model.Gebruiker gebruiker = new com.example.bp3.model.Gebruiker(telefoon, naam, regio, kamer);
        try {
            DatabasePostgreSqlConnection db = new DatabasePostgreSqlConnection();
            //connecting to the database
            connect = db.connectionClass();
            //check if there is a connection
            if (connect != null) {
                Statement st = connect.createStatement();
                //query
                ResultSet rs = st.executeQuery("UPDATE gebruikers SET telefoonnummer = '" + gebruiker.getTelefoonnummer() +"', naam = '" + gebruiker.getNaam() + "', regio = '" + gebruiker.getRegio() + "', kamer = '" + gebruiker.getKamer() + "' WHERE telefoonnummer = '" + oldtel + "';");
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

    //function to insert a gebruiker
    public static void insertGebruiker(String telefoon, String naam, String regio, String kamer){
        //Creating a gebruiker from the model
        com.example.bp3.model.Gebruiker gebruiker = new com.example.bp3.model.Gebruiker(telefoon, naam, regio, kamer);
        try {
            DatabasePostgreSqlConnection db = new DatabasePostgreSqlConnection();
            //connecting to the database
            connect = db.connectionClass();
            //check if there is a connection
            if (connect != null) {
                Statement st = connect.createStatement();
                //query
                ResultSet rs = st.executeQuery("insert into gebruikers (telefoonnummer, naam, regio, kamer) values ('" + gebruiker.getTelefoonnummer() + "', '" + gebruiker.getNaam() + "', '" + gebruiker.getRegio() + "', '" + gebruiker.getKamer() + "');");
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
}
