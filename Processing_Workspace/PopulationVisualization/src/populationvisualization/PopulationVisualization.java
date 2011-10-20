package populationvisualization;

import processing.core.PApplet;
import processing.core.PFont;
import processing.xml.XMLElement;

import de.fhpotsdam.unfolding.*;
import de.fhpotsdam.unfolding.geo.*;
import de.fhpotsdam.unfolding.utils.*;
import de.fhpotsdam.unfolding.providers.*;
import de.fhpotsdam.unfolding.mapdisplay.MapDisplayFactory;

public class PopulationVisualization extends PApplet 
{
	private static final long serialVersionUID = 1L;

	//| Map And Label Text
	Location sanFrancisco;
	Map map;
	PFont displayText;
	PFont displaySubText;
	
	//| Population Data
	XMLElement xml;
	int wid = 1021;
	int hei = 768;
	boolean parsed;
	int parse = 0;

	//| Marker Points
	Location coordTL;
	Location coordBR;

	public void setup() 
	{
		wid = screenWidth;
		hei = screenHeight;
		
		background(0);
		size(wid, hei);

		//| Model Boundaries
		coordTL = new Location(38.33357743803447f,-122.76329040527344f);
		coordBR = new Location(37.36415426173095f,-121.81434631347656f);
		
		//| San FRancisco
		//| http://maps.cloudmade.com/editor
		//| Light Map:44768, Dark Map: 44840, Teal Map:44842
	    sanFrancisco = new Location(37.87485339352928f, -121.79443359375f);
	    map = new Map(this, "map", 0, 0, screenWidth, screenHeight, true, false, new OpenStreetMap.CloudmadeProvider(MapDisplayFactory.OSM_API_KEY, 15153));
		map.zoomAndPanTo(new Location(37.87485339352928f, -121.79443359375f), 10);
		MapUtils.createDefaultEventDispatcher(this, map);
		
		//| XML
		parsed = false;
		xml = new XMLElement(this, "http://localhost/Experiments/Processing_Workspace/PopulationVisualization/src/data/php/blocks.php"); 
		
		//| Copy
		displayText = createFont("data/fonts/Explo/Explo-Ultra.otf", 50);
		displaySubText = createFont("data/fonts/Explo/Explo-Medi.otf", 50);
	}

	public void draw() 	
	{
		//| Map and Overlay
		background(0);
		//map.draw();
		
		//| Population Logic
		this.totalPopulation();
		
		//| Model Bounds
		this.markers();
		this.copy("The Eye of the Earth", "There are impossible scribblings in nature, \nwritten neither by men nor by devils.");
	}
	
	public void totalPopulation()
	{
		for(int i=0; i<xml.getChildCount()-1; i++){
			int total = xml.getChild(i).getInt("total");
			float lat = Float.valueOf(xml.getChild(i).getFloat("lat"));
			float lon = Float.valueOf(xml.getChild(i).getFloat("lon"));

			Location coord = new Location(lat,lon);
			float[] p = map.getScreenPositionFromLocation(coord);
			
			smooth();
			noStroke();
			fill(0,255,255,50);
			if(total > 0) ellipse(p[0], p[1], 3, 3);
		}
	}
	
	public void copy(String t, String s)
	{
		float[] tl = map.getScreenPositionFromLocation(coordTL);
		
		smooth();
		fill(0, 100, 100);
		textFont(displayText, 40);
		text(t, tl[0] + 29, tl[1] + 71);
		textFont(displaySubText, 20);
		text(s, tl[0] + 29, tl[1] + 106);
		
		fill(0, 255, 255);
		textFont(displayText, 40);
		text(t, tl[0] + 30, tl[1] + 70);
		textFont(displaySubText, 20);
		text(s, tl[0] + 30, tl[1] + 105);
	}
	
	public void markers()
	{
		//| Markers for Rough Models boundaries. 
		Location coordTL = new Location(38.33357743803447f,-122.76329040527344f);
		Location coordBR = new Location(37.36415426173095f,-121.81434631347656f);
		
		float[] tl = map.getScreenPositionFromLocation(coordTL);
		float[] br = map.getScreenPositionFromLocation(coordBR);

		fill(0,100,100);
		ellipse(tl[0] - 1, tl[1] + 1, 5, 5);
		ellipse(br[0] - 1, tl[1] + 1, 5, 5);
		ellipse(br[0] - 1, br[1] + 1, 5, 5);
		ellipse(tl[0] - 1, br[1] + 1, 5, 5);
		
		fill(0,255,255);
		ellipse(tl[0], tl[1], 5, 5);
		ellipse(br[0], tl[1], 5, 5);
		ellipse(br[0], br[1], 5, 5);
		ellipse(tl[0], br[1], 5, 5);
	}
	
	public static void main(String _args[]) 
	{
		PApplet.main(new String[] { "--present", "--bgcolor=#111111", "--hide-stop", PopulationVisualization.class.getName() });
	}
}
