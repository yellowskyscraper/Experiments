import ijeoma.motion.*;
import ijeoma.motion.tween.*;

int RED = color(255, 0, 0);
int GREEN = color(0, 255, 0);

Tween t;
PFont f;

@Override
void setup() {
  size(400, 100);
  smooth();

  f = createFont("Arial", 12);
  textFont(f);

  Motion.setup(this);

  t = new Tween();

  // Tween.addParamter(String _parameterName, float _begin, float _end)

  // This adds a new MotionParameter with a parameter named rect1X which
  // will tween from -400 to 400
  t.addParameter(new MotionParameter("rect1X", -width, width));
  // This adds a new MotionParameter with a parameter named rect2X which
  // will tween from 400 to -400
  t.addParameter(new MotionParameter("rect2X", width, -width));

  t.setDuration(100);
  t.repeat();
  t.play();
}

@Override
void draw() {
  background(255);

  noStroke();
  fill(0, 50);
  rect(t.getParameter("rect1X").getPosition(), 0, width, height);
  rect(t.getParameter("rect2X").getPosition(), 0, width, height);

  // The above 2 lines could also be written as
  // rect(t.getPosition("rect1X"), 0, width, height);
  // rect(t.getPosition("rect2X"), 0, width, height);

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

