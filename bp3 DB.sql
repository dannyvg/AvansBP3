/* CREATE Gebruikers TABLE*/
CREATE TABLE Gebruikers (
    Telefoonnummer varchar(255) NOT NULL UNIQUE,
    Naam varchar(255) NOT NULL,
    Regio varchar(255) NOT NULL,
    Kamer varchar(255) NOT NULL,
    PRIMARY KEY (Telefoonnummer)
);

/* CREATE Temperaturen TABLE*/
CREATE TABLE Temperaturen (
	Gebruiker varchar(255) NOT NULL,
	Luchtkwaliteit varchar(255) NOT NULL,
    DatumTijd timestamp NOT NULL UNIQUE,
    Temperatuur double precision NOT NULL,
    Luchtvochtigheid varchar(255) NOT NULL,
    Gaswaarde varchar(255) NOT NULL,
    PRIMARY KEY (DatumTijd),
    FOREIGN KEY (Gebruiker) REFERENCES Gebruikers(Telefoonnummer)
);

/* CREATE Favorieten TABLE*/
CREATE TABLE Favorieten (
	Gebruiker varchar(255) NOT NULL,
    DatumTijd timestamp NOT NULL UNIQUE,
    Temperatuur double precision NOT NULL,
    Luchtvochtigheid varchar(255) NOT NULL,
    Gaswaarde varchar(255) NOT NULL,
    PRIMARY KEY (DatumTijd),
    FOREIGN KEY (Gebruiker) REFERENCES Gebruikers(Telefoonnummer)
);

/* CONTROLE: UNIQUE CONSTRAIN FOR THE COMBINATION OF GEBRUIKER AND DATUMTIJD */
ALTER TABLE Temperaturen
  ADD CONSTRAINT uq_Temp UNIQUE(Gebruiker, DatumTijd);
  
/* FAVORIETEN: UNIQUE CONSTRAIN FOR THE COMBINATION OF GEBRUIKER AND DATUMTIJD */
ALTER TABLE Favorieten
  ADD CONSTRAINT uq_Fav UNIQUE(Gebruiker, DatumTijd);