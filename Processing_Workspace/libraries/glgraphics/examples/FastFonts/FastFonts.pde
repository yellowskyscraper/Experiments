// GLGraphics incorporates an OpenGL-accelerated version
// of PFont, called GLFont. Its use is identical to PFont.

import codeanticode.glgraphics.*;
import processing.opengl.*;

GLFont font;

void setup() {
  size(400, 400, GLConstants.GLGRAPHICS);

  // The GLGraphics renderer is needed to access the loadGLFont() method.
  GLGraphics renderer = (GLGraphics)g;
  // The font must be located in the sketch's 
  // "data" directory to load successfully
  font = renderer.loadGLFont("FreeSerif-32.vlw"); 
  textFont(font, 32); 
}

void draw() {
  background(0);

  fill(250, 10, 10);
  text("Fast Font demo", width/2 - textWidth("Fast Font demo")/2, height/2, -100);


  fill(255, 150);  
  text("[" + mouseX + ", " + mouseY + "]", mouseX, mouseY);
}
