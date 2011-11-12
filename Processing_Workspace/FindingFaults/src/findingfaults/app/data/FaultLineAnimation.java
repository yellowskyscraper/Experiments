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
	Tween tweenIN1;
	Tween tweenIN2;
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
		tweenIN1 = new Tween(0f, 120f, 20f);
		tweenIN2 = new Tween(0f, 255f, 20f, 6f);
		tweenOUT = new Tween(255f, 0f, 20f);
	}
	
	public void start()
	{
		tweenIN1.play();
		tweenIN2.play();
	}
	
	public void drawAni()
	{
		alphaBackground1 = tweenIN1.getPosition();
		alphaBackground2 = tweenIN2.getPosition();
	}
	
	public void draw(Map m)
	{
		float[] tl = m.getScreenPositionFromLocation(new Location(38.339f, -122.796f));	

		this.drawAni();
		parent.tint(255, alphaBackground1);
		parent.image(images[0], tl[0], tl[1], 1051, 1051);
		parent.tint(255, alphaBackground2);
		parent.image(images[1], tl[0], tl[1], 1051, 1051);
		parent.tint(255);
	}
}
