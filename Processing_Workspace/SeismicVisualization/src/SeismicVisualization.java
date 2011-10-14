
import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PFont;
import processing.xml.XMLElement;

import com.modestmaps.InteractiveMap;
import com.modestmaps.core.Point2f;
import com.modestmaps.geo.Location;
import com.modestmaps.providers.Microsoft;

import app.*;

public class SeismicVisualization extends PApplet 
{
	private static final long serialVersionUID = 1L;
	
	//| Map and Quakes
	Location sanFrancisco;
	InteractiveMap map;
	XMLElement xml;
	ArrayList<Ball> balls;
	Ball[] ballz = new Ball[1211];
	
	//| Staging Variables
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
///////////////////////////////////////////////////////////////////////////////////|
//| Main
	
	public static void main (String _args[]) 
	{
		PApplet.main(new String[] { "--present", "--bgcolor=#111111", "--hide-stop", SeismicVisualization.class.getName() });
	}
///////////////////////////////////////////////////////////////////////////////////|
//| Set Up Draw
	
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
	    sanFrancisco = new Location(37.87485339352928f, -121.79443359375f);
		map = new InteractiveMap(this, new Microsoft.AerialProvider());
		map.setCenterZoom(sanFrancisco, 10);
		
		//| Parsed USGS Google Earth Layer
		parsed = false;
		balls = new ArrayList<Ball>();

		count_year = 2010; //| <-- Single Year
//		count_year = 1973; //| <-- All
		
//		xml = new XMLElement(this, "http://localhost/USGS/usgs5.php?year="+count_year); //| <-- Single Year
//		xml = new XMLElement(this, "http://localhost/USGS/db_parse.php"); //| <-- All
		xml = new XMLElement(this, "../data/php/all.xml"); //| <-- All
		
		displayText = createFont("../data/fonts/Georgia-Bold.ttf", 50);
		displaySubText = createFont("../data/fonts/Georgia.ttf", 50);
	}
	
	
	public void draw() 	
	{
		//| Map and Overlay
		map.draw();
		fill(0x99000000);
		rect(-1,-1,screenWidth+2,screenHeight+2);

		//| Model Bounds
		this.markers();

		//| Quake and Logic
		if(parsed == true && interstishial == true) {
//			this.incrementThroughQuakes(); //| <-- Single Year
			this.totalQuakes(); //| <-- All
			
		} else if(parsed == true && interstishial == false) { 
			this.totalQuakes();
		
		} else if(parsed == false) {
			this.parseQuakes();
		}
	}
///////////////////////////////////////////////////////////////////////////////////|
//| Set Up Draw
	
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
		
		this.copy("Earthquakes " + m +" "+ count_day +", "+ count_year, "USGS Advanced National Seismic System");
		this.timeline(); //| <-- Single Year
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

		this.copy((balls.size()-1) +" Earthquakes "+ count_year, "USGS Advanced National Seismic System");
	}
	
	public void parseQuakes()
	{
		String name = xml.getChild(parse).getString("name");
	    float lon = Float.valueOf(xml.getChild(parse).getChild(0).getContent()).floatValue();
		float lat = Float.valueOf(xml.getChild(parse).getChild(1).getContent()).floatValue();

		Location coord = new Location(lat,lon);
		Point2f p = map.locationPoint(coord);
		
		if(p.x > 0 && p.x < wid && p.y > 0 && p.y < hei) {
			Ball b = new Ball(this);
			b.setup(name, p.x, p.y);
			balls.add(b);
		}
		
		parse += 1;
		
		this.copy("Processing " + parse + " Earthquakes", "USGS Advanced National Seismic System");
		
		if(parse > (xml.getChildCount()-1)) parsed = true;
//		else println(parse +" "+ (xml.getChildCount()-1));
	}
	
	public void markers()
	{
		//| Markers for Rough Models boundaries. 
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
///////////////////////////////////////////////////////////////////////////////////|
///////////////////////////////////////////////////////////////////////////////////|
}