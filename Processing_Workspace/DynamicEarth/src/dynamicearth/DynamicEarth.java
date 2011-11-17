package dynamicearth;

import processing.core.PApplet;
import processing.core.PImage;
import de.fhpotsdam.unfolding.Map;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.providers.Microsoft;
import dynamicearth.app.data.IntertitleDynamicEarth;
import dynamicearth.app.data.EarthquakeFeed;

public class DynamicEarth extends PApplet 
{
	private static final long serialVersionUID = 1L;

	//| Map And Label Text
	Map map;
	Location sanFrancisco;
	int wid = 1400;
	int hei = 1050;

	//| Marker Points & Text
	PImage baymodel;
	Location coordTL;
	Location coordBR;
	
	//| Director
	String SCENE = "INTERTITLE";
	
	//| Data
	IntertitleDynamicEarth intertitleDynamicEarth;
	EarthquakeFeed earthquakeFeed;
	
	public void setup() 
	{
		//| Start
		wid = screenWidth;
		hei = screenHeight;
		background(0);
//		size(1400, 1050);
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

		//| Intertitle
		intertitleDynamicEarth = new IntertitleDynamicEarth(this);
		intertitleDynamicEarth.setup(map, wid, hei);
		intertitleDynamicEarth.firstcall();
		
		//| Earthquake feed from USGS
		earthquakeFeed = new EarthquakeFeed(this);
		earthquakeFeed.setup(map, wid, hei);
	}
	
	public void update()
	{
		String stat = "null";
		
		if(SCENE.equals("INTERTITLE")) {
			stat = intertitleDynamicEarth.status();
			if(stat.equals("OFF")) intertitleDynamicEarth.start();
			if(stat.equals("DONE")) {
				SCENE = "EARTHQUAKE FEED";
				intertitleDynamicEarth.off();
			}

		} else if(SCENE.equals("EARTHQUAKE FEED")) {
			stat = earthquakeFeed.status();
			if(stat.equals("OFF")) earthquakeFeed.start();
			if(stat.equals("DONE")) {
				SCENE = "INTERTITLE";
				earthquakeFeed.off();
			}
		}
		
		//PApplet.println(SCENE + " " + stat);
	}

	public void draw() 
	{
		//| Map and Background
		background(0);
		this.renderMap();
		
		//| Sequence
		this.update();
		
		if(SCENE.equals("INTERTITLE")) {
			intertitleDynamicEarth.update();
			intertitleDynamicEarth.draw(map);
		}
		
		if(SCENE.equals("EARTHQUAKE FEED")) {
			earthquakeFeed.update();
			earthquakeFeed.draw(map);
		}
	}
	
	public void renderMap()
	{
		//map.draw();
		fill(0,0,0,100);
		rect(-1,-1,screenWidth+2,screenHeight+2);
		
		//| Percentage increase for Bay Model projection
		float[] tl = map.getScreenPositionFromLocation(coordTL);
		float[] br = map.getScreenPositionFromLocation(coordBR);
		float newX = tl[0];
		float newY = tl[1];
		float newW = br[0] - tl[0] + 190; 
		float newH = br[1] - tl[1];
		
		noSmooth();
		image(baymodel, newX, newY, newW, newH); //| Zoom 10 Scale
		image(baymodel, newX, newY, 1051, 1051); //| Bay Model Full Scale
	}
	
	public static void main(String _args[]) 
	{
		PApplet.main(new String[] { "--present", "--bgcolor=#111111", "--hide-stop", DynamicEarth.class.getName() });
	}
}
