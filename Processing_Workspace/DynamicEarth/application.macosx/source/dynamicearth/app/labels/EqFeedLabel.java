package dynamicearth.app.labels;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PFont;
import processing.core.PImage;
import ijeoma.motion.Motion;
import ijeoma.motion.tween.Tween;
import de.fhpotsdam.unfolding.Map;
import de.fhpotsdam.unfolding.mapdisplay.MapDisplayFactory;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.providers.OpenStreetMap;

public class EqFeedLabel {
	
	PApplet parent;
	boolean MODEL = false;

	PFont titleText;
	PFont secondaryTitleText;
	PFont secondaryText;

	Tween tweenBackgroundIN;
	Tween tweenForegroundIN;
	Tween tweenBackgroundOUT;
	Tween tweenForegroundOUT;
	int animating = 3;
	float alphaBackground = 0;
	float alphaForeground = 0;
	
	//| Data 
	Map miniMap;
	PImage miniMapImage;
	int zoom = 0;
	Location latestCoord;
	String[] latestEarthuake;
	String latestEarthquakeDate;
	int totalBayAreaQuakes;
	int totalWorldwideQuakes;
	int totalDaysArchived = 0;
	
	public EqFeedLabel(PApplet p)
	{
		parent = p;
	}
	
	public void setup()
	{
		//| Tween Test
		Motion.setup(parent);
		tweenBackgroundIN = new Tween(0f, 255f, 20f);
		tweenForegroundIN = new Tween(0f, 255f, 10f, 10f);
		tweenBackgroundOUT = new Tween(255f, 0f, 20f, 50f);
		tweenForegroundOUT = new Tween(255f, 0f, 10f, 40f);
		
		latestCoord = new Location(37.85316995894978f, -121.95510864257812f);
		
		OpenStreetMap.CloudmadeProvider osm = new OpenStreetMap.CloudmadeProvider(MapDisplayFactory.OSM_API_KEY, 44842);
		miniMap = new Map(parent, "miniMap", 0, 0, 256, 180, true, false, osm);
//		miniMap = new Map(parent, "miniMap", 0, 0, 127, 61, true, false, osm);
//		miniMap.zoomAndPanTo(latestCoord, zoom);
		
		miniMapImage = parent.loadImage("data/images/small_map.jpg");
				
		titleText = parent.createFont("data/fonts/ExploSlab/ExploSlab-Medi.otf", 16);
		secondaryTitleText = parent.createFont("data/fonts/Explo/Explo-Medi.otf", 12);
		secondaryText = parent.createFont("data/fonts/Explo/Explo-Medi.otf", 12);
	}
	
	public void setDaysArchived(int d)
	{
		totalDaysArchived = d;
	}
	
	public void update(String[] e, int w, int b)
	{	
		switch(animating){
			case 1:
			alphaBackground = tweenBackgroundIN.getPosition();
			alphaForeground = tweenForegroundIN.getPosition();
			break;
			
			case 0:
			alphaBackground = tweenBackgroundOUT.getPosition();
			alphaForeground = tweenForegroundOUT.getPosition();
			break;
		}

		String[] s = PApplet.split(e[0], ',');
		
		String n = s[0];
		String[] d = s[1].substring(1).split(" ");
		for(int i=0; i<d.length; i++) d[i] = toProperCase(d[i]); 
		
		latestEarthuake = new String[2];
		latestEarthuake[0] = n;
		latestEarthuake[1] = PApplet.join(d, ' ');
		
		latestEarthquakeDate = e[1];
		totalBayAreaQuakes = b;
		totalWorldwideQuakes = w;
		latestCoord = new Location(Float.parseFloat(e[2]), Float.parseFloat(e[3]));
	}
	
	public void open()
	{
		animating = 1;
		tweenBackgroundIN.play();
		tweenForegroundIN.play();
	}
	
	public void close()
	{
		animating = 0;
		tweenBackgroundOUT.play();
		tweenForegroundOUT.play();
	}
	
	public boolean opened()
	{
		return (alphaForeground == 255) ? true : false;
	}
	
	public boolean closed()
	{
		return (alphaBackground == 0) ? true : false;
	}
	
	public void draw(Map m) 	
	{	
		float[] tl = m.getScreenPositionFromLocation(new Location(38.339f, -122.796f));
		int xpos = Math.round(tl[0]);
		int ypos = Math.round(tl[0]);
		
		//| Backing
		parent.pushMatrix();
		parent.translate(xpos, ypos);
		
		//| Labal
		if(MODEL) this.drawLabel(1019 - 259 - 20, 34 + 346, -90);
		else this.drawLabel(27, 1019 - 259, 0);
		
		parent.popMatrix();
	}
	
	public void drawLabel(int x, int y, int r)
	{
		//| Box Location
		parent.pushMatrix();
		parent.translate(x,y);
		parent.rotate(PApplet.radians(r));
		
		//| Label
		int boxW = 328;
		int boxH = 259;
		parent.noStroke();
		parent.fill(255, alphaBackground);
		parent.rect(0, 0, boxW, boxH);
		parent.strokeWeight(1);
		parent.noFill();
		parent.stroke(154,194,185, alphaForeground);
		parent.rect(5, 5, boxW - 11, boxH - 11);
		
		//| Title Cpoy
		parent.textAlign(PConstants.LEFT);
		parent.smooth();
		parent.fill(0, alphaForeground);
		parent.textFont(titleText, 12);
		parent.text("Bay Area earthquakes in the last " + totalDaysArchived + " days: " + totalBayAreaQuakes, 25, 30);
		parent.text("Worldwide earthquakes in the last " + totalDaysArchived + " days: " + totalWorldwideQuakes, 25, 46);

		//| Map
		parent.pushMatrix();
		parent.translate(18, 62);
		
		parent.noStroke();
		parent.fill(100,100,100, alphaForeground);
		parent.rect(-2, 2, 2, 180);
		parent.rect(0, 180, 294, 2); 
		
		parent.fill(0,0,0, alphaForeground);
		parent.rect(0, 0, 296, 180);
		
		//| Mini Map
		miniMap.move(17, 0);
		parent.tint(255, alphaForeground);
		parent.image(miniMapImage, 0, 0);
		parent.tint(255);

		//| Epicenter Values
		String mag = latestEarthuake[0].substring(1);
		int diamiter = Math.round(Float.parseFloat(mag)*2);
		float[] p = miniMap.getScreenPositionFromLocation(latestCoord);

		parent.stroke(255, alphaForeground);
		parent.noFill();
		parent.ellipseMode(PApplet.CENTER);
		parent.ellipse(p[0], p[1], 5, 5);
		parent.stroke(255, alphaForeground - 100);
		parent.strokeWeight(diamiter);
		parent.ellipse(p[0], p[1], diamiter+5, diamiter+5);
		parent.noStroke();
		parent.fill(0, 255);
		
		//| Epicenter Text
		String des = latestEarthuake[1];
		parent.fill(255, alphaForeground);
		parent.textFont(secondaryTitleText, 12);
		parent.text("Magnitude " + mag + ", " + des, 30, 155);
		
		parent.textFont(secondaryText, 12);
		String[] ld = latestEarthquakeDate.split(" ");
		parent.text(ld[0] + " " + ld[1] + " " + ld[2] + " " + ld[3] + " " + ld[4], 30, 170);

		parent.popMatrix();
		parent.popMatrix();
		
	}

	/*
	public void drawLabel(int x, int y, int r)
	{
		//| Box Location
		parent.pushMatrix();
//		parent.translate(27, 841); //| Main Laptop View
		parent.translate(x,y); //| Model Projection View
		parent.rotate(PApplet.radians(r));
		
		//| Label
		int boxW = 346;
		int boxH = 184;
		parent.noStroke();
		parent.fill(255, alphaBackground);
		parent.rect(0, 0, boxW, boxH);
		parent.strokeWeight(1);
		parent.noFill();
		parent.stroke(154,194,185, alphaForeground);
		parent.rect(5, 5, boxW - 11, boxH - 11);
		
		//| Title Cpoy
		parent.textAlign(PConstants.LEFT);
		parent.smooth();
		parent.fill(0, alphaForeground);
		parent.textFont(titleText, 16);
		parent.text("Dynamic Earth",  25, 37);
		
		parent.fill(100, 100, 100, alphaForeground);
		parent.textFont(secondaryText, 12);
		parent.text("Bay Area earthquakes in the last " + totalDaysArchived + " days; " + totalBayAreaQuakes, 25, 60);
		parent.text("Worldwide earthquakes in the last " + totalDaysArchived + " days; " + totalWorldwideQuakes, 25, 75);
	
		//| Mini Map
		parent.pushMatrix();
		parent.translate(27, 97);
		
		//| Map & Marker Values
		String mag = latestEarthuake[0].substring(1);
		int diamiter = Math.round(Float.parseFloat(mag)*2);
		
		parent.noStroke();
		parent.fill(100,100,100, alphaForeground);
		parent.rect(-2, 2, 2, 61);
		parent.rect(0, 61, 125, 2);
		
		parent.fill(0,0,0, alphaForeground);
		parent.rect(0, 0, 127, 61);
		
		miniMap.move(0, 0);
		miniMap.zoomAndPanTo(latestCoord, zoom);
		if(alphaForeground == 255) miniMap.draw();
		
		parent.stroke(255, alphaForeground);
		parent.noFill();
		parent.ellipseMode(PApplet.CENTER);
		parent.ellipse(127/2, 61/2, 5, 5);
		parent.stroke(255, alphaForeground - 100);
		parent.strokeWeight(diamiter);
		parent.ellipse(127/2, 61/2, diamiter+5, diamiter+5);
		parent.noStroke();
		
		//| Secondary
		parent.fill(0, 103, 73, alphaForeground);
		parent.textFont(secondaryTitleText, 12);
		parent.text("Magnitude " + mag, 143, 13);
		
		parent.fill(100, 100, 100, alphaForeground);
		parent.textFont(secondaryText, 12);
		String des = latestEarthuake[1];
		parent.text(des, 143, 28);
		
		parent.textFont(secondaryText, 12);
		String[] ld = latestEarthquakeDate.split(" ");
		parent.text(ld[0] + " " + ld[1] + " " + ld[2], 143, 43);
		parent.text(ld[3] + " " + ld[4], 143, 58);

		parent.popMatrix();
		parent.popMatrix();
	}
	*/

	public static String toProperCase(String t)
	{	
		String forename = t;
		String forenameInitial = "";

		forename = forename.toLowerCase();
		forenameInitial = forename.substring(0, 1).toUpperCase();
		forename = forenameInitial + forename.substring(1,forename.length());

		return forename;
	}
	
	public void kill()
	{
		animating = 3;
		tweenBackgroundIN.stop();
		tweenForegroundIN.stop();
		tweenBackgroundOUT.stop();
		tweenForegroundOUT.stop();
		alphaBackground = 0;
		alphaForeground = 0;
	}
}
