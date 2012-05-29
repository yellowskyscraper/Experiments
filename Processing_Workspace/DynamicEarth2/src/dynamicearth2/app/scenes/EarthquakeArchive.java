package dynamicearth2.app.scenes;

import processing.core.PApplet;
import processing.core.PImage;
import processing.xml.XMLElement;

import dynamicearth2.app.elements.LabelEarthquakeArchive;
import dynamicearth2.app.elements.Quake;
import dynamicearth2.app.utils.BasicUtils;

import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.Map;

import ijeoma.motion.Motion;
import ijeoma.motion.tween.Tween;

public class EarthquakeArchive 
{
	PApplet parent;

	//| Data
	XMLElement xml;
	Quake[] quakeArchive;// ArrayList<Quake> quakeArchive;
	int archiveLength;
	
	LabelEarthquakeArchive label;
	
	//| Scrub Variables
	float scrubber = 0;
	
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
		xml = new XMLElement(parent, "../data/earthquakearchive/sample50mi1973-2011.xml");
		quakeArchive = new Quake[xml.getChildCount()];//quakeArchive = new ArrayList<Quake>();
		archiveLength = xml.getChildCount();
		
		//| Parse USGS Compiled Earthquakes
		PApplet.println("Earthquake Timeline: PARSING " + xml.getChildCount());
		for(int i = 0; i < archiveLength; i ++)
		{
			String name = xml.getChild(i).getChild(0).getContent();
			String description = xml.getChild(i).getChild(1).getContent();
			float lat = Float.valueOf(xml.getChild(i).getChild(2).getContent()).floatValue();
		    float lon = Float.valueOf(xml.getChild(i).getChild(3).getContent()).floatValue();
			
			Quake b = new Quake(parent);
			b.setup(name, description, lat, lon);
			quakeArchive[i] = b;//quakeArchive.add(b);
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
		
		//| Time Line Label
		label = new LabelEarthquakeArchive(parent);
//		label.setup(quakeArchive); 
	}
	
	//| UPDATE FUNCTIONS
	public void start()
	{
		STATUS = "ANIMATING IN";
		label.write(quakeArchive[0].getDate());
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
		this.batchCommandQuakeArchive();
		timekeeper = 0;
		scrubber = 0;
	}

	public void closed()
	{
		STATUS = "DONE";
		timekeeper = 0;	
		scrubber = 0;
	}
	
	public void update()
	{		
		if(STATUS.equals("OFF")) {
			
		} else if(STATUS.equals("ANIMATING IN")) {
//			label.update(1973,"January",1, 0);
			timekeeper += 1;
			if(timekeeper > 50) step1();
			
		} else if(STATUS.equals("ON")) {

			if(scrubber < archiveLength-1) {
				scrubber += 1;
				quakeArchive[Math.round(scrubber)].open();
				
//				if(count < ((metaArchive.length/3)*2)) parent.frameRate(10);
//				else parent.frameRate(10);
//				this.jumpThroughQuakes();
//				count += 1;
//				label.update(count_year,month[count_month - 1],count_day, count);
				
			} else {
				for (int i = 0; i < archiveLength; i++) quakeArchive[i].close();
				scrubber = 0;
//				timekeeper += 1;
			}
			
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
//		label.draw(m);
	}
	
	public void drawAllQuakes(Map m)
	{	
		//| Iterate Quakes & Draw
		for (int i = 0; i < archiveLength; i++) 
		{ 
			float[] pos = m.getScreenPositionFromLocation(quakeArchive[i].getLocation());
			quakeArchive[i].update(pos[0], pos[1]);
			quakeArchive[i].draw();
		}
	
//		label.writeStatic(quakeArchive.size());
	}
	
	public void jumpThroughQuakes()
	{
/*
		count_year = Integer.parseInt(metaArchive[count][0]);	
		count_month = Integer.parseInt(PApplet.split(metaArchive[count][5], ".")[1]);
		count_day = Integer.parseInt(metaArchive[count][2]);
*/
	}
	
	public void incrementThroughQuakes(Map m)
	{
/*
		PApplet.println(count + " " + metaArchive.length);
		for (int i = 0; i < quakeArchive.size()-1; i++) 
		{
			Quake ball = (Quake) quakeArchive.get(i);			
			String[] q = ball.getYearMonthDayMagnitude();
			float[] pos = m.getScreenPositionFromLocation(ball.getLocation());
			
			if(metaArchive[count][5].equals(q[5])){
				ball.triggerQuake();
				label.write(ball.getYearMonthDayMagnitude());
			}

			ball.update(pos[0], pos[1]);
			ball.draw();
		}
*/
	}
	
	public void batchCommandQuakeArchive()
	{	
		for (int i = 0; i < archiveLength; i++) 
		{
			if(STATUS.equals("ANIMATING IN")) quakeArchive[i].kill();
			if(STATUS.equals("ANIMATING OUT")) quakeArchive[i].close();
		}
	}
	
	public void kill() 
	{
		parent.frameRate(50);
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

	public String status() 
	{
		return STATUS;
	}
}


