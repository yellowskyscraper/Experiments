import ijeoma.motion.*;
import ijeoma.motion.tween.*;

int RED = color(255, 0, 0);
int GREEN = color(0, 255, 0);

Tween t;
PFont f;

public float rect1X, rect2X;

void setup() {
  size(400, 100);
  smooth();

  f = createFont("Arial", 12);
  textFont(f);

  Motion.setup(this);

  t = new Tween();

  // Tween.addParamter(Object _paramterObject, String _parameterName, float _begin, float _end)

  // This adds a parameter using the rect1X variable inside "this" PApplet
  // which will tween from -400 to 400
  t.addParameter(new MotionParameter(this, "rect1X", -width, width));
  // This adds a parameter using the rect2X variable inside "this" PApplet
  // which will tween from 400 to -400
  t.addParameter(new MotionParameter(this, "rect2X", width, -width));

  t.setDuration(100);
  t.repeat();
  t.play();
}

void draw() {
  background(255);

  noStroke();
  fill(0, 50);
  rect(rect1X, 0, width, height);
  rect(rect2X, 0, width, height);

  int seekColor = lerpColor(GREEN, RED, t.getSeekPosition());

  stroke(seekColor);
  line(t.getSeekPosition() * width, 0, t.getSeekPosition() * width, height);

  String timeAsString = t.getTime() + " / " + t.getDuration();

  noStroke();
  fill(seekColor);
  text(timeAsString, width - textWidth(timeAsString) - 10, height - 10);
}

void keyPressed() {
  if (key == ' ')
    t.play();
}

