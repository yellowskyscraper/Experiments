package thegroundbeneath.app.labels;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PFont;

import ijeoma.motion.Motion;
import ijeoma.motion.tween.Tween;

import de.fhpotsdam.unfolding.Map;
import de.fhpotsdam.unfolding.geo.Location;

public class LifelineLabel 
{
	PApplet parent;
	boolean MODEL = false;
	
	PFont titleText;
	PFont secondaryText;
	PFont legendText;
	
	Tween tweenBackgroundIN;
	Tween tweenForegroundIN;
	Tween tweenBackgroundOUT;
	Tween tweenForegroundOUT;
	
	int animating = 0;
	float alphaBackground = 0;
	float alphaForeground = 0;
	
	public LifelineLabel(PApplet p)
	{
		parent = p;
	}
	public void setup()
	{
		//| Tween Test
		Motion.setup(parent);
		tweenBackgroundIN = new Tween(0f, 255f, 20f);
		tweenForegroundIN = new Tween(0f, 255f, 10f, 10f);
		tweenBackgroundOUT = new Tween(255f, 0f, 10f, 10f);
		tweenForegroundOUT = new Tween(255f, 0f, 10f);
		
		titleText = parent.createFont("data/fonts/ExploSlab/ExploSlab-Medi.otf", 16);
		secondaryText = parent.createFont("data/fonts/Explo/Explo-Medi.otf", 12);
		legendText = parent.createFont("data/fonts/Explo/Explo-Medi.otf", 9);
	}
	
	public void update()
	{	
		switch(animating){		
			case 1:
			alphaBackground = tweenBackgroundIN.getPosition();
			alphaForeground = tweenForegroundIN.getPosition();
			if(alphaBackground == 255) tweenBackgroundIN.stop();
			if(alphaForeground == 255) tweenForegroundIN.stop();
			break;
			
			case 2:
			alphaBackground = tweenBackgroundOUT.getPosition();
			alphaForeground = tweenForegroundOUT.getPosition();
			if(alphaBackground == 0) tweenBackgroundOUT.stop();
			if(alphaForeground == 0) tweenForegroundOUT.stop();
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

		//| Labal
		if(MODEL) this.drawLabel(1019 - 147 - 20, 34 + 322, -90);
		else this.drawLabel(27, 1019 - 147, 0);
		
		parent.popMatrix();
	}
	
	public void drawLabel(int x, int y, int r)
	{
		//| Box Location
		parent.pushMatrix();
		parent.translate(x,y); //| Model Projection View
		parent.rotate(PApplet.radians(r));
		
		//| Label
		int boxW = 322;
		int boxH = 147;
		parent.noStroke();
		parent.fill(255, alphaBackground);
		parent.rect(0, 0, boxW, boxH);
		parent.strokeWeight(1);
		parent.noFill();
		parent.stroke(202,218,209, alphaForeground);
		parent.rect(5, 5, boxW - 11, boxH - 11);
		
		//| Title
		parent.textAlign(PConstants.LEFT);
		parent.smooth();
		parent.fill(0, alphaForeground);
		parent.textFont(titleText, 16);
		parent.text("Bay Area Lifeline Systems",  25, 37);
		
		parent.fill(100, 100, 100, alphaForeground);
		parent.textFont(secondaryText, 12);
		parent.textLeading(14);
		parent.text("Source: United States Geological Service", 25, 42, 268, 100);
		String des = "This map shows key infrastructural networks that may be impacted by a major earthquake.";
		parent.text(des, 25, 60, 268, 100);
		
		//| Legend
		parent.pushMatrix();
		parent.translate(27, 108);
		
		parent.noStroke();
	
		parent.fill(195,124,19, alphaForeground);
		parent.rect(0, 0, 90, 6);
		parent.fill(175,104,0, alphaForeground);
		parent.rect(-2, 2, 2, 4);
		parent.rect(-2, 6, 90, 2);

		parent.fill(122,193,67, alphaForeground);
		parent.rect(90, 0, 90, 6);
		parent.fill(102,173,47, alphaForeground);
		parent.rect(88, 6, 90, 2);

		parent.fill(0,255,255, alphaForeground);
		parent.rect(180, 0, 90, 6);
		parent.fill(0,225,225, alphaForeground);
		parent.rect(178, 6, 90, 2);

		parent.textAlign(PConstants.CENTER);
		parent.fill(0, alphaForeground);
		parent.textFont(legendText, 9);
		parent.text("Oil & Gas", 0, 11, 90, 12);
		parent.text("Electricity", 90, 11, 90, 12);
		parent.text("Water", 180, 11, 90, 12);

		parent.popMatrix();
		parent.popMatrix();
	}
	
	public void kill()
	{
		animating = 0;
		tweenBackgroundIN.stop();
		tweenForegroundIN.stop();
		tweenBackgroundOUT.stop();
		tweenForegroundOUT.stop();
		alphaBackground = 0;
		alphaForeground = 0;
	}
}
