package findingfaults.app.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import processing.core.PApplet;
import processing.xml.XMLElement;

import findingfaults.app.graphics.Ball;
import findingfaults.app.labels.EqTimelineLabel;
import findingfaults.app.util.BasicUtils;

import de.fhpotsdam.unfolding.Map;
import de.fhpotsdam.unfolding.geo.Location;

public class EarthquakeTimeline 
{
	PApplet parent;

	//| Data
	XMLElement xml;
	ArrayList<Ball> balls;
	String[][] metaBalls;
	boolean parsed;
	int parse = 0;
	boolean interstishial = false;
	int interstish = 0;
	
	//| Graphics
	EqTimelineLabel label;
	
	//| Julian Calendar Variables
	int cycle_speed = 1;
	String[] month = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
	int[] days_month = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31}; //| Feb 29 
	int count_year = 1973;
	int count_month = 0;
	int count_day = 1;
	int count_hour = 0;
	int count = 0;
	
	//| Sequencing
	String STATUS = "OFF";
	int timekeeper = 0;
	
	
	public EarthquakeTimeline(PApplet p)
	{
		parent = p;
	}
	
	public void setup(Map m, float w, float h)
	{
		//| Earthquake Data
		balls = new ArrayList<Ball>();
		xml = new XMLElement(parent, "data/php/all.xml");

		//| Parse USGS Compiled Earthquakes
		PApplet.println("BEGIN " + xml.getChildCount());
		for(int i = 0; i < xml.getChildCount(); i ++){
			String name = xml.getChild(i).getString("name");
		    float lon = Float.valueOf(xml.getChild(i).getChild(0).getContent()).floatValue();
			float lat = Float.valueOf(xml.getChild(i).getChild(1).getContent()).floatValue();

			Location coord = new Location(lat,lon);
			float[] p = m.getScreenPositionFromLocation(coord);
			float[] position = BasicUtils.scaleCoordinates(1050, 1050, p[0], p[1]);
			
			if(position[0] > 0 && position[0] < 1050 && position[1] > 0 && position[1] < h) {
				Ball b = new Ball(parent);
				b.setup(name, lat, lon);
				balls.add(b);
			}
		}
		PApplet.println("DONE " + balls.size());
		metaBalls = this.sortEarthquakes();
		
		//| Time Line Label
		label = new EqTimelineLabel(parent);
		label.setup(metaBalls); 
	}
	
	public void start()
	{
		STATUS = "ANIMATING IN";
		Ball ball = (Ball) balls.get(0);
		label.write(ball.getYearMonthDayMagnitude());
		label.open();
	}
	
	public void update()
	{		
		if(STATUS.equals("OFF")) {
			label.update(1973,"Jan",1, 0);
			
		} else if(STATUS.equals("ANIMATING IN")) {
			label.update(1973,"Jan",1, 0);
			if(label.opened()) STATUS = "ON";
			
		} else if(STATUS.equals("ON")) {
			if(count < metaBalls.length) {
				this.jumpThroughQuakes();
			} else {
				STATUS = "DIM";
				this.batchCommandQuakes();
				//label.timelineFinished();
				label.close();
			}
			label.update(count_year,month[count_month - 1],count_day, count);
			
		} else if(STATUS.equals("DIM")) {
			timekeeper += 1;
			label.update(count_year,month[count_month - 1],count_day, count);
			if(timekeeper > 40) {
				STATUS = "ANIMATING OUT"; 
				this.batchCommandQuakes();
				timekeeper = 0;
			}
			
		} else if(STATUS.equals("ANIMATING OUT")) {
			timekeeper += 1;
			label.update(count_year,month[count_month - 1],count_day, count);
			//if(label.closed()) 
			if(timekeeper > 20) {
				STATUS = "DONE";
			}
		}
	}
	
	public void draw(Map m) 	
	{	
		if(STATUS.equals("ON")) this.incrementThroughQuakes(m);
		if(STATUS.equals("DIM")) this.drawQuakes(m);
		if(STATUS.equals("ANIMATING OUT")) this.drawQuakes(m);
		if(STATUS.equals("DONE")) this.drawQuakes(m);
		
		label.draw(m);
	}
	
	public void drawQuakes(Map m)
	{	
		//| Iterate Quakes & Draw
		for (int i = balls.size()-1; i >= 0; i--) 
		{ 
			Ball ball = (Ball) balls.get(i);
			float[] pos = m.getScreenPositionFromLocation(ball.getLocation());
			ball.update(pos[0], pos[1]);
			ball.draw();
		} 
	}
	
	public void incrementThroughQuakes(Map m)
	{
		for (int i = 0; i < balls.size()-1; i++) 
		{
			Ball ball = (Ball) balls.get(i);			
			String[] q = ball.getYearMonthDayMagnitude();
			float[] pos = m.getScreenPositionFromLocation(ball.getLocation());
			
			if(metaBalls[count][5].equals(q[5])){
				ball.triggerQuake();
				label.write(ball.getYearMonthDayMagnitude());
			}

			ball.update(pos[0], pos[1]);
			ball.draw();
		}
		
		count += 1;
	}
	
	public void jumpThroughQuakes()
	{
		count_year = Integer.parseInt(metaBalls[count][0]);	
		count_month = Integer.parseInt(PApplet.split(metaBalls[count][5], ".")[1]);
		count_day = Integer.parseInt(metaBalls[count][2]);
	}
	
	public void batchCommandQuakes()
	{
		for (int i = balls.size()-1; i >= 0; i--) 
		{ 
			Ball ball = (Ball) balls.get(i);
			if(STATUS.equals("DIM")) ball.dim();
			if(STATUS.equals("ANIMATING OUT")) ball.close();
		}	
	}
	
	public void incrementThroughCalendar(Map m)
	{
		//| Iterate Quakes Based on "Current Date"
		for (int i = balls.size()-1; i >= 0; i--) 
		{ 
			Ball ball = (Ball) balls.get(i);

			float[] pos = m.getScreenPositionFromLocation(ball.getLocation());
			int yr = Integer.parseInt(ball.getYearMonthDayMagnitude()[0]);
			String mn = ball.getYearMonthDayMagnitude()[1];
			int dy = Integer.parseInt(ball.getYearMonthDayMagnitude()[2]);
			
			if(yr == count_year && mn.equals(month[count_month]) && dy == count_day) {
				PApplet.println("QUAKE: " + ball.getYearMonthDayMagnitude()[0] +" "+ ball.getYearMonthDayMagnitude()[1] +" "+ ball.getYearMonthDayMagnitude()[2]);
				ball.triggerQuake();
				label.write(ball.getYearMonthDayMagnitude());
			}
			
			ball.update(pos[0], pos[1]);
			ball.triggerQuake();
		}
	}
	
	public void julianCalendarHour()
	{
		count_hour += 1;
		if(count_hour > 1)
		{
			count_hour = 0;
			count_day += 1;
			
			if(count_year == 1976 || count_year == 1980 || count_year == 1984 || count_year == 1988 
					|| count_year == 1992 || count_year == 1996 || count_year == 2000 || count_year == 2004 || count_year == 2008) days_month[1] = 29;
			else days_month[1] = 28;
			
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
	
	public void julianCalendarDay()
	{
		count += 1;
		count_day += 1;
		
		if(count_year == 1976 || count_year == 1980 || count_year == 1984 || count_year == 1988 
				|| count_year == 1992 || count_year == 1996 || count_year == 2000 || count_year == 2004 || count_year == 2008) days_month[1] = 29;
		else days_month[1] = 28;
		
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

	public String[][] sortEarthquakes()
	{
		//| Sort By Date
		String[][] ordering = new String[balls.size()][6];
		
		for (int j = balls.size()-1; j >= 0; j--) 
		{ 
			Ball ball = (Ball) balls.get(j);
			String[] bundle = ball.getYearMonthDayMagnitude();
			ordering[j] = bundle;
		}
		
		Arrays.sort(ordering, new Comparator<String[]>() {
            @Override
            public int compare(final String[] entry1, final String[] entry2) {
                final String time1 = entry1[5];
                final String time2 = entry2[5];
                return time1.compareTo(time2);
            }
        });
		
		return ordering;
		
		//| Set into a Calendar
		/*
		float[] thirtySevenYears = new float[13878];
		
		for(int i = 0; i < thirtySevenYears.length; i++){
			this.julianCalendarDay();
			thirtySevenYears[i] = 0;
			PApplet.println("parse: " + count_year +" "+ count_month +" "+ count_day +" : "+ i);
			
			for(int j = 0; j < balls.size()-1; j ++){
				Ball ball = (Ball) balls.get(j);
				
				int yr = Integer.parseInt(ball.getYearMonthDayMagnitude()[0]);
				String mn = ball.getYearMonthDayMagnitude()[1];
				int dy = Integer.parseInt(ball.getYearMonthDayMagnitude()[2]);
				
				if(yr == count_year && mn.equals(month[count_month]) && dy == count_day) {
					float mag;
					
					if(ball.getYearMonthDayMagnitude()[4].equals("None")) mag = 0;
					else mag = Float.parseFloat(ball.getYearMonthDayMagnitude()[4]);
					
					if(mag > thirtySevenYears[i]) thirtySevenYears[i] = mag;
				}
			}
		}
		count_year = 1973;
		count_month = 0;
		count_day = 0;
		count = 0;
		
		return thirtySevenYears;
		*/
	}
	
	public void off() 
	{
		STATUS = "OFF";
		count = 0;
		timekeeper = 0;
	}

	public String status() 
	{
		return STATUS;
	}
}


