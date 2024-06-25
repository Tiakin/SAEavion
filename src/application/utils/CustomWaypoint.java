package application.utils;

import org.jxmapviewer.viewer.DefaultWaypoint;
import org.jxmapviewer.viewer.GeoPosition;

/**
 * La classe CustomWaypoint.
 * 
 * @author Farouk
 */
public class CustomWaypoint extends DefaultWaypoint {

	/**
	 * Le code.
	 */
	private String code;

	/**
	 * Instancie un nouveau custom waypoint.
	 *
	 * @param coord les coordonées
	 * @param code  le code
	 */
	public CustomWaypoint(GeoPosition coord, String code) {
		super(coord);
		this.code = code;
	}

	/**
	 * Récupère le code.
	 *
	 * @return le code
	 */
	public String getCode() {
		return code;
	}
}
