package dynamicearth.app.util;

public class BasicUtils {
	
	public BasicUtils()
	{

	}
	
	public static float[] scaleCoordinates(int oldwidth, int oldheight, float xpos, float ypos)
	{
		float percentincreaseW = 0.42f * oldwidth;
		float newwidth = oldwidth + percentincreaseW;
		
		float percentincreaseH = 0.14f * oldheight;
		float newheight = oldheight + percentincreaseH;

		//| Scaled position based on percentage of stretch (x = newMax * n/oldMax)
		float newX = newwidth * xpos/oldwidth;
		float newY = newheight * ypos/oldheight;
		
		float[] positions = {newX, newY};
		
		return positions;
	}
	
	
}
