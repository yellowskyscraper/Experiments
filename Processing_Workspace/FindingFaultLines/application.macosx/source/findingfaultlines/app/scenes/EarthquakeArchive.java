package findingfaultlines.app.scenes;

import processing.core.PApplet;
import processing.core.PImage;
import processing.xml.XMLElement;

import de.fhpotsdam.unfolding.Map;
import de.fhpotsdam.unfolding.geo.Location;
import findingfaultlines.app.elements.LabelEarthquakeArchive;
import findingfaultlines.app.elements.Quake;

import ijeoma.motion.Motion;
import ijeoma.motion.tween.Tween;
import ijeoma.motion.tween.TweenParallel;

public class EarthquakeArchive 
{
	PApplet parent;
	
	boolean BAYMODEL = true;
	int wid = (BAYMODEL) ? 1500 : 1680;
	int hei = (BAYMODEL) ? 1200 : 1050;
	
	//| Data
	Quake[] quakeArchive;
	int archiveLength = 0;
	
	LabelEarthquakeArchive label;
	PImage timedial;
	PImage citiesLandmarks;
	PImage citiesSecondary;
	PImage citiesPrimary;
	PImage faultlinesNames;
	PImage faultlinesMajor;
	PImage faultlinesMinor;
	
	//| Scrub Variables
	float scrubber = 0;
	double rotaryValue = 0;
	float rotation = 0.0f;
	TweenParallel pTweenElementsIN;
	TweenParallel pTweenElementsDIM;
	TweenParallel pTweenElementsOUT;
	TweenParallel pTweenFaultsIN;
	TweenParallel pTweenFaultsOUT;
	float alphaDial = 0;
	float alphaLandmarks = 0;
	float alphaSecondary = 0;
	float alphaPrimary = 0;
	float alphaNamesFaultLines = 0;
	float alphaMajorFaultLines = 0;
	float alphaMinorFaultLines = 0;
	boolean faultlines = false;
	boolean faulttransitioning = false;
	
	//| Sequencing
	String STATUS = "OFF";
	int timekeeper = 0;
	int finalTimekeeper = 0;
	
	public EarthquakeArchive(PApplet p)
	{
		parent = p;
	}
	
	public void setup(Map m, float w, float h)
	{
		//| Earthquake Data
		XMLElement xml = new XMLElement(parent, "data/earthquakearchive/sample50mi1973-2012June13.xml");
//		XMLElement xml = new XMLElement(parent, "data/earthquakearchive/sample50mi2011.xml");
		int totallength = xml.getChildCount();
		
		PApplet.println("Earthquake Timeline: CROPPING " + totallength);
		for(int j = 0; j < totallength; j++)
		{ 
			float lt = Float.valueOf(xml.getChild(j).getChild(2).getContent()).floatValue();
		    float ln = Float.valueOf(xml.getChild(j).getChild(3).getContent()).floatValue();
			Location c = new Location(lt, ln);
			float[] p = m.getScreenPositionFromLocation(c);
			
			if(p[0] > 0 && p[0] < 985 && p[1] > 0 && p[1] < 788) archiveLength += 1;
		}

		//| Parse USGS Compiled Earthquakes
		quakeArchive = new Quake[archiveLength];
		float[] magnitudeArchive = new float[archiveLength];
		int arrayCount = 0;
		
		PApplet.println("Earthquake Timeline: PARSING " + totallength + " to " + archiveLength);
		for(int i = 0; i < totallength; i ++)
		{
			String name = xml.getChild(i).getChild(0).getContent();
			String description = xml.getChild(i).getChild(1).getContent();
			float lat = Float.valueOf(xml.getChild(i).getChild(2).getContent()).floatValue();
		    float lon = Float.valueOf(xml.getChild(i).getChild(3).getContent()).floatValue();

			Location coord = new Location(lat, lon);
			float[] position = m.getScreenPositionFromLocation(coord);
			
			if(position[0] > 0 && position[0] < 985 && position[1] > 0 && position[1] < 788) {
				Quake b = new Quake(parent);
				b.setup(name, description, lat, lon);
				
				quakeArchive[arrayCount] = b;
				magnitudeArchive[arrayCount] = b.getMagnitude();
				
				arrayCount += 1;
			}
		}
		PApplet.println("Earthquake Timeline: DONE LOADING " + quakeArchive.length);

		//| Dial and Fault Liens
		citiesLandmarks = parent.loadImage("data/images/cities_landmarks.png");
		citiesSecondary = parent.loadImage("data/images/cities_secondaries.png");
		citiesPrimary = parent.loadImage("data/images/cities_primaries.png");
		
//		timedial = parent.loadImage("data/images/dialL.png");
		
		faultlinesNames = parent.loadImage("data/images/faultlines_names.png");
		faultlinesMajor = parent.loadImage("data/images/faultlines_major.png");
		faultlinesMinor = parent.loadImage("data/images/faultlines_minor.png");
		
		//| Animaion Tween
		Motion.setup(parent);
		pTweenElementsIN = new TweenParallel();
		pTweenElementsIN.addChild(new Tween("LandmarksIN", 0f, 255f, 33f, 7f));
		pTweenElementsIN.addChild(new Tween("SecondariesIN", 0f, 255f, 27f, 13f));
		pTweenElementsIN.addChild(new Tween("PrimariesIN", 0f, 255f, 23f, 17f));
		pTweenElementsIN.addChild(new Tween("DialIN", 0f, 255f, 15f, 23f));
		
		pTweenElementsDIM = new TweenParallel();
		pTweenElementsDIM.addChild(new Tween("LandmarksDIM", 255f, 0f, 4f));
		pTweenElementsDIM.addChild(new Tween("SecondariesDIM", 255f, 0f, 6f));
		pTweenElementsDIM.addChild(new Tween("PrimariesDIM", 255f, 0f, 8f));

		pTweenElementsOUT = new TweenParallel();
		pTweenElementsOUT.addChild(new Tween("LandmarksOUT", 255f, 0f, 20f, 5f));
		pTweenElementsOUT.addChild(new Tween("SecondariesOUT", 255f, 0f, 20f, 10f));
		pTweenElementsOUT.addChild(new Tween("PrimariesOUT", 255f, 0f, 20f, 15f));
		pTweenElementsOUT.addChild(new Tween("DialOUT", 255f, 0f, 20f));
		
		pTweenFaultsIN = new TweenParallel();
		pTweenFaultsIN.addChild(new Tween("NamesIN", 0f, 255f, 17f, 10f));
		pTweenFaultsIN.addChild(new Tween("MajorIN", 0f, 255f, 15f, 5f));
		pTweenFaultsIN.addChild(new Tween("MinorIN", 0f, 100f, 20f, 15f));
		
		pTweenFaultsOUT = new TweenParallel();
		pTweenFaultsOUT.addChild(new Tween("NamesOUT", 255f, 0f, 13f));
		pTweenFaultsOUT.addChild(new Tween("MajorOUT", 255f, 0f, 17f, 5f));
		pTweenFaultsOUT.addChild(new Tween("MinorOUT", 100f, 0f, 10f));
		
		//| Time Line Label
		label = new LabelEarthquakeArchive(parent);
		label.setup(magnitudeArchive);
	}
		
	//| Update Sequence, Called Once
	public void start()
	{
		STATUS = "ANIMATING IN";
		pTweenElementsIN.play();
		label.open();
	}
	
	public void step1()
	{
		STATUS = "ON";
		timekeeper = 0;
	}
	
	public void step2()
	{
		STATUS = "FAULT LINES FINAL";
		timekeeper = 0;
		
		pTweenFaultsIN.seek(0);
		pTweenFaultsIN.play();
		
		pTweenElementsOUT.play();
		label.close();
	}
	
	public void close()
	{
		STATUS = "ANIMATING OUT";
		timekeeper = 0;
		for (int i = 0; i < archiveLength; i++) quakeArchive[i].close();
		pTweenFaultsOUT.play();
	}

	public void closed()
	{
		STATUS = "DONE";
	}
	
	public String status()
	{
		return STATUS;
	}	
	
	public void update()
	{		
		if(STATUS.equals("OFF")) {
			
		} else if(STATUS.equals("ANIMATING IN")) {
			alphaLandmarks = pTweenElementsIN.getTween("LandmarksIN").getPosition();
			alphaSecondary = pTweenElementsIN.getTween("SecondariesIN").getPosition();
			alphaPrimary = pTweenElementsIN.getTween("PrimariesIN").getPosition();
			alphaDial = pTweenElementsIN.getTween("DialIN").getPosition();
			label.update(quakeArchive[Math.round(scrubber)].getDate(), quakeArchive[Math.round(scrubber)].getMagnitude());
			if(label.isOpened() && alphaDial == 255) step1();
			
		} else if(STATUS.equals("ON")) {
			this.manageRotaryInflux();
			
			if(faultlines == true && faulttransitioning == true) {
				alphaNamesFaultLines = pTweenFaultsIN.getTween("NamesIN").getPosition();
				alphaMajorFaultLines = pTweenFaultsIN.getTween("MajorIN").getPosition();
				alphaMinorFaultLines = pTweenFaultsIN.getTween("MinorIN").getPosition();
				alphaLandmarks = alphaSecondary = alphaPrimary = pTweenElementsDIM.getTween("LandmarksDIM").getPosition();
				if(alphaMinorFaultLines == 255) faulttransitioning = false;
				
			} else if(faultlines == false && faulttransitioning == true) {
				alphaNamesFaultLines = pTweenFaultsOUT.getTween("NamesOUT").getPosition();
				alphaMajorFaultLines = pTweenFaultsOUT.getTween("MajorOUT").getPosition();
				alphaMinorFaultLines = pTweenFaultsOUT.getTween("MinorOUT").getPosition();
				alphaLandmarks = pTweenElementsIN.getTween("LandmarksIN").getPosition();
				alphaSecondary = pTweenElementsIN.getTween("SecondariesIN").getPosition();
				alphaPrimary = pTweenElementsIN.getTween("PrimariesIN").getPosition();
				if(alphaPrimary == 255) faulttransitioning = false;
			}
			
			label.scrubber(scrubber);
			label.update(quakeArchive[Math.round(scrubber)].getDate(), quakeArchive[Math.round(scrubber)].getMagnitude());
			
		} else if(STATUS.equals("FAULT LINES FINAL")) {
			if(faultlines == false) {
				alphaNamesFaultLines = pTweenFaultsIN.getTween("NamesIN").getPosition();
				alphaMajorFaultLines = pTweenFaultsIN.getTween("MajorIN").getPosition();
				alphaMinorFaultLines = pTweenFaultsIN.getTween("MinorIN").getPosition();

				alphaLandmarks = pTweenElementsOUT.getTween("LandmarksOUT").getPosition();
				alphaSecondary = pTweenElementsOUT.getTween("SecondariesOUT").getPosition();
				alphaPrimary = pTweenElementsOUT.getTween("PrimariesOUT").getPosition();
			}
			
			alphaDial = pTweenElementsOUT.getTween("DialOUT").getPosition();
			
			label.scrubber(scrubber);
			label.update(quakeArchive[Math.round(scrubber)].getDate(), quakeArchive[Math.round(scrubber)].getMagnitude());
			
			if(label.isClosed() && alphaMinorFaultLines == 100) timekeeper += 1;
			if(timekeeper > 100) this.close();
			
		} else if(STATUS.equals("ANIMATING OUT")) {
			alphaNamesFaultLines = pTweenFaultsOUT.getTween("NamesOUT").getPosition();
			alphaMajorFaultLines = pTweenFaultsOUT.getTween("MajorOUT").getPosition();
			alphaMinorFaultLines = pTweenFaultsOUT.getTween("MinorOUT").getPosition();

			if(alphaMinorFaultLines == 0 && alphaMajorFaultLines == 0 && alphaNamesFaultLines == 0) timekeeper += 1;
			if(timekeeper > 10) this.closed();
		}
	}
	
	//| DRAW FUNCTIONS
	public void draw(Map m)
	{	
		//| Timeline Animation
		this.drawAllQuakes(m);
		
		//| Fault Lines
		parent.tint(255, alphaMinorFaultLines); 
		parent.image(faultlinesMinor, 0, 0); 
		parent.tint(255, alphaMajorFaultLines);
		parent.image(faultlinesMajor, 0, 0);
		parent.tint(255, alphaNamesFaultLines); 
		parent.image(faultlinesNames, 0, 0); 
		parent.tint(255);
		
		//| Cities & Details
		parent.tint(255, alphaLandmarks); 
		parent.image(citiesLandmarks, 0, 0); 
		parent.tint(255, alphaSecondary); 
		parent.image(citiesSecondary, 0, 0); 
		parent.tint(255, alphaPrimary); 
		parent.image(citiesPrimary, 0, 0); 
		
/*		//| Dial Rotation
		parent.pushMatrix();
		parent.translate(wid/2, hei + 50);
		parent.rotate(rotation);
		parent.translate(-timedial.width/2, -timedial.height/2);
		parent.tint(255, alphaDial);
		parent.image(timedial, 0, 0);
		parent.popMatrix();
*/	
		//| Legend
		label.draw(m);
	}
	
	private void drawAllQuakes(Map m)
	{	
		//| Iterate Quakes & Draw
		for (int i = 0; i < archiveLength; i++) 
		{ 
			float[] pos = m.getScreenPositionFromLocation(quakeArchive[i].getLocation());
			quakeArchive[i].update(pos[0], pos[1]);
			quakeArchive[i].draw();
		}
	}
	
	private void manageRotaryInflux()
	{
		//| Check Polarity
		boolean finished = false;
		int polarity = 0;
		if(rotaryValue > 0) polarity = 1;
		if(rotaryValue < 0) polarity = -1;
		
		//| Manage Direction, Idle, and Overlooked Children
		if(scrubber <= archiveLength - 1 && scrubber >= 0) {
			switch (polarity) {
				case -1: //| Negative
					timekeeper = 0;
					quakeArchive[Math.round(scrubber)].close();
					scrubber += rotaryValue * 10; //| += because it's sometime negative
					if(scrubber < 0) scrubber = 0;
					for (int i = 0; i < archiveLength; i++) if(quakeArchive[i].getStatus() == true && i > scrubber) quakeArchive[i].close(); //| Check for lost children
					rotation += rotaryValue*2;
					break;
				
				case 0: //| Neutral
					if(timekeeper > 300) {
						quakeArchive[Math.round(scrubber)].open();
						if(scrubber > archiveLength - 1) {
							scrubber = archiveLength - 1;

						} else {
							scrubber += 0.1f;
							rotation += 0.01f;
						}
					} else {
						timekeeper += 1;
					}
					break;
				
				case 1: //| Positive
					timekeeper = 0;
					quakeArchive[Math.round(scrubber)].open();
					scrubber += rotaryValue * 10;
					if(scrubber > archiveLength - 1)scrubber = archiveLength - 1;
					for (int i = 0; i < archiveLength; i++) if(quakeArchive[i].getStatus() == false && i < scrubber) quakeArchive[i].open(); //| Check for lost children
					rotation += rotaryValue*2;
					break;
				
				default:
					break;
			}
			
		} else {
			finished = true;
		}
		
		//| Check if at the end.
		if(finished) finalTimekeeper += 1;
		else finalTimekeeper = 0;
		
		if(finalTimekeeper > 50) this.step2();
	}
	
	public void rotaryInterfaceEvent(float s, int c)
	{
		rotaryValue = s;
	}
	
	public void upFaultsEvent()
	{
		if(faultlines == false) {
			faultlines = true;
			faulttransitioning = true;
			
			pTweenFaultsOUT.seek(0);
			pTweenFaultsIN.play();
			pTweenElementsDIM.play();
			
		} else if(faultlines == true) {
			faultlines = false;
			faulttransitioning = true;
			
			pTweenFaultsIN.seek(0);
			pTweenFaultsOUT.play();
			pTweenElementsIN.play();
		}
	}
	
	public void kill()
	{
		STATUS = "OFF";
		scrubber = 0;
		alphaDial = 0;
		alphaLandmarks = 0;
		alphaSecondary = 0;
		alphaPrimary = 0;
		alphaNamesFaultLines = 0;
		alphaMajorFaultLines = 0;
		alphaMinorFaultLines = 0;
		faultlines = false;
		faulttransitioning = false;
		
		for (int i = 0; i < archiveLength; i++) quakeArchive[i].kill();

		pTweenFaultsIN.seek(0);
		pTweenFaultsOUT.seek(0);
		pTweenElementsIN.seek(0);
		
		label.kill();
	}
	
	public void off()
	{
		this.kill();
	}
	
}


