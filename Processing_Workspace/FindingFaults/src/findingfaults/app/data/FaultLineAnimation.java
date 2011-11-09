package findingfaults.app.data;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;

import gifAnimation.Gif;
import de.fhpotsdam.unfolding.Map;
import de.fhpotsdam.unfolding.geo.Location;

public class FaultLineAnimation {

	PApplet parent;
//	Gif animation;
	PImage[] images = new PImage[3];
	PImage maskImg;
	PGraphics maskGph;
	float easingA = 0.075f;
	float easingB = 0.025f;
	float mainfaultsAlpha = 0;
	float subfaultsAlpha = 0;

	float maskHeight = 0;
	
	  
	public FaultLineAnimation(PApplet p)
	{
		parent = p;
	}
	
	public void setup()
	{
//		animation = new Gif(parent, "data/animations/Faultlines.gif");
//		animation.play();
//		animation.noLoop();
		
		images[0]  = parent.loadImage("data/images/bay_00.png");
		images[1]  = parent.loadImage("data/images/bay_01.png");
		images[2]  = parent.loadImage("data/images/bay_02.png");
		
		//maskImg = parent.loadImage("data/images/mask.png");
	}
	
	public void drawAni()
	{
		
	}
	
	public void draw(Map m)
	{
		float[] tl = m.getScreenPositionFromLocation(new Location(38.339f, -122.796f));	
		
		/*
		float ta = 200 - subfaultsAlpha;
		if(ta > 0) subfaultsAlpha += ta * easingB;
		if(ta < 5) subfaultsAlpha = 200;
		*/
		
		/*
		maskGph = parent.createGraphics(100, 80, PApplet.P3D); // transform the mask
		maskGph.beginDraw();
		maskGph.endDraw();
		
		images[0].mask( maskGph );
		*/
		parent.tint(255, 100);
		parent.image(images[0], tl[0], tl[1], 1051, 1051);
		//parent.image(images[1], tl[0], tl[1], 1051, 1051);
		
		parent.tint(255);
	}
}
