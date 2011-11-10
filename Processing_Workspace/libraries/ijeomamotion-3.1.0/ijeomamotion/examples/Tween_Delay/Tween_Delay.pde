import ijeoma.motion.*;
import ijeoma.motion.tween.*;

int RED = color(255, 0, 0);
int GREEN = color(0, 255, 0);

PFont font;

Tween t;

void setup() {
  size(400, 100);
  smooth();

  font = createFont("Arial", 12);
  textFont(font);

  Motion.setup(this);

  //Tween(begin, end, duration, delay);

  //This creates a tween that begins at 0, ends at 400, delays for 100 frames, and plays for 100 frames
  t = new Tween(0f, width, 100f, 100f);

  // If the delay mode is set to EVERY then it will delay for X seconds/frames before every
  // repeated play. By default it is set to EVERY
  t.setDelayMode(MotionConstant.EVERY);

  // If the delay mode is set to ONCE then it will delay for X seconds/frames before the first
  // play and 0 seconds/frames for every repeated play after.
  // t.setDelayMode(MotionConstant.ONCE);

  t.repeat();
  t.play();
}

void draw() {
  background(255);

  noStroke();
  fill(0);
  rect(0, 0, t.getPosition(), height);

  int seekColor = lerpColor(GREEN, RED, t.getSeekPosition());

  stroke(seekColor);
  line(t.getSeekPosition() * width, 0, t.getSeekPosition() * width, height);

  String timeAsString = t.getTime() + " / " + t.getDuration();

  fill(seekColor);
  text(timeAsString, width - textWidth(timeAsString) - 10, height - 10);
}

void keyPressed() {
  if (key == ' ')
    t.play();
}

