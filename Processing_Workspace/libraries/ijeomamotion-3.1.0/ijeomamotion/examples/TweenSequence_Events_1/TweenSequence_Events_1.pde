/**
 	  Author: Ekene Ijeoma
 	  Date: October 2010   
 */

import ijeoma.motion.*;
import ijeoma.motion.tween.*;

PFont font;

TweenSequence ts;

void setup() {
  size(400, 400);

  smooth();

  font = createFont("Arial", 12);
  textFont(font);

  Motion.setup(this);

  ts = new TweenSequence();
  ts.appendChild(new Tween("tween1", -width, width, 100));
  ts.appendChild(new Tween("tween2", -width, width, 75));
  ts.appendChild(new Tween("tween3", -width, width, 50));
  ts.appendChild(new Tween("tween4", -width, width, 25));
  ts.play();
}

void draw() {
  background(255);

  noStroke();
  fill(0);

  for (int i = 0; i < ts.getChildCount(); i++)
    rect(ts.getTween(i).getPosition(), i * 100, width, 100);

  drawUI();
}

void drawUI() {
  int red = color(255, 0, 0);
  int green = color(0, 255, 0);

  String time;

  // This draws the seek for the ts TweenParallel object
  stroke(lerpColor(green, red, ts.getSeekPosition()));
  line(ts.getSeekPosition() * width, 0, ts.getSeekPosition() * width,
  height);

  // This draws the time for every Tween object
  for (int i = 0; i < ts.getChildCount(); i++) {
    Tween t = ts.getTween(i);
    time = (int) t.getTime() + " / " + (int) t.getDuration();

    fill(lerpColor(green, red, t.getSeekPosition()));
    text(time, 10, i * 100 + 10 + 12);
  }

  // This draws the time for the ts TweenSequence object
  time = (int) ts.getTime() + " / " + (int) ts.getDuration();

  fill(lerpColor(green, red, ts.getSeekPosition()));
  text(time, width - textWidth(time) - 10, height - 10);
}

void keyPressed() {
  ts.play();
}

void tweenSequenceStarted(TweenSequence _ts) {
  println(_ts + " started");
}

void tweenSequenceEnded(TweenSequence _ts) {
  println(_ts + " ended");
}

