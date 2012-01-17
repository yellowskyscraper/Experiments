package dynamicearth.app.data;

import processing.core.PApplet;
import processing.core.PImage;

import dynamicearth.app.labels.FaultLinesLabel;

import de.fhpotsdam.unfolding.Map;
import de.fhpotsdam.unfolding.geo.Location;

import ijeoma.motion.Motion;
import ijeoma.motion.tween.Tween;

public class FaultLineAnimation {

	PApplet parent;

	//| Graphics
	FaultLinesLabel label;
	
	//| Faults
	int animating = 0;
	int timekeeper = 0;
	String STATUS = "OFF";
	
	PImage[] images = new PImage[3];
	
	float alphaSecondaryFaults = 0;
	float alphaPrimaryFaults = 0;
	float alphaFaultNames = 0;
	
	Tween tweenSecondaryIN;
	Tween tweenPrimaryIN;
	Tween tweenNamesIN;
	
	Tween tweenSecondaryOUT;
	Tween tweenPrimaryOUT;
	Tween tweenNamesOUT;
	
	  
	public FaultLineAnimation(PApplet p)
	{
		parent = p;
	}
	
	public void setup()
	{
		//| Fault Images
		images[0] = parent.loadImage("data/images/faults_secondary.png");
		images[1] = parent.loadImage("data/images/faults_primary.png");
		images[2] = parent.loadImage("data/images/faults_names.png");

		//| Tween Test
		Motion.setup(parent);
		tweenSecondaryIN = new Tween(0f, 125f, 20f);
		tweenPrimaryIN = new Tween(0f, 255f, 10f, 60f);
		tweenNamesIN = new Tween(0f, 255f, 10f);

		tweenSecondaryOUT = new Tween(125f, 0f, 20f);
		tweenPrimaryOUT = new Tween(255f, 0f, 10f);
		tweenNamesOUT = new Tween(255f, 0f, 10f);
		
		//| Faults Label
		label = new FaultLinesLabel(parent);
		label.setup(); 
	}
	
	public void showSecondaryFaults()
	{
		if(animating == 0) {
			tweenPrimaryIN.play();
		}
		animating = 1;
	}
	
	public void showMainFaults()
	{
		if(animating == 1) {
			STATUS = "ANIMATING IN";
			tweenSecondaryIN.play();
			tweenPrimaryIN.stop();
			tweenNamesIN.play();
			label.open();
		}
		animating = 2;
	}
	
	public void dimFaults()
	{
		STATUS = "DIM";
		label.close();
		animating = 3;
		timekeeper = 0;

		tweenSecondaryIN.stop();
		tweenPrimaryIN.stop();
		tweenNamesIN.stop();
		
		tweenNamesOUT.play();
	}
	
	public void close()
	{
		if(animating == 3) {
			STATUS = "ANIMATING OUT";
			timekeeper = 0;
	
			tweenNamesOUT.stop();
			tweenSecondaryOUT.play();
			tweenPrimaryOUT.play();
		}
		animating = 4;
	}
	
	public void update()
	{
		switch(animating){
			case 1:
			alphaSecondaryFaults = 0;
			alphaPrimaryFaults = tweenPrimaryIN.getPosition();
			break;
		
			case 2:
			alphaSecondaryFaults = tweenSecondaryIN.getPosition();
			alphaPrimaryFaults = 255;
			alphaFaultNames = tweenNamesIN.getPosition();
			if(alphaFaultNames == 255) timekeeper += 1;
			if(timekeeper > 500) this.dimFaults();
			break;

			case 3:
			alphaSecondaryFaults = 125;
			alphaPrimaryFaults = 255;
			alphaFaultNames = tweenNamesOUT.getPosition();
//			if(alphaFaultNames == 0 && label.closed() == true) STATUS = "DONE";
			if(alphaFaultNames == 0 && label.closed() == true) this.close();
			break;
			
			case 4:
			alphaSecondaryFaults = tweenSecondaryOUT.getPosition();
			alphaPrimaryFaults = tweenPrimaryOUT.getPosition();
			alphaFaultNames = 0;
			if(alphaPrimaryFaults == 0 && alphaSecondaryFaults == 0) STATUS = "DONE";
			break;
		}
		
		label.update();
	}
	
	public void draw(Map m)
	{
		float[] tl = m.getScreenPositionFromLocation(new Location(38.339f, -122.796f));	
		int xpos = Math.round(tl[0]);
		int ypos = Math.round(tl[0]);
		
		parent.tint(255, alphaSecondaryFaults);
		parent.image(images[0], xpos, ypos, 1051, 1051);
		
		parent.tint(255, alphaPrimaryFaults);
		parent.image(images[1], xpos, ypos, 1051, 1051);
		
		parent.tint(255, alphaFaultNames);
		parent.image(images[2], xpos, ypos, 1051, 1051);
		parent.tint(255);
		
		label.draw(m);
	}
	
	public void off() 
	{
		STATUS = "OFF";
		animating = 0;
		timekeeper = 0;
	}

	public String status() 
	{
		return STATUS;
	}
}
