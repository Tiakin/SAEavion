package application.utils.graphic;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.viewer.GeoPosition;

import application.graph.GraphMap;

import org.jxmapviewer.painter.Painter;

/**
 * Paints a route.
 * 
 * @author Killian
 * @author Martin Steiger
 */
public class RoutePainter implements Painter<JXMapViewer> {

	/**
	 * L'anti aliasing.
	 */
	private boolean antiAlias = true;

	/**
	 * Les lignes.
	 */
	private List<GeoPosition[]> lines;

	/**
	 * Les couleurs.
	 */
	private List<Integer> colors;

	/**
	 * Le graphmap.
	 */
	private GraphMap<String, Integer> gm;

	/**
	 * Instancie un nouveau route painter.
	 *
	 * @param gm       le graphmap
	 * @param allLines les lignes
	 * @param colors   les couleurs
	 * 
	 */
	public RoutePainter(GraphMap<String, Integer> gm, List<GeoPosition[]> allLines, List<Integer> colors) {
		this.gm = gm;
		this.lines = new ArrayList<GeoPosition[]>(allLines);
		this.colors = new ArrayList<Integer>(colors);
	}

	/**
	 * Paint.
	 *
	 * @param g   le grahics
	 * @param map la map
	 * @param w   la largeur
	 * @param h   la hauteur
	 * 
	 */
	@Override
	public void paint(Graphics2D g, JXMapViewer map, int w, int h) {
		g = (Graphics2D) g.create();

		// convert from viewport to world bitmap
		Rectangle rect = map.getViewportBounds();
		g.translate(-rect.x, -rect.y);

		if (antiAlias)
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		drawRoute(g, map, false);
		drawRoute(g, map, true);

		g.dispose();
	}

	/**
	 * Draw route.
	 *
	 * @param g       le graphics
	 * @param map     la map
	 * @param colored si dessin en couleur
	 * 
	 */
	private void drawRoute(Graphics2D g, JXMapViewer map, boolean colored) {
		for (int i = 0; i < lines.size(); i++) {
			if (colored) {
				String[] rgb = gm.getColor(colors.get(i)).split("\\(")[1].replace(")", "").split(",");

				g.setColor(new Color(Integer.parseInt(rgb[0]), Integer.parseInt(rgb[1]), Integer.parseInt(rgb[2])));
				g.setStroke(new BasicStroke(3));
			} else {
				g.setColor(Color.BLACK);
				g.setStroke(new BasicStroke(5));
			}

			// convert geo-coordinate to world bitmap pixel
			Point2D pt1 = map.getTileFactory().geoToPixel(lines.get(i)[0], map.getZoom());
			Point2D pt2 = map.getTileFactory().geoToPixel(lines.get(i)[1], map.getZoom());

			g.drawLine((int) pt1.getX(), (int) pt1.getY(), (int) pt2.getX(), (int) pt2.getY());
		}
	}
}
