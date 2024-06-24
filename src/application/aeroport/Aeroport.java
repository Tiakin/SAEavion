package application.aeroport;

import org.jxmapviewer.viewer.Waypoint;

/**
 * La classe Aeroport représente un aéroport avec son code, sa latitude et sa longitude.
 * 
 * @author Farouk
 * @author Killian
 */
public class Aeroport {
    
    /**
     * Le code.
     */
    private String code;
    
    /**
     * La latitude.
     */
    private double latitude;
    
    /**
     * La longitude.
     */
    private double longitude;
    
    /**
     * est visible.
     */
    private boolean visible;
    
    /**
     * Le waypoint.
     */
    private Waypoint waypoint;

    /**
     * Constructeur de la classe Aeroport.
     *
     *
     * @param code      le code de l'aéroport
     * @param latitude  la latitude de l'aéroport
     * @param longitude la longitude de l'aéroport
     * 
     */
    public Aeroport(String code, double latitude, double longitude) {
        this.code = code;
        this.latitude = latitude;
        this.longitude = longitude;
        this.visible = false;
        this.waypoint = null;
    }

    /**
     * Obtient le code de l'aéroport.
     *
     * @return le code de l'aéroport
     * 
     */
    public String getCode() {
        return code;
    }

    /**
     * Regarde si c'est visible.
     *
     * @return true, si c'est visible
     * 
     */
    public boolean isVisible() {
    	return visible;
    }
    
    /**
     * change le visible.
     *
     * @param bool le nouveau visible
     * 
     */
    public void setVisible(boolean bool) {
    	visible = bool;
    }
    
    /**
     * Récupère le waypoint.
     *
     * @return le waypoint
     * 
     */
    public Waypoint getWaypoint() {
    	return waypoint;
    }
    
    /**
     * change le waypoint.
     *
     * @param way le nouveau waypoint
     * 
 
     */
    public void setWaypoint(Waypoint way) {
    	waypoint = way;
    }
    /**
     * Définit le code de l'aéroport.
     *
     * @param code le nouveau code de l'aéroport
     * 
 
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Obtient la latitude de l'aéroport.
     *
     * @return la latitude de l'aéroport
     * 
 
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Définit la latitude de l'aéroport.
     *
     * @param latitude la nouvelle latitude de l'aéroport
     * 
 
     */
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    /**
     * Obtient la longitude de l'aéroport.
     *
     * @return la longitude de l'aéroport
     * 
 
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * Définit la longitude de l'aéroport.
     *
     * @param longitude la nouvelle longitude de l'aéroport
     * 
 
     */
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    
    /**
     * To string.
     *
     * @return le String
     * 
 
     */
    @Override
    public String toString() {
        return "Aeroport{" +
                "code='" + code + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
