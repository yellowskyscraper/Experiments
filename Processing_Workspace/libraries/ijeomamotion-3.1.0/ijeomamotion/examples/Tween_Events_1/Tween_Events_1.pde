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

  t = new Tween(0, width, 100);
  t.repeat();

  // If you set the tween to repeat without setting the tweenCount then
  // the tweenEnded event will never be called but if you set the
  // repeatCount then itll be called after X repeats
  t.setRepeatCount(2);

  t.play();
}

void draw() {
  background(255);

  noStroke();
  fill(0);
  rect(0, 0, t.getPosition(), height);

  String timeAsString = t.getTime() + " / " + t.getDuration();

  fill(lerpColor(GREEN, RED, t.getSeekPosition()));
  text(timeAsString, width - textWidth(timeAsString) - 10, height - 10);
}

void keyPressed() {
  if (key == ' ')
    t.play();
}

void mouseDragged() {
  t.seek(norm(mouseX, 0, width));
}

void tweenDelayStarted(Tween _t) {
  println(_t + "delay started");
}

void tweenDelayEnded(Tween _t) {
  println(_t + "delay ended");
}

void tweenStarted(Tween _t) {
  println(_t + " started");
}

void tweenEnded(Tween _t) {
  println(_t + " ended");
}

void tweenResumed(Tween _t) {
  println(_t + " resumed");
}

void tweenPaused(Tween _t) {
  println(_t + " paused");
}

void tweenRepeated(Tween _t) {
  println(_t + " repeated");
}

