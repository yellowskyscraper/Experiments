package thegroundbeneath.app.graphics;

import processing.core.PApplet;
import thegroundbeneath.app.util.BasicUtils;
import de.fhpotsdam.unfolding.geo.Location;

public class Block
{
	PApplet parent;
	int wid = 1400;
	int hei = 1050;
	
	float xpos, ypos;
	Location block;
	int population;
	int scale;
	float magnitude;
	int mag;
	
	public Block(PApplet p)
	{
		parent = p;
	}
	
	public void setup(int n, float la, float lo)
	{
		wid = parent.screenWidth;
		hei = parent.screenHeight;
		population = n;

		scale = 2;
		if(population > 20 && population < 40) scale = 3;
		else if(population > 40 && population < 60) scale = 4;
		else if(population > 60 && population < 80) scale = 5;
		else if(population > 80 && population < 100) scale = 6;
		else if(population > 100 && population < 120) scale = 7;
		else if(population > 120 && population < 140) scale = 8;
		else if(population > 140 && population < 160) scale = 9;
		else if(population > 160) scale = 10;
		
		block = new Location(la,lo);
	}
	
	public Location getLocation()
	{
		return block;
	}
	
	public void update(float x, float y)
	{	
		xpos = x;
		ypos = y;
	}
	
	public void draw() 	
	{
		//| Larger Scale to Bay Model Screen Resolution
		float[] position = BasicUtils.scaleCoordinates(wid, hei, xpos, ypos);

		parent.smooth();
		parent.noStroke();
		parent.fill(84,188,235);
		parent.ellipse(position[0], position[1], 2, 2);
		parent.fill(4,188,235, 90);
		parent.ellipse(position[0], position[1], scale, scale);
	}
}












