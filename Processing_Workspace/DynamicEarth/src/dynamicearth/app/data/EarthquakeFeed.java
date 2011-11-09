package dynamicearth.app.data;

import java.util.ArrayList;
import processing.core.PApplet;
import processing.core.PFont;
import processing.xml.XMLElement;
import de.fhpotsdam.unfolding.Map;
import de.fhpotsdam.unfolding.geo.Location;
import dynamicearth.app.graphics.Ball;
import dynamicearth.app.labels.EqFeedLabel;

public class EarthquakeFeed 
{
	PApplet parent;
	PFont displayText;

	//| Data
	XMLElement xml;
	ArrayList<Ball> balls;
	
	//| Graphics
	EqFeedLabel label;
	String[] lastQuake = new String[2];
	int countQuake;
	
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
	
	public void update()
	{	
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
}
