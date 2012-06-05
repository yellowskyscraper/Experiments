package dynamicearth2.app.utils;

public class BasicUtils {
	
	public BasicUtils()
	{

	}
	
	public static float[] scaleCoordinates(int oldwidth, int oldheight, int newwidth, int newheight, float xpos, float ypos)
	{
		
		float percentincreaseW = 0.52284f * oldwidth;
		float W = oldwidth + percentincreaseW;
		
		float percentincreaseH = 0.52284f * oldheight;
		float H = oldheight + percentincreaseH;

		//| Scaled position based on percentage of stretch (x = newMax * n/oldMax)
		float newX = W * xpos/oldwidth;
		float newY = H * ypos/oldheight;
		
		float[] positions = {newX, newY};
		
/*
		//| (985, 788, 1500, 1200, x, y);
		//| 2 = 100 / 50
		float differenceW = newwidth / oldwidth;
		float differenceH = newwidth / oldheight;
		
		//| 100 = 2 * 50
		float percentincreaseW = differenceW * oldwidth;
		float percentincreaseH = differenceH * oldheight;
		
		//| 150 = 50 + 100
		float W = oldwidth + percentincreaseW;
		float H = oldheight + percentincreaseH;

		//| New = 150 * (Old / 50)
		float newX = W * xpos/oldwidth;
		float newY = H * ypos/oldheight;
		float[] positions = {newX, newY};
*/
		return positions;
	}
}
