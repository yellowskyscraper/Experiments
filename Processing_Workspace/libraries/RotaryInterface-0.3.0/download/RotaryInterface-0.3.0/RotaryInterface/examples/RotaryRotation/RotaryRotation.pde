import processing.serial.*;
import edu.exploratorium.rotary.serial.*;
import edu.exploratorium.rotary.*;

RotaryInterface rotaryInterface;

float spinSpeed = 0f;
float rot = 0f;

void setup () {
  size(400, 400);
  smooth();
  frameRate(30);
  
  rotaryInterface = new RotaryInterface(this);
  
  // listen for values out of the range used for rotary control,
  // e.g. to add button press handling.
  rotaryInterface.setDispatchOutOfRange(true);
}

void draw () {
  background(0);
  
  /*
  // this demonstrates polling instead of handling RotaryEvents...
  spinSpeed = rotaryInterface.getSpeed();
  int outOfRangeVal = rotaryInterface.getOutOfRangeValue();
  if (outOfRangeVal == 33) {
    println("heard '33' out of range.");
  }
  */
  
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

void onRotaryEvent (RotaryEvent evt) {
  if (evt.type() == RotaryEvent.ROTARY_CHANGE) {
    // println("Speed:"+evt.speed()+"; channel:"+evt.channel());
    spinSpeed = evt.speed();
  } else if (evt.type() == RotaryEvent.OUT_OF_ROTARY_RANGE) {
    println("Out-of-range value:"+ evt.rawValue());
  }
}