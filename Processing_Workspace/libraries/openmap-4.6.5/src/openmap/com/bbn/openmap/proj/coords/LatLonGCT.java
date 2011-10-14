//**********************************************************************
//
//<copyright>
//
//BBN Technologies
//10 Moulton Street
//Cambridge, MA 02138
//(617) 873-8000
//
//Copyright (C) BBNT Solutions LLC. All rights reserved.
//
//</copyright>
//**********************************************************************
//
//$Source:
///cvs/darwars/ambush/aar/src/com/bbn/ambush/mission/MissionHandler.java,v
//$
//$RCSfile: LatLonGCT.java,v $
//$Revision: 1.1.2.1 $
//$Date: 2008/01/25 17:44:27 $
//$Author: dietrick $
//
//**********************************************************************

package com.bbn.openmap.proj.coords;

import java.awt.geom.Point2D;

import com.bbn.openmap.LatLonPoint;

public class LatLonGCT implements GeoCoordTransformation {

    public final static LatLonGCT INSTANCE = new LatLonGCT();
    
    public Point2D forward(double lat, double lon) {
        return forward(lat, lon, new Point2D.Double());
    }

    public Point2D forward(double lat, double lon, Point2D ret) {
        ret.setLocation(lon, lat);
        return ret;
    }

    public LatLonPoint inverse(double x, double y) {
        return inverse(x, y, new LatLonPoint());
    }

    public LatLonPoint inverse(double x, double y, LatLonPoint ret) {
        ret.setLatLon((float) y, (float) x);
        return ret;
    }

}
