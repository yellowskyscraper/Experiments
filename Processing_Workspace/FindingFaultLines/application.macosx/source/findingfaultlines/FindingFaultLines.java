package findingfaultlines;

import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

import processing.core.PApplet;
import processing.core.PImage;

import edu.exploratorium.rotary.*;
import findingfaultlines.app.scenes.EarthquakeArchive;
import findingfaultlines.app.scenes.IntertitleDynamicEarth;

import de.fhpotsdam.unfolding.Map;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.providers.Microsoft;

public class FindingFaultLines extends PApplet {

	private static final long serialVersionUID = 1L;

	//| Map And Label Text
	Map map;
	Location sanFrancisco;
	
	boolean BAYMODEL = true;
	int wid = (BAYMODEL) ? 1500 : 1680;
	int hei = (BAYMODEL) ? 1200 : 1050;

	//| Marker Points & Text
	PImage baymodel;
	PImage pause;
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
		size(1500, 1200);
		noCursor();
		
		//| Rotary Interface for Dial Functioning
		rotaryInterface = new RotaryInterface(this);
		rotaryInterface.setAccel(0.1f);
		rotaryInterface.setInertia(0.9f);
		rotaryInterface.setDispatchOutOfRange(true);

		//| Model Boundaries & Image
		baymodel = loadImage("data/images/bounds4_1500x1200.jpg");
		pause = loadImage("data/images/pause.jpg");
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
		
		//| Window Sleep and Wake; Application Pause and Reset 
		frame.addWindowFocusListener(new WindowFocusListener () {
			@Override public void windowGainedFocus (WindowEvent e) {
				System.out.println("FINDING FAULT LINES KINDLY SAYS HELLO");
				intertitleDynamicEarth.firstcall();
				earthquakeArchive.kill();
				SCENE = "INTERTITLE";	
				PAUSE = false;
			}
			
			@Override public void windowLostFocus (WindowEvent e) {
				System.out.println("FINDING FAULT LINES REGRETFULLY SAYS GOODBYE");
				PAUSE = true;
			}
		});
	}
	
	public void reset()
	{
		intertitleDynamicEarth.firstcall();
		earthquakeArchive.kill();
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
//		PApplet.println(SCENE + " " + stat + " (" + frameRate + ")");		
	}

	public void draw() 
	{
		if(PAUSE)
		{
			tint(255, 255);
			image(pause, 0, 0);
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
//		if(!BAYMODEL) map.draw();

		//| Percentage increase for Bay Model projection
		float[] tl = map.getScreenPositionFromLocation(coordTL);
		float[] br = map.getScreenPositionFromLocation(coordBR);
		float newX = Math.round(tl[0]);
		float newY = Math.round(tl[1]);
		float newW = br[0] - tl[0]; 
		float newH = br[1] - tl[1];	
				
		//| Map Image
		noSmooth();
		tint(255);
		if(BAYMODEL) image(baymodel, newX, newY);
		else image(baymodel, newX, newY, newW, newH);
		
		smooth();
		noFill();
		strokeWeight(1);
		
		//| Map Bounds Stroke and Scale
		stroke(0, 90, 90);
		if(!BAYMODEL) rect(tl[0], tl[1], br[0]-tl[0], br[1]-tl[1]);
		if(!BAYMODEL) line(br[0]-tl[0], br[1]-tl[1], wid-1, hei-1);
		
		//| Outer Stroke
//		stroke(255);
//		rect(0,0,wid-1,hei-1);
	}
	
	//| Rotary Interface Events
	public void onRotaryEvent (RotaryEvent evt) {
		float speed = evt.speed() * 2; 
		int channel = evt.channel(); 
		
		if(rotarySpinBool == true) earthquakeArchive.rotaryInterfaceEvent(speed, channel);
		
		if (evt.type() == RotaryEvent.OUT_OF_ROTARY_RANGE) {
		    if (evt.rawValue() == 33) {
		    	if(SCENE.equals("EARTHQUAKE ARCHIVE") && earthquakeArchive.status().equals("ON")) earthquakeArchive.upFaultsEvent();
		    }
		}
	}
	
	//| Override Key Pressed
	public void keyPressed() 
	{
		/*
		if (key == ' ') {
			if(!PAUSE) {
				PAUSE = true;
			} else if(PAUSE) {
				PAUSE = false;
				this.reset();
			}
			
		} else if (key == 'f') {
	    	if(SCENE.equals("EARTHQUAKE ARCHIVE") && earthquakeArchive.status().equals("ON")) earthquakeArchive.upFaultsEvent();
		}
		*/
	}
	
	//| Override Main
	public static void main(String _args[]) 
	{
		PApplet.main(new String[] { "--location=0,0", "--present", "--bgcolor=#000000", "--hide-stop", FindingFaultLines.class.getName() });
	}
}


