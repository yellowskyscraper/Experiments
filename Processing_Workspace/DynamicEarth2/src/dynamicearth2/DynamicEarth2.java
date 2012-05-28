package dynamicearth2;

import de.fhpotsdam.unfolding.Map;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.providers.Microsoft;
import dynamicearth2.app.data.EarthquakeArchive;
import dynamicearth2.app.data.IntertitleDynamicEarth;
//import de.fhpotsdam.unfolding.utils.MapUtils;
import processing.core.PApplet;
import processing.core.PImage;
//import processing.core.PImage;
import processing.core.PMatrix2D;


public class DynamicEarth2 extends PApplet {

	private static final long serialVersionUID = 1L;

	//| Map And Label Text
		Map map;
		Location sanFrancisco;
		int wid = 1680;
		int hei = 1050;

		//| Marker Points & Text
		PImage baymodel;
		Location coordTL;
		Location coordBR;
		
		//| Director
		boolean PLAY = true;
		String SCENE = "INTERTITLE";
		
		//| Data
		IntertitleDynamicEarth intertitleDynamicEarth;
		EarthquakeArchive earthquakeArchive;
		
		public void setup() 
		{
			//| Start
			background(0);
			size(wid, hei);
			//noCursor();
			frameRate(60);

			//| Model Boundaries & Image
			coordTL = new Location(38.223078f,-122.915039f); //| Rougly for now
			coordBR = new Location(37.3647f,-121.556854f); //| Rougly for now
			
			//| San Francisco
		    sanFrancisco = new Location(37.807614f, -122.209167f);
		    map = new Map(this, 0, 0, width, height, new Microsoft.AerialProvider());
			map.zoomAndPanTo(sanFrancisco, 10);
//			MapUtils.createDefaultEventDispatcher(this, map);
			
			//| Position Projection to top Left Coordinate
			float[] tl = map.getScreenPositionFromLocation(coordTL);
			Location nl = map.getLocationFromScreenPosition(wid/2+tl[0], hei/2+tl[1]);
			map.zoomAndPanTo(nl, 10);

			//| Intertitle
			intertitleDynamicEarth = new IntertitleDynamicEarth(this);
			intertitleDynamicEarth.setup(map, wid, hei);
			intertitleDynamicEarth.firstcall();
			
			//| Earthquake feed from USGS
			earthquakeArchive = new EarthquakeArchive(this);
			earthquakeArchive.setup(map, wid, hei);
		}
		
		public void reset()
		{
			if(SCENE.equals("INTERTITLE")) intertitleDynamicEarth.kill();
			if(SCENE.equals("EARTHQUAKE ARCHIVE")) earthquakeArchive.kill();
			SCENE = "INTERTITLE";	
		}
		
		public void update()
		{
			String stat = "null";
			
			if(SCENE.equals("INTERTITLE")) {
				stat = intertitleDynamicEarth.status();
				if(stat.equals("OFF")) intertitleDynamicEarth.start();
				if(stat.equals("DONE")) {
					SCENE = "EARTHQUAKE ARCHIVE";
					intertitleDynamicEarth.off();
				}

			} else if(SCENE.equals("EARTHQUAKE ARCHIVE")) {
				stat = earthquakeArchive.status();
				if(stat.equals("OFF")) earthquakeArchive.start();
				if(stat.equals("DONE")) {
					SCENE = "INTERTITLE";
					earthquakeArchive.off();
				}
				
			}
			
			PApplet.println(SCENE + " " + stat + " (" + frameRate + ")");		
		}

		public void draw() 
		{
			if(!PLAY) return;
			
			//| Map and Background
			background(0);
			this.renderMap();
			this.update();

			if(SCENE.equals("INTERTITLE")) {
				intertitleDynamicEarth.update();
				intertitleDynamicEarth.draw(map);
			}
			
			if(SCENE.equals("EARTHQUAKE ARCHIVE")) {
				earthquakeArchive.update();
				earthquakeArchive.draw(map);
			}
			
			this.renderBoarder();
		}
		
		public void renderMap()
		{
			//| Percentage increase for Bay Model projection
//			float[] tl = map.getScreenPositionFromLocation(coordTL);
//			float[] br = map.getScreenPositionFromLocation(coordBR);
//			float newX = tl[0];
//			float newY = tl[1];
//			float newW = br[0] - tl[0] + 190; 
//			float newH = br[1] - tl[1];
			
			map.draw();
			fill(0,0,0,100);
			rect(-1,-1,screenWidth+2,screenHeight+2);
		}
		
		public void renderBoarder()
		{
			float[] tl = map.getScreenPositionFromLocation(coordTL);
			float[] br = map.getScreenPositionFromLocation(coordBR);
			
			smooth();
			fill(255, 255, 255, 255);
			stroke(170, 170, 170, 255);
			strokeWeight(1);
			ellipse(tl[0], tl[1], 3, 3);
			ellipse(br[0], tl[1], 3, 3);
			ellipse(br[0], br[1], 3, 3);
			ellipse(tl[0], br[1], 3, 3);
			
//			noFill();
//			stroke(255);
//			strokeWeight(1);
//			rect(0,0, wid, hei);
		}

		//| Override Key Pressed
		public void keyPressed() 
		{
			/*
			if (key == ' ') {
				if(!PLAY) PLAY = true;
				else if(PLAY) PLAY = false;
				
			} else if(key == 'r') {
				this.reset();
			}
			*/
		}
		
		//| Override Main
		public static void main(String _args[]) 
		{
			PApplet.main(new String[] { "--location=0,0", "--present", "--bgcolor=#000000", "--hide-stop", DynamicEarth2.class.getName() });
		}
		
		//| adjust this value to whatever depth is actually necessary
		public final int STACK_DEPTH = 5000;
		public float[][] matrixStack = new float[STACK_DEPTH][6];
		public int matrixStackDepth;
		 
		//| this version will override the built-in version pushMatrix function
		public void pushMatrix() 
		{
			if (matrixStackDepth == 5000) {
				throw new RuntimeException("too many calls to pushMatrix()");
			}
			this.getMatrix().get(matrixStack[matrixStackDepth]);
			matrixStackDepth++;
		}
		 
		//| this version will override the built-in version popMatrix function
		public void popMatrix() 
		{
			if (matrixStackDepth == 0) {
				throw new RuntimeException("too many calls to popMatrix()" + "(or too few to pushMatrix)");
			}
			matrixStackDepth--;
			PMatrix2D m = new PMatrix2D();
			m.set(matrixStack[matrixStackDepth]);
			this.setMatrix(m);
		}
}
