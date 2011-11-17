package findingfaults.app.graphics;

import de.fhpotsdam.unfolding.geo.Location;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;
import findingfaults.app.util.BasicUtils;

public class Vector 
{
	PApplet parent;
	int wid = 1400;
	int hei = 1050;
	
	Location station;
	float vN, vE;
	float xpos, ypos;
	
	float easing = 0.1f;
	float easingB = 0.05f;
	float pulse = 0.3f;
	float alpha = 255;
	
	public Vector(PApplet p)
	{
		parent = p;
	}
	
	public void setup(String n, float la, float lo, float vn, float ve)
	{
		wid = parent.screenWidth;
		hei = parent.screenHeight;
		
		station = new Location(la,lo);
		vN = vn;
		vE = ve;
	}
	
	public Location getLocation()
	{
		return station;
	}
	
	public void update(float x, float y)
	{	
		xpos = x;
		ypos = y;
	}
	
	public void start()
	{
		
	}
	
	public void ani()
	{
		float tp = 2.0f - pulse;
		float ta = 0 + alpha;
		
		if(tp > 0.07f) pulse += tp * easing; 
		if(tp < 0.07f) alpha -= ta * easing; 
		if(alpha < 1) {
			pulse = 0.5f;
			alpha = 255;
		}
	}
	
	public void draw()
	{
		//| Animation Control
		this.ani();
		
		//| Larger Scale to Bay Model Screen Resolution
		float[] position = BasicUtils.scaleCoordinates(wid, hei, xpos, ypos);
		float x2 = position[0] + vE * pulse;
		float y2 = position[1] - vN * pulse;

		//parent.filter(PApplet.BLUR, 4);
		parent.smooth();
		parent.fill(241, 100, 93, alpha);
		parent.stroke(241, 100, 93, alpha);
		parent.strokeWeight(5);
		
		PVector v = new PVector(vN,vE,0);
		float arrowWid = 3; //| * 1.7f  without Stroke
		float arrowFan = 7; //| * 1.7f  without Stroke
		
		parent.pushMatrix();
		parent.translate(x2, y2);
			parent.rotate(v.heading2D());
			parent.beginShape();
			parent.vertex(0, 0);
			parent.vertex(arrowWid, arrowFan);
			parent.vertex(-arrowWid, arrowFan);
			parent.vertex(0, 0);
			parent.endShape(PConstants.CLOSE);
		parent.popMatrix();
		
		parent.stroke(241, 100, 93, alpha);
		parent.strokeWeight(5);
		parent.line(position[0], position[1], x2, y2);
		/*
		parent.fill(241, 100, 93);
		parent.stroke(241, 100, 93);
		parent.strokeWeight(3);
		parent.ellipseMode(PConstants.CENTER);
		parent.ellipse(position[0], position[1], 5, 5);
		*/
		parent.strokeWeight(0);
		parent.noStroke();
	}
}
