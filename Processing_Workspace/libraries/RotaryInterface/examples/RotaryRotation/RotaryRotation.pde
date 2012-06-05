import processing.serial.*;
import edu.exploratorium.rotary.serial.*;
import edu.exploratorium.rotary.*;

RotaryInterface rotaryInterface;

float spinSpeed = 0f;
float rot = 0f;

void setup () {
  size(400, 400);
  rotaryInterface = new RotaryInterface(this, true);
  smooth();
  
  frameRate(30);
}

void draw () {
  background(0);
  
//  spinSpeed = rotaryInterface.getSpeed();
  
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

void onRotaryEvent (float speed, int channel) {
//  println("Speed:"+speed+"; channel:"+channel);
  spinSpeed = speed;
}
