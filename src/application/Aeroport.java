package application;

/**
 * La classe Aeroport représente un aéroport avec son code, sa latitude et sa longitude.
 */
public class Aeroport {
    private String code;
    private double latitude;
    private double longitude;

    /**
     * Constructeur de la classe Aeroport.
     *
     * @param code      le code de l'aéroport
     * @param latitude  la latitude de l'aéroport
     * @param longitude la longitude de l'aéroport
     */
    public Aeroport(String code, double latitude, double longitude) {
        this.code = code;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * Obtient le code de l'aéroport.
     *
     * @return le code de l'aéroport
     */
    public String getCode() {
        return code;
    }

    /**
     * Définit le code de l'aéroport.
     *
     * @param code le nouveau code de l'aéroport
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Obtient la latitude de l'aéroport.
     *
     * @return la latitude de l'aéroport
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Définit la latitude de l'aéroport.
     *
     * @param latitude la nouvelle latitude de l'aéroport
     */
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    /**
     * Obtient la longitude de l'aéroport.
     *
     * @return la longitude de l'aéroport
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * Définit la longitude de l'aéroport
     *
     * @param longitude la nouvelle longitude de l'aéroport
     */
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
