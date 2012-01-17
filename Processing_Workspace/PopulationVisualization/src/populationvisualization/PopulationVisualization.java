package populationvisualization;

import processing.core.PApplet;
import processing.core.PFont;
import processing.xml.XMLElement;

import de.fhpotsdam.unfolding.*;
import de.fhpotsdam.unfolding.geo.*;
import de.fhpotsdam.unfolding.providers.*;

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
	    map = new Map(this, 0, 0, width, height, new Microsoft.AerialProvider());
		map.zoomAndPanTo(sanFrancisco, 10);
		
		//| XML
		parsed = false;
		//xml = new XMLElement(this, "http://localhost/Experiments/Processing_Workspace/PopulationVisualization/src/data/php/occupancy2010.php");
		xml = new XMLElement(this, "http://localhost/Experiments/Processing_Workspace/PopulationVisualization/src/data/php/occupancy_bay_all.xml");
		
		//| Copy
		displayText = createFont("data/fonts/Explo/Explo-Ultra.otf", 50);
		displaySubText = createFont("data/fonts/Explo/Explo-Medi.otf", 50);
	}

	public void draw() 	
	{
		//| Map and Overlay
		background(0);
		map.draw();
		fill(0,0,0,200);
		rect(-1,-1,screenWidth+2,screenHeight+2);
		
		//| Population Logic
		this.totalPopulation();
		
		//| Model Bounds
		//this.markers();
		this.copy("No Self Is Of Itself Alone", "For it is never only about the thing itself; it \nis also about the image one obtains of a thing.");
	}
	
	public void totalPopulation()
	{
		for(int i=0; i<xml.getChildCount()-1; i++){
			int population = xml.getChild(i).getInt("total");
			float lat = Float.valueOf(xml.getChild(i).getFloat("lat"));
			float lon = Float.valueOf(xml.getChild(i).getFloat("lon"));

			Location coord = new Location(lat,lon);
			float[] position = map.getScreenPositionFromLocation(coord);
			
			int scale = 2;
			if(population > 20 && population < 40) scale = 3;
			else if(population > 40 && population < 60) scale = 4;
			else if(population > 60 && population < 80) scale = 5;
			else if(population > 80 && population < 100) scale = 6;
			else if(population > 100 && population < 120) scale = 7;
			else if(population > 120 && population < 140) scale = 8;
			else if(population > 140 && population < 160) scale = 9;
			else if(population > 160 && population < 180) scale = 10;
			else if(population > 180 && population < 200) scale = 11;
			else if(population > 200 && population < 220) scale = 12;
			else if(population > 220 && population < 240) scale = 13;
			
			smooth();
			noStroke();
			//stroke(255);
			fill(84,188,235, 100);
			ellipse(position[0], position[1], 2, 2);
			fill(4,188,235, 100);
			ellipse(position[0], position[1], scale, scale);
		}
	}
	
	public void copy(String t, String s)
	{
		float[] tl = map.getScreenPositionFromLocation(coordTL);
		
		smooth();
		fill(0,0,0);
		textFont(displayText, 40);
		text(t, tl[0] + 29, tl[1] + 71);
		textFont(displaySubText, 20);
		text(s, tl[0] + 29, tl[1] + 106);
		
		fill(255,255,255);
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
	}
	
	public static void main(String _args[]) 
	{
		PApplet.main(new String[] { "--present", "--bgcolor=#111111", "--hide-stop", PopulationVisualization.class.getName() });
	}
}
