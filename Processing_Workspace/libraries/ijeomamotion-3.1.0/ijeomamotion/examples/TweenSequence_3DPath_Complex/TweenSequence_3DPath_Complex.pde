/**
 	  Author: Ekene Ijeoma / Bloom
 	  Date: March 2010  
 	  Description: An object follows a complex path 
 */

import ijeoma.motion.*;
import ijeoma.motion.tween.*;

PFont f;

TweenSequence ts;

int HALF_SIZE = 100;
	float[][] path = { { HALF_SIZE, HALF_SIZE, HALF_SIZE }, { HALF_SIZE * 1.5f, 0, 0 }, { HALF_SIZE, -HALF_SIZE, -HALF_SIZE },
			{ 0, -HALF_SIZE * 1.5f, 0 }, { -HALF_SIZE, -HALF_SIZE, HALF_SIZE }, { -HALF_SIZE * 1.5f, 0, 0 },
			{ -HALF_SIZE, HALF_SIZE, -HALF_SIZE }, { 0, HALF_SIZE * 1.5f, 0 } };

public float x, y, z;

void setup() {
  size(400, 400, P3D);

  f = createFont("Arial", 12);
  textFont(f);
  textMode(SCREEN);

  smooth();

  z = y = z = 0;

  Motion.setup(this);

  ts = new TweenSequence();

  for (int i = 0; i < path.length; i++) {
    int next = ((i + 1) == path.length) ? 0 : i + 1;

    Tween t = new Tween();
    t.addParameter(new MotionParameter(this, "x", path[i][0],
    path[next][0]));
    t.addParameter(new MotionParameter(this, "y", path[i][1],
    path[next][1]));
    t.addParameter(new MotionParameter(this, "z", path[i][2],
    path[next][2]));

    // This could also be written as
    // Tween t = new Tween(new String[] { "x:" + path[i][0] + "," +
    // path[next][0], "y:" + path[i][1] + "," + path[next][1], "z:" +
    // path[i][2] + "," + path[next][2] });

    t.setDuration(50f);
    t.setEasing(MotionConstant.LINEAR_EASE_OUT);

    ts.appendChild(t);
  }

  ts.repeat();
  ts.play();
}

void draw() {
  background(255);

  pushMatrix();
  translate(width / 2, height / 2, 0);
  rotateY(frameCount / 100.0f);
  rotateX(2.0f);
  rotateZ(frameCount / 200.0f);

  noFill();
  stroke(200);
  box(HALF_SIZE * 2);

  noFill();
  stroke(100);
  beginShape();
  for (int i = 0; i < path.length; i++)
    vertex(path[i][0], path[i][1], path[i][2]);
  endShape(CLOSE);

  translate(x, y, z);

  // This could also be written as
  // Tween t = ts.getCurrentTween();
  // translate(t.getPosition("x"), t.getPosition("y"),
  // t.getPosition("z"));

  fill(0);
  box(20);
  popMatrix();

  int seekColor = lerpColor(color(0, 255, 0), color(255, 0, 0),
  ts.getSeekPosition());

  String timeAsString = ts.getTime() + " / " + ts.getDuration();

  fill(seekColor);
  text(timeAsString, width - textWidth(timeAsString) - 10, height - 10);
}

