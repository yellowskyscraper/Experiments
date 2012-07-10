import processing.serial.*;
import edu.exploratorium.rotary.serial.*;
import edu.exploratorium.rotary.*;

RotaryInterface rotaryInterface;

float y = 0f;

void setup () {
  size(400, 400);
  rotaryInterface = new RotaryInterface(this, true);
  smooth();
  
  frameRate(30);
  background(0);
}

void draw () {
  fill(0, 50);
  rect(0, 0, width, height);
  
  float spinSpeed = rotaryInterface.getSpeed();
  y += height*spinSpeed;
  while (y > height) {
    y -= height;
  }
  while (y < 0) {
    y += height;
  }
  
  noStroke();
  fill(255);
  rect(0, y, width, 50);
}