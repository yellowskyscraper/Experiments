package thegroundbeneath.app.data;

import processing.core.PApplet;
import processing.core.PImage;

import thegroundbeneath.app.labels.LifelineLabel;

import de.fhpotsdam.unfolding.Map;
import de.fhpotsdam.unfolding.geo.Location;

import ijeoma.motion.Motion;
import ijeoma.motion.tween.Tween;

public class LifelineAnimation {

	PApplet parent;

	//| Graphics
	LifelineLabel label;

	//| Scene Images
	PImage mainFaultLinesImage;
	PImage mainFaultNamesImage;
	PImage oilGasImage;
	PImage electricityImage;
	PImage waterImage;
	
	PImage[] slideArray;
	int[] slidePauseArray;
	int slide = 0;

	//| Sequence Variable
	int animating = 0;
	int timekeeper = 0;
	String STATUS = "OFF";
	
	float alphaFaultLines = 0;
	float alphaOilGas = 0;
	float alphaElectricity = 0;
	float alphaWater = 0;
	
	Tween tweenFaultsIN;
	Tween tweenFaultsOUT;
	
	Tween tweenOilGasIN;
	Tween tweenElectricityIN;
	Tween tweenWaterIN;
	Tween tweenAllOUT;
	
	public LifelineAnimation(PApplet p)
	{
		parent = p;
	}
	
	public void setup()
	{
		//| Fault Images
		mainFaultLinesImage = parent.loadImage("data/images/faults_primary.png");

		oilGasImage = parent.loadImage("data/images/infrastructure_oil_gas.png");
		electricityImage = parent.loadImage("data/images/infrastructure_electricity.png");
		waterImage = parent.loadImage("data/images/infrastructure_water.png");

		//| Tween Test
		Motion.setup(parent);
		tweenFaultsIN = new Tween(0f, 255f, 10f);
		tweenFaultsOUT = new Tween(255f, 0f, 20f, 8f);
		
		tweenOilGasIN = new Tween(0f, 255f, 10f, 20f);
		tweenElectricityIN = new Tween(0f, 255f, 10f, 170f);
		tweenWaterIN = new Tween(0f, 255f, 10f, 270f);
		
		tweenAllOUT = new Tween(255f, 0f, 10f);
		
		//| Faults Label
		label = new LifelineLabel(parent);
		label.setup(); 
	}
	
	public void start()
	{
		if(animating == 0) {
			label.open();
			tweenFaultsIN.play();
			STATUS = "ANIMATING IN";
		}
		animating = 1;
	}
	
	public void beginAnnotations()
	{
		STATUS = "ON";
		animating = 2;
		tweenFaultsIN.stop();
		
		tweenOilGasIN.play();
		tweenElectricityIN.play();
		tweenWaterIN.play();
	}

	public void closeLiquefaction()
	{
		timekeeper = 0;
		animating = 4;	

		tweenOilGasIN.stop();
		tweenElectricityIN.stop();
		tweenWaterIN.stop();
		
		tweenFaultsOUT.play();
		tweenAllOUT.play();
		label.close();	
	}
	
	public void update()
	{
		switch(animating){
		
			case 1:
			alphaFaultLines = tweenFaultsIN.getPosition();
			if(alphaFaultLines == 255) this.beginAnnotations();
			break;
			
			case 2:
			alphaOilGas = tweenOilGasIN.getPosition();
			alphaElectricity = tweenElectricityIN.getPosition();
			alphaWater = tweenWaterIN.getPosition();
			if(alphaOilGas == 255 && alphaElectricity == 255 && alphaWater == 255) animating = 3;
			break;
			
			case 3:
			timekeeper += 1;
			if(timekeeper > 500) this.closeLiquefaction();
			break;
			
			case 4:
			alphaFaultLines = tweenFaultsOUT.getPosition();
			alphaOilGas = alphaElectricity = alphaWater = tweenAllOUT.getPosition();
			if(alphaWater == 0 && label.closed() == true) STATUS = "DONE";
			break;
		}
		
		label.update();
	}
	
	public void draw(Map m)
	{
		float[] tl = m.getScreenPositionFromLocation(new Location(38.339f, -122.796f));	
		int xpos = Math.round(tl[0]);
		int ypos = Math.round(tl[0]);
		
		//| Faults
		parent.tint(255, alphaFaultLines);
		parent.image(mainFaultLinesImage, xpos, ypos, 1051, 1051);
		
		parent.tint(255, alphaOilGas);
		parent.image(oilGasImage, xpos, ypos, 1051, 1051);

		parent.tint(255, alphaElectricity);
		parent.image(electricityImage, xpos, ypos, 1051, 1051);

		parent.tint(255, alphaWater);
		parent.image(waterImage, xpos, ypos, 1051, 1051);
		

		//| Label
		parent.tint(255);
		label.draw(m);
	}
	
	public void off() 
	{
		STATUS = "OFF";
		animating = 0;
		timekeeper = 0;

		tweenFaultsOUT.stop();
		tweenAllOUT.stop();
		
		alphaFaultLines = 0;
		alphaOilGas = 0;
		alphaElectricity = 0;
		alphaWater = 0;
	}

	public String status() 
	{
		return STATUS;
	}
}
