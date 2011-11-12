package dynamicearth.app.data;

import java.util.ArrayList;
import processing.core.PApplet;
import processing.xml.XMLElement;
import de.fhpotsdam.unfolding.Map;
import de.fhpotsdam.unfolding.geo.Location;
import ijeoma.motion.tween.Tween;
import dynamicearth.app.graphics.Ball;
import dynamicearth.app.labels.EqFeedLabel;

public class EarthquakeFeed 
{
	PApplet parent;

	//| Data
	XMLElement xml;
	ArrayList<Ball> balls;
	
	//| Graphics
	EqFeedLabel label;
	String[] lastQuake = new String[2];
	int countQuake;
	
	//| Sequencing
	String STATUS = "OFF";
	boolean animating = false;
	int timekeeper = 0;
	
	Tween tweenUP;
	Tween tweenOUT;
	float alphaBackground = 255;
	float alphaForeground = 0;

	
	public EarthquakeFeed(PApplet p)
	{
		parent = p;
	}
	
	public void setup(Map m, float w, float h)
	{
		//| Data Sources
		this.checkUSGS(m, w, h);
		
		label = new EqFeedLabel(parent);
		label.setup();
	}
	
	public void checkUSGS(Map m, float w, float h)
	{
		balls = new ArrayList<Ball>();
		xml = new XMLElement(parent, "http://earthquake.usgs.gov/earthquakes/catalogs/eqs7day-M2.5.xml");
//		xml = new XMLElement(parent, "data/php/eqs1day-M0.xml");
		
		int tracker = 0;
		countQuake = 0;
		for(int i = 0; i < xml.getChild(0).getChildCount(); i ++)
		{
			if(xml.getChild(0).getChild(i).getName().equals("item")){
				countQuake += 1;
				
				String name = xml.getChild(0).getChild(i).getChild(1).getContent();
				String description = xml.getChild(0).getChild(i).getChild(2).getContent();
				float lat = new Float(xml.getChild(0).getChild(i).getChild(4).getContent());
			    float lon = new Float(xml.getChild(0).getChild(i).getChild(5).getContent());
				
			    if(tracker == 0){
			    	tracker = 1;
			    	lastQuake[0] = name;
			    	lastQuake[1] = description;
			    }
			    
				Location coord = new Location(lat,lon);
				float[] p = m.getScreenPositionFromLocation(coord);
				
				if(p[0] > 0 && p[0] < w && p[1] > 0 && p[1] < h) {
					Ball b = new Ball(parent);
					b.setup(name, description, lat, lon);
					balls.add(b);
				}
			}
		}	
	}

	public void start()
	{
		STATUS = "ANIMATING IN";
		timekeeper = 0;
		label.open();
		for (int i = balls.size()-1; i >= 0; i--) 
		{
			Ball ball = (Ball) balls.get(i);
			ball.open();
		}
	}
	
	public void update()
	{	
		if(STATUS.equals("OFF")) {
			
		} else if(STATUS.equals("ANIMATING IN")) {
			if(label.opened()) STATUS = "ON";
			
		} else if(STATUS.equals("ON")) {
			timekeeper += 1;
			if(timekeeper > 500) {
				STATUS = "ANIMATING OUT";
				label.close();
				for (int i = balls.size()-1; i >= 0; i--) 
				{
					Ball ball = (Ball) balls.get(i);
					ball.close();
				}
			}
			
		} else if(STATUS.equals("ANIMATING OUT")) {
			if(label.closed()) STATUS = "DONE";
		}
		
		label.update(lastQuake, countQuake);
	}
	
	public void draw(Map m) 	
	{	
		//| Iterate Quakes & Draw
		for (int i = balls.size()-1; i >= 0; i--) 
		{
			Ball ball = (Ball) balls.get(i);
			float[] pos = m.getScreenPositionFromLocation(ball.getLocation());
			ball.update(pos[0], pos[1]);
			ball.draw();
		}
		label.draw(m);
	}
	
	public void off() 
	{
		STATUS = "OFF";
	}

	public String status() 
	{
		return STATUS;
	}
}
