package application;

import java.time.Duration;
import java.time.LocalTime;


public class ToolBox {

    
    public static double compDistanceBetweenPoints(double x1, double y1, double x2, double y2) {
        return Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
    }
    
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
            if (Math.abs(elapsedMinutes) <= 15) {
                colliding = true;
            }
        }

        return colliding;
    }

}