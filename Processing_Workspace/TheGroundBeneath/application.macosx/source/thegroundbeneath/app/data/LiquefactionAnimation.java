package thegroundbeneath.app.data;

import processing.core.PApplet;
import processing.core.PImage;

import thegroundbeneath.app.labels.LiquefactionLabel;

import de.fhpotsdam.unfolding.Map;
import de.fhpotsdam.unfolding.geo.Location;

import ijeoma.motion.Motion;
import ijeoma.motion.tween.Tween;

public class LiquefactionAnimation {

	PApplet parent;

	//| Graphics
	LiquefactionLabel label;

	//| Scene Images
	PImage mainLiquefactionImage;
	PImage mainLandmarksImage;
	PImage[] annotationsArray;
	int[] annotationsPause;
	int annotation = 0;

	//| Sequence Variable
	int animating = 9;
	int timekeeper = 0;
	String STATUS = "OFF";
	
	float alphaMainLiquefation = 0;
	float alphaMainLandmarks = 0;
	float alphaAnnotation = 0;
	
	Tween tweenLiquefactionIN;
	Tween tweenLandmarksIN;
	Tween tweenLandmarksOUT;
	Tween tweenLiqueLandmarksOUT;
	Tween tweenAnnotationIN;
	Tween tweenAnnotationOUT;
	
	public LiquefactionAnimation(PApplet p)
	{
		parent = p;
	}
	
	public void setup()
	{
		//| Fault Images
		annotationsPause = new int[]{250,250,250,250}; //| First Is Innitital Leadin Time
		String[] a = new String[]{
			"data/images/liquefaction_an_alluvium.png",
			"data/images/liquefaction_an_artifical_fill.png",
			"data/images/liquefaction_an_islands.png",
			"data/images/liquefaction_an_sand.png"
		};
		annotationsArray = new PImage[a.length];
		for(int i = 0; i < a.length; i++) annotationsArray[i] = parent.loadImage(a[i]);

		mainLiquefactionImage = parent.loadImage("data/images/liquefaction_main.png");
		mainLandmarksImage = parent.loadImage("data/images/liquefaction_landmarks.png");

		//| Tween Test
		Motion.setup(parent);
		tweenLiquefactionIN = new Tween(0f, 255f, 10f);
		tweenLandmarksIN = new Tween(0f, 255f, 10f);
		tweenLandmarksOUT = new Tween(255f, 0f, 10f);
		tweenLiqueLandmarksOUT = new Tween(255f, 0f, 10f);
		tweenAnnotationIN = new Tween(0f, 255f, 10f);
		tweenAnnotationOUT = new Tween(255f, 0f, 20f, 8f);
		
		//| Faults Label
		label = new LiquefactionLabel(parent);
		label.setup(); 
	}
	
	public void start()
	{
		if(animating == 0) {
			label.open();
			tweenLiquefactionIN.play();
			tweenLandmarksIN.play();
			STATUS = "ANIMATING IN";
		}
		animating = 1;
	}
	
	public void uplandmarks()
	{
		animating = 0;
		tweenLandmarksIN.play();
	}
	
	public void beginAnnotations()
	{
		STATUS = "ON";
		animating = 2;
		tweenLiquefactionIN.stop();
		tweenLandmarksIN.stop();
	}
	
	public void upAnnotation()
	{
		timekeeper = 0;
		animating = 3;
		tweenAnnotationIN.play();
		tweenAnnotationOUT.stop();
		if(alphaMainLandmarks == 255) tweenLandmarksOUT.play();
	}
	
	public void downAnnotation()
	{	
		timekeeper = 0;
		animating = 4;
		tweenAnnotationIN.stop();
		tweenAnnotationOUT.play();
	}
	
	public void finishAnnotation()
	{	
		annotation -= 1;
		timekeeper = 0;
		animating = 5;	
		tweenAnnotationIN.stop();
		tweenAnnotationOUT.stop();
		tweenLandmarksOUT.stop();
		tweenLandmarksIN.play();
	}
	
	public void closeLiquefaction()
	{
		timekeeper = 0;
		animating = 6;	
		tweenLandmarksIN.stop();
		tweenLiqueLandmarksOUT.play();
		label.close();	
	}
	
	public void update()
	{
		switch(animating){
			case 0:
			alphaMainLandmarks = tweenLandmarksIN.getPosition();
			break;
		
			case 1:
			alphaMainLiquefation = tweenLiquefactionIN.getPosition();
			//alphaMainLandmarks = tweenLandmarksIN.getPosition();
			if(alphaMainLiquefation == 255) this.beginAnnotations();
			break;
			
			case 2:
			timekeeper += 1;
			if(timekeeper > 300) this.upAnnotation(); 
			break;
			
			case 3: //| Annotation In
			alphaAnnotation = tweenAnnotationIN.getPosition();
			alphaMainLandmarks = tweenLandmarksOUT.getPosition();
			if(alphaAnnotation == 255) timekeeper += 1;
			if(timekeeper > annotationsPause[annotation]) this.downAnnotation();
			break;
			
			case 4: //| Annotation Out
			timekeeper += 1;
			alphaAnnotation = tweenAnnotationOUT.getPosition();
			if(alphaAnnotation == 0) {
				annotation += 1;
				if(annotation < annotationsArray.length) this.upAnnotation();
				else this.finishAnnotation();
			}
			break;
			
			case 5:
			alphaMainLandmarks = tweenLandmarksIN.getPosition();
			if(alphaMainLandmarks == 255) timekeeper += 1;
			if(timekeeper > 200) this.closeLiquefaction();
			break;
			
			case 6:
			alphaMainLiquefation = alphaMainLandmarks = tweenLiqueLandmarksOUT.getPosition();
			if(alphaMainLiquefation == 0 && label.closed() == true) STATUS = "DONE";
			break;
		}
		
		label.update();
	}
	
	public void draw(Map m)
	{
		float[] tl = m.getScreenPositionFromLocation(new Location(38.339f, -122.796f));	
		int xpos = Math.round(tl[0]);
		int ypos = Math.round(tl[0]);
		
		//| Main Sequence
		parent.tint(255, alphaMainLiquefation);
		parent.image(mainLiquefactionImage, xpos, ypos, 1051, 1051);
		parent.tint(255, alphaMainLandmarks);
		parent.image(mainLandmarksImage, xpos, ypos, 1050, 1050);
		
		//| Annotation
		parent.tint(255, alphaAnnotation);
		parent.image(annotationsArray[annotation], xpos, ypos, 1050, 1050);

		//| Label
		parent.tint(255);
		label.draw(m);
	}
	
	public void off() 
	{
		STATUS = "OFF";
		animating = 9;
		timekeeper = 0;
		annotation = 0;

		tweenLiqueLandmarksOUT.stop();
		
		alphaMainLiquefation = 0;
		alphaMainLandmarks = 0;
		alphaAnnotation = 0;
	}

	public String status() 
	{
		return STATUS;
	}
}
