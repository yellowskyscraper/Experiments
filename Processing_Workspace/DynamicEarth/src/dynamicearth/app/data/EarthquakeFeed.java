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
	String[][] lastQuake = new String[5][4];
	int totalBayAreaQuakes = 0;
	int totalWorldwideQuakes = 0;
	
	//| Sequencing
	String STATUS = "OFF";
	boolean animating = false;
	int timekeeper = 0;
	int recentQuaketicker = 0;
	int recentQuakecount = 0;
	
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
		//| Worldwide 7 Day Quakes
		xml = new XMLElement(parent, "http://earthquake.usgs.gov/earthquakes/catalogs/eqs7day-M2.5.xml");
		int tracker = 0;
		for(int i = 0; i < xml.getChild(0).getChildCount(); i ++)
		{
			if(xml.getChild(0).getChild(i).getName().equals("item")){
				totalWorldwideQuakes += 1;
				
				String name = xml.getChild(0).getChild(i).getChild(1).getContent();
				String description = xml.getChild(0).getChild(i).getChild(2).getContent();
				float lat = new Float(xml.getChild(0).getChild(i).getChild(4).getContent());
			    float lon = new Float(xml.getChild(0).getChild(i).getChild(5).getContent());
				
			    if(tracker < 5){
			    	lastQuake[tracker][0] = name;
			    	lastQuake[tracker][1] = description;
			    	lastQuake[tracker][2] = ""+lat;
			    	lastQuake[tracker][3] = ""+lon;
			    	tracker += 1;
			    }
			}
		}
		
		//| Bay Area 30 Day Quakes
		balls = new ArrayList<Ball>();
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
			recentQuaketicker += 1;
			if(recentQuaketicker > 83){
				recentQuaketicker = 0;
				recentQuakecount += 1;
				if(recentQuakecount > lastQuake.length-1) recentQuakecount = 0;
			}
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
		
		String[] lq = new String[4];
		lq[0] = lastQuake[recentQuakecount][0];
		lq[1] = lastQuake[recentQuakecount][1];
		lq[2] = lastQuake[recentQuakecount][2];
		lq[3] = lastQuake[recentQuakecount][3];
		label.update(lq, totalWorldwideQuakes, totalBayAreaQuakes);
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
		recentQuaketicker = 0;
		recentQuakecount = 0;
	}

	public String status() 
	{
		return STATUS;
	}
}
