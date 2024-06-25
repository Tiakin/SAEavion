package application.utils.graphic;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.viewer.WaypointRenderer;

import application.utils.CustomWaypoint;

import org.jxmapviewer.viewer.Waypoint;
import org.jxmapviewer.viewer.GeoPosition;

/**
 * La classe CustomWaypointRenderer.
 * 
 * @author Farouk
 * @author Killian
 */
public class CustomWaypointRenderer implements WaypointRenderer<Waypoint> {

	/**
	 * Le waypoint image.
	 */
	private Image waypointImage;

	/**
	 * Instancie un nouveau custom waypoint renderer.
	 * 
	 */
	public CustomWaypointRenderer() {
		// Charger l'image personnalisée
		try (InputStream stream = getClass().getResourceAsStream("/ressources/images/aeroport.png")) {
			if (stream != null) {
				waypointImage = ImageIO.read(stream);
			} else {

				System.err.println("L'image d'aéroport n'a pas été trouvée ou le chemin est incorrect.");
				waypointImage = null;
			}
		} catch (IOException e) {
			System.err.println("Erreur lors du chargement de l'image d'aéroport : " + e.getMessage());
			waypointImage = null;
		}
	}

	/**
	 * Paint waypoint.
	 *
	 * @param g        le graphics
	 * @param map      la map
	 * @param waypoint le waypoint
	 * 
	 */
	@Override
	public void paintWaypoint(Graphics2D g, JXMapViewer map, Waypoint waypoint) {
		if (waypointImage != null && waypoint instanceof CustomWaypoint) {
			CustomWaypoint customWaypoint = (CustomWaypoint) waypoint;
			GeoPosition pos = customWaypoint.getPosition();
			Point2D point = map.getTileFactory().geoToPixel(pos, map.getZoom());

			// Taille désirée pour l'image du waypoint (par exemple, 30x30 pixels)
			int width = 30;
			int height = 30;

			int x = (int) point.getX() - width / 2;
			int y = (int) point.getY() - height / 2;

			g.drawImage(waypointImage, x, y, width, height, null);

		}
	}

}