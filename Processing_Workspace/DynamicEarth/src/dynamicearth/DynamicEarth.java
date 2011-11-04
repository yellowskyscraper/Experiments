package dynamicearth;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;
import processing.xml.XMLElement;

import de.fhpotsdam.unfolding.*;
import de.fhpotsdam.unfolding.geo.*;
import de.fhpotsdam.unfolding.providers.Microsoft;
import de.fhpotsdam.unfolding.utils.MapUtils;

import dynamicearth.app.Ball;

public class DynamicEarth extends PApplet 
{
	private static final long serialVersionUID = 1L;

	//| Map And Label Text
	Location sanFrancisco;
	Map map;
	XMLElement xml;
	ArrayList<Ball> balls;
	
	PFont displayText;
	PFont displaySubText;
	
	//| Data
	String lastQuake;
	//| Projector 1400, 1050
	//| My Screen 1680, 1050
	int wid = 1400;
	int hei = 1050;
	boolean parsed;
	int parse = 0;
	boolean interstishial = false;
	int interstish = 0;

	//| Marker Points
	Location coordTL;
	Location coordBR;
	PImage baymodel;
	
	public void setup() 
	{
		wid = screenWidth;
		hei = screenHeight;
		
		background(0);
		size(wid, hei);

		//| Model Boundaries & Image
		baymodel = loadImage("data/images/bay.jpg");
		coordTL = new Location(38.339f, -122.796f);
		coordBR = new Location(37.342f, -121.781f);
		
		//| San Francisco
	    sanFrancisco = new Location(37.85316995894978f, -121.95510864257812f);
	    map = new Map(this, 0, 0, width, height, new Microsoft.AerialProvider());
		map.zoomAndPanTo(sanFrancisco, 10);
		//MapUtils.createDefaultEventDispatcher(this, map);
		this.setLeft();
		
		
		//| Data Sources
		parsed = false;
		balls = new ArrayList<Ball>();
		//xml = new XMLElement(this, "data/php/all.xml");
		xml = new XMLElement(this, "http://earthquake.usgs.gov/earthquakes/catalogs/eqs7day-M2.5.xml");

		int tracker = 0;
		for(int i = 0; i < xml.getChild(0).getChildCount(); i ++)
		{
			if(xml.getChild(0).getChild(i).getName().equals("item")){
				String name = xml.getChild(0).getChild(i).getChild(1).getContent();
				float lat = new Float(xml.getChild(0).getChild(i).getChild(4).getContent());
			    float lon = new Float(xml.getChild(0).getChild(i).getChild(5).getContent());
				
			    if(tracker == 0){
			    	tracker = 1;
			    	lastQuake = name;
			    }
			    
				Location coord = new Location(lat,lon);
				float[] p = map.getScreenPositionFromLocation(coord);
				
				if(p[0] > 0 && p[0] < wid && p[1] > 0 && p[1] < hei) {
					Ball b = new Ball(this);
					b.setup(name, lat, lon);
					balls.add(b);
				}
			}
		}
		
		//| Copy
		displayText = createFont("data/fonts/Explo/Explo-Ultra.otf", 50);
		displaySubText = createFont("data/fonts/Explo/Explo-Medi.otf", 50);
	}

	public void draw() 
	{
		//| Map and Overlay
		background(0);
		map.draw();
		this.newMapProjection();
		fill(0,0,0,170);
		rect(-1,-1,screenWidth+2,screenHeight+2);

		//| Display Data
		this.totalQuakes();
		
		//| Model Bounds
		//this.markers();
		this.copy("The Eye Of The Earth", lastQuake + "\n\nBut instead am wandering awed about on a splintered " +
				"\nwreck I've come to care for, whose gnawed trees breathe \na delicate air.");
	}
	
	public void totalQuakes()
	{	
		//| Iterate Quakes & Draw
		for (int i = balls.size()-1; i >= 0; i--) 
		{
			Ball ball = (Ball) balls.get(i);
			float[] pos = map.getScreenPositionFromLocation(ball.getLocation());
			ball.update(pos[0], pos[1]);
			ball.draw();
		}
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
	
	public void setLeft()
	{
		//| Position Projection to top Left Coordinate
		float[] tl = map.getScreenPositionFromLocation(coordTL);
		Location nl = map.getLocationFromScreenPosition(wid/2+tl[0], hei/2+tl[1]);
		map.zoomAndPanTo(nl, 10);
	}
	
	public void newMapProjection()
	{
		//| Percentage increase for Bay Model projection
		float[] tl = map.getScreenPositionFromLocation(coordTL);
		float[] br = map.getScreenPositionFromLocation(coordBR);

		float newX = tl[0];
		float newY = tl[1];
		float newW = br[0] - tl[0] + 190; 
		float newH = br[1] - tl[1];
		
		//image(baymodel, newX, newY, newW, newH); //| Zoom 10 Scale
		image(baymodel, newX, newY, 1051, 1051); //| Bay Model Full Scale
	}
	
	public static void main(String _args[]) 
	{
		PApplet.main(new String[] { "--present", "--bgcolor=#111111", "--hide-stop", DynamicEarth.class.getName() });
	}
}
