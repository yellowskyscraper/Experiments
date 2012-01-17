package thegroundbeneath.app.data;


import ijeoma.motion.tween.Tween;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;
import processing.xml.XMLElement;

import thegroundbeneath.app.graphics.Block;
import thegroundbeneath.app.labels.PopulationLabel;
//import thegroundbeneath.app.util.BasicUtils;

import de.fhpotsdam.unfolding.Map;
import de.fhpotsdam.unfolding.geo.Location;

public class PopulationDensity 
{
	PApplet parent;
	PGraphics buffer;
	
	//| Population Data
	XMLElement xml;
	ArrayList<Block> blocks;
	boolean parsed;
	int parse = 0;
	
	//| Population Image
	PImage mainPopulationImage;
	float alphaMainPopulation;
	Tween tweenPopulationIN;
	Tween tweenPopulationOUT;
	
	//| Scene Variables
	int animating = 0;
	int timekeeper = 0;
	String STATUS = "OFF";
	
	//| Label
	PopulationLabel label;
	
	public PopulationDensity(PApplet p)
	{
		parent = p;
	}

	public void setup(Map m, float w, float h) 
	{
/*		//| Population Data
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
*/		
		//| Image
		mainPopulationImage  = parent.loadImage("data/images/population.png");
		
		//| Tweens
		tweenPopulationIN = new Tween(0f, 255f, 20f, 10f);
		tweenPopulationOUT = new Tween(255f, 0f, 20f, 30f);
		
		//| Faults Label
		label = new PopulationLabel(parent);
		label.setup();
	}
	
	public void start()
	{
		if(animating == 0) {
			isOff = false;
			STATUS = "ANIMATING IN";
			tweenPopulationIN.play();
			label.open();
		}
		animating = 1;
	}
	
	public void update() 	
	{	
/*		//| Rendering Population Data
		switch(animating){
			case 1:
			timekeeper += 1;
			if(label.opened()) STATUS = "ON";
			if(timekeeper > 25) this.finish();
			break;
			
			case 2:
			if(label.closed()) STATUS = "DONE";
			break;
		}
*/
		switch(animating){
			case 1:
			alphaMainPopulation = tweenPopulationIN.getPosition();
			if(label.opened() && alphaMainPopulation == 255) {
				animating = 2;
				tweenPopulationIN.stop();
				STATUS = "ON";
			}
			break;
			
			case 2:
			timekeeper += 1;
			if(timekeeper > 100) {
				animating = 3;
				tweenPopulationOUT.play();
				label.close();
				STATUS = "ANIMATING OUT";
			}
			break;
			
			case 3:
			alphaMainPopulation = tweenPopulationOUT.getPosition();
			if(label.closed()) STATUS = "DONE";
			break;
			
			case 4:
			alphaMainPopulation = tweenPopulationOUT.getPosition();
			if(alphaMainPopulation == 0) this.off(true);
			break;
		}

		label.update();
	}

	public void draw(Map m) 	
	{	
/*		//| Rendering Population Data
		parent.smooth();
		parent.noStroke();
		for (int i = blocks.size()-1; i >= 0; i--) 
		{ 
			Block block = (Block) blocks.get(i);
			float[] pos = m.getScreenPositionFromLocation(block.getLocation());
			block.update(pos[0], pos[1], animating);
			block.draw();
		} 
*/
		float[] tl = m.getScreenPositionFromLocation(new Location(38.339f, -122.796f));	
		int xpos = Math.round(tl[0]);
		int ypos = Math.round(tl[1]);
		
		//| Main Sequence
		parent.tint(255, alphaMainPopulation);
		parent.image(mainPopulationImage, xpos, ypos, 1051, 1051);
		
		label.draw(m);
	}

	public void off() 
	{
		STATUS = "OFF";
		animating = 4;
		timekeeper = 0;
	}
	
	boolean isOff = true;
	public void off(boolean internal) 
	{
		isOff = true;
		animating = 0;
		tweenPopulationOUT.stop();
	}
	
	public boolean isOff()
	{
		return isOff;
	}

	public String status() 
	{
		return STATUS;
	}
}
