package application;

import java.time.Duration;
import java.time.LocalTime;


public class ToolBox {
    public static double[] SegInter(double x11, double y11, double x12, double y12, double x21, double y21, double x22, double y22) {
        double dx1 = x12-x11;
        double dy1 = y12-y11;
        double dx2 = x22-x21;
        double dy2 = y22-y21;
        double dxx = x11-x21;
        double dyy = y11-y21;
        double det, t1, t2;
        
        double[] res = new double[2];
        
        det = dy2*dx1-dx2*dy1;
        t1 = (dx1*dyy-dy1*dxx) / det;
        t2 = (dx2*dyy-dy2*dxx) / det;
        
        if (Math.abs(det) < 1.0e-10)
            res = null; //collinéarité
        else if (t1 < 0 || t1 > 1.0)
            res = null; //intersection en dehors
        else if (t2 < 0 || t2 > 1.0)
            res = null; //intersection en dehors
        else {
            res[0] = x11 + t2 * dx1;
            res[1] = y11 + t2 * dy1;
        }
        return res;
    }
    
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
    public static boolean processACollision (ChargerAeroport ch, String dep1, String arr1, String dep2, String arr2, LocalTime t1, LocalTime t2, int dur1, int dur2) {
        boolean colliding = false;
        
        String[] coord1 = (String[])ch.getMapAero().get(dep1);
        String[] coord2 = (String[])ch.getMapAero().get(arr1);
        String[] coord3 = (String[])ch.getMapAero().get(dep2);
        String[] coord4 = (String[])ch.getMapAero().get(arr2);
        
        double x11= (double)CompXY(Integer.valueOf(coord1[2]),
        Integer.valueOf(coord1[3]),Integer.valueOf(coord1[4]),
        coord1[5].charAt(0), Integer.valueOf(coord1[6]),
        Integer.valueOf(coord1[7]),Integer.valueOf(coord1[8]),
        coord1[9].charAt(0))[0];
        
        double y11= (double)CompXY(Integer.valueOf(coord1[2]),
        Integer.valueOf(coord1[3]), Integer.valueOf(coord1[4]),
        coord1[5].charAt(0), Integer.valueOf(coord1[6]),
        Integer.valueOf(coord1[7]), Integer.valueOf(coord1[8]),
        coord1[9].charAt(0))[1];
        
        double x12= (double)CompXY(Integer.valueOf(coord2[2]),
        Integer.valueOf(coord2[3]), Integer.valueOf(coord2[4]),
        coord2[5].charAt(0), Integer.valueOf(coord2[6]),
        
        Integer.valueOf(coord2[7]), Integer.valueOf(coord2[8]),
        coord2[9].charAt(0))[0];
        
        double y12= (double)CompXY(Integer.valueOf(coord2[2]),
        Integer.valueOf(coord2[3]), Integer.valueOf(coord2[4]),
        coord2[5].charAt(0), Integer.valueOf(coord2[6]),
        Integer.valueOf(coord2[7]), Integer.valueOf(coord2[8]),
        coord2[9].charAt(0))[1];
        
        double x21= (double)CompXY(Integer.valueOf(coord3[2]),
        Integer.valueOf(coord3[3]), Integer.valueOf(coord3[4]),
        coord3[5].charAt(0), Integer.valueOf(coord3[6]),
        Integer.valueOf(coord3[7]), Integer.valueOf(coord3[8]),
        coord3[9].charAt(0))[0];
        
        double y21= (double)CompXY(Integer.valueOf(coord3[2]),
        Integer.valueOf(coord3[3]), Integer.valueOf(coord3[4]),
        coord3[5].charAt(0), Integer.valueOf(coord3[6]),
        Integer.valueOf(coord3[7]), Integer.valueOf(coord3[8]),
        coord3[9].charAt(0))[1];
        
        double x22= (double)CompXY(Integer.valueOf(coord4[2]),
        Integer.valueOf(coord4[3]), Integer.valueOf(coord4[4]),
        coord4[5].charAt(0), Integer.valueOf(coord4[6]),
        Integer.valueOf(coord4[7]), Integer.valueOf(coord4[8]),
        coord4[9].charAt(0))[0];
        
        double y22= (double)CompXY(Integer.valueOf(coord4[2]),
        Integer.valueOf(coord4[3]), Integer.valueOf(coord4[4]),
        coord4[5].charAt(0), Integer.valueOf(coord4[6]),
        Integer.valueOf(coord4[7]), Integer.valueOf(coord4[8]),
        coord4[9].charAt(0))[1];
        
        double[] resInters = SegInter(x11,y11,x12,y12,x21,y21,x22,y22);
        if (resInters != null) {
            //coord. of intersection point
            double inX= (double)resInters[0];
            double inY= (double)resInters[1];
            //comp. distances
            
            double distTot1 = compDistanceBetweenPoints(x11,y11,x12,y12);
            double distTot2 = compDistanceBetweenPoints(x21,y21,x22,y22);
            double distInter1 = compDistanceBetweenPoints(x11,y11,inX,inY);
            double distInter2 = compDistanceBetweenPoints(x21,y21,inX,inY);
            
            int duréeVolInter1 = (int)(dur1*distInter1/distTot1);
            int duréeVolInter2 = (int)(dur2*distInter2/distTot2);
            
            t1= t1.plusMinutes(duréeVolInter1);
            t2= t2.plusMinutes(duréeVolInter2);
            
            long elapsedMinutes = Duration.between(t1, t2).toMinutes();
            if (Math.abs(elapsedMinutes)<=15) colliding = true;
        }
        return colliding;
    }
}