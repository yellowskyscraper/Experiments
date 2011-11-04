package crustaldeformationvisualization;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PVector;

import de.fhpotsdam.unfolding.*;
import de.fhpotsdam.unfolding.geo.*;
import de.fhpotsdam.unfolding.providers.Microsoft;

public class CrustalDeformationVisualization extends PApplet 
{
	private static final long serialVersionUID = 1L;

	//| Map And Label Text
	Location sanFrancisco;
	Map map;
	PFont displayText;
	PFont displaySubText;
	
	//| Population Data
	int wid = 1021;
	int hei = 768;

	//| Marker Points
	Location coordTL;
	Location coordBR;

	//| Velocity Array
	String[] vectors;
	
	public void setup() 
	{
		wid = screenWidth;
		hei = screenHeight;
		
		background(0);
		size(wid, hei);

		//| Model Boundaries
		coordTL = new Location(38.33357743803447f,-122.76329040527344f);
		coordBR = new Location(37.36415426173095f,-121.81434631347656f);
		
		//| San Francisco
	    sanFrancisco = new Location(37.87485339352928f, -121.79443359375f);
	    map = new Map(this, 0, 0, width, height, new Microsoft.AerialProvider());
		map.zoomAndPanTo(new Location(37.87485339352928f, -121.79443359375f), 10);

		//| Velocity Data CSV File
		//| http://geon.unavco.org/unavco/GPS/pbo_final_frame.csv
		//| http://earthquake.usgs.gov/monitoring/gps/data/networks/SFBayArea/cleaned_velocity_file
		vectors = loadStrings("data/velocity/cleaned_velocity_file.txt");
		
		//| Copy
		displayText = createFont("data/fonts/Explo/Explo-Ultra.otf", 50);
		displaySubText = createFont("data/fonts/Explo/Explo-Medi.otf", 50);
	}

	public void draw() 
	{
		//| Map and Overlay
		background(0);
		map.draw();
		fill(0,0,0,200);
		rect(-1,-1,screenWidth+2,screenHeight+2);

		//| Draw Vectors
		this.drawVelocities();
		
		//| Model Bounds
		//this.markers();
		this.copy("History's Shadow", "The universe is not being pushed from behind, \nit is being pulled from the future.");
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
				float[] station = map.getScreenPositionFromLocation(coord);
				float vN = Float.valueOf(vector[2].trim()).floatValue();
				float vE = Float.valueOf(vector[3].trim()).floatValue();
				
				float x1 = station[0];
				float y1 = station[1];
				
				float x2 = x1 + vE *2;
				float y2 = y1 - vN *2;
				
				
				fill(255, 255, 255);
				stroke(255, 255, 255);
				PVector v = new PVector(vN,vE,0);
				pushMatrix();
				    translate(x2, y2);
				    rotate(v.heading2D());
					beginShape();
					vertex(0, 0);
					vertex(3, 7);
					vertex(-3, 7);
					vertex(0, 0);
					endShape(CLOSE);
				popMatrix();

				line(station[0], station[1], x2, y2);
			    ellipseMode(CENTER);
				ellipse(station[0], station[1], 5, 5);
			}
		}	
	}
	
	public void copy(String t, String s)
	{
		float[] tl = map.getScreenPositionFromLocation(coordTL);
		
		smooth();

		fill(0,0,0);
		textFont(displayText, 40);
		text(t, tl[0] + 29, tl[1] + 71);
		textFont(displaySubText, 20);
		text(s, tl[0] + 29, tl[1] + 106);

		fill(255,255,255);
		textFont(displayText, 40);
		text(t, tl[0] + 30, tl[1] + 70);
		textFont(displaySubText, 20);
		text(s, tl[0] + 30, tl[1] + 105);
	}
	
	public void markers()
	{
		//| Markers for Rough Models boundaries. 
		Location coordTL = new Location(38.33357743803447f,-122.76329040527344f);
		Location coordBR = new Location(37.36415426173095f,-121.81434631347656f);
		
		float[] tl = map.getScreenPositionFromLocation(coordTL);
		float[] br = map.getScreenPositionFromLocation(coordBR);

		noStroke();
		fill(0,0,0);
		ellipse(tl[0] - 1, tl[1] + 1, 5, 5);
		ellipse(br[0] - 1, tl[1] + 1, 5, 5);
		ellipse(br[0] - 1, br[1] + 1, 5, 5);
		ellipse(tl[0] - 1, br[1] + 1, 5, 5);
		
		fill(255,255,255);
		ellipse(tl[0], tl[1], 5, 5);
		ellipse(br[0], tl[1], 5, 5);
		ellipse(br[0], br[1], 5, 5);
		ellipse(tl[0], br[1], 5, 5);
	}
	
	public static void main(String _args[]) 
	{
		PApplet.main(new String[] { "--present", "--bgcolor=#111111", "--hide-stop", CrustalDeformationVisualization.class.getName() });
	}
}
