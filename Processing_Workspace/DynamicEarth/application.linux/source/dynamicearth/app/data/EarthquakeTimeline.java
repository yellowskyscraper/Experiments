package dynamicearth.app.data;

import ijeoma.motion.Motion;
import ijeoma.motion.tween.Tween;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import processing.core.PApplet;
import processing.core.PImage;
import processing.xml.XMLElement;

import dynamicearth.app.graphics.ArchiveQuake;
import dynamicearth.app.labels.EqTimelineLabel;
import dynamicearth.app.util.BasicUtils;

import de.fhpotsdam.unfolding.Map;
import de.fhpotsdam.unfolding.geo.Location;

public class EarthquakeTimeline 
{
	PApplet parent;

	//| Data
	XMLElement xml;
	ArrayList<ArchiveQuake> quakeArchive;
	String[][] metaArchive;
	boolean parsed;
	int parse = 0;
	boolean interstishial = false;
	int interstish = 0;
	
	//| Graphics//| Graphics
	PImage baymodel;
	PImage cities;
	PImage compass;
	PImage fault_primary;
	PImage fault_secondary;
	PImage fault_names;
	
	Tween tweenCompassIN;
	Tween tweenCompassOUT;
	Tween tweenCitiesIN;
	Tween tweenCitiesOUT;
	Tween tweenBayModelIN;
	Tween tweenBayModelOUT;
	Tween tweenFaultPrimaryIN;
	Tween tweenFaultPrimaryOUT;
	Tween tweenFaultSecondaryIN;
	Tween tweenFaultSecondaryOUT;
	Tween tweenFaultNamesIN;
	Tween tweenFaultNamesOUT;
	
	float alphaCompass = 0;
	float alphaCities = 0;
	float alphaBayModel = 0;
	float alphaFaultPrimary = 0;
	float alphaFaultSecondary = 0;
	float alphaFaultNames = 0;
	
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
	
	
	public EarthquakeTimeline(PApplet p)
	{
		parent = p;
	}
	
	public void setup(Map m, float w, float h)
	{
		//| Earthquake Data
		quakeArchive = new ArrayList<ArchiveQuake>();
		xml = new XMLElement(parent, "data/php/all.xml");

		//| Parse USGS Compiled Earthquakes
		PApplet.println("Earthquake Timeline: PARSING " + xml.getChildCount());
		for(int i = 0; i < xml.getChildCount(); i ++){
			String name = xml.getChild(i).getString("name");
		    float lon = Float.valueOf(xml.getChild(i).getChild(0).getContent()).floatValue();
			float lat = Float.valueOf(xml.getChild(i).getChild(1).getContent()).floatValue();

			Location coord = new Location(lat,lon);
			float[] p = m.getScreenPositionFromLocation(coord);
			float[] position = BasicUtils.scaleCoordinates(1050, 1050, p[0], p[1]);
			
			if(position[0] > 0 && position[0] < 1050 && position[1] > 0 && position[1] < h) {
				ArchiveQuake b = new ArchiveQuake(parent);
				b.setup(name, lat, lon);
				quakeArchive.add(b);
			}
		}
		metaArchive = this.sortEarthquakes();
		PApplet.println("Earthquake Timeline: DONE LOADING " + quakeArchive.size());
		
		//| Graphics
		cities = parent.loadImage("data/images/main_cities.png");
		compass = parent.loadImage("data/images/main_compass.png");
		baymodel = parent.loadImage("data/images/bay.jpg");
		fault_primary = parent.loadImage("data/images/faults_primary.png");
		fault_secondary = parent.loadImage("data/images/faults_secondary.png");
		fault_names = parent.loadImage("data/images/faults_names.png");
		
		//| Tween Test
		Motion.setup(parent);
		tweenCompassIN 			= new Tween(0f, 255f, 20f);
		tweenCitiesIN 			= new Tween(0f, 255f, 20f, 150f);
		tweenBayModelIN 		= new Tween(0f, 255f, 30f, 80f);
		tweenFaultPrimaryIN 	= new Tween(0f, 255f, 20f);
		tweenFaultSecondaryIN 	= new Tween(0f, 255f, 20f);
		tweenFaultNamesIN 		= new Tween(0f, 255f, 20f);
		
		tweenCompassOUT 		= new Tween(255f, 0f, 20f);
		tweenCitiesOUT 			= new Tween(255f, 0f, 20f);
		tweenBayModelOUT 		= new Tween(255f, 0f, 20f);
		tweenFaultPrimaryOUT 	= new Tween(255f, 0f, 20f);
		tweenFaultSecondaryOUT 	= new Tween(255f, 0f, 20f);
		tweenFaultNamesOUT 		= new Tween(255f, 0f, 20f);
		
		//| Time Line Label
		label = new EqTimelineLabel(parent);
		label.setup(metaArchive); 
	}
	
	public void start()
	{
		STATUS = "ANIMATING IN";
		tweenCompassIN.play();
		tweenCitiesIN.play();
		tweenBayModelIN.play();
		ArchiveQuake ball = (ArchiveQuake) quakeArchive.get(0);
		label.write(ball.getYearMonthDayMagnitude());
		label.open();
	}
	
	public void step1()
	{
		STATUS = "ON";
		timekeeper = 0;
		tweenCompassIN.stop();
		tweenCitiesIN.stop();
		tweenBayModelIN.stop();
	}
	
	public void step2()
	{
		parent.frameRate(50);
		STATUS = "FAULTS";
		this.batchCommandQuakeArchive();
		timekeeper = 0;
		tweenFaultPrimaryIN.play();
		tweenFaultNamesIN.play();
		tweenCitiesOUT.play();
	}
	
	public void step3()
	{
		STATUS = "DIM"; 
		this.batchCommandQuakeArchive();
		timekeeper = 0;
		tweenFaultPrimaryIN.stop();
		tweenFaultNamesIN.stop();
		tweenCitiesOUT.stop();
		tweenFaultSecondaryIN.play();
		label.close();
	}
	
	public void close()
	{
		STATUS = "ANIMATING OUT"; 
		this.batchCommandQuakeArchive();
		timekeeper = 0;	
		count = 0;
		tweenFaultSecondaryIN.stop();
		tweenFaultPrimaryOUT.play();
		tweenFaultSecondaryOUT.play();
		tweenFaultNamesOUT.play();
		tweenCompassOUT.play();
		tweenBayModelOUT.play();
	}

	
	public void closed()
	{
		STATUS = "DONE";
		timekeeper = 0;	
		count = 0;
		tweenFaultPrimaryOUT.stop();
		tweenFaultSecondaryOUT.stop();
		tweenFaultNamesOUT.stop();
		tweenCompassOUT.stop();
		tweenBayModelOUT.stop();
	}
	
	public void update()
	{		
		if(STATUS.equals("OFF")) {
			
		} else if(STATUS.equals("ANIMATING IN")) {
			alphaCompass = tweenCompassIN.getPosition();
			alphaCities = tweenCitiesIN.getPosition();
			alphaBayModel = tweenBayModelIN.getPosition();
			label.update(1973,"Jan",1, 0);
			if(label.opened() && alphaCompass == 255 && alphaCities == 255 && alphaBayModel == 255) timekeeper += 1;
			if(timekeeper > 80) step1();
			
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
			alphaFaultPrimary = tweenFaultPrimaryIN.getPosition();
			alphaFaultNames = tweenFaultNamesIN.getPosition();
			alphaCities = tweenCitiesOUT.getPosition();
			label.update(count_year,month[count_month - 1],count_day, count);
			if(alphaFaultPrimary == 255 && alphaFaultNames == 255 && alphaCities == 0) timekeeper += 1;
			if(timekeeper > 80) this.step3();
			
		} else if(STATUS.equals("DIM")) {
			alphaFaultSecondary = tweenFaultSecondaryIN.getPosition();
			label.update(count_year,month[count_month - 1],count_day, count);
			if(label.closed() == true && alphaFaultSecondary == 255) timekeeper += 1;
			if(timekeeper > 100) this.close();
			
		} else if(STATUS.equals("ANIMATING OUT")) {
			alphaBayModel = tweenBayModelOUT.getPosition();
			alphaCompass = tweenCompassOUT.getPosition();
			alphaFaultPrimary = tweenFaultPrimaryOUT.getPosition();
			alphaFaultSecondary = tweenFaultSecondaryOUT.getPosition();
			alphaFaultNames = tweenFaultNamesOUT.getPosition();
			label.update(1973,"Jan",1, 0);
			if(label.closed() && alphaBayModel == 0 && alphaCompass == 0 && alphaFaultPrimary == 0 && alphaFaultSecondary == 0 && alphaFaultNames == 0) timekeeper += 1;
			if(timekeeper > 100) this.closed();
		}
	}
	
	public void draw(Map m) 	
	{	
		//| Main Constants
		parent.tint(255, alphaBayModel);
		parent.image(baymodel, 0, 0, 1050, 1050);
		parent.tint(255, alphaCities);
		parent.image(cities, 0, 0, 1050, 1050);
		parent.tint(255, alphaCompass);
		parent.image(compass, 0, 0, 1050, 1050);
		parent.tint(255, alphaFaultSecondary);
		parent.image(fault_secondary, 0, 0, 1050, 1050);
		parent.tint(255, alphaFaultPrimary);
		parent.image(fault_primary, 0, 0, 1050, 1050);
		parent.tint(255);
		
		//| Timeline Animation
		if(STATUS.equals("ON")) this.incrementThroughQuakes(m);
		else if(STATUS.equals("FAULTS")) this.drawAllQuakes(m);
		else if(STATUS.equals("DIM")) this.drawAllQuakes(m);
		else if(STATUS.equals("ANIMATING OUT")) this.drawAllQuakes(m);
		
		//| Main Graphics
		parent.tint(255, alphaFaultNames);
		parent.image(fault_names, 0, 0, 1050, 1050);
		parent.tint(255);
		
		//| Legend
		label.draw(m);
	}
	
	public void jumpThroughQuakes()
	{
		count_year = Integer.parseInt(metaArchive[count][0]);	
		count_month = Integer.parseInt(PApplet.split(metaArchive[count][5], ".")[1]);
		count_day = Integer.parseInt(metaArchive[count][2]);
	}
	
	public void incrementThroughQuakes(Map m)
	{
		PApplet.println(count + " " + metaArchive.length);
		for (int i = 0; i < quakeArchive.size()-1; i++) 
		{
			ArchiveQuake ball = (ArchiveQuake) quakeArchive.get(i);			
			String[] q = ball.getYearMonthDayMagnitude();
			float[] pos = m.getScreenPositionFromLocation(ball.getLocation());
			
			if(metaArchive[count][5].equals(q[5])){
				ball.triggerQuake();
				label.write(ball.getYearMonthDayMagnitude());
			}

			ball.update(pos[0], pos[1]);
			ball.draw();
		}
	}
	
	public void drawAllQuakes(Map m)
	{	
		//| Iterate Quakes & Draw
		for (int i = quakeArchive.size()-1; i >= 0; i--) 
		{ 
			ArchiveQuake ball = (ArchiveQuake) quakeArchive.get(i);
			float[] pos = m.getScreenPositionFromLocation(ball.getLocation());
			ball.update(pos[0], pos[1]);
			ball.draw();
		}
		
		label.writeStatic(quakeArchive.size());
	}
	
	public void batchCommandQuakeArchive()
	{
		for (int i = quakeArchive.size()-1; i >= 0; i--) 
		{ 
			ArchiveQuake ball = (ArchiveQuake) quakeArchive.get(i);
			if(STATUS.equals("FAULTS")) ball.dim();
			if(STATUS.equals("DIM")) ball.close();
		}	
	}
			
	public String[][] sortEarthquakes()
	{
		//| Sort By Date
		String[][] ordering = new String[quakeArchive.size()][6];
		
		for (int j = quakeArchive.size()-1; j >= 0; j--) 
		{ 
			ArchiveQuake ball = (ArchiveQuake) quakeArchive.get(j);
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
		tweenCompassIN.stop();
		tweenCompassOUT.stop();
		tweenCitiesIN.stop();
		tweenCitiesOUT.stop();
		tweenBayModelIN.stop();
		tweenBayModelOUT.stop();
		tweenFaultPrimaryIN.stop();
		tweenFaultPrimaryOUT.stop();
		tweenFaultSecondaryIN.stop();
		tweenFaultSecondaryOUT.stop();
		tweenFaultNamesIN.stop();
		tweenFaultNamesOUT.stop();
		
		alphaCompass = 0;
		alphaCities = 0;
		alphaBayModel = 0;
		alphaFaultPrimary = 0;
		alphaFaultSecondary = 0;
		alphaFaultNames = 0;
		
		count_year = 1973;
		count_month = 0;
		count_day = 1;
		count = 0;
		
		for (int i = quakeArchive.size()-1; i >= 0; i--) 
		{ 
			ArchiveQuake ball = (ArchiveQuake) quakeArchive.get(i);
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


