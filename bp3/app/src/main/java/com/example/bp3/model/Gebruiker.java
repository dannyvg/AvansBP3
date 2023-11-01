package com.example.bp3.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

//Implementing parcable to make it a parcable object
public class Gebruiker implements Parcelable {
    private String Telefoonnummer, Naam, Regio, Kamer;

    public Gebruiker(String telefoonnummer, String naam, String regio, String kamer) {
        this.Telefoonnummer = telefoonnummer;
        this.Naam = naam;
        this.Regio = regio;
        this.Kamer = kamer;
    }

    protected Gebruiker(Parcel in) {
        Telefoonnummer = in.readString();
        Naam = in.readString();
        Regio = in.readString();
        Kamer = in.readString();
    }

    public static final Creator<Gebruiker> CREATOR = new Creator<Gebruiker>() {
        @Override
        public Gebruiker createFromParcel(Parcel in) {
            return new Gebruiker(in);
        }

        @Override
        public Gebruiker[] newArray(int size) {
            return new Gebruiker[size];
        }
    };

    public String getTelefoonnummer() {
        return Telefoonnummer;
    }

    public void setTelefoonnummer(String telefoonnummer) {
        Telefoonnummer = telefoonnummer;
    }

    public String getNaam() {
        return Naam;
    }

    public void setNaam(String naam) {
        Naam = naam;
    }

    public String getRegio() {
        return Regio;
    }

    public void setRegio(String regio) {
        Regio = regio;
    }

    public String getKamer() {
        return Kamer;
    }

    public void setKamer(String kamer) {
        Kamer = kamer;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(Telefoonnummer);
        parcel.writeString(Naam);
        parcel.writeString(Regio);
        parcel.writeString(Kamer);
    }
}
