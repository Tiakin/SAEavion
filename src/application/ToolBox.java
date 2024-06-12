/*
 * La boite à outils
 */
package application;

import java.time.Duration;
import java.time.LocalTime;

import javax.swing.JOptionPane;


/**
 * La classe ToolBox.
 */
public class ToolBox {

    
    /**
     * Comp distance between points.
     *
     * @param x1 le x du point 1
     * @param y1 le y du point 1
     * @param x2 le x du point 2
     * @param y2 le y du point 2
     * @return la distance
     */
    public static double compDistanceBetweenPoints(double x1, double y1, double x2, double y2) {
        return Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
    }
    
    /**
     * Comp XY.
     *
     * @param latD le degrée de la latitude
     * @param latM la minute de la latitude 
     * @param latS la seconde de la latitude
     * @param latDir la direction pour la latitude
     * @param longD le degrée de la longitude
     * @param longM la minute de la longitude
     * @param longS la seconde de la longitude
     * @param longDir la direction pour la longitude
     * @return le double[] x et y
     */
    public static double[] CompXY(int latD, int latM, int latS, char latDir, int longD, int longM,int longS, char longDir) {
        double[] res = new double[2];
        int coefLat = (latDir=='N') ? 1 : -1;
        int coefLong = (longDir=='E') ? 1 : -1;
        
        double decLat = coefLat * (latD + latM/60.0 + latS/3600.0);
        double decLong = coefLong * (longD + longM/60.0 + longS/3600.0);
        double radLat = decLat * Math.PI / 180.0;
        double radLong = decLong * Math.PI / 180.0;
        double x = 6371 * Math.cos(radLat) * Math.sin(radLong);
        double y = 6371 * Math.cos(radLat) * Math.cos(radLong);
        
        res[0] = x;
        res[1] = y;
        
        return res;
    }
    
    /**
     * Seg inter.
     *
     * @param x11 le x du point 1 de la ligne 1
     * @param y11 le y du point 1 de la ligne 1
     * @param x12 le x du point 2 de la ligne 1
     * @param y12 le y du point 2 de la ligne 1
     * @param x21 le x du point 1 de la ligne 2
     * @param y21 le y du point 1 de la ligne 2
     * @param x22 le x du point 2 de la ligne 2
     * @param y22 le y du point 2 de la ligne 2
     * @return le double[] point d'intersection, null sinon
     */
    public static double[] SegInter(double x11, double y11, double x12, double y12, double x21, double y21, double x22, double y22) {
        double dx1 = x12 - x11;
        double dy1 = y12 - y11;
        double dx2 = x22 - x21;
        double dy2 = y22 - y21;
        double dxx = x11 - x21;
        double dyy = y11 - y21;
        double det = dy2 * dx1 - dx2 * dy1;

        if (Math.abs(det) < 1.0e-10) {
            // Les segments sont collinéaires
            if (Math.abs(dy1 * dxx - dx1 * dyy) < 1.0e-10 && Math.abs(dy2 * dxx - dx2 * dyy) < 1.0e-10) {
                // Vérifier si les segments se chevauchent
                double[] bounds1 = {Math.min(x11, x12), Math.max(x11, x12), Math.min(y11, y12), Math.max(y11, y12)};
                double[] bounds2 = {Math.min(x21, x22), Math.max(x21, x22), Math.min(y21, y22), Math.max(y21, y22)};
                if (bounds1[1] < bounds2[0] || bounds1[0] > bounds2[1] || bounds1[3] < bounds2[2] || bounds1[2] > bounds2[3]) {
                    return null; // Les segments ne se chevauchent pas
                }
                // Retourner un point d'intersection commun (arbitraire)
                return new double[]{Math.max(bounds1[0], bounds2[0]), Math.max(bounds1[2], bounds2[2])};
            }
            return null;
        }

        double t1 = (dx2 * dyy - dy2 * dxx) / det;
        double t2 = (dx1 * dyy - dy1 * dxx) / det;

        if (t1 < 0 || t1 > 1.0 || t2 < 0 || t2 > 1.0) {
            return null; // Intersection en dehors
        }

        return new double[]{x11 + t1 * dx1, y11 + t1 * dy1};
    }

    /**
     * Process A collision.
     *
     * @param ch the ch
     * @param dep1 the dep 1
     * @param arr1 the arr 1
     * @param dep2 the dep 2
     * @param arr2 the arr 2
     * @param t1 the t 1
     * @param t2 the t 2
     * @param dur1 the dur 1
     * @param dur2 the dur 2
     * @return true, si c'est vrai
     */
    public static boolean processACollision(ChargerAeroport ch, String dep1, String arr1, String dep2, String arr2, LocalTime t1, LocalTime t2, int dur1, int dur2) {
        boolean colliding = false;

        String[] coord1 = (String[]) ch.getMapAero().get(dep1);
        String[] coord2 = (String[]) ch.getMapAero().get(arr1);
        String[] coord3 = (String[]) ch.getMapAero().get(dep2);
        String[] coord4 = (String[]) ch.getMapAero().get(arr2);

        double[] xy1 = CompXY(
            Integer.valueOf(coord1[2]), Integer.valueOf(coord1[3]), Integer.valueOf(coord1[4]), coord1[5].charAt(0), 
            Integer.valueOf(coord1[6]), Integer.valueOf(coord1[7]), Integer.valueOf(coord1[8]), coord1[9].charAt(0)
        );
        double x11 = xy1[0];
        double y11 = xy1[1];

        double[] xy2 = CompXY(
            Integer.valueOf(coord2[2]), Integer.valueOf(coord2[3]), Integer.valueOf(coord2[4]), coord2[5].charAt(0), 
            Integer.valueOf(coord2[6]), Integer.valueOf(coord2[7]), Integer.valueOf(coord2[8]), coord2[9].charAt(0)
        );
        double x12 = xy2[0];
        double y12 = xy2[1];

        double[] xy3 = CompXY(
            Integer.valueOf(coord3[2]), Integer.valueOf(coord3[3]), Integer.valueOf(coord3[4]), coord3[5].charAt(0), 
            Integer.valueOf(coord3[6]), Integer.valueOf(coord3[7]), Integer.valueOf(coord3[8]), coord3[9].charAt(0)
        );
        double x21 = xy3[0];
        double y21 = xy3[1];

        double[] xy4 = CompXY(
            Integer.valueOf(coord4[2]), Integer.valueOf(coord4[3]), Integer.valueOf(coord4[4]), coord4[5].charAt(0), 
            Integer.valueOf(coord4[6]), Integer.valueOf(coord4[7]), Integer.valueOf(coord4[8]), coord4[9].charAt(0)
        );
        double x22 = xy4[0];
        double y22 = xy4[1];

        // Vérifier si les segments se touchent
        double[] resInters = SegInter(x11, y11, x12, y12, x21, y21, x22, y22);
        if (resInters != null) {
            double inX = resInters[0];
            double inY = resInters[1];

            double distTot1 = compDistanceBetweenPoints(x11, y11, x12, y12);
            double distTot2 = compDistanceBetweenPoints(x21, y21, x22, y22);
            double distInter1 = compDistanceBetweenPoints(x11, y11, inX, inY);
            double distInter2 = compDistanceBetweenPoints(x21, y21, inX, inY);

            int dureeVolInter1 = (int) (dur1 * distInter1 / distTot1);
            int dureeVolInter2 = (int) (dur2 * distInter2 / distTot2);

            LocalTime t1Inter = t1.plusMinutes(dureeVolInter1);
            LocalTime t2Inter = t2.plusMinutes(dureeVolInter2);

            long elapsedMinutes = Duration.between(t1Inter, t2Inter).toMinutes();
            if (Math.abs(elapsedMinutes) < 15) {
            	colliding = true;
            }
        }

        // Vérifier si les deux avions décollent du même point avec un écart de moins de 15 minutes
        if (x11 == x21 && y11 == y21 && Math.abs(Duration.between(t1, t2).toMinutes()) < 15) {
        	colliding = true;
        }

        // Vérifier si les deux avions atterrissent au même point avec un écart de moins de 15 minutes
        if (x12 == x22 && y12 == y22 && Math.abs(Duration.between(t1.plusMinutes(dur1), t2.plusMinutes(dur2)).toMinutes()) < 15) {
        	colliding = true;
        }

        // Calculer les temps d'arrivée des deux avions
        LocalTime t1Arr = t1.plusMinutes(dur1);
        LocalTime t2Arr = t2.plusMinutes(dur2);
		
		//Vérifier si les deux avions décollent face à face sur la même ligne
		if (x11 == x22 && y11 == y22 && x12 == x21 && y12 == y21) {
		    // Vérifier si un avion décolle pendant que l'autre vole ou si un avion décolle avant 15 minutes après l'atterrissage de l'autre
		    if ((t1.isAfter(t2) && t1.isBefore(t2Arr)) || 
		        (t2.isAfter(t1) && t2.isBefore(t1Arr)) ||
		        (Math.abs(Duration.between(t1, t2Arr).toMinutes()) < 15) ||
		        (Math.abs(Duration.between(t2, t1Arr).toMinutes()) < 15)) {
		    	colliding = true; // Collision si l'un décolle pendant que l'autre vole ou si l'un décolle avant 15 minutes après l'atterrissage de l'autre
		    }
		}

        // Vérifier si deux avions décollent du même endroit et vont au même endroit
        if (x11 == x21 && y11 == y21 && x12 == x22 && y12 == y22) {
            if (Math.abs(Duration.between(t1Arr, t2Arr).toMinutes()) < 15) {
            	colliding = true; // Collision si les temps d'arrivée sont proches
            }
            // Vérifier si l'avion part plus tard mais arrive plus tôt
            if ((t2.isAfter(t1) && (t2Arr.isBefore(t1Arr) || t2Arr.equals(t1Arr) || (t2Arr.isAfter(t1) && t2Arr.isBefore(t1Arr)))) || 
                (t1.isAfter(t2) && (t1Arr.isBefore(t2Arr) || t1Arr.equals(t2Arr) || (t1Arr.isAfter(t2) && t1Arr.isBefore(t2Arr))))) {
            	colliding = true; // Collision si l'avion part plus tard mais arrive plus tôt
            }
        }


        return colliding;
    }
    
    /**
     * envoie un message d'erreur.
     *
     * @param text le texte
     */
    public static void sendErrorMessage(String text) {
    	JOptionPane.showMessageDialog(null, text, "Erreur", JOptionPane.ERROR_MESSAGE);
    }
    
}