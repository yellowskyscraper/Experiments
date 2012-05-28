package dynamicearth2.app.data;

import ijeoma.motion.Motion;
import ijeoma.motion.tween.Tween;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import processing.core.PApplet;
import processing.core.PImage;
import processing.xml.XMLElement;

import dynamicearth2.app.graphics.Quake;
import dynamicearth2.app.labels.EqTimelineLabel;
import dynamicearth2.app.util.BasicUtils;

import de.fhpotsdam.unfolding.Map;
import de.fhpotsdam.unfolding.geo.Location;

public class EarthquakeArchive 
{
	PApplet parent;

	//| Data
	XMLElement xml;
	ArrayList<Quake> quakeArchive;
	String[][] metaArchive;
	boolean parsed;
	int parse = 0;
	boolean interstishial = false;
	int interstish = 0;
	
	EqTimelineLabel label;
	
	//| Julian Calendar Variables
	int cycle_speed = 1;
	String[] month = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
	int[] days_month = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
	int count_year = 1973;
	int count_month = 0;
	int count_day = 1;
	int count = 0;
	
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
		quakeArchive = new ArrayList<Quake>();
		xml = new XMLElement(parent, "../data/earthquakearchive/sample50mi1973-2011.xml");

		//| Parse USGS Compiled Earthquakes
		PApplet.println("Earthquake Timeline: PARSING " + xml.getChildCount());
		for(int i = 0; i < xml.getChildCount(); i ++)
		{
			String name = xml.getChild(i).getChild(0).getContent();
			String description = xml.getChild(i).getChild(1).getContent();
			float lat = Float.valueOf(xml.getChild(i).getChild(2).getContent()).floatValue();
		    float lon = Float.valueOf(xml.getChild(i).getChild(3).getContent()).floatValue();

//			Location coord = new Location(lon, lat);
//			float[] p = m.getScreenPositionFromLocation(coord);
//			float[] position = BasicUtils.scaleCoordinates(1050, 1050, p[0], p[1]);
			
			Quake b = new Quake(parent);
			b.setup(name, description, lat, lon);
			quakeArchive.add(b);
//			PApplet.print(".");
//			PApplet.println("Q: " + b.getName() + " " + lat + " " + lon);
/*
			if(p[0] > 0 && p[0] < w && p[1] > 0 && p[1] < h) {
				Quake b = new Quake(parent);
				b.setup(name, description, lat, lon);
				quakeArchive.add(b);
				PApplet.println("Q: " + b.getName());
			}
*/
		}
//		metaArchive = this.sortEarthquakes();
		PApplet.println("Earthquake Timeline: DONE LOADING " + quakeArchive.size());
		
		//| Time Line Label
//		label = new EqTimelineLabel(parent);
//		label.setup(metaArchive); 
	}
	
	//| UPDATE FUNCTIONS
	public void start()
	{
		STATUS = "ANIMATING IN";

//		Quake ball = (Quake) quakeArchive.get(0);
//		label.write(ball.getYearMonthDayMagnitude());
//		label.open();
	}
	
	public void step1()
	{
		STATUS = "ON";
		timekeeper = 0;
	}
	
	public void step2()
	{
		parent.frameRate(50);
		STATUS = "FAULTS";
		this.batchCommandQuakeArchive();
		timekeeper = 0;
	}
	
	public void step3()
	{
		STATUS = "DIM"; 
		this.batchCommandQuakeArchive();
		timekeeper = 0;
		label.close();
	}
	
	public void close()
	{
		STATUS = "ANIMATING OUT"; 
		this.batchCommandQuakeArchive();
		timekeeper = 0;	
		count = 0;
	}

	public void closed()
	{
		STATUS = "DONE";
		timekeeper = 0;	
		count = 0;
	}
	
	public void update()
	{		
		if(STATUS.equals("OFF")) {
			
		} else if(STATUS.equals("ANIMATING IN")) {
//			label.update(1973,"Jan",1, 0);
//			timekeeper += 1;
//			if(timekeeper > 80) step1();
			
		} else if(STATUS.equals("ON")) {
			if(count < metaArchive.length - 1) {
				if(count < ((metaArchive.length/3)*2)) parent.frameRate(10);
				else parent.frameRate(10);
				this.jumpThroughQuakes();
				count += 1;
				label.update(count_year,month[count_month - 1],count_day, count);
			} else {
				timekeeper += 1;
			}
			if(timekeeper > 10) this.step2();
			
		} else if(STATUS.equals("FAULTS")) {
			label.update(count_year,month[count_month - 1],count_day, count);
			timekeeper += 1;
			if(timekeeper > 80) this.step3();
			
		} else if(STATUS.equals("DIM")) {
			label.update(count_year,month[count_month - 1],count_day, count);
			if(label.closed() == true) timekeeper += 1;
			if(timekeeper > 100) this.close();
			
		} else if(STATUS.equals("ANIMATING OUT")) {
			label.update(1973,"Jan",1, 0);
			if(label.closed()) timekeeper += 1;
			if(timekeeper > 100) this.closed();
		}
	}
	
	//| DRAW FUNCTIONS
	public void draw(Map m) 	
	{	
		//| Timeline Animation
		if(STATUS.equals("ON")) this.incrementThroughQuakes(m);
		else if(STATUS.equals("ANIMATING IN")) this.drawAllQuakes(m);
		else if(STATUS.equals("DIM")) this.drawAllQuakes(m);
		else if(STATUS.equals("ANIMATING OUT")) this.drawAllQuakes(m);
		
		//| Legend
//		label.draw(m);
	}
	
	public void jumpThroughQuakes()
	{
		count_year = Integer.parseInt(metaArchive[count][0]);	
		count_month = Integer.parseInt(PApplet.split(metaArchive[count][5], ".")[1]);
		count_day = Integer.parseInt(metaArchive[count][2]);
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
	
	public void drawAllQuakes(Map m)
	{	
		//| Iterate Quakes & Draw
		for (int i = quakeArchive.size()-1; i >= 0; i--) 
		{ 
			Quake ball = (Quake) quakeArchive.get(i);
			float[] pos = m.getScreenPositionFromLocation(ball.getLocation());
			ball.update(pos[0], pos[1]);
			ball.draw();
		}
	
//		label.writeStatic(quakeArchive.size());
	}
	
	//| OTHER STUFF
	public void batchCommandQuakeArchive()
	{
		/*
		for (int i = quakeArchive.size()-1; i >= 0; i--) 
		{ 
			Quake ball = (Quake) quakeArchive.get(i);
			if(STATUS.equals("FAULTS")) ball.dim();
			if(STATUS.equals("DIM")) ball.close();
		}
		*/	
	}
			
	public String[][] sortEarthquakes()
	{
		//| Sort By Date
		String[][] ordering = new String[quakeArchive.size()][6];
		
		for (int j = quakeArchive.size()-1; j >= 0; j--) 
		{ 
			Quake ball = (Quake) quakeArchive.get(j);
			String[] bundle = ball.getYearMonthDayMagnitude();
			ordering[j] = bundle;
		}
		
		Arrays.sort(ordering, new Comparator<String[]>() {
            @Override
            public int compare(final String[] entry1, final String[] entry2) {
                final String time1 = entry1[5];
                final String time2 = entry2[5];
                return time1.compareTo(time2);
            }
        });
		
		return ordering;
	}
	
	public void kill() 
	{
		parent.frameRate(50);
		STATUS = "OFF";
		
		count_year = 1973;
		count_month = 0;
		count_day = 1;
		count = 0;
		
		for (int i = quakeArchive.size()-1; i >= 0; i--) 
		{ 
			Quake ball = (Quake) quakeArchive.get(i);
			ball.kill();
		}
		
		label.kill();
	}
	
	public void off() 
	{
		STATUS = "OFF";
		count = 0;
		timekeeper = 0;
	}

	public String status() 
	{
		return STATUS;
	}
}


