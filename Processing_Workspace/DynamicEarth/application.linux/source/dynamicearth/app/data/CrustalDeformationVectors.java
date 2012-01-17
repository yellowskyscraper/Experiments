package dynamicearth.app.data;

import java.util.ArrayList;
import processing.core.PApplet;
import dynamicearth.app.graphics.Vector;
import dynamicearth.app.labels.CrustalDeformationLabel;
import dynamicearth.app.util.BasicUtils;
import de.fhpotsdam.unfolding.Map;
import de.fhpotsdam.unfolding.geo.Location;

public class CrustalDeformationVectors {

	PApplet parent;
	int wid = 1400;
	int hei = 1050;

	//| Graphics
	CrustalDeformationLabel label;

	int animating = 0;
	int timekeeper = 0;
	String STATUS = "OFF";
	
	ArrayList<Vector> vectors;
	  
	public CrustalDeformationVectors(PApplet p)
	{
		parent = p;
	}
	
	public void setup(Map m, float w, float h)
	{
		wid = parent.screenWidth;
		hei = parent.screenHeight;

		vectors = new ArrayList<Vector>();
		String[] velocityData = parent.loadStrings("data/velocity/cleaned_velocity_file.txt");
		
		for(int i = 0; i < velocityData.length; i++) {
			String[] vector = velocityData[i].split("\t");
			
			if(!vector[1].equals("Longitude")) {
				float lat = Float.valueOf(vector[0].trim()).floatValue();
				float lon = Float.valueOf(vector[1].trim()).floatValue();
				float vN = Float.valueOf(vector[2].trim()).floatValue();
				float vE = Float.valueOf(vector[3].trim()).floatValue();	
				String name = vector[7];
				
				Location coord = new Location(lat,lon);
				float[] p = m.getScreenPositionFromLocation(coord);
				float[] position = BasicUtils.scaleCoordinates(1050, 1050, p[0], p[1]);
				
				if(position[0] > 0 && position[0] < 1050 && position[1] > 0 && position[1] < h) {
					Vector v = new Vector(parent);
					v.setup(name, lat, lon, vN, vE);
					vectors.add(v);
				}
				
			}
		}

		//| Faults Label
		label = new CrustalDeformationLabel(parent);
		label.setup(); 
	}
	
	public void open()
	{
		STATUS = "ON";
		animating = 1;
		label.open();
	}
	
	public void close()
	{
		if(animating == 1){
			timekeeper = 0;
			label.close();
		}
		animating = 2;
	}
	
	public void update()
	{
		switch(animating){
			case 1:
			timekeeper += 1;
			if(timekeeper > 300) {
				STATUS = "ANIMATING OUT";
			}
			break;
		
			case 2:
			timekeeper += 1;
			if(timekeeper > 30) STATUS = "DONE";
			break;
	
			case 3:
			break;
		}
		
		label.update();
	}
	
	public void draw(Map m)
	{	
		if(STATUS.equals("ON")) this.drawVecotors(m);
		label.draw(m);	
	}
	
	public void drawVecotors(Map m)
	{
		//| Iterate Velocities
		for (int i = vectors.size()-1; i >= 0; i--) 
		{ 
			Vector vector = (Vector) vectors.get(i);
			float[] pos = m.getScreenPositionFromLocation(vector.getLocation());
			vector.update(pos[0], pos[1]);
			vector.draw();
		} 
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
