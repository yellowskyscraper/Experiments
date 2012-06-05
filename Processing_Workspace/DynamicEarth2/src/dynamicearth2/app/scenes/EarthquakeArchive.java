package dynamicearth2.app.scenes;

import processing.core.PApplet;
import processing.core.PImage;
import processing.xml.XMLElement;

import dynamicearth2.app.elements.LabelEarthquakeArchive;
import dynamicearth2.app.elements.Quake;

import de.fhpotsdam.unfolding.Map;
import ijeoma.motion.Motion;
import ijeoma.motion.tween.Tween;

public class EarthquakeArchive 
{
	PApplet parent;
	
	boolean BAYMODEL = false;
	int wid = (BAYMODEL) ? 1500 : 1680;
	int hei = (BAYMODEL) ? 1200 : 1050;
	
	//| Data
	Quake[] quakeArchive;
	int archiveLength;
	
	LabelEarthquakeArchive label;
	PImage timedial;
	
	//| Scrub Variables
	float scrubber = 0;
	double rotaryValue = 0;
	float rotation = 0.0f;
	
	//| Sequencing
	String STATUS = "OFF";
	int timekeeper = 0;
	
	public EarthquakeArchive(PApplet p)
	{
		parent = p;
	}
	
	public void setup(Map m, float w, float h)
	{
		//| Earthquake Data
		XMLElement xml = new XMLElement(parent, "../data/earthquakearchive/sample50mi1973-2011.xml");
		archiveLength = xml.getChildCount();
		quakeArchive = new Quake[archiveLength]; //quakeArchive = new ArrayList<Quake>();
		float[] magnitudeArchive = new float[archiveLength];
		
		//| Parse USGS Compiled Earthquakes
		PApplet.println("Earthquake Timeline: PARSING " + archiveLength);
		for(int i = 0; i < archiveLength; i ++)
		{
			String name = xml.getChild(i).getChild(0).getContent();
			String description = xml.getChild(i).getChild(1).getContent();
			float lat = Float.valueOf(xml.getChild(i).getChild(2).getContent()).floatValue();
		    float lon = Float.valueOf(xml.getChild(i).getChild(3).getContent()).floatValue();
			
			Quake b = new Quake(parent);
			b.setup(name, description, lat, lon);
			quakeArchive[i] = b;
			magnitudeArchive[i] = b.getMagnitude();
/* 
			Location coord = new Location(lon, lat);
			float[] p = m.getScreenPositionFromLocation(coord);
			float[] position = BasicUtils.scaleCoordinates(1050, 1050, p[0], p[1]);
			if(p[0] > 0 && p[0] < w && p[1] > 0 && p[1] < h) {
				Quake b = new Quake(parent);
				b.setup(name, description, lat, lon);
				quakeArchive.add(b);
				PApplet.println("Q: " + b.getName());
			}
*/
		}
		PApplet.println("Earthquake Timeline: DONE LOADING " + quakeArchive.length);
		
		timedial = parent.loadImage("../data/images/timedial.png");
		
		//| Time Line Label
		label = new LabelEarthquakeArchive(parent);
		label.setup(magnitudeArchive);
	}
		
	//| Update Sequence, Called Once
	public void start()
	{
		STATUS = "ANIMATING IN";
		label.open();
	}
	
	public void step1()
	{
		STATUS = "ON";
		timekeeper = 0;
	}
	
	public void close()
	{
		STATUS = "ANIMATING OUT"; 
		timekeeper = 0;
		scrubber = 0;
	}

	public void closed()
	{
		STATUS = "DONE";
		timekeeper = 0;	
		scrubber = 0;
	}
	
	public String status()
	{
		return STATUS;
	}	
	
	public void update()
	{		
		if(STATUS.equals("OFF")) {
			
		} else if(STATUS.equals("ANIMATING IN")) {
			label.update(quakeArchive[Math.round(scrubber)].getDate(), quakeArchive[Math.round(scrubber)].getMagnitude());
			if(label.isOpened()) step1();
			
		} else if(STATUS.equals("ON")) {
			this.manageRotaryInflux();
			label.scrubber(scrubber);
			label.update(quakeArchive[Math.round(scrubber)].getDate(), quakeArchive[Math.round(scrubber)].getMagnitude());
			
		} else if(STATUS.equals("ANIMATING OUT")) {
//			label.update(1973,"Jan",1, 0);
//			if(label.closed()) timekeeper += 1;
//			if(timekeeper > 100) this.closed();
		}
	}
	
	//| DRAW FUNCTIONS
	public void draw(Map m)
	{	
		//| Timeline Animation
		if(STATUS.equals("ANIMATING IN")) this.drawAllQuakes(m);
		else if(STATUS.equals("ON")) this.drawAllQuakes(m);
		else if(STATUS.equals("ANIMATING OUT")) this.drawAllQuakes(m);
		
		//| Legend
		label.draw(m);
	}
	
	private void drawAllQuakes(Map m)
	{	
		//| Iterate Quakes & Draw
		for (int i = 0; i < archiveLength; i++) 
		{ 
			float[] pos = m.getScreenPositionFromLocation(quakeArchive[i].getLocation());
			quakeArchive[i].update(pos[0], pos[1]);
			quakeArchive[i].draw();
		}
	}
	
	private void manageRotaryInflux()
	{
		//| Check Polarity
		int polarity = 0;
		if(rotaryValue > 0) polarity = 1;
		if(rotaryValue < 0) polarity = -1;
//		PApplet.println(scrubber + " - " + rotaryValue * 10 + " - " +polarity);

		//| Manage Direction, Idle, and Overlooked Children
		if(scrubber <= archiveLength - 1 && scrubber >= 0) {
			switch (polarity) {
				case -1: //| Negative
					timekeeper = 0;
					quakeArchive[Math.round(scrubber)].close();
					scrubber += rotaryValue * 10; //| += because it's sometime negative
					if(scrubber < 0) scrubber = 0;
					//| Check for lost children
					for (int i = 0; i < archiveLength; i++) if(quakeArchive[i].getStatus() == true && i > scrubber) quakeArchive[i].close();
					rotation += rotaryValue*2;
					break;
				
				case 0: //| Neutral
					if(timekeeper > 100) {
						quakeArchive[Math.round(scrubber)].open();
						scrubber += 0.1f;
						rotation += 0.01f;
						if(scrubber > archiveLength - 1) {
							scrubber = archiveLength - 1;
//							for (int i = 0; i < archiveLength; i++) quakeArchive[i].close();
						}
					} else {
						timekeeper += 1;
					}
					break;
				
				case 1: //| Positive
					timekeeper = 0;
					quakeArchive[Math.round(scrubber)].open();
					scrubber += rotaryValue * 10;
					if(scrubber > archiveLength - 1) scrubber = archiveLength - 1;
					//| Check for lost children
					for (int i = 0; i < archiveLength; i++) if(quakeArchive[i].getStatus() == false && i < scrubber) quakeArchive[i].open();
					rotation += rotaryValue*2;
					break;
			}
		}
		
		//| Dial Rotation
		parent.pushMatrix();
		parent.translate(wid/2, hei);
		parent.rotate(rotation);
		parent.translate(-timedial.width/2, -timedial.height/2);
		parent.tint(255, 255);
		parent.image(timedial, 0, 0);
		parent.popMatrix();
	}
	
	public void rotaryInterfaceEvent(float s, int c)
	{
		rotaryValue = s;
	}
	
	public void kill()
	{
		STATUS = "OFF";
		scrubber = 0;
		for (int i = 0; i < archiveLength; i++) quakeArchive[i].kill();
		label.kill();
	}
	
	public void off()
	{
		STATUS = "OFF";
		scrubber = 0;
		timekeeper = 0;
	}
	
}


