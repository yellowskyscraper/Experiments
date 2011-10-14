import com.modestmaps.InteractiveMap;
import com.modestmaps.core.Point2f;
import com.modestmaps.geo.Location;
import com.modestmaps.providers.Microsoft;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PVector;

public class VelocityVisualization extends PApplet 
{
	private static final long serialVersionUID = 1L;

	Location sanFrancisco;
	InteractiveMap map;
	PFont displayText;
	PFont displaySubText;
	
	//| Marker Points
	Location coordTL;
	Location coordBR;
	
	//| Velocity Array
	String[] vectors;
	
	public static void main (String _args[]) 
	{
		PApplet.main(new String[] { "--present", "--bgcolor=#111111", "--hide-stop", VelocityVisualization.class.getName() });
	}
	
	public VelocityVisualization()
	{
		super();
	}
	
	public void setup() 
	{
		size(screenWidth, screenHeight);

		//| Model Boundaries
		coordTL = new Location(38.33357743803447f,-122.76329040527344f);
		coordBR = new Location(37.36415426173095f,-121.81434631347656f);
		
		//| San FRancisco
	    sanFrancisco = new Location(37.87485339352928f, -121.79443359375f);
		map = new InteractiveMap(this, new Microsoft.AerialProvider());
		map.setCenterZoom(sanFrancisco, 10);
		
		//| Velocity Data CSV File
		//| http://geon.unavco.org/unavco/GPS/pbo_final_frame.csv
		//| http://earthquake.usgs.gov/monitoring/gps/data/networks/SFBayArea/cleaned_velocity_file
		vectors = loadStrings("../data/velocity/cleaned_velocity_file.txt");
		
		//| Copy
		displayText = createFont("../data/fonts/Georgia-Bold.ttf", 50);
		displaySubText = createFont("../data/fonts/Georgia.ttf", 50);
	}

	public void draw() 	
	{
		//| Map and Overlay
		map.draw();
		fill(0x99000000);
		rect(-1,-1,screenWidth+2,screenHeight+2);
		
		//| Draw Vectors
		this.drawVelocities();
		
		//| Model Bounds
		this.markers();
		this.copy("Bay Velocity", "USGS Crustal Deformation Monitoring System");
	}
	
	public Point2f coordString2Point(String lat, String lon)
	{
		float lt = Float.valueOf(lat.trim()).floatValue();
		float ln = Float.valueOf(lon.trim()).floatValue();
		Location coord = new Location(lt,ln);
		Point2f point = map.locationPoint(coord);
		
		return point;
	}
	
	public void drawVelocities()
	{
		for(int i = 0; i < vectors.length; i++) {
			String[] vector = vectors[i].split("\t");
			
			if(vector[1].equals("Longitude")){
				
			} else {
//				Point2f station = this.coordString2Point(vector[0], vector[1]);
				float lat = Float.valueOf(vector[0].trim()).floatValue();
				float lon = Float.valueOf(vector[1].trim()).floatValue();
				Location coord = new Location(lat,lon);
				Point2f station = map.locationPoint(coord);
				float vN = Float.valueOf(vector[2].trim()).floatValue();
				float vE = Float.valueOf(vector[3].trim()).floatValue();
				
				float x1 = station.x;
				float y1 = station.y;
				
				float x2 = x1 + vE *2;
				float y2 = y1 - vN *2;
				
				
				fill(255);
				stroke(255);
				PVector v = new PVector(vN,vE,0);
				pushMatrix();
				    translate(x2, y2);
				    rotate(v.heading2D());
					//line(0, 0, 3, 4);
					//line(0, 0, -3, 4);
					beginShape();
					vertex(0, 0);
					vertex(3, 7);
					vertex(-3, 7);
					vertex(0, 0);
					endShape(CLOSE);
				popMatrix();

				line(station.x, station.y, x2, y2);
				stroke(255);
				fill(0, 255, 0);
			    ellipseMode(CENTER);
				ellipse(station.x, station.y, 15, 15);
			}
		}	
	}
	
	public void copy(String t, String s)
	{
		Point2f tl = map.locationPoint(coordTL);
		
		smooth();
		
		fill(0);
		textFont(displayText, 40);
		text(t, tl.x + 32, tl.y + 72);
		textFont(displaySubText, 20);
		text(s, tl.x + 32, tl.y + 107);
		
		fill(255);
		textFont(displayText, 40);
		text(t, tl.x + 30, tl.y + 70);
		textFont(displaySubText, 20);
		text(s, tl.x + 30, tl.y + 105);
	}
	
	public void markers()
	{
		//| Markers for Rough Models boundaries. 
		Location coordTL = new Location(38.33357743803447f,-122.76329040527344f);
		Location coordBR = new Location(37.36415426173095f,-121.81434631347656f);
		
		Point2f tl = map.locationPoint(coordTL);
		Point2f br = map.locationPoint(coordBR);
		
		fill(0);
		noStroke();
		ellipse(tl.x + 2, tl.y + 2, 5, 5);
		ellipse(br.x + 2, tl.y + 2, 5, 5);
		ellipse(br.x + 2, br.y + 2, 5, 5);
		ellipse(tl.x + 2, br.y + 2, 5, 5);
		
		fill(255);
		ellipse(tl.x, tl.y, 5, 5);
		ellipse(br.x, tl.y, 5, 5);
		ellipse(br.x, br.y, 5, 5);
		ellipse(tl.x, br.y, 5, 5);
	}
}