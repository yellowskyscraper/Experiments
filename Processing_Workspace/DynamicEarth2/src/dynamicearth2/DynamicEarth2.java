package dynamicearth2;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PMatrix2D;

import dynamicearth2.app.scenes.EarthquakeArchive;
import dynamicearth2.app.scenes.IntertitleDynamicEarth;

import edu.exploratorium.rotary.*;

import de.fhpotsdam.unfolding.Map;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.providers.Microsoft;
import de.fhpotsdam.unfolding.utils.MapUtils;

public class DynamicEarth2 extends PApplet {

	private static final long serialVersionUID = 1L;

	//| Map And Label Text
	Map map;
	Location sanFrancisco;
	
	boolean BAYMODEL = false;
	int wid = (BAYMODEL) ? 1500 : 1680;
	int hei = (BAYMODEL) ? 1200 : 1050;

	//| Marker Points & Text
	PImage baymodel;
	Location coordTL;
	Location coordBR;
	
	//| Dial Inerface
	RotaryInterface rotaryInterface;
	boolean rotarySpinBool = false;
	
	//| Director
	boolean PAUSE = false;
	String SCENE = "INTERTITLE";
	
	//| Data
	IntertitleDynamicEarth intertitleDynamicEarth;
	EarthquakeArchive earthquakeArchive;
	
	public void setup() 
	{
		//| Start
		background(0);
		size(wid, hei);
		noCursor();
		frameRate(50);
		
		//| Rotary Interface for Dial Functioning
		rotaryInterface = new RotaryInterface(this, true);
//		rotaryInterface.setAccel(0.1f);
//		rotaryInterface.setInertia(0.0f);
		rotaryInterface.setAccel(0.1f);
		rotaryInterface.setInertia(0.9f);

		//| Model Boundaries & Image
		baymodel = loadImage("data/images/bounds_1500x1200.jpg");
		coordTL = new Location(38.2268534751704f,-122.917099f);
		coordBR = new Location(37.37179426133591f,-121.56440734863281f);
		
		//| San Francisco
	    sanFrancisco = new Location(37.807614f, -122.209167f);
	    map = new Map(this, 0, 0, width, height, new Microsoft.AerialProvider());
		map.zoomAndPanTo(sanFrancisco, 10);
//		MapUtils.createDefaultEventDispatcher(this, map);
		
		//| Position Projection to top Left Coordinate
		float[] tl = map.getScreenPositionFromLocation(coordTL);
		Location nl = map.getLocationFromScreenPosition(wid/2+tl[0], hei/2+tl[1]);
		map.zoomAndPanTo(nl, 10);

		//| Intertitle
		intertitleDynamicEarth = new IntertitleDynamicEarth(this);
		intertitleDynamicEarth.setup(map, wid, hei);
		intertitleDynamicEarth.firstcall();
		
		//| Earthquake feed from USGS
		earthquakeArchive = new EarthquakeArchive(this);
		earthquakeArchive.setup(map, wid, hei);
	}
	
	public void reset()
	{
		if(SCENE.equals("INTERTITLE")) intertitleDynamicEarth.kill();
		if(SCENE.equals("EARTHQUAKE ARCHIVE")) earthquakeArchive.kill();
		SCENE = "INTERTITLE";	
	}
	
	public void update()
	{
		String stat = "null";
		
		if(SCENE.equals("INTERTITLE")) {
			stat = intertitleDynamicEarth.status();
			if(stat.equals("OFF")) intertitleDynamicEarth.start();
			if(stat.equals("DONE")) {
				SCENE = "EARTHQUAKE ARCHIVE";
				intertitleDynamicEarth.off();
			}

		} else if(SCENE.equals("EARTHQUAKE ARCHIVE")) {
			stat = earthquakeArchive.status();
			if(stat.equals("OFF")) earthquakeArchive.start();
			if(stat.equals("ON")) rotarySpinBool = true;
			if(stat.equals("ANIMATING OUT")) rotarySpinBool = false;
			if(stat.equals("DONE")) {
				SCENE = "INTERTITLE";
				earthquakeArchive.off();
			}

		}
		//PApplet.println(SCENE + " " + stat + " (" + frameRate + ")");		
	}

	public void draw() 
	{
		if(PAUSE)
		{
			//| TASK: Image that signals paused appears
			PApplet.println("PAUSED");
			return;
		} 
		
		//| Map and Background
		background(0);
		this.renderMapChrome();
		this.update();
		
		if(SCENE.equals("INTERTITLE")) {
			intertitleDynamicEarth.update();
			intertitleDynamicEarth.draw(map);
		}
		
		if(SCENE.equals("EARTHQUAKE ARCHIVE")) {
			earthquakeArchive.update();
			earthquakeArchive.draw(map);
		}

	}
	
	public void renderMapChrome()
	{		
		//| Map from Internet
//		map.draw();

		//| Percentage increase for Bay Model projection
		float[] tl = map.getScreenPositionFromLocation(coordTL);
		float[] br = map.getScreenPositionFromLocation(coordBR);
		float newX = Math.round(tl[0]);
		float newY = Math.round(tl[1]);
		float newW = br[0] - tl[0]; 
		float newH = br[1] - tl[1];	
		
		//| Map Image
		noSmooth();
		tint(255, 255, 255, 120); 
		if(BAYMODEL) image(baymodel, newX, newY);
		else image(baymodel, newX, newY, newW, newH);

		fill(0,0,0,100);
		rect(-1,-1,wid+2,hei+2);
		
		smooth();
		noFill();
		strokeWeight(1);
		
		//| Map Bounds Stroke and Scale
		stroke(0, 90, 90);
		rect(tl[0], tl[1], br[0]-tl[0], br[1]-tl[1]);
		line(br[0]-tl[0], br[1]-tl[1], wid-1, hei-1);
		
		//| Outer Stroke
		stroke(255);
		rect(0,0,wid-1,hei-1);
	}
	
	//| Rotary Interface Events
	public void onRotaryEvent(float speed, int channel) 
	{
		if(rotarySpinBool == true) earthquakeArchive.rotaryInterfaceEvent(speed, channel);
	}
	
	//| Override Key Pressed
	public void keyPressed() 
	{
		if (key == ' ') {
			if(!PAUSE) {
				PAUSE = true;
			} else if(PAUSE) {
				PAUSE = false;
				this.reset();
			}
			
		} else if(key == 'f') {
			//| Fault Line Reveal
		}
	}
	
	//| Override Main
	public static void main(String _args[]) 
	{
		PApplet.main(new String[] { "--location=0,0", "--present", "--bgcolor=#000000", "--hide-stop", DynamicEarth2.class.getName() });
	}
	
	//| adjust this value to whatever depth is actually necessary
	public final int STACK_DEPTH = 5000;
	public float[][] matrixStack = new float[STACK_DEPTH][6];
	public int matrixStackDepth;
	 
	//| this version will override the built-in version pushMatrix function
	public void pushMatrix() 
	{
		if (matrixStackDepth == 5000) {
			throw new RuntimeException("too many calls to pushMatrix()");
		}
		this.getMatrix().get(matrixStack[matrixStackDepth]);
		matrixStackDepth++;
	}
	 
	//| this version will override the built-in version popMatrix function
	public void popMatrix() 
	{
		if (matrixStackDepth == 0) {
			throw new RuntimeException("too many calls to popMatrix()" + "(or too few to pushMatrix)");
		}
		matrixStackDepth--;
		PMatrix2D m = new PMatrix2D();
		m.set(matrixStack[matrixStackDepth]);
		this.setMatrix(m);
	}
}
