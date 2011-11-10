import ijeoma.motion.*;
import ijeoma.motion.tween.*;

int RED = color(255, 0, 0);
int GREEN = color(0, 255, 0);

Tween t;
PFont f;

public float rect1X, rect2X;

public void setup() {
  size(400, 100);
  smooth();

  f = createFont("Arial", 12);

  Motion.setup(this);

  // Tween(Object parameterObject, String[] parameters, float duration);
  // The parameterObject will either be "this" or a custom object
  // A parameter string can be formatted as
  // "paramterName/variableName: begin". ex "rect1X:-400" or
  // "paramterName/variableName: begin, end". ex "rect1X: -400, 400"

  // This creates a Tween with 2 parameters using variables rect1X and
  // rect2X which are inside "this" which plays for 100 frames
  t = new Tween(this, new String[] { 
    "rect1X: " + -width + "," + width, "rect2X: " + width + ", " + -width
  }
  , 100);
  t.repeat();
  t.play();
}

public void draw() {
  background(255);

  noStroke();
  fill(0, 50);
  rect(rect1X, 0, width, height);
  rect(rect2X, 0, width, height);

  int seekColor = lerpColor(GREEN, RED, t.getSeekPosition());

  stroke(seekColor);
  line(t.getSeekPosition() * width, 0, t.getSeekPosition() * width, height);

  String time = t.getTime() + " / " + t.getDuration();

  noStroke();
  fill(seekColor);
  text(time, width - textWidth(time) - 10, height - 10);
}

public void keyPressed() {
  t.play();
}

