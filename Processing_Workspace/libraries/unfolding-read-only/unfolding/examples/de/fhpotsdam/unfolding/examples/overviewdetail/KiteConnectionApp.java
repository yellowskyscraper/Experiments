package de.fhpotsdam.unfolding.examples.overviewdetail;

import processing.core.PApplet;
import codeanticode.glgraphics.GLConstants;

public class KiteConnectionApp extends PApplet {

	OverviewPlusDetailConnection connection;

	public void setup() {
		size(800, 600, GLConstants.GLGRAPHICS);

		connection = new KiteConnection(this);
	}

	public void draw() {
		background(255);

		connection.draw();
		//kiteConnection.drawDebug();
	}

	public void mouseMoved() {
		connection.setOverviewPosition(mouseX, mouseY);
	}

	public void mouseDragged() {
		connection.setDetailPosition(mouseX, mouseY);
	}

}
