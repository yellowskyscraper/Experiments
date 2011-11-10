import ijeoma.motion.Motion;
import ijeoma.motion.timeline.Timeline;
import ijeoma.motion.tween.Tween;

PFont font;

Timeline tl;

void setup() {
  size(400, 400);
  smooth();

  frameRate(80);

  font = createFont("Arial", 12);
  textFont(font);

  Motion.setup(this);

  tl = new Timeline();

  tl.insertChild(new Tween(-width, width + 100, 100), random(100));
  tl.insertChild(new Tween(-height, height + 100, 100), random(100));
  tl.insertChild(new Tween(height, -height - 100, 100), random(100));

  tl.insertChild(new Tween(-width, width + 100, 75), random(100));
  tl.insertChild(new Tween(-height, height + 100, 75), random(100));
  tl.insertChild(new Tween(height, -height - 100, 75), random(100));

  tl.insertChild(new Tween(-width, width + 100, 50), random(100));
  tl.insertChild(new Tween(-height, height + 100, 50), random(100));
  tl.insertChild(new Tween(height, -height - 100, 50), random(100));

  tl.insertChild(new Tween(-width, width + 100, 25), random(100));
  tl.insertChild(new Tween(-height, height + 100, 25), random(100));
  tl.insertChild(new Tween(height, -height - 100, 25), random(100));

  tl.repeat(Timeline.REVERSE);
  tl.play();
}

void draw() {
  background(255);

  noStroke();
  fill(0);

  for (int i = 0; i < tl.getChildCount(); i++) {
    if (i % 3 == 0) {
      fill(0, 200);
      rect(tl.getTween(i).getPosition(), i / 3 * 100, width, 100);
    } 
    else if ((i - 1) % 3 == 0) {
      fill((255 / 4) * 2, 200);
      rect((i - 1) / 3 * 100, tl.getTween(i).getPosition(), 100,
      height);
    } 
    else if ((i + 1) % 3 == 0) {
      fill((255 / 4) * 3, 200);
      rect(((i + 1) / 3 - 1) * 100, tl.getTween(i).getPosition(),
      100, height);
    }
  }

  drawUI();
}

void drawUI() {
  int red = color(255, 0, 0);
  int green = color(0, 255, 0);

  String time;

  stroke(lerpColor(green, red, tl.getSeekPosition()));
  line(tl.getSeekPosition() * width, 0, tl.getSeekPosition() * width,
  height);

  time = (int) tl.getTime() + " / " + (int) tl.getDuration();

  fill(lerpColor(green, red, tl.getSeekPosition()));
  text(time, width - textWidth(time) - 10, height - 10);
}

void keyPressed() {
  tl.play();
}

void mousePressed() {
  tl.pause();
}

void mouseReleased() {
  tl.resume();
}

void mouseDragged() {
  tl.seek(norm(mouseX, 0, width));
}

