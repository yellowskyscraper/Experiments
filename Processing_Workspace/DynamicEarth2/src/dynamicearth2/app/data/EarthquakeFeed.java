package dynamicearth2.app.data;

import java.util.ArrayList;
import processing.core.PApplet;
import processing.core.PImage;
import processing.xml.XMLElement;
import de.fhpotsdam.unfolding.Map;
import de.fhpotsdam.unfolding.geo.Location;
import ijeoma.motion.Motion;
import ijeoma.motion.tween.Tween;
import dynamicearth2.app.graphics.Quake;
import dynamicearth2.app.labels.EqFeedLabel;
import dynamicearth2.app.util.BasicUtils;

public class EarthquakeFeed 
{
	PApplet parent;

	//| Data
	XMLElement xml;
	ArrayList<Quake> balls;
	
	//| Graphics
	PImage cities;
	PImage compass;
	PImage baymodel;
	
	Tween tweenCompassIN;
	Tween tweenCompassOUT;
	Tween tweenCitiesIN;
	Tween tweenCitiesOUT;
	Tween tweenBayModelIN;
	Tween tweenBayModelOUT;
	
	float alphaCompass = 0;
	float alphaCities = 0;
	float alphaBayModel = 0;
	
	EqFeedLabel label;
	String[][] lastQuake = new String[10][4];
	int totalBayAreaQuakes = 0;
	int totalWorldwideQuakes = 0;
	int totalDaysArchived = 0;
	
	//| Sequencing
	String STATUS = "OFF";
	boolean animating = false;
	
	int totalTime = 2400;
	int interTime = Math.round(totalTime/12);
	
	int timekeeper = 0;
	int recentQuaketicker = 0;
	int recentQuakecount = 0;
	
	public EarthquakeFeed(PApplet p)
	{
		parent = p;
	}
	
	public void setup(Map m, float w, float h)
	{
		//| Data Sources
		this.checkUSGS(m, w, h);
		
		cities = parent.loadImage("data/images/main_cities.png");
		compass = parent.loadImage("data/images/main_compass.png");
		baymodel = parent.loadImage("data/images/bay.jpg");
		
		//| Tween Test
		Motion.setup(parent);
		tweenCompassIN 		= new Tween(0f, 255f, 20f);
		tweenCitiesIN 		= new Tween(0f, 255f, 20f, 150f);
		tweenBayModelIN 	= new Tween(0f, 255f, 30f, 80f);
		tweenCompassOUT 	= new Tween(255f, 0f, 20f, 50f);
		tweenCitiesOUT 		= new Tween(255f, 0f, 20f, 50f);
		tweenBayModelOUT 	= new Tween(255f, 0f, 20f, 50f);
		
		//| Legend
		label = new EqFeedLabel(parent);
		label.setup();
		label.setDaysArchived(totalDaysArchived);
	}
	
	public void checkUSGS(Map m, float w, float h)
	{

		balls = new ArrayList<Quake>();
		int tracker = 0;
		
		//| Seven Day - 2.5 Magnitude
//		xml = new XMLElement(parent, "data/php/eqs7day-M2.5.xml");
		xml = new XMLElement(parent, "http://earthquake.usgs.gov/earthquakes/catalogs/eqs7day-M2.5.xml");
		for(int i = 0; i < xml.getChild(0).getChildCount(); i ++)
		{
			if(xml.getChild(0).getChild(i).getName().equals("item")){
				
				String name = xml.getChild(0).getChild(i).getChild(1).getContent();
				String description = xml.getChild(0).getChild(i).getChild(2).getContent();
				float lat = new Float(xml.getChild(0).getChild(i).getChild(4).getContent());
			    float lon = new Float(xml.getChild(0).getChild(i).getChild(5).getContent());
				
			    if(tracker < 10){
			    	lastQuake[tracker][0] = name;
			    	lastQuake[tracker][1] = description;
			    	lastQuake[tracker][2] = ""+lat;
			    	lastQuake[tracker][3] = ""+lon;
			    	tracker += 1;
			    }
			}
		}
		
		//| 30 Day - 2.5 Magnitude
//		xml = new XMLElement(parent, "data/php/retrieve.xml");
		xml = new XMLElement(parent, "http://localhost/Yellowskyscraper/applications/usgs/dailyarchive/retrieve.php");
		totalDaysArchived = xml.getInt("days");
		
		for(int i = 0; i < xml.getChildCount(); i ++)
		{
			totalWorldwideQuakes += 1;
			
			String name = xml.getChild(i).getChild(0).getContent();
			String description = xml.getChild(i).getChild(1).getContent();
			float lat = new Float(xml.getChild(i).getChild(2).getContent());
		    float lon = new Float(xml.getChild(i).getChild(3).getContent());

			Location coord = new Location(lat,lon);
			float[] p = m.getScreenPositionFromLocation(coord);
			p = BasicUtils.scaleCoordinates(1051, 1051, p[0], p[1]);

			if(p[0] > 0 && p[0] < 1051 && p[1] > 0 && p[1] < h) {	
				totalBayAreaQuakes += 1;
				Quake b = new Quake(parent);
//				b.setup(name, description, lat, lon, true);
				balls.add(b);
			}
		}
		
/*
		//| 30 Day - 2.5 Magnitude
		xml = new XMLElement(parent, "http://earthquake.usgs.gov/earthquakes/shakemap/rss.xml");
		for(int i = 0; i < xml.getChild(0).getChildCount(); i ++)
		{
			if(xml.getChild(0).getChild(i).getName().equals("item")){
				
				String name = xml.getChild(0).getChild(i).getChild(0).getContent();
				String description = xml.getChild(0).getChild(i).getChild(3).getContent();;
				float lat = new Float(xml.getChild(0).getChild(i).getChild(4).getContent());
			    float lon = new Float(xml.getChild(0).getChild(i).getChild(5).getContent());

				Location coord = new Location(lat,lon);
				float[] p = m.getScreenPositionFromLocation(coord);

				if(p[0] > 0 && p[0] < w && p[1] > 0 && p[1] < h) {	
					totalBayAreaQuakes += 1;
					Quake b = new Quake(parent);
					b.setup(name, description, lat, lon, false);
					balls.add(b);
				}
			}
		}
*/
	}

	public void start()
	{
		STATUS = "ANIMATING IN";
		timekeeper = 0;
		label.open();
		tweenCompassIN.play();
		tweenCitiesIN.play();
		tweenBayModelIN.play();
	}
	
	public void step1()
	{
		STATUS = "ON";
		timekeeper = 0;
		tweenCompassIN.stop();
		tweenCitiesIN.stop();
		tweenBayModelIN.stop();
		for (int i = balls.size()-1; i >= 0; i--) 
		{
			Quake ball = (Quake) balls.get(i);
			ball.open();
		}
	}
	
	public void close()
	{
		STATUS = "ANIMATING OUT";
		timekeeper = 0;
		label.close();
		tweenCompassOUT.play();
		tweenCitiesOUT.play();
		tweenBayModelOUT.play();
		for (int i = balls.size()-1; i >= 0; i--) 
		{
			Quake ball = (Quake) balls.get(i);
			ball.close();
		}
	}
	
	public void closed()
	{
		STATUS = "DONE";
		tweenCompassOUT.stop();
		tweenCitiesOUT.stop();
		tweenBayModelOUT.stop();
	}

	public void update()
	{	
		if(STATUS.equals("OFF")) {
			
		} else if(STATUS.equals("ANIMATING IN")) {
			alphaCompass = tweenCompassIN.getPosition();
			alphaCities = tweenCitiesIN.getPosition();
			alphaBayModel = tweenBayModelIN.getPosition();
			if(label.opened() && alphaCompass == 255 && alphaCities == 255 && alphaBayModel == 255) timekeeper += 1;
			if(timekeeper > 80) this.step1();
			
		} else if(STATUS.equals("ON")) {
			timekeeper += 1;
			recentQuaketicker += 1;
			if(recentQuaketicker > interTime){
				recentQuaketicker = 0;
				recentQuakecount += 1;
				if(recentQuakecount > lastQuake.length-1) recentQuakecount = 0;
			}
			if(timekeeper > totalTime) this.close();
			
		} else if(STATUS.equals("ANIMATING OUT")) {
			alphaCompass = tweenCompassOUT.getPosition();
			alphaCities = tweenCitiesOUT.getPosition();
			alphaBayModel = tweenBayModelOUT.getPosition();
			if(label.closed() && alphaCompass == 0 && alphaCities == 0 && alphaBayModel == 0) timekeeper += 1;
			if(timekeeper > 30) this.closed();
		}
		
		String[] lq = new String[4];
		lq[0] = lastQuake[recentQuakecount][0];
		lq[1] = lastQuake[recentQuakecount][1];
		lq[2] = lastQuake[recentQuakecount][2];
		lq[3] = lastQuake[recentQuakecount][3];
		label.update(lq, totalWorldwideQuakes, totalBayAreaQuakes);
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
		parent.tint(255);

		//| Iterate Quakes & Draw
		for (int i = balls.size()-1; i >= 0; i--) 
		{
			Quake ball = (Quake) balls.get(i);
			float[] pos = m.getScreenPositionFromLocation(ball.getLocation());
			ball.update(pos[0], pos[1]);
			ball.draw();
		}
		
		//| Legend
		label.draw(m);
	}

	public void kill() 
	{
		STATUS = "OFF";
		recentQuaketicker = 0;
		recentQuakecount = 0;
		
		tweenCompassIN.stop();
		tweenCompassOUT.stop();
		tweenCitiesIN.stop();
		tweenCitiesOUT.stop();
		tweenBayModelIN.stop();
		tweenBayModelOUT.stop();
		
		alphaCompass = 0;
		alphaCities = 0;
		alphaBayModel = 0;
		
		for (int i = balls.size()-1; i >= 0; i--) 
		{
			Quake ball = (Quake) balls.get(i);
			ball.kill();
		}
		
		label.kill();	
	}
	
	public void off() 
	{
		STATUS = "OFF";
		recentQuaketicker = 0;
		recentQuakecount = 0;
	}

	public String status() 
	{
		return STATUS;
	}
}
