package findingfaults.app.data;

import java.util.ArrayList;
import processing.core.PApplet;
import findingfaults.app.graphics.Vector;
import de.fhpotsdam.unfolding.Map;

public class CrustalDeformationVectors {

	PApplet parent;
	int wid = 1400;
	int hei = 1050;
	
//	Gif animation;
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
				
				Vector v = new Vector(parent);
				v.setup(name, lat, lon, vN, vE);
				vectors.add(v);
			}
		}
	}
	
	public void drawAni()
	{
		
	}
	
	public void draw(Map m)
	{
		//| Iterate Quakes & Draw
		for (int i = vectors.size()-1; i >= 0; i--) 
		{ 
			Vector vector = (Vector) vectors.get(i);
			float[] pos = m.getScreenPositionFromLocation(vector.getLocation());
			vector.update(pos[0], pos[1]);
			vector.draw();
		} 
	}
	
}
