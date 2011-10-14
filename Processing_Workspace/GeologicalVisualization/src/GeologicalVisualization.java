import java.awt.Point;

import processing.core.PApplet;
import processing.core.PFont;

import codeanticode.glgraphics.GLConstants;

import de.fhpotsdam.unfolding.*;
import de.fhpotsdam.unfolding.geo.*;
import de.fhpotsdam.unfolding.utils.*;
import de.fhpotsdam.unfolding.providers.*;
import de.fhpotsdam.unfolding.mapdisplay.MapDisplayFactory;

public class GeologicalVisualization extends PApplet 
{
	private static final long serialVersionUID = 1L;
	
	//| Map And Label Text
	Location sanFrancisco;
	Map map;
	PFont displayText;
	PFont displaySubText;

	//| Marker Points
	Location coordTL;
	Location coordBR;
	
	public static void main (String _args[]) 
	{
		PApplet.main(new String[] { "--present", "--bgcolor=#111111", "--hide-stop", GeologicalVisualization.class.getName() });
	}
	
	public GeologicalVisualization()
	{
		super();
	}
	
	public void setup() 
	{
		size(screenWidth, screenHeight);

		//| Model Boundaries
		coordTL = new Location(38.33357743803447f,-122.76329040527344f);
		coordBR = new Location(37.36415426173095f,-121.81434631347656f);
		
		//| San FRancisco
		//| http://maps.cloudmade.com/editor
		//| Light Map:44768, Dark Map: 44840, Teal Map:44842
	    sanFrancisco = new Location(37.87485339352928f, -121.79443359375f);
	    map = new Map(this, "map", 0, 0, screenWidth, screenHeight, true, false, 
				new OpenStreetMap.CloudmadeProvider(MapDisplayFactory.OSM_API_KEY, 19472));

		map.zoomAndPanTo(new Location(37.87485339352928f, -121.79443359375f), 10);
		MapUtils.createDefaultEventDispatcher(this, map);
		
		//| Copy
		displayText = createFont("../data/fonts/Georgia-Bold.ttf", 50);
		displaySubText = createFont("../data/fonts/Georgia.ttf", 50);
	}

	public void draw() 	
	{
		//| Map and Overlay
		map.draw();
		
		//| Model Bounds
//		this.markers();
//		this.copy("Bay Geology", "Alas; time stays, we go!");
	}
	
	public void copy(String t, String s)
	{
		float[] tl = map.getScreenPositionFromLocation(coordTL);
		
		smooth();
		fill(255);
		
		fill(255);
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

		fill(255);
		ellipse(tl[0], tl[1], 5, 5);
		ellipse(br[0], tl[1], 5, 5);
		ellipse(br[0], br[1], 5, 5);
		ellipse(tl[0], br[1], 5, 5);
	}
}