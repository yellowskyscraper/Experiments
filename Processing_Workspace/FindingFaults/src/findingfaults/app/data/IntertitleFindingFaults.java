package findingfaults.app.data;

import ijeoma.motion.Motion;
import ijeoma.motion.tween.Tween;
import processing.core.PApplet;
import processing.core.PFont;
import de.fhpotsdam.unfolding.Map;
import de.fhpotsdam.unfolding.geo.Location;

public class IntertitleFindingFaults 
{
	PApplet parent;
	int wid = 1400;
	int hei = 1050;
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
	  
	public IntertitleFindingFaults(PApplet p)
	{
		parent = p;
	}
	
	public void setup(Map m, float w, float h)
	{
		wid = parent.screenWidth;
		hei = parent.screenHeight;
		coordTL = new Location(38.339f, -122.796f);
		
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

	public void off() 
	{
		STATUS = "OFF";
		tweenBackgroundOUT.stop();
		tweenForegroundOUT.stop();
	}
	
	public void update()
	{
		if(STATUS.equals("OFF")) {
			alphaBackground = alphaForeground = 0;
			
		} else if(STATUS.equals("FIRST CALL")) {
			alphaBackground = 255;
			alphaForeground = tweenForegroundIN.getPosition();
			if(alphaForeground == 255) {
				STATUS = "ON";
				tweenForegroundIN.stop();
			}
			
		} else if(STATUS.equals("ANIMATING IN")) {
			alphaBackground = tweenBackgroundIN.getPosition();
			alphaForeground = tweenForegroundIN.getPosition();
			if(alphaForeground == 255) {
				STATUS = "ON";
				tweenBackgroundIN.stop();
				tweenForegroundIN.stop();
			}
			
		} else if(STATUS.equals("ON")) {
			timekeeper += 1;
			if(timekeeper > 120) {
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
		float[] tl = m.getScreenPositionFromLocation(coordTL);
		int buffer = 25;
		float topLeftX = tl[0] + buffer; 
		float topLeftY = tl[1] + buffer; 
		float bottomLeftX = 1051 - buffer*2; 
		float bottomLeftY = 1051 - buffer*2; 
		float flairTLX = topLeftX + 5;
		float flairTLY = topLeftY + 5;
		float flairTRX = bottomLeftX + 15;
		float flairTRY = flairTLY;
		float flairBRX = bottomLeftX + 15;
		float flairBRY = bottomLeftY + 15;
		float flairBLX = flairTLX;
		float flairBLY = bottomLeftY + 15;
		
		parent.fill(0, alphaBackground);
		//parent.noFill();
		parent.noStroke();
		parent.rect(tl[0], tl[1], wid, hei);
		parent.stroke(255, alphaForeground);
		parent.noFill();
		parent.strokeWeight(1);
		parent.rect(topLeftX, topLeftY, bottomLeftX, bottomLeftY);
		parent.rect(flairTLX, flairTLY, 5, 5);
		parent.rect(flairTRX, flairTRY, 5, 5);
		parent.rect(flairBRX, flairBRY, 5, 5);
		parent.rect(flairBLX, flairBLY, 5, 5);
		
		String title = "Finding Faults";
		//String quote = "I am a frayed and nibbled survivor in a fallen world, and I am getting along. I am aging and eaten and have done my share of eating too. I am not washed and beautiful, in control of a shining world in which everything fits, but instead am wondering awed about on a splintered wreck I've come to care for, whose gnawed trees breathe a delicate air, whose bloodied and scarred creatures are my dearest companions, and whose beauty bats and shines not in its imperfections but overwhelmingly in spite of them...";
		String quote = "[It] is the little causes, long continued, which are considered as bringing about the greatest changes of the earth. \n- James Hutton";
		float quoteWid = 450;
		
		float mainTitleX = tl[0];
		float mainTitleY = 1050/2;
		float mainQuoteX = 1050/2 - quoteWid/2;
		float mainQuoteY = 1050/2 + 120;
		
		parent.fill(255, alphaForeground);
		parent.textAlign(PApplet.CENTER);
		parent.textFont(displayText, 72);
		parent.text(title, mainTitleX, mainTitleY, 1050, 200);
		
		parent.textAlign(PApplet.LEFT);
		parent.textLeading(15);
		parent.textFont(displaySubText, 20);
		parent.text(quote, mainQuoteX, mainQuoteY, quoteWid, 1000);
	}
	
	public String status() 
	{
		return STATUS;
	}
		
}
