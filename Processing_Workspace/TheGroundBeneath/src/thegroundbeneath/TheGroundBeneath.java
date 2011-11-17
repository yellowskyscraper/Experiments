package thegroundbeneath;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PGraphics;
import thegroundbeneath.app.data.IntertitleTheGroundBeneath;
import thegroundbeneath.app.data.LiquefactionAnimation;
import thegroundbeneath.app.data.PopulationDensity;

import de.fhpotsdam.unfolding.Map;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.providers.Microsoft;


public class TheGroundBeneath extends PApplet 
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
	IntertitleTheGroundBeneath intertitleTheGroundBeneath;
	LiquefactionAnimation liquefactionAni;
	PopulationDensity populationDensity;
	
	public void setup() 
	{
		//| Start
		wid = screenWidth;
		hei = screenHeight;
		background(0);
		size(wid,hei);

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
		intertitleTheGroundBeneath = new IntertitleTheGroundBeneath(this);
		intertitleTheGroundBeneath.setup(map, wid, hei);
		intertitleTheGroundBeneath.firstcall();
		
		//| GIF Animation
		liquefactionAni = new LiquefactionAnimation(this);
		liquefactionAni.setup();
		
		//| Population Density
		populationDensity = new PopulationDensity(this);
		populationDensity.setup(map, wid, hei);
	}
	
	public void update()
	{
		String stat = "null";

		if(SCENE.equals("INTERTITLE")) {
			stat = intertitleTheGroundBeneath.status();
			if(stat.equals("OFF")) intertitleTheGroundBeneath.start();
			if(stat.equals("DONE")) {
				SCENE = "LIQUEFACTION";
				intertitleTheGroundBeneath.off();
			}

		} else if(SCENE.equals("LIQUEFACTION")) {
			stat = liquefactionAni.status();
			if(stat.equals("OFF")) liquefactionAni.start();
			if(stat.equals("DONE")) {
				SCENE = "POPULATION";
				liquefactionAni.off();
			}
		} else if(SCENE.equals("POPULATION")) {
			stat = populationDensity.status();
			if(stat.equals("OFF")) populationDensity.start();
			if(stat.equals("DONE")) {
				SCENE = "INTERTITLE";
				populationDensity.off();
			}
		}

		PApplet.println(SCENE + " " + stat);
	}

	public void draw() 
	{
		//| Map and Background
		background(0);
		this.renderMap();
		
		//| Sequence
		this.update();
		
		if(SCENE.equals("INTERTITLE")) {
			intertitleTheGroundBeneath.update();
			intertitleTheGroundBeneath.draw(map);
		}
		
		if(SCENE.equals("LIQUEFACTION")) {
			liquefactionAni.update();
			liquefactionAni.draw(map);
		}
		
		if(SCENE.equals("POPULATION")) {
			populationDensity.update();
			populationDensity.draw(map);
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
		PApplet.main(new String[] { "--present", "--bgcolor=#111111", "--hide-stop", TheGroundBeneath.class.getName() });
	}
}
