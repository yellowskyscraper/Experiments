package thegroundbeneath.app.data;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.xml.XMLElement;
import de.fhpotsdam.unfolding.Map;
import de.fhpotsdam.unfolding.geo.Location;
import thegroundbeneath.app.graphics.Block;
import thegroundbeneath.app.labels.PopulationLabel;
import thegroundbeneath.app.util.BasicUtils;

public class PopulationDensity 
{
	PApplet parent;
	
	//| Population
	XMLElement xml;
	ArrayList<Block> blocks;
	boolean parsed;
	int parse = 0;
	
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

		label.open();
	}
	
	public void update() 	
	{	
		label.update();
	}

	public void draw(Map m) 	
	{	
		for (int i = blocks.size()-1; i >= 0; i--) 
		{ 
			Block block = (Block) blocks.get(i);
			float[] pos = m.getScreenPositionFromLocation(block.getLocation());
			block.update(pos[0], pos[1]);
			block.draw();
		} 
		
		label.draw(m);
	}
}
