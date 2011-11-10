import ijeoma.motion.*;
import ijeoma.motion.tween.*;

PFont f;

TweenParallel tp;

int barCount = 10;
float barWidth = 0;
float barHeightMin, barHeightMax;
int barTopMargin = 24;

void setup() {
  size(400, 200);
  smooth();

  f = createFont("Arial", 12);
  textFont(f, 12);

  setupBarChart();
  setupBarChartMotion();
}

void setupBarChart() {
  barWidth = width / barCount;
  barHeightMax = height;
  barHeightMin = height / 5;
}

void setupBarChartMotion() {
  Motion.setup(this);

  tp = new TweenParallel();

  for (int i = 0; i < barCount; i++) {
    float randomHeight = random(barHeightMin, barHeightMax);
    tp.addChild(new Tween(0f, randomHeight, randomHeight/2));
  }

  tp.play();
}

void draw() {
  background(255);

  // This animates the 1st pie chart a TweenParallel object
  for (int i = 0; i < tp.getChildCount(); i++) {
    Tween t = tp.getTween(i);

    float barHeight = t.getPosition();

    stroke(0);
    fill(lerpColor(color(255), color(0), t.getSeekPosition() * t.getEnd() / height));
    rect(i * barWidth, height, barWidth, -barHeight);

    noStroke();
    fill(255);
    text((int) barHeight + "", i * barWidth + (barWidth - textWidth((int) barHeight + "")) / 2, height - (barHeight - barTopMargin));
  }

  String timeAsString = (int) tp.getTime() + " / " + (int) tp.getDuration();

  noStroke();
  fill(lerpColor(color(0, 255, 0), color(255, 0, 0), tp.getSeekPosition()));
  text(timeAsString, width - textWidth(timeAsString) - 10, height - 10);
}

void keyPressed() {
  setupBarChartMotion();
}

