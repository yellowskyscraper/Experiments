package earthquakevisualization;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PFont;
import processing.xml.XMLElement;

import de.fhpotsdam.unfolding.*;
import de.fhpotsdam.unfolding.geo.*;
import de.fhpotsdam.unfolding.providers.Microsoft;

import earthquakevisualization.app.Ball;

public class EarthquakeVisualization extends PApplet 
{
	private static final long serialVersionUID = 1L;

	//| Map And Label Text
	Location sanFrancisco;
	Map map;
	XMLElement xml;
	ArrayList<Ball> balls;
	Ball[] ballz = new Ball[1211];
	
	int wid = 1021;
	int hei = 768;
	boolean parsed;
	int parse = 0;
	boolean interstishial = false;
	int interstish = 0;

	//| Marker Points
	Location coordTL;
	Location coordBR;

	//| Julian Calendar Variables
	int cycle_speed = 1;
	String[] month = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
	int[] days_month = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
	int count_year = 1973;
	int count_month = 0;
	int count_day = 1;
	int count_hour = 0;
	PFont displayText;
	PFont displaySubText;
	
	public void setup() 
	{
		wid = screenWidth;
		hei = screenHeight;
		
		background(0);
		size(wid, hei);

		//| Model Boundaries
		coordTL = new Location(38.33357743803447f,-122.76329040527344f);
		coordBR = new Location(37.36415426173095f,-121.81434631347656f);
		
		//| San FRancisco
		//| http://maps.cloudmade.com/editor
		//| Light Map:44768, Dark Map: 44840, Teal Map:44842
	    sanFrancisco = new Location(37.87485339352928f, -121.79443359375f);
	    map = new Map(this, 0, 0, width, height, new Microsoft.AerialProvider());
		map.zoomAndPanTo(new Location(37.87485339352928f, -121.79443359375f), 10);

		//| Parsed USGS Google Earth Layer
		parsed = false;
		balls = new ArrayList<Ball>();
		count_year = 2010; //| <-- Single Year
//		count_year = 1973; //| <-- All
		
		
//		xml = new XMLElement(this, "http://localhost/USGS/usgs5.php?year="+count_year); //| <-- Single Year
//		xml = new XMLElement(this, "http://localhost/USGS/db_parse.php"); //| <-- All
		xml = new XMLElement(this, "../data/php/all.xml"); //| <-- All

		for(int i = 0; i < xml.getChildCount(); i ++){
			String name = xml.getChild(i).getString("name");
		    float lon = Float.valueOf(xml.getChild(i).getChild(0).getContent()).floatValue();
			float lat = Float.valueOf(xml.getChild(i).getChild(1).getContent()).floatValue();
	
			Location coord = new Location(lat,lon);
			float[] p = map.getScreenPositionFromLocation(coord);
			
			if(p[0] > 0 && p[0] < wid && p[1] > 0 && p[1] < hei) {
				Ball b = new Ball(this);
				b.setup(name, p[0], p[1]);
				balls.add(b);
			}
		}
		parsed = true;
		
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
		
		//| Quake and Logic
		if(parsed == true && interstishial == true) {
//			this.incrementThroughQuakes(); //| <-- Single Year
			this.totalQuakes(); //| <-- All
			
		} else if(parsed == true && interstishial == false) { 
			this.totalQuakes();
		
		} else if(parsed == false) {
			this.parseQuakes();
		}
		
		//| Model Bounds
		//this.markers();
		this.copy("I Have Tried In My Way To Be Free", "I live in another world, but you live in it too.");
	}
	
	public void timeline()
	{
		/*
		Point2f tl = map.locationPoint(coordTL);
		
		float ypos = tl.y + 450;
		int day = 0;
		
		rect(tl.x + 30, ypos, 1, 365);
		rect(tl.x + 30, ypos, 5, 1);
		rect(tl.x + 30, ypos + 365, 5, 1);
		
		for (int i = 0; i <= 365; i++)
		{
			for (int j = balls.size()-1; j >= 0; j--) 
			{ 
				Ball ball = (Ball) balls.get(i);
				int y = Integer.parseInt(ball.getYearMonthDay()[0]);
				String m = ball.getYearMonthDay()[1];
				int d = Integer.parseInt(ball.getYearMonthDay()[2]);
				
				if(m.equals(month[count_month]) && d == count_day){
					
				}
			}
		}
		*/
	}
		
	public void incrementThroughQuakes()
	{
//		this.singleYear(); //| <-- Single Year
		this.multipleYear(); //| <-- All

		//| Iterate Quakes 
		for (int i = balls.size()-1; i >= 0; i--) 
		{ 
			Ball ball = (Ball) balls.get(i);
			int y = Integer.parseInt(ball.getYearMonthDay()[0]);
			String m = ball.getYearMonthDay()[1];
			int d = Integer.parseInt(ball.getYearMonthDay()[2]);
			
//			if(m.equals(month[count_month]) && d == count_day) ball.start(); //| <-- Single Year
			if(y == count_year && m.equals(month[count_month]) && d == count_day) ball.start(); //| <-- All
		
			ball.drawAni();
		}
		
		String m = "";
		if(month[count_month].equals("Jan")) m = "January";
		else if(month[count_month].equals("Feb")) m = "February";
		else if(month[count_month].equals("Mar")) m = "March";
		else if(month[count_month].equals("Apr")) m = "April";
		else if(month[count_month].equals("May")) m = "May";
		else if(month[count_month].equals("Jun")) m = "June";
		else if(month[count_month].equals("Jul")) m = "July";
		else if(month[count_month].equals("Aug")) m = "August";
		else if(month[count_month].equals("Sep")) m = "September";
		else if(month[count_month].equals("Oct")) m = "October";
		else if(month[count_month].equals("Nov")) m = "November";
		else if(month[count_month].equals("Dec")) m = "December";
		else m = "broken";
		
		//this.copy("Earthquakes " + m +" "+ count_day +", "+ count_year, "USGS Advanced National Seismic System");
		//this.timeline(); //| <-- Single Year
	}
	
	public void totalQuakes()
	{	
		interstish += 1;
		if(interstish > 300) interstishial = true;
		
		//| Iterate Quakes 
		for (int i = balls.size()-1; i >= 0; i--) 
		{ 
			Ball ball = (Ball) balls.get(i);
			ball.draw();
		}
		//this.copy((balls.size()-1) +" Earthquakes "+ count_year, "USGS Advanced National Seismic System");
	}
	
	public void parseQuakes()
	{
		/*
		String name = xml.getChild(parse).getString("name");
	    float lon = Float.valueOf(xml.getChild(parse).getChild(0).getContent()).floatValue();
		float lat = Float.valueOf(xml.getChild(parse).getChild(1).getContent()).floatValue();

		Location coord = new Location(lat,lon);
		float[] p = map.getScreenPositionFromLocation(coord);
		
		if(p[0] > 0 && p[0] < wid && p[1] > 0 && p[1] < hei) {
			Ball b = new Ball(this);
			b.setup(name, p[0], p[1]);
			balls.add(b);
		}
		
		parse += 1;
		
		//this.copy("Processing " + parse + " Earthquakes", "USGS Advanced National Seismic System");
		
		if(parse > (xml.getChildCount()-1)) parsed = true;
//		else println(parse +" "+ (xml.getChildCount()-1));
		*/
	}
	
	public void multipleYear()
	{
		count_hour += 1;
		if(count_hour > 1)
		{
			count_hour = 0;
			count_day += 1;
			if(count_day > days_month[count_month])
			{
				count_day = 1;
				count_month += 1;
				if(count_month > 11)
				{
					count_month = 0;
					count_year += 1;
					if(count_year > 2010) count_year = 1973;
				}
			}
		}
	}
	
	public void singleYear()
	{
		count_hour += 1;
		if(count_hour > 5)
		{
			count_hour = 0;
			count_day += 1;
			if(count_day > days_month[count_month])
			{
				count_day = 1;
				count_month += 1;
				if(count_month > 11) {
					count_month = 0;
					interstishial = false;
					interstish = 0;
				}
			}
		}
	}
	
	public void copy(String t, String s)
	{
		float[] tl = map.getScreenPositionFromLocation(coordTL);
		
		smooth();
		fill(0, 0, 0);
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
		PApplet.main(new String[] { "--present", "--bgcolor=#111111", "--hide-stop", EarthquakeVisualization.class.getName() });
	}
}
