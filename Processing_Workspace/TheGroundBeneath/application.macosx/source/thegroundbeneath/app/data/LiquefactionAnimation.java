package thegroundbeneath.app.data;

import processing.core.PApplet;
import processing.core.PImage;

import thegroundbeneath.app.labels.LiquefactionLabel;
import thegroundbeneath.app.util.ImageLoader;
import thegroundbeneath.app.util.QueueListener;
import thegroundbeneath.app.util.AsyncImageLoader;

import de.fhpotsdam.unfolding.Map;
import de.fhpotsdam.unfolding.geo.Location;

import ijeoma.motion.Motion;
import ijeoma.motion.tween.Tween;

public class LiquefactionAnimation {

	PApplet parent;
	
	//| Image Values
	QueueListener listener;
	int currID = 0;
	ImageLoader loader;

	//| Graphics
	LiquefactionLabel label;
	
	//| Faults
	int animating = 0;
	int timekeeper = 0;
	String STATUS = "OFF";
	
	PImage[] images;
	
	float alphaMainLiquefation = 0;
	Tween tweenLiquefactionIN;
	
	public LiquefactionAnimation(PApplet p)
	{
		parent = p;
	}
	
	public void setup()
	{
		//| Fault Images
		String[] files = new String[]{
			"data/images/liquefaction_main.png",
			"data/images/liquefaction_landmarks.png",
			"data/images/liquefaction_annotation1.png",
			"data/images/liquefaction_annotation2.png",
			"data/images/liquefaction_annotation3.png"
		};
/*		
		images = new PImage[files.length];
		loader = new ImageLoader(parent);
		images[0] = loader.addFile(files[0]);
		loader.finish();
*/		
/*		images = new PImage[files.length];
		
		images[0] = this.loadImageAsync(files[0]);
		images[1] = this.loadImageAsync(files[1]);
		images[2] = this.loadImageAsync(files[2]);
*/
		
/**/	images = new PImage[5];
		images[0]  = parent.loadImage("data/images/liquefaction_main.png");
		System.out.println("1");
		images[1]  = parent.loadImage("data/images/liquefaction_landmarks.png");
		System.out.println("2");
		images[2]  = parent.loadImage("data/images/liquefaction_annotation1.png");
		System.out.println("3");
		images[3]  = parent.loadImage("data/images/liquefaction_annotation2.png");
		System.out.println("4");
		images[4]  = parent.loadImage("data/images/liquefaction_annotation3.png");
		System.out.println("5");
		
//		listener = new QueueListener(parent, files);

		
		
		//| Tween Test
		Motion.setup(parent);
		tweenLiquefactionIN = new Tween(0f, 255f, 10f);
		
		//| Faults Label
		label = new LiquefactionLabel(parent);
		label.setup(); 
	}
	
	public void start()
	{
		if(animating == 0) {
			label.open();
			tweenLiquefactionIN.play();
		}
		animating = 1;
	}
	
	public void update()
	{
		switch(animating){
			case 1:
			alphaMainLiquefation = tweenLiquefactionIN.getPosition();
			break;
		}
		
		label.update();
	}
	
	public void draw(Map m)
	{
		
		float[] tl = m.getScreenPositionFromLocation(new Location(38.339f, -122.796f));	
/*
		if(loader.isFinished())  {
			parent.image(images[0],tl[0], tl[1], 1051, 1051);
		}else{
			PApplet.println("loaderNOTDone"); 
		}
*/	
/*	*/	parent.tint(255, alphaMainLiquefation);
		parent.image(images[0], tl[0], tl[1], 1051, 1051);
		
		parent.tint(255, alphaMainLiquefation);
		parent.image(images[1], tl[0], tl[1], 1050, 1050);

		parent.tint(255, alphaMainLiquefation);
		parent.image(images[2], tl[0], tl[1], 1050, 1050);

		parent.tint(255, alphaMainLiquefation);
		parent.image(images[3], tl[0], tl[1], 1050, 1050);

		parent.tint(255, alphaMainLiquefation);
		parent.image(images[4], tl[0], tl[1], 1050, 1050);

		parent.tint(255);
		label.draw(m);
	}

	
	public void imageHelper()
	{
		
		
/*		if (listener.isMediaLoaded) {
			// images are downloaded, now cycle through them... 
			parent.frameRate(1);
			parent.image(listener.images[currID],0,0);
			currID = (currID+1)%listener.images.length;
			
		} else {
			// anything in here is executed during image loading
			parent.stroke(parent.random(255));
			parent.line(0, 0, 100, 100);
		}*/
	}
	
	public PImage loadImageAsync(String filename) 
	{
		PImage vessel = parent.createImage(0, 0, PApplet.ARGB);
		AsyncImageLoader ail = new AsyncImageLoader(parent, filename, vessel);
		ail.start();
		return vessel;
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
