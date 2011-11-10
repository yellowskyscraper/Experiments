package findingfaults.app.labels;

import ijeoma.motion.Motion;
import ijeoma.motion.tween.Tween;
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

	Tween tweenIN;
	Tween tweenOUT;
	Tween tweenDIM;
	int animating = 3;
	float alphaBackground = 0;
	float alphaForeground = 0;
	float alphaForegroundTicker = 0;
	int delay = 0;
	
	//| Data 
	String[][] quakes;
	String latestEarthuake;
	String latestEarthquakePlace;
	String latestEarthquakeDate;
	int totalEarthuakes;
	float markerTick = 51;
	
	public EqTimelineLabel(PApplet p)
	{
		parent = p;
	}
	
	public void setup(String[][] q)
	{
		quakes = q;

		latestEarthuake = "";
		latestEarthquakePlace = "";
		latestEarthquakeDate = "";
		totalEarthuakes = 0;

		//| Tween Test
		Motion.setup(parent);
		tweenIN = new Tween(0f, 255f, 10f);
		tweenOUT = new Tween(255f, 0f, 20f);
		tweenDIM = new Tween(255f, 0f, 10f);

		titleText = parent.createFont("data/fonts/ExploSlab/ExploSlab-Medi.otf", 21);
		secondaryTitleText = parent.createFont("data/fonts/ExploSlab/ExploSlab-Medi.otf", 15);
		secondaryText = parent.createFont("data/fonts/ExploSlab/ExploSlab.otf", 15);
	}

	public void open()
	{
		animating = 1;
		tweenIN.play();
	}
	
	public void close()
	{
		animating = 0;
		tweenOUT.play();
	}
	
	public boolean opened()
	{
		if(alphaForeground == 255) delay += 1;
		return (alphaForeground == 255 && delay > 20) ? true : false;
	}
	
	public boolean closed()
	{
		return (alphaForeground == 0) ? true : false;
	}
	
	public void done()
	{
		animating = 2;
		tweenDIM.play();
	}
	
	public void update(int y, String m, int d, float c)
	{	
		switch(animating){
			case 2:
			float j = tweenDIM.getPosition();
			alphaForegroundTicker = j;
			break;
		
			case 1:
			float v = tweenIN.getPosition();
			alphaBackground = v;
			alphaForeground = v;
			alphaForegroundTicker = v;
			break;
			
			case 0:
			float x = tweenOUT.getPosition();
			alphaBackground = x;
			alphaForeground = x;
			break;
		}
		
		markerTick = 0.420f * c;
		latestEarthquakeDate = y +" "+ m +" "+ d;
		latestEarthquakeDate = "";
	}
	
	public void draw(Map m)
	{	
		float[] tl = m.getScreenPositionFromLocation(new Location(38.339f, -122.796f));
		
		//| Backing
		parent.pushMatrix();
		parent.translate(tl[0], tl[1]);
		
		parent.noStroke();
		parent.fill(255, alphaBackground);
		parent.rect(20, 845, 388, 186);
		
		parent.strokeWeight(1);
		parent.noFill();
		parent.stroke(154,194,185, alphaBackground);
		parent.rect(25, 850, 378, 176);
			
		//| Title
		parent.textAlign(PConstants.LEFT);
		parent.smooth();
		parent.fill(0, alphaForeground);
		parent.textFont(titleText, 21);
		parent.text("Cumulative Earthquakes 1973-2010",  50, 890);
	
		//| Sub Title
		int tick = PApplet.round(markerTick) + 49;
		if(tick > 293) {
			parent.textAlign(PConstants.RIGHT);
			tick = 383;
		}
		
		parent.fill(0, 103, 73, alphaForegroundTicker);
		parent.textFont(secondaryTitleText, 15);
		parent.text(latestEarthuake, tick, 934);

		parent.fill(100, 100, 100, alphaForegroundTicker);
		parent.textFont(secondaryText, 15);
		parent.text(latestEarthquakePlace, tick, 952);
		
		parent.text(latestEarthquakeDate, tick, 970);
		
		this.drawTimeline();
		
		parent.popMatrix();
	}
	
	public void drawTimeline()
	{	
		//| Draw Time Line
		//| W:324 H:20 X:50 Y:981
		float start = 0.0f;
//		float increment = 0.0235f;
		float increment = 0.420f; 
		parent.noSmooth();
		parent.noStroke();
		
		for(int i = 0; i < quakes.length; i ++) 
		{
			float mag = Float.parseFloat(quakes[i][4]) * 2;
			if(mag == 0) mag = 1;

			parent.fill(188, 214, 205, alphaForeground);
			parent.rect(51 + start, 995 - mag, 1, mag);
			
			start += increment;
		}
		
		float tick = PApplet.round(markerTick) + 51;
		
		parent.smooth();
		parent.fill(158, 194, 175, alphaForeground);
		parent.rect(50, 995, 328, 1);
		parent.fill(0, 103, 73, alphaForegroundTicker);
		parent.rect(tick, 960, 2, 35);
		parent.rect(tick, 960, 3, 1);
		/*parent.rect(tick, 965, 3, 6);
		parent.rect(tick, 965, 4, 5);
		parent.rect(tick, 965, 5, 4);
		parent.rect(tick, 965, 6, 3);
		parent.rect(tick, 965, 7, 2);
		parent.rect(tick, 965, 8, 1);*/
		parent.fill(0, alphaForeground);
		parent.rect(50, 981, 1, 15);
		parent.rect(378, 981, 1, 15);
	}
	
	public void write(String[] q)
	{	
		latestEarthuake = "Magnitude " + q[4];
		//String[] temp = q[3].substring(1).split(" ");
		//for(int i=0; i<temp.length; i++) temp[i] = toProperCase(temp[i]); 
		//latestEarthquakePlace = PApplet.join(temp, ' ');
		latestEarthquakePlace = q[1] +" "+ q[2] +", "+ q[0];
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
