package findingfaultlines.app.scenes;

import processing.core.PApplet;
import processing.core.PFont;
import de.fhpotsdam.unfolding.Map;
import de.fhpotsdam.unfolding.geo.Location;
import ijeoma.motion.*;
import ijeoma.motion.tween.*;

public class IntertitleDynamicEarth 
{
	PApplet parent;

	boolean BAYMODEL = true;
	int wid = (BAYMODEL) ? 1500 : 1680;
	int hei = (BAYMODEL) ? 1200 : 1050;
	
	Location coordTL;
	
	//| Main Controle
	String STATUS = "OFF";
	int timekeeper = 0;
	
	Tween tweenBackgroundIN;
	Tween tweenForegroundIN;
	Tween tweenBackgroundOUT;
	Tween tweenForegroundOUT;
	float alphaBackground = 0;
	float alphaForeground = 0;
	
	//| Copy
	PFont displayText;
	PFont displaySubText;
	  
	public IntertitleDynamicEarth(PApplet p)
	{
		parent = p;
	}
	
	public void setup(Map m, float w, float h)
	{
		coordTL = new Location(38.2268534751704f,-122.917099f);
		
		//| Tween Test
		Motion.setup(parent);
		tweenBackgroundIN = new Tween(0f, 255f, 20f);
		tweenForegroundIN = new Tween(0f, 255f, 30f, 20f);
		tweenBackgroundOUT = new Tween(255f, 0f, 30f, 20f);
		tweenForegroundOUT = new Tween(255f, 0f, 20f);
		
		//| Copy
		displayText = parent.createFont("data/fonts/ExploSlab/ExploSlab-Bold.otf", 72);
		displaySubText = parent.createFont("data/fonts/ExploSlab/ExploSlab.otf", 22);
	}
	
	public void firstcall()
	{
		STATUS = "FIRST CALL";
		tweenForegroundIN.play();
	}
	
	public void start()
	{
		STATUS = "ANIMATING IN";
		timekeeper = 0;
		tweenBackgroundIN.play();
		tweenForegroundIN.play();
	}
	
	public void update()
	{
		if(STATUS.equals("OFF")) {
			alphaBackground = alphaForeground = 0;
			
		} else if(STATUS.equals("FIRST CALL")) {
			alphaBackground = 255;
			alphaForeground = tweenForegroundIN.getPosition();
			if(alphaForeground == 255) STATUS = "ON"; 
			
		} else if(STATUS.equals("ANIMATING IN")) {
			alphaBackground = tweenBackgroundIN.getPosition();
			alphaForeground = tweenForegroundIN.getPosition();
			if(alphaForeground == 255) STATUS = "ON"; 
			
		} else if(STATUS.equals("ON")) {
			timekeeper += 1;
			if(timekeeper > 600) {
				STATUS = "ANIMATING OUT";
				tweenBackgroundOUT.play();
				tweenForegroundOUT.play();
			}
			
		} else if(STATUS.equals("ANIMATING OUT")) {
			alphaBackground = tweenBackgroundOUT.getPosition();
			alphaForeground = tweenForegroundOUT.getPosition();
			if(alphaBackground == 0) STATUS = "DONE";
		}
	}
	
	public void draw(Map m)
	{	
		int xpos = 0;
		int ypos = 0;
		int buffer = 25;
		
		float topLeftX = xpos + buffer; 
		float topLeftY = ypos + buffer; 
		float bottomLeftX = wid - buffer*2; 
		float bottomLeftY = hei - buffer*2; 
		
		float flairTLX = topLeftX + 5;
		float flairTLY = topLeftY + 5;
		float flairTRX = bottomLeftX + 15;
		float flairTRY = flairTLY;
		float flairBRX = bottomLeftX + 15;
		float flairBRY = bottomLeftY + 15;
		float flairBLX = flairTLX;
		float flairBLY = bottomLeftY + 15;
		
		parent.fill(0, alphaBackground);
		parent.noStroke();
		parent.rect(xpos, ypos, wid, hei);
		parent.stroke(255, alphaForeground);
		parent.noFill();
		parent.strokeWeight(1);

		parent.line(flairTLX, topLeftY, flairTRX + 5, topLeftY);
		parent.line(flairTRX + 10, flairTRY, flairTRX + 10, flairBRY + 5);
		parent.line(flairBLX, bottomLeftY + 25, flairBRX + 5, bottomLeftY + 25);
		parent.line(flairTLX - 5,topLeftY + 5,flairTLX - 5, flairBLY + 5);
		parent.line(flairTLX, topLeftY, flairTLX, flairTLY);
		parent.line(flairTLX - 5,topLeftY + 5, flairTLX, flairTLY);
		parent.line(flairTRX + 5, topLeftY, flairTRX + 5, flairTRY);
		parent.line(flairTRX + 10, flairTRY, flairTRX + 5, flairTRY);
		parent.line(flairTRX + 10, flairBRY + 5, flairBRX + 5, flairBRY + 5);
		parent.line(flairBRX + 5, bottomLeftY + 25, flairBRX + 5, flairBRY + 5);
		parent.line(flairTLX - 5, flairBLY + 5, flairBLX, flairBLY + 5);
		parent.line(flairBLX, flairBLY + 5, flairBLX, bottomLeftY + 25);
		
		parent.rect(flairTLX, flairTLY, 5, 5);
		parent.rect(flairTRX, flairTRY, 5, 5);
		parent.rect(flairBRX, flairBRY, 5, 5);
		parent.rect(flairBLX, flairBLY, 5, 5);
		
		String title = "Finding Fault Lines";
		String quote = "We learn geology the morning after the earthquake. \n- Ralph Waldo Emerson";
		float quoteWid = 550;
		
		float mainTitleX = xpos;
		float mainTitleY = hei/3;
		float mainQuoteX = (BAYMODEL) ? 450 : 540;
		float mainQuoteY = hei/3 + 110;
		
		parent.fill(255, alphaForeground);
		parent.textAlign(PApplet.CENTER);
		parent.textFont(displayText, 72);
		parent.text(title, mainTitleX, mainTitleY, wid, 200);
		
		parent.textAlign(PApplet.LEFT);
		parent.textLeading(15);
		parent.textFont(displaySubText, 20);
		parent.text(quote, mainQuoteX, mainQuoteY, quoteWid, 1000);
	}

	public void kill()
	{
		STATUS = "OFF";	
		timekeeper = 0;
		alphaBackground = 0;
		alphaForeground = 0;
		tweenBackgroundIN.seek(0);
		tweenForegroundIN.seek(0);
		tweenBackgroundOUT.seek(0);
		tweenForegroundOUT.seek(0);
	}

	public void off() 
	{
		this.kill();
	}
	
	public String status() 
	{
		return STATUS;
	}
}
