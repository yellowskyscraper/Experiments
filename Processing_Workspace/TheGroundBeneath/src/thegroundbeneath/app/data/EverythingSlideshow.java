package thegroundbeneath.app.data;

import processing.core.PApplet;
import processing.core.PImage;

import thegroundbeneath.app.labels.LifelineLabel;
import thegroundbeneath.app.labels.LiquefactionLabel;
import thegroundbeneath.app.labels.PopulationLabel;

import de.fhpotsdam.unfolding.Map;
import de.fhpotsdam.unfolding.geo.Location;

import ijeoma.motion.Motion;
import ijeoma.motion.tween.Tween;

public class EverythingSlideshow {

	PApplet parent;

	//| Scene Images
	PImage baymodel;
	PImage cities;
	PImage landmarks;
	PImage compass;
	
	PImage fault_primary;
	PImage fault_names;
	
	PImage liquefaction;
	PImage[] annotationsArray;
	
	PImage population;
	
	PImage oil_gas;
	PImage electricity;
	PImage water;
	
	//| Tweens
	Tween tweenCompassIN;
	Tween tweenCompassOUT;
	Tween tweenCitiesIN;
	Tween tweenCitiesOUT;
	Tween tweenLandmarksIN;
	Tween tweenLandmarksOUT;
	Tween tweenBayModelIN;
	Tween tweenBayModelOUT;
	
	Tween tweenFaultPrimaryIN;
	Tween tweenFaultPrimaryOUT;
	Tween tweenFaultNamesIN;
	Tween tweenFaultNamesOUT;
	
	Tween tweenLiquefactionIN;
	Tween tweenLiquefactionOUT;
	Tween tweenAnnotationIN;
	Tween tweenAnnotationOUT;
	
	Tween tweenPopulationIN;
	Tween tweenPopulationOUT;
	
	Tween tweenOilGasIN;
	Tween tweenOilGasOUT;
	Tween tweenElectricityIN;
	Tween tweenElectricityOUT;
	Tween tweenWaterIN;
	Tween tweenWaterOUT;
	
	//| Alpha
	float alphaCompass = 0;
	float alphaCities = 0;
	float alphaLandmarks = 0;
	float alphaBayModel = 0;
	
	float alphaFaultPrimary = 0;
	float alphaFaultNames = 0;
	
	float alphaLiquefaction = 0;
	float alphaAnnotation = 0;

	float alphaPopulation = 0;
	
	float alphaOilGas = 0;
	float alphaElectricity = 0;
	float alphaWater = 0;

	//| Labels
	LiquefactionLabel labelLIQUE;
	PopulationLabel labelPOP;
	LifelineLabel labelLIFE;
	
	//| Sequence Variable
	int animating = 9;
	int timekeeper = 0;
	String STATUS = "OFF";

	int[] annotationsPause = new int[]{350,350,350,350};
	int annotation = 0;
	
	public EverythingSlideshow(PApplet p)
	{
		parent = p;
	}
	
	public void setup()
	{
		//| Main Graphics
		baymodel = parent.loadImage("data/images/bay.jpg");
		cities = parent.loadImage("data/images/main_cities.png");
		landmarks = parent.loadImage("data/images/liquefaction_landmarks.png");
		compass = parent.loadImage("data/images/main_compass.png");
		
		fault_primary = parent.loadImage("data/images/faults_primary.png");
		fault_names = parent.loadImage("data/images/faults_names.png");
		
		liquefaction = parent.loadImage("data/images/liquefaction_main.png");
		String[] a = new String[]{
				"data/images/liquefaction_an_islands.png",
				"data/images/liquefaction_an_artifical_fill.png",
				"data/images/liquefaction_an_sand.png",
				"data/images/liquefaction_an_alluvium.png" };
		annotationsArray = new PImage[a.length];
		for(int i = 0; i < a.length; i++) annotationsArray[i] = parent.loadImage(a[i]);
		
		population  = parent.loadImage("data/images/population.png");
		
		oil_gas = parent.loadImage("data/images/infrastructure_oil_gas2.png");
		electricity = parent.loadImage("data/images/infrastructure_electricity2.png");
		water = parent.loadImage("data/images/infrastructure_water2.png");
		
		//| Tweens
		Motion.setup(parent);
		tweenBayModelIN 		= new Tween(0f, 255f, 30f, 10f);
		tweenBayModelOUT 		= new Tween(255f, 0f, 20f, 17f);
		tweenCompassIN 			= new Tween(0f, 255f, 20f, 3f);
		tweenCompassOUT 		= new Tween(255f, 0f, 20f, 13f);
		tweenCitiesIN 			= new Tween(0f, 255f, 20f, 10f);
		tweenCitiesOUT 			= new Tween(255f, 0f, 20f, 13f);
		tweenLandmarksIN 		= new Tween(0f, 255f, 30f, 20f);
		tweenLandmarksOUT 		= new Tween(255f, 0f, 20f);
		
		tweenFaultPrimaryIN 	= new Tween(0f, 255f, 20f);
		tweenFaultPrimaryOUT 	= new Tween(255f, 0f, 20f);
		tweenFaultNamesIN 		= new Tween(0f, 255f, 20f, 5f);
		tweenFaultNamesOUT 		= new Tween(255f, 0f, 20f);
		
		tweenLiquefactionIN 	= new Tween(0f, 255f, 30f);
		tweenLiquefactionOUT 	= new Tween(255f, 0f, 30f);
		tweenAnnotationIN 		= new Tween(0f, 255f, 10f);
		tweenAnnotationOUT 		= new Tween(255f, 0f, 20f, 8f);

		tweenPopulationIN 		= new Tween(0f, 255f, 30f, 40f);
		tweenPopulationOUT 		= new Tween(255f, 0f, 20f);

		tweenOilGasIN 			= new Tween(0f, 255f, 20f, 70f);
		tweenOilGasOUT 			= new Tween(255f, 0f, 20f);
		tweenElectricityIN 		= new Tween(0f, 255f, 20f, 270f);
		tweenElectricityOUT 	= new Tween(255f, 0f, 20f);
		tweenWaterIN 			= new Tween(0f, 255f, 20f, 470f);
		tweenWaterOUT 			= new Tween(255f, 0f, 20f);
		
		//| Labels
		labelLIQUE = new LiquefactionLabel(parent);
		labelLIQUE.setup(); 
		
		labelPOP = new PopulationLabel(parent);
		labelPOP.setup(); 
		
		labelLIFE = new LifelineLabel(parent);
		labelLIFE.setup(); 
	}
	
	public void start()
	{
		STATUS = "ANIMATING IN";
		tweenBayModelIN.play();
		tweenCompassIN.play();
		tweenFaultPrimaryIN.play();
		tweenFaultNamesIN.play();
		tweenCitiesIN.play();
	}
	
	public void step1()
	{
		STATUS = "LIQUEFACTION START";
		timekeeper = 0;
		tweenBayModelIN.stop();
		tweenCompassIN.stop();
		tweenFaultPrimaryIN.stop();
		tweenCitiesIN.stop();
		tweenFaultNamesIN.stop();
		tweenFaultNamesOUT.play();
		
		labelLIQUE.open();
	}
	
	public void step2()
	{
		STATUS = "LIQUEFACTION UP";
		timekeeper = 0;
		tweenFaultNamesOUT.stop();
		tweenLiquefactionIN.play();
		tweenLandmarksIN.play();
	}
	
	public void step3()
	{
		STATUS = "LIQUEFACTION ANNOTAIONS"; 
		timekeeper = 0;
		tweenLiquefactionIN.stop();
		tweenLandmarksIN.stop();
		tweenCitiesOUT.play();
		tweenLandmarksOUT.play();
	}
	
	public void step4()
	{
		STATUS = "ANNOTAIONS IN"; 
		timekeeper = 0;
		tweenAnnotationIN.play();
		tweenAnnotationOUT.stop();
	}
	
	public void step5()
	{
		STATUS = "ANNOTAIONS OUT"; 
		timekeeper = 0;
		tweenAnnotationIN.stop();
		tweenAnnotationOUT.play();
	}
	
	public void step6()
	{
		STATUS = "POPULATION IN"; 
		timekeeper = 0;
		tweenAnnotationIN.stop();
		tweenAnnotationOUT.stop();
		tweenCitiesIN.play();
		tweenLandmarksIN.play();
		tweenPopulationIN.play();
		
		labelLIQUE.close();
		labelPOP.open();
	}
	
	public void step7()
	{
		STATUS = "LIQUEFACTION OUT"; 
		timekeeper = 0;
		tweenCitiesIN.stop();
		tweenLandmarksIN.stop();
		tweenPopulationIN.stop();
		tweenLiquefactionOUT.play();
	}
	
	public void step8()
	{
		STATUS = "POPULATION OUT"; 
		timekeeper = 0;
		tweenLiquefactionOUT.stop();
		tweenLandmarksOUT.play();
		tweenPopulationOUT.play();
		labelPOP.close();
	}
	
	public void step9()
	{
		STATUS = "LIFELINES IN"; 
		timekeeper = 0;
		tweenLandmarksOUT.stop();
		tweenPopulationOUT.stop();
		tweenOilGasIN.play();
		tweenElectricityIN.play();
		tweenWaterIN.play();
		
		labelLIFE.open();
	}
	
	public void close()
	{
		STATUS = "ANIMATING OUT"; 
		timekeeper = 0;
		tweenOilGasIN.stop();
		tweenElectricityIN.stop();
		tweenWaterIN.stop();

		tweenOilGasOUT.play();
		tweenElectricityOUT.play();
		tweenWaterOUT.play();
		tweenBayModelOUT.play();
		tweenCompassOUT.play();
		tweenFaultPrimaryOUT.play();
		tweenCitiesOUT.play();
		
		labelLIFE.close();
	}
	public void closed()
	{
		STATUS = "DONE";
		timekeeper = 0;
		annotation = 0;
	}
	
	public void update()
	{
		if(STATUS.equals("OFF")) {
			
		} else if(STATUS.equals("ANIMATING IN")) {
			alphaCompass = tweenCompassIN.getPosition();
			alphaBayModel = tweenBayModelIN.getPosition();
			alphaFaultPrimary = tweenFaultPrimaryIN.getPosition();
			alphaFaultNames = tweenFaultNamesIN.getPosition();
			alphaCities = tweenCitiesIN.getPosition();
			if(alphaCompass == 255 && alphaBayModel == 255 && alphaFaultPrimary == 255 && alphaFaultNames == 255 && alphaCities == 255) timekeeper += 1;
			if(timekeeper > 300) step1();
			
		} else if(STATUS.equals("LIQUEFACTION START")) {
			alphaFaultNames = tweenFaultNamesOUT.getPosition();
			if(labelLIQUE.opened() == true && alphaFaultNames == 0) timekeeper += 1;
			if(timekeeper > 100) this.step2();
			
		} else if(STATUS.equals("LIQUEFACTION UP")) {
			alphaLiquefaction = tweenLiquefactionIN.getPosition();
			alphaLandmarks = tweenLandmarksIN.getPosition();
			if(alphaLiquefaction == 255 && alphaLandmarks == 255) timekeeper += 1;
			if(timekeeper > 1000) this.step3();
			
		} else if(STATUS.equals("LIQUEFACTION ANNOTAIONS")) {
			alphaCities = tweenCitiesOUT.getPosition();
			alphaLandmarks = tweenLandmarksOUT.getPosition();
			if(alphaCities == 0 && alphaLandmarks == 0) this.step4();
			
		} else if(STATUS.equals("ANNOTAIONS IN")) {
			alphaAnnotation = tweenAnnotationIN.getPosition();
			if(alphaAnnotation == 255) timekeeper += 1;
			if(timekeeper > annotationsPause[annotation]) this.step5();
			
		} else if(STATUS.equals("ANNOTAIONS OUT")) {
			timekeeper += 1;
			alphaAnnotation = tweenAnnotationOUT.getPosition();
			if(alphaAnnotation == 0) {
				annotation += 1;
				if(annotation < annotationsArray.length - 1) this.step4();
				else this.step6();
			}
			
		} else if(STATUS.equals("POPULATION IN")) {
			alphaCities = tweenCitiesIN.getPosition();
			alphaLandmarks = tweenLandmarksIN.getPosition();
			alphaPopulation = tweenPopulationIN.getPosition();
			if(alphaCities == 255 && alphaLandmarks == 255 && alphaPopulation == 255) timekeeper += 1;
			if(timekeeper > 100) this.step7();
			
		} else if(STATUS.equals("LIQUEFACTION OUT")) {
			alphaLiquefaction = tweenLiquefactionOUT.getPosition();
			if(labelPOP.opened() == true && labelLIQUE.closed() == true && alphaLiquefaction == 0) timekeeper += 1;
			if(timekeeper > 800) this.step8();
			
		} else if(STATUS.equals("POPULATION OUT")) {
			alphaLandmarks = tweenLandmarksOUT.getPosition();
			alphaPopulation = tweenPopulationOUT.getPosition();
			if(labelPOP.closed() == true && alphaLandmarks == 0 && alphaPopulation == 0) timekeeper += 1;
			if(timekeeper > 20) this.step9();
			
		} else if(STATUS.equals("LIFELINES IN")) {
			alphaOilGas = tweenOilGasIN.getPosition();
			alphaElectricity = tweenElectricityIN.getPosition();
			alphaWater = tweenWaterIN.getPosition();
			if(labelLIFE.opened() == true && alphaOilGas == 255 && alphaElectricity == 255 && alphaWater == 255) timekeeper += 1;
			if(timekeeper > 800) this.close();
			
		} else if(STATUS.equals("ANIMATING OUT")) {
			alphaOilGas = tweenOilGasOUT.getPosition();
			alphaElectricity = tweenElectricityOUT.getPosition();
			alphaWater = tweenWaterOUT.getPosition();
			alphaBayModel = tweenBayModelOUT.getPosition();
			alphaCompass = tweenCompassOUT.getPosition();
			alphaFaultPrimary = tweenFaultPrimaryOUT.getPosition();
			alphaCities = tweenCitiesOUT.getPosition();
			if(labelLIFE.closed() == true && alphaOilGas == 0 && alphaElectricity == 0 && alphaWater == 0 && alphaBayModel == 0
					&& alphaCompass == 0 && alphaFaultPrimary == 0 && alphaCities == 0) timekeeper += 1;
			if(timekeeper > 100) this.closed();
		}
		
		labelLIQUE.update();
		labelPOP.update();
		labelLIFE.update();
	}
	
	public void draw(Map m)
	{
//		float[] tl = m.getScreenPositionFromLocation(new Location(38.339f, -122.796f));	
//		int xpos = Math.round(tl[0]);
//		int ypos = Math.round(tl[0]);
		
		//| Main Graphics
		parent.tint(255, alphaBayModel);
		parent.image(baymodel, 0, 0, 1050, 1050);
		
		//| Liquefaction & Population
		parent.tint(255, alphaLiquefaction);
		parent.image(liquefaction, 0, 0, 1050, 1050);

		parent.tint(255, alphaPopulation);
		parent.image(population, 0, 0, 1050, 1050);
		
		//| main Graphics
		parent.tint(255, alphaFaultPrimary);
		parent.image(fault_primary, 0, 0, 1050, 1050);
		
		//| Liquefaction Annotation
		parent.tint(255, alphaAnnotation);
		parent.image(annotationsArray[annotation], 0, 0, 1050, 1050);
		
		//| Lifelines
		parent.tint(255, alphaOilGas);
		parent.image(oil_gas, 0, 0, 1050, 1050);

		parent.tint(255, alphaElectricity);
		parent.image(electricity, 0, 0, 1050, 1050);

		parent.tint(255, alphaWater);
		parent.image(water, 0, 0, 1050, 1050);
		parent.tint(255);
		
		//| Main graphics
		parent.tint(255, alphaCities);
		parent.image(cities, 0, 0, 1050, 1050);
		
		parent.tint(255, alphaLandmarks);
		parent.image(landmarks, 0, 0, 1050, 1050);
		
		parent.tint(255, alphaFaultNames);
		parent.image(fault_names, 0, 0, 1050, 1050);
		
		parent.tint(255, alphaCompass);
		parent.image(compass, 0, 0, 1050, 1050);
		
		//| Label
		labelLIQUE.draw(m);
		labelPOP.draw(m);
		labelLIFE.draw(m);
	}
	
	public void kill()
	{
		STATUS = "OFF";	
		
		timekeeper = 0;
		annotation = 0;
		
		tweenCompassIN.stop();
		tweenCompassOUT.stop();
		tweenCitiesIN.stop();
		tweenCitiesOUT.stop();
		tweenLandmarksIN.stop();
		tweenLandmarksOUT.stop();
		tweenBayModelIN.stop();
		tweenBayModelOUT.stop();
		tweenFaultPrimaryIN.stop();
		tweenFaultPrimaryOUT.stop();
		tweenFaultNamesIN.stop();
		tweenFaultNamesOUT.stop();
		tweenLiquefactionIN.stop();
		tweenLiquefactionOUT.stop();
		tweenAnnotationIN.stop();
		tweenAnnotationOUT.stop();
		tweenPopulationIN.stop();
		tweenPopulationOUT.stop();
		tweenOilGasIN.stop();
		tweenOilGasOUT.stop();
		tweenElectricityIN.stop();
		tweenElectricityOUT.stop();
		tweenWaterIN.stop();
		tweenWaterOUT.stop();
		
		alphaCompass = 0;
		alphaCities = 0;
		alphaLandmarks = 0;
		alphaBayModel = 0;
		alphaFaultPrimary = 0;
		alphaFaultNames = 0;
		alphaLiquefaction = 0;
		alphaAnnotation = 0;
		alphaPopulation = 0;
		alphaOilGas = 0;
		alphaElectricity = 0;
		alphaWater = 0;
		
		labelLIQUE.kill();
		labelPOP.kill();
		labelLIFE.kill();
	}
	
	public void off() 
	{
		STATUS = "OFF";
	}

	public String status() 
	{
		return STATUS;
	}
}
