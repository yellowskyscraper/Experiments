package findingfaults;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;

import de.fhpotsdam.unfolding.Map;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.providers.Microsoft;

import findingfaults.app.data.CrustalDeformationVectors;
import findingfaults.app.data.EarthquakeTimeline;
import findingfaults.app.data.FaultLineAnimation;


public class FindingFaults extends PApplet 
{
	private static final long serialVersionUID = 1L;

	Map map;
	Location sanFrancisco;
	int wid = 1400;
	int hei = 1050;

	//| Marker Points & Text
	PImage baymodel;
	Location coordTL;
	Location coordBR;
	PFont displayText;
	PFont displaySubText;
	
	//| Data
	EarthquakeTimeline earthquakeTimeline;
	FaultLineAnimation faultComplexAni;
	CrustalDeformationVectors cristalVelocities;
	
	public void setup() 
	{
		wid = screenWidth;
		hei = screenHeight;
		
		background(0);
		frameRate(100);
		size(wid, hei);

		//| Model Boundaries & Image
		baymodel = loadImage("data/images/bay.jpg");
		coordTL = new Location(38.339f, -122.796f);
		coordBR = new Location(37.342f, -121.781f);
		
		//| San Francisco
	    sanFrancisco = new Location(37.85316995894978f, -121.95510864257812f);
	    map = new Map(this, 0, 0, width, height, new Microsoft.AerialProvider());
		map.zoomAndPanTo(sanFrancisco, 10);
		
		//| Position Projection to top Left Coordinate
		float[] tl = map.getScreenPositionFromLocation(coordTL);
		Location nl = map.getLocationFromScreenPosition(wid/2+tl[0], hei/2+tl[1]);
		map.zoomAndPanTo(nl, 10);
//		MapUtils.createDefaultEventDispatcher(this, map);
		
		//| GIF Animation
		faultComplexAni = new FaultLineAnimation(this);
 		faultComplexAni.setup();
		
		//| Earthquake feed from USGS		
		earthquakeTimeline = new EarthquakeTimeline(this);
		earthquakeTimeline.setup(map, wid, hei);

		//| Crustal Velocities
//		cristalVelocities = new CrustalDeformationVectors(this);
//		cristalVelocities.setup(map, wid, hei);
	
		//| Copy
		displayText = createFont("data/fonts/Explo/Explo-Ultra.otf", 50);
		displaySubText = createFont("data/fonts/Explo/Explo-Medi.otf", 50);
	}

	public void draw() 
	{
		//| Map and Overlay
		background(0);
		this.renderMap();

		//| GIF Animation
		faultComplexAni.draw(map);	
		
		//| Display Data
		earthquakeTimeline.update();
		earthquakeTimeline.draw(map);
		
		//| Crustal Velocities
//		cristalVelocities.draw(map);	
		
		//| Model Bounds & Text
//		this.markers();
//		this.copy("The Eye Of The Earth", "But instead am wandering awed about on a splintered " +
//				"\nwreck I've come to care for, whose gnawed trees breathe \na delicate air.");
	}
	
	public void copy(String t, String s)
	{
		float[] tl = map.getScreenPositionFromLocation(coordTL);

		smooth();

		fill(0,0,0);
		textFont(displayText, 40);
		text(t, tl[0] + 29, tl[1] + 91);
		textFont(displaySubText, 20);
		text(s, tl[0] + 29, tl[1] + 126);

		fill(255,255,255);
		textFont(displayText, 40);
		text(t, tl[0] + 30, tl[1] + 90);
		textFont(displaySubText, 20);
		text(s, tl[0] + 30, tl[1] + 125);
	}
	
	public void markers()
	{
		//| Markers for Rough Models boundaries. 
		float[] tl = map.getScreenPositionFromLocation(coordTL);
		float[] br = map.getScreenPositionFromLocation(coordBR);

		float newX = tl[0];
		float newY = tl[1];
		float newW = br[0] - tl[0] + 190; 
		float newH = br[1] - tl[1];
		
		noStroke();
		fill(0,0,0);
		ellipse(tl[0] - 1, tl[1] + 1, 5, 5);
		ellipse(br[0] - 1, tl[1] + 1, 5, 5);
		ellipse(br[0] - 1, br[1] + 1, 5, 5);
		ellipse(tl[0] - 1, br[1] + 1, 5, 5);
		
		fill(255,255,255);
		ellipse(tl[0], tl[1], 5, 5);
		ellipse(br[0], tl[1], 5, 5);
		ellipse(br[0], br[1], 5, 5);
		ellipse(tl[0], br[1], 5, 5);
		
		//| Percentage Increase
		fill(0,0,0);
		ellipse(newX + newW - 1, newY + 1, 5, 5);
		ellipse(newX + newW - 1, newY + newH + 1, 5, 5);
		
		fill(255,255,255);
		ellipse(newX + newW, newY, 5, 5);
		ellipse(newX + newW, newY + newH, 5, 5);

	}
		
	public void renderMap()
	{
		map.draw();
		fill(0,0,0,100);
		rect(-1,-1,screenWidth+2,screenHeight+2);
		
		//| Percentage increase for Bay Model projection
		float[] tl = map.getScreenPositionFromLocation(coordTL);
		float[] br = map.getScreenPositionFromLocation(coordBR);
		float newX = tl[0];
		float newY = tl[1];
		float newW = br[0] - tl[0] + 190; 
		float newH = br[1] - tl[1];
		
		image(baymodel, newX, newY, newW, newH); //| Zoom 10 Scale
		image(baymodel, newX, newY, 1051, 1051); //| Bay Model Full Scale
	}
	
	public static void main(String _args[]) 
	{
		PApplet.main(new String[] { "--present", "--bgcolor=#111111", "--hide-stop", FindingFaults.class.getName() });
	}
}
