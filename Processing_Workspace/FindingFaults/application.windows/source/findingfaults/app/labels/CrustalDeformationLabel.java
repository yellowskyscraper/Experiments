package findingfaults.app.labels;

import ijeoma.motion.Motion;
import ijeoma.motion.tween.Tween;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PFont;
import processing.core.PVector;
import de.fhpotsdam.unfolding.Map;
import de.fhpotsdam.unfolding.geo.Location;

public class CrustalDeformationLabel 
{
	PApplet parent;
	PFont titleText;
	PFont secondaryText;
	
	Tween tweenBackgroundIN;
	Tween tweenForegroundIN;
	Tween tweenBackgroundOUT;
	Tween tweenForegroundOUT;
	
	int animating = 0;
	float alphaBackground = 0;
	float alphaForeground = 0;
	
	public CrustalDeformationLabel(PApplet p)
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
		secondaryText = parent.createFont("data/fonts/Explo/Explo-Medi.otf", 12);
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
		
		//| Box Location
		this.drawLabel(1019 - 95, 34 + 310, -90);
		
		parent.popMatrix();
	}
	
	public void drawLabel(int x, int y, int r)
	{
		//| Box Location
		parent.pushMatrix();
		parent.translate(x,y); //| Model Projection View
		parent.rotate(PApplet.radians(r));
		
		//| Label
		int boxW = 310;
		int boxH = 95;
		parent.noStroke();
		parent.fill(255, alphaBackground);
		parent.rect(0, 0, boxW, boxH);
		parent.strokeWeight(1);
		parent.noFill();
		parent.stroke(241, 100, 93, alphaForeground);
		parent.rect(5, 5, boxW - 11, boxH - 11);
		
		//| Title
		parent.textAlign(PConstants.LEFT);
		parent.smooth();
		parent.fill(0, alphaForeground);
		parent.textFont(titleText, 16);
		parent.text("Crustal Deformation Velocities USGS",  25, 37);
		
		parent.fill(100, 100, 100, alphaForeground);
		parent.textFont(secondaryText, 12);
		parent.text("20 millimeters a year", 25, 63);
		
	
		float x2 = 190;
		float y2 = 60;
		
		//parent.filter(PApplet.BLUR, 4);
		parent.smooth();
		parent.fill(241, 100, 93, alphaForeground);
		parent.stroke(241, 100, 93, alphaForeground);
		parent.strokeWeight(5);
		
		float arrowWid = 3; //| * 1.7f  without Stroke
		float arrowFan = 7; //| * 1.7f  without Stroke
		
		parent.pushMatrix();
		parent.translate(x2,y2); 
			parent.rotate(PApplet.radians(90));
			parent.beginShape();
			parent.vertex(0, 0);
			parent.vertex(arrowWid, arrowFan);
			parent.vertex(-arrowWid, arrowFan);
			parent.vertex(0, 0);
			parent.endShape(PConstants.CLOSE);
		parent.popMatrix();
		
		parent.stroke(241, 100, 93, alphaForeground);
		parent.strokeWeight(5);
		parent.line(150, 60, x2, y2);
	
		parent.popMatrix();
	}
}
