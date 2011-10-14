package de.fhpotsdam.unfolding.examples.overviewdetail;

import processing.core.PApplet;
import codeanticode.glgraphics.GLConstants;
import de.fhpotsdam.unfolding.Map;
import de.fhpotsdam.unfolding.geo.Location;

/**
 * A loupe on a map. The loupe, a small movable map, always updates its view according to its
 * position on the large background map.
 * 
 */
public class KiteConnectedMapOnStaticMap extends PApplet {

	Map mapStatic;
	Map mapZoom;

	float mapZoomX = 100;
	float mapZoomY = 100;

	OverviewPlusDetailConnection kiteConnection;

	public void setup() {
		size(800, 600, GLConstants.GLGRAPHICS);

		mapStatic = new Map(this, "static", 0, 0, 800, 600);
		// MapUtils.createDefaultEventDispatcher(this, mapStatic);

		mapZoom = new Map(this, "zoom", 400, 300, 150, 150);
		mapZoom.setTweening(false);
		mapZoom.zoomToLevel(6);

		kiteConnection = new KiteConnection(this);
	}

	public void draw() {
		background(0);

		mapStatic.draw();

		kiteConnection.draw();

		mapZoom.draw();
	}

	public void mouseDragged() {
		// Move the small map to mouse position, but center it around it
		mapZoomX = mouseX - mapZoom.mapDisplay.getWidth() / 2;
		mapZoomY = mouseY - mapZoom.mapDisplay.getHeight() / 2;
		mapZoom.move(mapZoomX, mapZoomY);

		kiteConnection.setOverviewPosition(mouseX, mouseY);
	}

	public void mouseClicked() {
		kiteConnection.setDetailPosition(mouseX, mouseY);

		// Read geo location of the mouse position from the background map
		Location locationOnStaticMap = mapStatic.mapDisplay.getLocationFromScreenPosition(mouseX, mouseY);
		// Pan the small map toward that location
		mapZoom.panTo(locationOnStaticMap);
	}

}
