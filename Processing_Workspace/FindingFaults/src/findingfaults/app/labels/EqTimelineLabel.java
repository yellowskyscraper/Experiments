package findingfaults.app.labels;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PFont;
import de.fhpotsdam.unfolding.Map;
import de.fhpotsdam.unfolding.geo.Location;
import findingfaults.app.graphics.Ball;

public class EqTimelineLabel {
	
	PApplet parent;

	PFont titleText;
	PFont secondaryTitleText;
	PFont secondaryText;
	
	//| Data 
	float[] quakes;
	String latestEarthuake;
	String latestEarthquakePlace;
	String latestEarthquakeDate;
	int totalEarthuakes;
	float markerTick = 51;
	
	public EqTimelineLabel(PApplet p)
	{
		parent = p;
	}
	
	public void setup(float[] q)
	{
		quakes = q;
		
		latestEarthuake = "";
		latestEarthquakePlace = "";
		latestEarthquakeDate = "";
		totalEarthuakes = 0;

		titleText = parent.createFont("data/fonts/ExploSlab/ExploSlab-Medi.otf", 21);
		secondaryTitleText = parent.createFont("data/fonts/ExploSlab/ExploSlab-Medi.otf", 15);
		secondaryText = parent.createFont("data/fonts/ExploSlab/ExploSlab.otf", 15);
	}
	
	public void update(int y, String m, int d, float c)
	{	
		//PApplet.println(y +" "+ m +" "+ d);
		markerTick = 0.0235f * c;
		latestEarthquakeDate = y +" "+ m +" "+ d;
	}
	
	public void write(String[] q)
	{	
		latestEarthuake = "Magnitude " + q[4];
		
		String[] temp = q[3].substring(1).split(" ");
		for(int i=0; i<temp.length; i++) temp[i] = toProperCase(temp[i]); 
		latestEarthquakePlace = PApplet.join(temp, ' ') + " " + q[5];
	}
	
	public void draw(Map m)
	{	
		float[] tl = m.getScreenPositionFromLocation(new Location(38.339f, -122.796f));
		
		//| Backing
		parent.pushMatrix();
		parent.translate(tl[0], tl[1]);
		
		parent.noStroke();
		parent.fill(255);
		parent.rect(20, 845, 388, 186);
		
		parent.strokeWeight(1);
		parent.stroke(154,194,185);
		parent.rect(25, 850, 378, 176);
			
		//| Title
		parent.smooth();
		parent.fill(0);
		parent.textFont(titleText, 21);
		parent.text("Cumulative Earthquakes 1973-2010",  50, 890);
	
		//| Sub Title
		int tick = PApplet.round(markerTick) + 49;
		
		if(tick > 200) {
			parent.textAlign(PConstants.LEFT);
		}
		
		parent.fill(0, 103, 73);
		parent.textFont(secondaryTitleText, 15);
		parent.text(latestEarthuake, tick, 924);

		parent.fill(100, 100, 100);
		parent.textFont(secondaryText, 15);
		parent.text(latestEarthquakePlace, tick, 942);
		
		parent.text(latestEarthquakeDate, tick, 960);
		
		this.drawTimeline();
		
		parent.popMatrix();
	}
	
	public void drawTimeline()
	{	
		//| Draw Time Line
		//| W:324 H:20 X:50 Y:981
		float start = 0.0f;
		float increment = 0.0235f; 
		parent.noSmooth();
		parent.noStroke();
		
		//| Iterate Quakes & Draw

		for(int i = 0; i < quakes.length; i ++) 
		{
			float mag = quakes[i] * 2;
			if(mag == 0) mag = 1;

			parent.fill(188, 214, 205);
			parent.rect(51 + start, 995 - mag, 1, mag);
			
			start += increment;
		}
		
		int tick = PApplet.round(markerTick) + 51;
		
		parent.smooth();
		parent.fill(158, 194, 175);
		parent.rect(50, 995, 328, 1);
		parent.fill(0, 103, 73);
		parent.rect(tick, 965, 2, 30);
		parent.rect(tick, 965, 3, 6);
		parent.rect(tick, 965, 4, 5);
		parent.rect(tick, 965, 5, 4);
		parent.rect(tick, 965, 6, 3);
		parent.rect(tick, 965, 7, 2);
		parent.rect(tick, 965, 8, 1);
		parent.fill(0);
		parent.rect(50, 981, 1, 15);
		parent.rect(378, 981, 1, 15);
	}
	
	public static String toProperCase(String t)
	{	
		String forename = t;
		String forenameInitial = "";

		forename = forename.toLowerCase();
		forenameInitial = forename.substring(0, 1).toUpperCase();
		forename = forenameInitial + forename.substring(1,forename.length());
	
		return forename;
	}
}
