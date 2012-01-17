package findingfaults.app.labels;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PFont;

import ijeoma.motion.Motion;
import ijeoma.motion.tween.Tween;

import de.fhpotsdam.unfolding.Map;
import de.fhpotsdam.unfolding.geo.Location;

public class FaultLinesLabel 
{
	PApplet parent;
	PFont titleText;
	
	Tween tweenBackgroundIN;
	Tween tweenForegroundIN;
	Tween tweenBackgroundOUT;
	Tween tweenForegroundOUT;
	
	int animating = 0;
	float alphaBackground = 0;
	float alphaForeground = 0;
	
	public FaultLinesLabel(PApplet p)
	{
		parent = p;
	}
	public void setup()
	{
		//| Tween Test
		Motion.setup(parent);
		tweenBackgroundIN = new Tween(0f, 255f, 20f);
		tweenForegroundIN = new Tween(0f, 255f, 10f, 10f);
		tweenBackgroundOUT = new Tween(255f, 0f, 10f, 5f);
		tweenForegroundOUT = new Tween(255f, 0f, 10f);
		
		titleText = parent.createFont("data/fonts/ExploSlab/ExploSlab-Medi.otf", 16);
	}
	
	public void update()
	{	
		switch(animating){		
			case 1:
			alphaBackground = tweenBackgroundIN.getPosition();
			alphaForeground = tweenForegroundIN.getPosition();
			break;
			
			case 2:
			alphaBackground = tweenBackgroundOUT.getPosition();
			alphaForeground = tweenForegroundOUT.getPosition();
			break;
		}
	}
	
	public void open()
	{
		animating = 1;
		tweenBackgroundIN.play();
		tweenForegroundIN.play();
	}
	
	public void close()
	{
		animating = 2;
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
		
		this.drawLabel(1019 - 65, 34 + 278, -90);
		
		//| Box Location
		parent.pushMatrix();
		parent.translate(27, 960);
		parent.popMatrix();
	}
	
	public void drawLabel(int x, int y, int r)
	{
		//| Box Location
		parent.pushMatrix();
		parent.translate(x,y); //| Model Projection View
		parent.rotate(PApplet.radians(r));
		
		//| Label
		int boxW = 278;
		int boxH = 65;
		parent.noStroke();
		parent.fill(255, alphaBackground);
		parent.rect(0, 0, boxW, boxH);
		parent.strokeWeight(1);
		parent.noFill();
		parent.stroke(154,194,185, alphaForeground);
		parent.rect(5, 5, boxW - 11, boxH - 11);
		
		//| Title
		parent.textAlign(PConstants.LEFT);
		parent.smooth();
		parent.fill(0, alphaForeground);
		parent.textFont(titleText, 16);
		parent.text("The San Andreas Fault Complex",  25, 37);

		parent.popMatrix();
		
		
	}
}
