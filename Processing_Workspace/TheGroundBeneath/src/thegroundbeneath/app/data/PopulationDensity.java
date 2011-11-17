package thegroundbeneath.app.data;


import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PGraphics;
import processing.xml.XMLElement;

import thegroundbeneath.app.graphics.Block;
import thegroundbeneath.app.labels.PopulationLabel;
import thegroundbeneath.app.util.BasicUtils;

import de.fhpotsdam.unfolding.Map;
import de.fhpotsdam.unfolding.geo.Location;

import ijeoma.motion.Motion;
import ijeoma.motion.tween.Tween;

public class PopulationDensity 
{
	PApplet parent;
	PGraphics buffer;
	
	//| Population
	XMLElement xml;
	ArrayList<Block> blocks;
	boolean parsed;
	int parse = 0;
	
	//| Scene Variables
	int animating = 0;
	int timekeeper = 0;
	String STATUS = "OFF";
	
	float populationAlpha = 100;
	
	//| Label
	PopulationLabel label;
	
	public PopulationDensity(PApplet p)
	{
		parent = p;
	}

	public void setup(Map m, float w, float h) 
	{
		//| XML
		parsed = false;
		xml = new XMLElement(parent, "data/php/occupancy_bay_50mi.xml"); 
		blocks = new ArrayList<Block>();
		buffer = parent.createGraphics(1051, 1051, PApplet.JAVA2D);

		for(int i=0; i<xml.getChildCount()-1; i++){
			int total = xml.getChild(i).getInt("total");
			float lat = Float.valueOf(xml.getChild(i).getFloat("lat"));
			float lon = Float.valueOf(xml.getChild(i).getFloat("lon"));

			Location coord = new Location(lat,lon);
			float[] p = m.getScreenPositionFromLocation(coord);
			float[] position = BasicUtils.scaleCoordinates(1060, 1050, p[0], p[1]);
			
			if(position[0] > 0 && position[0] < 1050 && position[1] > 0 && position[1] < 1050) {
				Block b = new Block(parent);
				b.setup(total, lat, lon);
				blocks.add(b);
			}
		}
		
		//| Faults Label
		label = new PopulationLabel(parent);
		label.setup();
	}
	
	public void start()
	{
		if(animating == 0) {
			STATUS = "ANIMATING IN";
			label.open();
		}
		animating = 1;
	}
	
	public void finish()
	{
		animating = 2;
		label.close();
	}
	
	public void update() 	
	{	
		switch(animating){
			case 1:
			timekeeper += 1;
			if(label.opened()) STATUS = "ON";
			if(timekeeper > 50) this.finish();
			break;
			
			case 2:
			if(label.closed()) STATUS = "DONE";
			break;
		}

		label.update();
	}

	public void draw(Map m) 	
	{	
		//buffer.beginDraw();
		parent.smooth();
		parent.noStroke();
		for (int i = blocks.size()-1; i >= 0; i--) 
		{ 
			Block block = (Block) blocks.get(i);
			float[] pos = m.getScreenPositionFromLocation(block.getLocation());
			block.update(pos[0], pos[1], animating);
			//block.draw(buffer);
			block.draw();
		} 
		//buffer.endDraw();
		//PImage img = buffer.get(0, 0, buffer.width, buffer.height);
		//parent.tint(255, populationAlpha);
		//parent.image(img, 0, 0);
		//parent.tint(255);
		
		label.draw(m);
	}

	public void off() 
	{
		STATUS = "OFF";
		animating = 0;
		timekeeper = 0;
	}

	public String status() 
	{
		return STATUS;
	}
}
