package com.example.bp3.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.sql.Time;
import java.sql.Timestamp;

//Implementing parcable to make it a parcable object
public class Temperatuur implements Parcelable  {

    private String Telefoon, Gebruiker, Luchtkwaliteit, Luchtvochtigheid, Gaswaarde;
    private double Temperatuur;
    private Timestamp DatumTijd;

    public Temperatuur(String telefoon, String gebruiker, String luchtkwaliteit, Timestamp datumTijd, Double temperatuur, String luchtvochtigheid, String gaswaarde) {
        this.Telefoon = telefoon;
        this.Gebruiker = gebruiker;
        this.Luchtkwaliteit = luchtkwaliteit;
        this.DatumTijd = datumTijd;
        this.Temperatuur = temperatuur;
        this.Luchtvochtigheid = luchtvochtigheid;
        this.Gaswaarde = gaswaarde;
    }

    protected Temperatuur(Parcel in) {
        Telefoon = in.readString();
        Gebruiker = in.readString();
        Luchtkwaliteit = in.readString();
        Luchtvochtigheid = in.readString();
        Gaswaarde = in.readString();
        Temperatuur = in.readDouble();
    }

    public static final Creator<Temperatuur> CREATOR = new Creator<Temperatuur>() {
        @Override
        public Temperatuur createFromParcel(Parcel in) {
            return new Temperatuur(in);
        }

        @Override
        public Temperatuur[] newArray(int size) {
            return new Temperatuur[size];
        }
    };

    public String getGebruiker() {
        return Gebruiker;
    }

    public void setGebruiker(String gebruiker) {
        Gebruiker = gebruiker;
    }

    public String getLuchtkwaliteit() {
        return Luchtkwaliteit;
    }

    public void setLuchtkwaliteit(String luchtkwaliteit) {
        Luchtkwaliteit = luchtkwaliteit;
    }

    public String getLuchtvochtigheid() {
        return Luchtvochtigheid;
    }

    public void setLuchtvochtigheid(String luchtvochtigheid) {
        Luchtvochtigheid = luchtvochtigheid;
    }

    public String getGaswaarde() {
        return Gaswaarde;
    }

    public void setGaswaarde(String gaswaarde) {
        Gaswaarde = gaswaarde;
    }

    public double getTemperatuur() {
        return Temperatuur;
    }

    public void setTemperatuur(double temperatuur) {
        Temperatuur = temperatuur;
    }

    public Timestamp getDatumTijd() {
        return DatumTijd;
    }

    public void setDatumTijd(Timestamp datumTijd) {
        DatumTijd = datumTijd;
    }

    public String getTelefoon() {
        return Telefoon;
    }

    public void setTelefoon(String telefoon) {
        Telefoon = telefoon;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(Telefoon);
        parcel.writeString(Gebruiker);
        parcel.writeString(Luchtkwaliteit);
        parcel.writeString(String.valueOf(DatumTijd));
        parcel.writeDouble(Temperatuur);
        parcel.writeString(Luchtvochtigheid);
        parcel.writeString(Gaswaarde);
    }
}
