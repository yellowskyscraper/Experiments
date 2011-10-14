package cooladaLoader;
import java.io.File;
import processing.core.*;
/**
 * <p>Lucerne University of Applied Sciences and Arts <a href="http://www.hslu.ch">http://www.hslu.ch</a></p>
 *
 * <p>This source is free; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License and by nameing of the originally author</p>
 * 
 * @author Markus Zimmermann <a href="http://www.die-seite.ch">http://www.die-seite.ch</a>
 * @version 1.0
 */
public class Viewer extends PApplet
{
    float rotX = -0.048000026f;
    float rotY = -0.32000035f;
    int x = 250;
    int y = 250;
    float changeSize = 2.5f;

//    float lenX, lenY, lenZ, angle;
//    char axis ='0';

    Triangle[] triangles;
    Line[] lines;
    ColladaLoader model;



    public void setup()
    {
      size(500, 500, P3D);
      //model = ColladaLoader.getInstance("testfile2.dae", this);
      //model = ColladaLoader.getInstance("susanOnWall.kmz", this);
      model = ColladaLoader.getInstance("truck5_8.dae", null);
      triangles = model.getTriangles();
      lines = model.getLines();
      frameRate(10);

    }


    public void draw(){
      background(16);

      lights();
      
      translate(x, y);
      rotateX(rotY);
      rotateY(rotX);
      scale(changeSize);

//      model.rotate(angle, axis);
//      model.shift(lenX, 'x');
//      model.shift(lenY, 'y');
//      model.shift(lenZ, 'z');
//      lenY =0; lenX = 0; lenZ =0;

      //model.draw();
      drawModel();
      
      stroke(125, 0, 0);
      strokeWeight(1);
      line(0, 0, 0, width, 0, 0);
      stroke(0, 125, 0);
      line(0, 0, 0, 0, 0, -width);
      stroke(0, 0, 125);
      line(0, 0, 0, 0, -height, 0);


    }

    public void drawModel()
    {
      noStroke();

      fill(127);

        for (Triangle tri : triangles)
        {

            if (!tri.containsTexture)
            {
                fill(tri.colour.red*255, tri.colour.green*255, tri.colour.blue*255, tri.colour.transparency * 255);
                beginShape(TRIANGLES);

                vertex(tri.A.x, tri.A.y, tri.A.z);
                vertex(tri.B.x, tri.B.y, tri.B.z);
                vertex(tri.C.x, tri.C.y, tri.C.z);

                endShape();

            } else
            {
                beginShape(TRIANGLES);
                texture(tri.imageReference);

                vertex(tri.A.x, tri.A.y, tri.A.z, tri.texA.x, tri.texA.y);
                vertex(tri.B.x, tri.B.y, tri.B.z, tri.texB.x, tri.texB.y);
                vertex(tri.C.x, tri.C.y, tri.C.z, tri.texC.x, tri.texC.y);

                endShape();

            }

        }

        for (Line lin : lines)
        {
            stroke(lin.colour.red*255, lin.colour.green*255, lin.colour.blue*255);
            strokeWeight(1);
            line(lin.A.x, lin.A.y, lin.A.z, lin.B.x, lin.B.y, lin.B.z);

        }
    }

    public void mouseDragged()
    {
        if (key == 'a' )
        {
            rotX += (mouseX - pmouseX) * 0.001f;
            rotY -= (mouseY - pmouseY) * 0.001f;
            //model.rotate((mouseX - pmouseX) * 0.004f, 'y');
            //model.rotate((mouseY - pmouseY) * 0.004f, 'x');

        }
        if (key == 'd')
        {
            //changeSize -= (mouseY - pmouseY) * 0.01f;
            model.scale(1+(pmouseY - mouseY) * 0.01f);
        }
        if (key == 's' )
        {
            x += (mouseX - pmouseX)*5;
            y += (mouseY - pmouseY)*5;
            //model.shift((mouseX - pmouseX)*5, 'x');
            //model.shift((mouseY - pmouseY)*5,'y');

        }

        if (key == 'y'||key == 'x'||key == 'c' )
        {
            char axis = (key == 'y')?'x':(key == 'x')?'y':(key == 'c')?'z':'0';
            model.shift((mouseY - pmouseY)*5, axis);
        }
        if (key == '1'||key == '2'||key == '3' )
        {
            char axis = (key == '1')?'x':(key == '2')?'y':(key == '3')?'z':'0';
            model.rotate((mouseY - pmouseY) * 0.004f, axis);
        }
    }

//    @Override
//    public void keyPressed() {
//        if (keyCode == LEFT) {lenX = -5f; angle = 0;}
//        if (keyCode == RIGHT) {lenX = 5f; angle=0;}
//        if (keyCode == UP) {lenY = -5f; angle = 0;}
//        if (keyCode == DOWN) {lenY = 5f; angle=0;}
//        if (key == 'x'||key == 'y'||key == 'z') {axis = key; angle = 0.04f;}
//        System.out.println(lenX);
//
//    }





    public static void main(String[] args)
    {
        PApplet.main(new String[]{"cooladaLoader.Viewer"});
    }

}



