import java.util.Arrays;
import java.util.Collections;

import ijeoma.motion.*;
import ijeoma.motion.tween.*;

int RED = color(255, 0, 0);
int GREEN = color(0, 255, 0);

PFont f;

TweenParallel tp;
TweenSequence ts;

float pieDiameter = 150;

int wedgeCount = 5;
int[] wedgeAngles;

void setup() {
  size(400, 200);
  smooth();

  setupPieChart();
  setupPieChartMotion();
}

void setupPieChart() {
  wedgeAngles = randomIntsInSum(360, wedgeCount);
}

void setupPieChartMotion() {
  Motion.setup(this);

  tp = new TweenParallel();
  ts = new TweenSequence();

  float lastWedgeAngle = 0;

  for (int i = 0; i < wedgeAngles.length; i++) {
    stroke(0);
    fill(wedgeAngles[i] * 3);

    tp.addChild(new Tween(lastWedgeAngle, lastWedgeAngle
      + radians(wedgeAngles[i]), 50));

    ts.appendChild(new Tween(lastWedgeAngle, lastWedgeAngle
      + radians(wedgeAngles[i]), 50));

    lastWedgeAngle += radians(wedgeAngles[i]);
  }

  tp.play();
  ts.play();
}

void draw() {
  background(255);

  // This animates the 1st pie chart a TweenParallel object
  for (int i = 0; i < tp.getChildCount(); i++) {
    Tween t = tp.getTween(i);

    fill(255 * t.getChange() / TWO_PI);
    arc(100, height / 2, pieDiameter, pieDiameter, t.getBegin(),
    t.getPosition());
  }

  String timeAsString = tp.getTime() + " / " + tp.getDuration();

  fill(lerpColor(GREEN, RED, tp.getSeekPosition()));
  text(timeAsString, width / 2 - textWidth(timeAsString) - 10,
  height - 10);

  // This animates the 2nd pie chart using a TweenSequence object
  for (int i = 0; i < ts.getChildCount(); i++) {
    Tween t = ts.getTween(i);

    fill(255 * t.getChange() / TWO_PI);
    arc(300, height / 2, pieDiameter, pieDiameter, t.getBegin(),
    t.getPosition());
  }

  timeAsString = ts.getTime() + " / " + ts.getDuration();

  fill(lerpColor(GREEN, RED, ts.getSeekPosition()));
  text(timeAsString, width - textWidth(timeAsString) - 10, height - 10);
}

void keyPressed() {
  setupPieChart();
  setupPieChartMotion();
}

int[] randomIntsInSum(int _sum, int count) {
  int[] randomNumbers = new int[count];

  for (int i = 0; i < randomNumbers.length - 1; i++) {
    int randomNumber = (int) random(0, _sum);
    randomNumbers[i] = randomNumber;
    _sum -= randomNumber;
  }

  randomNumbers[randomNumbers.length - 1] = _sum;

  Collections.shuffle(Arrays.asList(randomNumbers));

  return randomNumbers;
}

