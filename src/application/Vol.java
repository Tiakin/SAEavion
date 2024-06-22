package application;

import java.time.LocalTime;

public class Vol {
    private String nom;
    private String aeroportDepart;
    private String aeroportArrivee;
    private LocalTime heureDepart;
    private int duree;

    public Vol(String nom, String aeroportDepart, String aeroportArrivee, LocalTime heureDepart, int duree) {
        this.nom = nom;
        this.aeroportDepart = aeroportDepart;
        this.aeroportArrivee = aeroportArrivee;
        this.heureDepart = heureDepart;
        this.duree = duree;
    }

    public String getNom() {
        return nom;
    }

    public String getAeroportDepart() {
        return aeroportDepart;
    }

    public String getAeroportArrivee() {
        return aeroportArrivee;
    }

    public LocalTime getHeureDepart() {
        return heureDepart;
    }

    public int getDuree() {
        return duree;
    }
}
