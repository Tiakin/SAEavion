package application;

import java.time.LocalTime;

public class Vol {
    private String nom;
    private Aeroport aeroportDepart;
    private Aeroport aeroportArrivee;
    private LocalTime heureDepart;
    private int duree;

    public Vol(String nom, Aeroport aeroportDepart, Aeroport aeroportArrivee, LocalTime heureDepart, int duree) {
        this.nom = nom;
        this.aeroportDepart = aeroportDepart;
        this.aeroportArrivee = aeroportArrivee;
        this.heureDepart = heureDepart;
        this.duree = duree;
    }

    public String getNom() {
        return nom;
    }

    public Aeroport getAeroportDepart() {
        return aeroportDepart;
    }

    public Aeroport getAeroportArrivee() {
        return aeroportArrivee;
    }

    public LocalTime getHeureDepart() {
        return heureDepart;
    }

    public int getDuree() {
        return duree;
    }
}
