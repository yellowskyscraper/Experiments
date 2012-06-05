/*
NOTE: this build is currently using Processing 2.0a4.
	in order for serial (rxtx) to work with 2.0a4 on OSX,
	follow these instructions:
	http://code.google.com/p/processing/issues/detail?id=944

*/

package rotaryinterfacebuild;

import edu.exploratorium.rotary.RotaryInterface;
import processing.core.PApplet;


public class RotaryInterfaceBuild extends PApplet {
	private static final long serialVersionUID = -6196261738866639116L;
	
	RotaryInterface rotaryInterface;
	
	float spinSpeed = 0f;
	float rot = 0f;
	
	
	public void setup () {
		size(400, 400);
		rotaryInterface = new RotaryInterface(this, true);
		smooth();
		
		frameRate(30);
	}

	public void draw () {
		background(0);
		
//		spinSpeed = rotaryInterface.getSpeed();
		
		rot += 5*spinSpeed;
		
		strokeWeight(2);
		stroke(255);
		noFill();
		
		pushMatrix();
		translate(0.5f*width, 0.5f*height);
		rotate(rot);
		ellipse(0, 0, 200, 200);
		line(0, -25, 0, -100);
		popMatrix();
	}
	
	public void onRotaryEvent (float speed, int channel) {
//		println("Speed:"+speed+"; channel:"+channel);
		spinSpeed = speed;
	}
	
	public static void main(String _args[]) {
		PApplet.main(new String[] { rotaryinterfacebuild.RotaryInterfaceBuild.class.getName() });
	}
}
