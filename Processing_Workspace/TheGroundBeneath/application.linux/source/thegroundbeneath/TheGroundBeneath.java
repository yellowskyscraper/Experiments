package thegroundbeneath;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PMatrix2D;
import thegroundbeneath.app.data.EverythingSlideshow;
import thegroundbeneath.app.data.IntertitleTheGroundBeneath;
import thegroundbeneath.app.data.LifelineAnimation;
import thegroundbeneath.app.data.LiquefactionAnimation;
import thegroundbeneath.app.data.PopulationDensity;

import de.fhpotsdam.unfolding.Map;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.providers.Microsoft;


public class TheGroundBeneath extends PApplet 
{
	private static final long serialVersionUID = 1L;
	
	//| Map And Label Text
	Map map;
	Location sanFrancisco;
	int wid = 1400;
	int hei = 1051;

	//| Marker Points & Text
	PImage baymodel;
	Location coordTL;
	Location coordBR;
	
	//| Director
	boolean PLAY = true;
	String SCENE = "INTERTITLE";
	
	//| Data
	IntertitleTheGroundBeneath intertitleTheGroundBeneath;
	EverythingSlideshow allEverything;
	PopulationDensity populationDensity;
	LiquefactionAnimation liquefactionAni;
	LifelineAnimation infrastructuralAni;
	
	public void setup() 
	{
		//| Start
		background(0);
		size(1400, 1051);

		//| Model Boundaries & Image
		baymodel = loadImage("data/images/bay.jpg");
		coordTL = new Location(38.339f, -122.796f);
		coordBR = new Location(37.342f, -121.781f);
		
		//| San Francisco
	    sanFrancisco = new Location(37.85316995894978f, -121.95510864257812f);
	    map = new Map(this, 0, 0, width, height, new Microsoft.AerialProvider());
		map.zoomAndPanTo(sanFrancisco, 10);
		
		//| Position Projection to top Left Coordinate
		float[] tl = map.getScreenPositionFromLocation(coordTL);
		Location nl = map.getLocationFromScreenPosition(wid/2+tl[0], hei/2+tl[1]);
		map.zoomAndPanTo(nl, 10);

		//| Intertitle
		intertitleTheGroundBeneath = new IntertitleTheGroundBeneath(this);
		intertitleTheGroundBeneath.setup(map, wid, hei);
		intertitleTheGroundBeneath.firstcall();
		
		//| All Everything
		allEverything = new EverythingSlideshow(this);
		allEverything.setup();
		
		/*
		//| Population Density
		populationDensity = new PopulationDensity(this);
		populationDensity.setup(map, wid, hei);
		
		//| Liquefation Slideshow
		liquefactionAni = new LiquefactionAnimation(this);
		liquefactionAni.setup();

		//| Lifeline Slideshow
		infrastructuralAni = new LifelineAnimation(this);
		infrastructuralAni.setup();
		*/
	}
	
	public void reset()
	{
		if(SCENE.equals("INTERTITLE")) intertitleTheGroundBeneath.kill();
		if(SCENE.equals("ALL EVERYTHING")) allEverything.kill();
		SCENE = "INTERTITLE";
	}
	
	public void update()
	{
		String stat = "null";
		
		if(SCENE.equals("INTERTITLE")) {
			stat = intertitleTheGroundBeneath.status();
			if(stat.equals("OFF")) intertitleTheGroundBeneath.start();
			if(stat.equals("DONE")) {
				SCENE = "ALL EVERYTHING";
				intertitleTheGroundBeneath.off();
			}

		} else if(SCENE.equals("ALL EVERYTHING")) {
			stat = allEverything.status();
			if(stat.equals("OFF")) allEverything.start();
			if(stat.equals("DONE")) {
				SCENE = "INTERTITLE";
				allEverything.off();
			}
		}
		
		/* 		
 		else if(SCENE.equals("POPULATION")) {
			stat = populationDensity.status();
			if(stat.equals("OFF")) {
				populationDensity.start();
				liquefactionAni.uplandmarks();
			}
			if(stat.equals("DONE")) {
				SCENE = "LIQUEFACTION";
				populationDensity.off();
			}
			
		} else if(SCENE.equals("LIQUEFACTION")) {
			stat = liquefactionAni.status();
			if(stat.equals("OFF")) liquefactionAni.start();
			if(stat.equals("DONE")) {
				SCENE = "INFRASTRUCTURE";
				liquefactionAni.off();
			}
			
		} else if(SCENE.equals("INFRASTRUCTURE")) {
			stat = infrastructuralAni.status();
			if(stat.equals("OFF")) infrastructuralAni.start();
			if(stat.equals("DONE")) {
				SCENE = "INTERTITLE";
				infrastructuralAni.off();
			}
		}
		*/
		
		PApplet.println(SCENE + " " + stat);
	}

	public void draw() 
	{			
		if(!PLAY) return;
		
		//| Map and Background
		background(0);
		noCursor();
		this.renderMap();
		
		//| Sequence
		this.update();
		
		if(SCENE.equals("INTERTITLE")) {
			intertitleTheGroundBeneath.update();
			intertitleTheGroundBeneath.draw(map);
		}
		
		if(SCENE.equals("ALL EVERYTHING")) {
			allEverything.update();
			allEverything.draw(map);
		}
		/*
		if(SCENE.equals("POPULATION")) {
			populationDensity.update();
			populationDensity.draw(map);
			
			liquefactionAni.update();
			liquefactionAni.draw(map);
		}
		
		if(SCENE.equals("LIQUEFACTION")) {
			if(populationDensity.isOff() == false) {
				populationDensity.update();
				populationDensity.draw(map);
			}
			
			liquefactionAni.update();
			liquefactionAni.draw(map);
		}
		
		if(SCENE.equals("INFRASTRUCTURE")) {
			infrastructuralAni.update();
			infrastructuralAni.draw(map);
		}
		*/
		this.renderBoarder();
	}
	
	public void renderMap()
	{
		/*
		//map.draw();
		fill(0,0,0,100);
		rect(-1,-1,screenWidth+2,screenHeight+2);
		
		//| Percentage increase for Bay Model projection
		float[] tl = map.getScreenPositionFromLocation(coordTL);
		float[] br = map.getScreenPositionFromLocation(coordBR);
		float newX = tl[0];
		float newY = tl[1];
		float newW = br[0] - tl[0] + 190; 
		float newH = br[1] - tl[1];
		
		noSmooth();
		image(baymodel, newX, newY, newW, newH); //| Zoom 10 Scale
		image(baymodel, newX, newY, 1051, 1051); //| Bay Model Full Scale
		*/
	}

	public void renderBoarder()
	{
		/*
		noFill();
		stroke(255);
		strokeWeight(1);
		rect(0,0, 1050, 1050);
		*/
	}
	
	//| Override Key Pressed
	public void keyPressed() 
	{
		if (key == ' ') {
			if(!PLAY) PLAY = true;
			else if(PLAY) PLAY = false;
			
		} else if(key == 'r') {
			this.reset();
		}
	}
	
	//| Override Main
	public static void main(String _args[]) 
	{
		PApplet.main(new String[] { "--location=0,0", "--present", "--bgcolor=#000000", "--hide-stop", TheGroundBeneath.class.getName() });
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
