package orientation;

import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

import processing.core.PApplet;
import processing.core.PImage;
import orientation.app.scenes.IntertitleOrientation;
import orientation.app.scenes.OrientationSlideshow;

public class Orientation extends PApplet {

	private static final long serialVersionUID = 1L;
	
	boolean BAYMODEL = true;
	int wid = (BAYMODEL) ? 1500 : 1680;
	int hei = (BAYMODEL) ? 1200 : 1050;

	//| Pause Image Screen
	PImage pause;
	
	//| Director
	boolean PAUSE = false;
	String SCENE = "INTERTITLE";
	
	//| Data
	IntertitleOrientation intertitleOrientation;
	OrientationSlideshow orientationSlideshow;
	
	public void setup() 
	{
		//| Start
		background(0);
		size(1500, 1200);
		noCursor();
		frameRate(50);

		//| Intertitle
		intertitleOrientation = new IntertitleOrientation(this);
		intertitleOrientation.setup(wid, hei);
		intertitleOrientation.firstcall();
		
		//| Pause Image Screen
		pause = loadImage("data/images/pause.jpg");

		//| Earthquake feed from USGS
		orientationSlideshow = new OrientationSlideshow(this);
		orientationSlideshow.setup();
		
		//| Window Sleep and Wake; Application Pause and Reset 
		frame.addWindowFocusListener(new WindowFocusListener () {
			@Override public void windowGainedFocus (WindowEvent e) {
				System.out.println("ORIENTATION KINDLY SAYS HELLO");
				intertitleOrientation.firstcall();
				orientationSlideshow.kill();
				SCENE = "INTERTITLE";	
				PAUSE = false;
			}
			
			@Override public void windowLostFocus (WindowEvent e) {
				System.out.println("ORIENTATION REGRETFULLY SAYS GOODBYE");
				PAUSE = true;
			}
		});
	}
	
	public void reset()
	{
		intertitleOrientation.firstcall();
		orientationSlideshow.kill();
		SCENE = "INTERTITLE";	
	}
	
	public void update()
	{		
		String stat = "null"; 
		
		if(SCENE.equals("INTERTITLE")) {
			stat = intertitleOrientation.status();
			if(stat.equals("OFF")) intertitleOrientation.start();
			if(stat.equals("DONE")) {
				SCENE = "ORIENTATION";
				intertitleOrientation.off();
			}

		} else if(SCENE.equals("ORIENTATION")) {
			stat = orientationSlideshow.status();
			if(stat.equals("OFF")) orientationSlideshow.start();
			if(stat.equals("DONE")) {
				SCENE = "INTERTITLE";
				orientationSlideshow.off();
			}
		}
//		PApplet.println(SCENE + " " + stat + " (" + frameRate + ")");		
	}

	public void draw() 
	{
		if(PAUSE)
		{
//			tint(255, 255);
//			image(pause, 0, 0);		
			fill(0);
			noStroke();
			rect(0,0,wid-1,hei-1);
			return;
		}
		
		//| Map and Background
		background(0);
//		this.renderMapChrome();
		this.update();
		
		if(SCENE.equals("INTERTITLE")) {
			intertitleOrientation.update();
			intertitleOrientation.draw();
		}
		
		if(SCENE.equals("ORIENTATION")) {
			orientationSlideshow.update();
			orientationSlideshow.draw();
		}

	}
	
	public void renderMapChrome()
	{				
		//| Outer Boarder
		smooth();
		noFill();
		stroke(255);
		strokeWeight(1);
		rect(0,0,wid-1,hei-1);
		if(!BAYMODEL) rect(0,0,1500,hei-1);
	}
	
	//| Override Key Pressed
	public void keyPressed() 
	{
		/*
		if (key == ' ') {
			if(!PAUSE) {
				PAUSE = true;
				
			} else if(PAUSE) {
				PAUSE = false;
				this.reset();
			}
			
		} else if(key == 'f') {
			
		}
		*/
	}
	
	//| Override Main
	public static void main(String _args[]) 
	{
		PApplet.main(new String[] { "--location=0,0", "--present", "--bgcolor=#000000", "--hide-stop", Orientation.class.getName() });
	}
}
