package application;

import org.jxmapviewer.viewer.DefaultWaypoint;
import org.jxmapviewer.viewer.GeoPosition;

public class CustomWaypoint extends DefaultWaypoint {
    private String code;

    public CustomWaypoint(GeoPosition coord, String code) {
        super(coord);
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
