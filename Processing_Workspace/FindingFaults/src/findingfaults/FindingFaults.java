package findingfaults;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;

import de.fhpotsdam.unfolding.Map;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.providers.Microsoft;

import findingfaults.app.data.IntertitleFindingFaults;
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
	
	//| Director
	String SCENE = "INTERTITLE";
	
	//| Data
	IntertitleFindingFaults intertitleFindingFaults;
	FaultLineAnimation faultComplexAni;
	EarthquakeTimeline earthquakeTimeline;
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
		
		//| Intertitle
		intertitleFindingFaults = new IntertitleFindingFaults(this);
		intertitleFindingFaults.setup(map, wid, hei);
		intertitleFindingFaults.firstcall();
		
		//| GIF Animation
		faultComplexAni = new FaultLineAnimation(this);
 		faultComplexAni.setup();
		
		//| Earthquake feed from USGS		
		earthquakeTimeline = new EarthquakeTimeline(this);
		earthquakeTimeline.setup(map, wid, hei);

		//| Crustal Velocities
		cristalVelocities = new CrustalDeformationVectors(this);
		cristalVelocities.setup(map, wid, hei);
	}
	
	public void checkSceneStatus()
	{
		String stat = "null";
		
		if(SCENE.equals("INTERTITLE")) {
			stat = intertitleFindingFaults.status();
			if(stat.equals("OFF")) intertitleFindingFaults.start();
			if(stat.equals("DONE")) {
				SCENE = "EARTHQUAKE ANIMATION";
				intertitleFindingFaults.off();
			}

		} else if(SCENE.equals("EARTHQUAKE ANIMATION")) {
			stat = earthquakeTimeline.status();
			if(stat.equals("OFF")) earthquakeTimeline.start();
			if(stat.equals("DONE")) {
				SCENE = "CRUSTAL VELOCITIES";
				//earthquakeTimeline.off();
				//faultComplexAni.start();
			}
		}
		
		PApplet.println(SCENE + " " + stat);
	}

	public void draw() 
	{
		//| Map and Overlay
		background(0);
		this.renderMap();

		//| GIF Animation
		faultComplexAni.draw(map);	

		//| Sequence
		this.checkSceneStatus();
		
		if(SCENE.equals("INTERTITLE")) {
			intertitleFindingFaults.update();
			intertitleFindingFaults.draw(map);
		}
		
		if(SCENE.equals("EARTHQUAKE ANIMATION")) {
			earthquakeTimeline.update();
			earthquakeTimeline.draw(map);
		}

		if(SCENE.equals("CRUSTAL VELOCITIES")) {
			//earthquakeTimeline.update();
			//earthquakeTimeline.draw(map);
			cristalVelocities.draw(map);
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
		PApplet.main(new String[] { "--present", "--bgcolor=#111111", "--hide-stop", FindingFaults.class.getName() });
	}
}
