import ijeoma.motion.*;
import ijeoma.motion.tween.*;

int RED = color(255, 0, 0);
int GREEN = color(0, 255, 0);

PFont f;

Tween t;

void setup() {
  size(400, 100);
  smooth();

  f = createFont("Arial", 12);
  textFont(f);

  Motion.setup(this);

  // Tween(begin, end, duration);
  // This creates a tween that begins at 0, ends at 400, and plays for 100
  // frames
  t = new Tween(0f, width, 100f);
  t.repeat();

  // If the repeat mode is set to REVERSE then it will reverse the begin and end values on
  // every repeat
  t.setRepeatMode(MotionConstant.REVERSE);

  // If the repeat mode NO_REVERSE then it will not reverse the begin and end
  // parameter on every repeat
  // t.setDelayMode(MotionConstant.NO_REVERSE);

  // The repeatMode can also be with the overloaded method Tween.repeat(String _repeatMode)
  // t.repeat(MotionConstant.REVERSE);

  t.setRepeatCount(3);
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
  t.play();
}

