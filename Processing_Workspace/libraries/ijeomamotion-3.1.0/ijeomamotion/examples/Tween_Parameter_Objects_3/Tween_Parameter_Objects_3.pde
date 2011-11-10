package ijeoma.motion.tween.test;

import ijeoma.motion.*;
import ijeoma.motion.tween.*;
import processing.core.*;

int RED = color(255, 0, 0);
int GREEN = color(0, 255, 0);

PFont f;

Tween t;
Rect r1, r2;

void setup() {
  size(400, 100);
  smooth();

  f = createFont("Arial", 12);
  textFont(f);

  Motion.setup(this);

  r1 = new Rect();
  r2 = new Rect();

  t = new Tween();

  // Tween.addParamter(Object _paramterObject, String _parameterName, float _begin, float _end)
  // A parameter can also be formatted as an object using the MotionParameter class
  // The parameterObject will either be "this" or a custom object
  // The paramterName will be name of the variable to be tweened inside the parameterObject


  // This creates a parameter using the x variable inside the r1 object
  t.addParameter(new MotionParameter(r1, "x", -width, width));
  // This creates a parameter using the x variable inside the r2 object
  t.addParameter(new MotionParameter(r2, "x", width, -width));

  t.setDuration(100);
  t.repeat();
  t.play();
}

void draw() {
  background(255);

  noStroke();
  fill(0, 50);
  rect(r1.x, 0, width, height);
  rect(r2.x, 0, width, height);

  int seekColor = lerpColor(GREEN, RED, t.getSeekPosition());

  stroke(seekColor);
  line(t.getSeekPosition() * width, 0, t.getSeekPosition() * width, height);

  String time = t.getTime() + " / " + t.getDuration();

  noStroke();
  fill(seekColor);
  text(time, width - textWidth(time) - 10, height - 10);
}

void keyPressed() {
  t.play();
}

