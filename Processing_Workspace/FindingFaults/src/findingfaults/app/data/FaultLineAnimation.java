package findingfaults.app.data;

import ijeoma.motion.Motion;
import ijeoma.motion.tween.Tween;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;

import de.fhpotsdam.unfolding.Map;
import de.fhpotsdam.unfolding.geo.Location;

public class FaultLineAnimation {

	PApplet parent;
//	Gif animation;
	PImage[] images = new PImage[3];
	float alphaBackground1 = 0;
	float alphaBackground2 = 0;
	float alphaBackground3 = 0;
	Tween tweenIN;
	Tween tweenOUT;
	
	  
	public FaultLineAnimation(PApplet p)
	{
		parent = p;
	}
	
	public void setup()
	{
		//| Fault Images
		images[0]  = parent.loadImage("data/images/bay_00.png");
		images[1]  = parent.loadImage("data/images/bay_01.png");
		images[2]  = parent.loadImage("data/images/bay_02.png");

		//| Tween Test
		Motion.setup(parent);
		tweenIN = new Tween(0f, 255f, 10f);
		tweenOUT = new Tween(255f, 0f, 20f);
	}
	
	public void drawAni()
	{
		alphaBackground1 = tweenIN.getPosition();
	}
	
	public void draw(Map m)
	{
		float[] tl = m.getScreenPositionFromLocation(new Location(38.339f, -122.796f));	

		parent.tint(255, alphaBackground1);
		parent.image(images[0], tl[0], tl[1], 1051, 1051);
		parent.tint(255);
	}
}
