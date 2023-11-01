package com.example.bp3.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.sql.Timestamp;

//Implementing parcable to make it a parcable object
public class Favoriet implements Parcelable  {

    private String Gebruiker, Luchtvochtigheid, Gaswaarde;
    private double Temperatuur;
    private Timestamp DatumTijd;

    public Favoriet(String gebruiker, Timestamp datumTijd, Double temperatuur, String luchtvochtigheid, String gaswaarde) {
        this.Gebruiker = gebruiker;
        this.DatumTijd = datumTijd;
        this.Temperatuur = temperatuur;
        this.Luchtvochtigheid = luchtvochtigheid;
        this.Gaswaarde = gaswaarde;
    }

    protected Favoriet(Parcel in) {
        Gebruiker = in.readString();
        Luchtvochtigheid = in.readString();
        Gaswaarde = in.readString();
        Temperatuur = in.readDouble();
    }


    public static final Creator<Favoriet> CREATOR = new Creator<Favoriet>() {
        @Override
        public Favoriet createFromParcel(Parcel in) {
            return new Favoriet(in);
        }

        @Override
        public Favoriet[] newArray(int size) {
            return new Favoriet[size];
        }
    };

    public String getGebruiker() {
        return Gebruiker;
    }

    public void setGebruiker(String gebruiker) {
        Gebruiker = gebruiker;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(Gebruiker);
        parcel.writeString(String.valueOf(DatumTijd));
        parcel.writeDouble(Temperatuur);
        parcel.writeString(Luchtvochtigheid);
        parcel.writeString(Gaswaarde);
    }

}
