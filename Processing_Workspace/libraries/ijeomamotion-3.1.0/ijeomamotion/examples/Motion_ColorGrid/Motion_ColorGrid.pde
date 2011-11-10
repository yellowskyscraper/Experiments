import ijeoma.motion.tween.*;
import ijeoma.motion.easing.*;

PFont font;
int fontSize = 16;

Tween[] tweens;

int gridRows;
int gridCols;
int gridSize;
int gridOffset;

void setup() {
  size(400, 400, P3D);

  noStroke();
  rectMode(CENTER);
  imageMode(CENTER);

  font = loadFont("Ziggurat-HTF-Black-32.vlw");
  textFont(font, fontSize);

  gridSize = 50;
  gridOffset = gridSize / 2;
  gridCols = width / gridSize;
  gridRows = height / gridSize;

  tweens = new Tween[gridCols*gridRows];

  for (int i = 0; i < gridCols; i++) 
    for (int j = 0; j < gridRows; j++) 
      tweens[i*gridCols+j] = new Tween(this, "Tween_" + i + "_" + j, 0f, 0f, 0, Tween.FRAMES, 0f, Tween.QUAD_EASE_IN);
}

void draw() {
  background(0);

  for (int i = 0; i < gridCols; i++) {
    for (int j = 0; j < gridRows; j++) {
      int x = i * gridSize + gridOffset;
      int y = j * gridSize + gridOffset;
      int r = (dist(x, y, mouseX - gridSize / 2, mouseY - gridSize / 2) <= gridSize / 2) ? 255 : 0;

      int k = i*gridCols+j;

      if (r == 255) {
        tweens[k] = new Tween(this, "Tween_" + i + "_" + j, r, 0, 150, Tween.FRAMES, 0f, Tween.QUAD_EASE_IN);
        tweens[k].play();
      }

      tweens[k].update();

      int p = (int) tweens[k].getPosition();

      pushMatrix();
      translate(x, y);
      fill(p, 0, 0);
      rect(0, 0, gridSize, gridSize);

      fill(255);
      if (p > 0)
        text(p, -textWidth(p + "") / 2, fontSize / 2);
      popMatrix();
    }
  }
}




