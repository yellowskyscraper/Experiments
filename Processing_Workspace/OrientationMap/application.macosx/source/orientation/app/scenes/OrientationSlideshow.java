package orientation.app.scenes;

import processing.core.PApplet;
import processing.core.PImage;

import orientation.app.elements.LabelOrientation;

import ijeoma.motion.Motion;
import ijeoma.motion.tween.Tween;
import ijeoma.motion.tween.TweenParallel;

public class OrientationSlideshow {

	PApplet parent;

	//| Sequence Variable
	String STATUS = "OFF";
	boolean isLastElevation = false;
	boolean isPaused = false;
	boolean isElevationIn = false;
	int timekeeper = 0;
	int stepkeeper = 0;
	int terrestrialkeeper = 0;
	int annotationCurrent = 0;
	int elevation = 0;
	int elevationCountCurrent = 0;
	int elevationCountPast = 0;	
	int elevationCountResidue = 0;	
	int elevationCountForLabel = 0;
	int slightPause = 0;
	int switchold = 0;
	int switchnew = 0;

	//| Scene Images
	PImage bayModel;
	
	PImage[] elevationCurrentArray;
	PImage[] elevationPastArray;
	
	PImage labelsWaterbodies;
	PImage labelsTerrestrials;
	PImage labelsLandmarks;
	PImage labelsSecondaries;
	PImage labelsPrimaries;

	PImage elevationResidue1;
	PImage elevationResidue2;
	PImage elevationResedue4;
	PImage elevationResedue5;
	PImage elevationResedue6;
	PImage elevationResedue7;
	PImage elevationResedue8;
	PImage elevationResedue9;
	PImage elevationResedue10;
	PImage elevationResedue11;
	PImage elevationResedue12;
	
	PImage elevationAnnotation1;
	PImage elevationAnnotation2;
	PImage elevationAnnotation3;
	PImage elevationAnnotation4;
	PImage elevationAnnotation5;
	PImage elevationAnnotation6;
	PImage elevationAnnotation7;
	PImage elevationAnnotation8;
	PImage elevationAnnotation9;
	PImage elevationAnnotation10;
	PImage elevationAnnotation11;
	PImage elevationAnnotation12;
	
	//| Tweens
	Tween tweenBayModelIN;
	
	Tween tweenElevationIN;
	Tween tweenElevationOUT;
	
	Tween tweenPastElevationDIM;
	
	TweenParallel pTweenFinalSequence;
	TweenParallel pTweenFinalSequenceOUT;

	Tween tweenResidue1IN;
	Tween tweenResidue2IN;
	Tween tweenResidue3IN;
	Tween tweenResidue4IN;
	
	Tween tweenAnnotation1IN;
	Tween tweenAnnotation2IN;
	Tween tweenAnnotation3IN;
	Tween tweenAnnotation4IN;
	Tween tweenAnnotation5IN;
	Tween tweenAnnotation6IN;
	Tween tweenAnnotation7IN;
	Tween tweenAnnotation8IN;
	Tween tweenAnnotation9IN;
	Tween tweenAnnotation10IN;
	Tween tweenAnnotation11IN;
	Tween tweenAnnotation12IN;
	
	Tween tweenAnnotation1OUT;
	Tween tweenAnnotation2OUT;
	Tween tweenAnnotation3OUT;
	Tween tweenAnnotation4OUT;
	Tween tweenAnnotation5OUT;
	Tween tweenAnnotation6OUT;
	Tween tweenAnnotation7OUT;
	Tween tweenAnnotation8OUT;
	Tween tweenAnnotation9OUT;
	Tween tweenAnnotation10OUT;
	Tween tweenAnnotation11OUT;
	Tween tweenAnnotation12OUT;

	//| Alpha
	float alphaBayModel = 0;
	
	float alphaCurrentElevation = 0;
	float alphaPreviousElevation = 0;
	float alphaPastElevation = 0;
	
	float alphaWaterbodies = 0;
	float alphaTerrestrials = 0;
	float alphaLandmarks = 0;
	float alphaSecondaries = 0;
	float alphaPrimaries = 0;
	
	float alphaResidue1 = 0;
	float alphaResidue2 = 0;
	float alphaResidue4 = 0;
	float alphaResidue5 = 0;
	float alphaResidue6 = 0;
	float alphaResidue7 = 0;
	float alphaResidue8 = 0;
	float alphaResidue9 = 0;
	float alphaResidue10 = 0;
	float alphaResidue11 = 0;
	float alphaResidue12 = 0;
	
	float alphaAnnotation1 = 0;
	float alphaAnnotation2 = 0;
	float alphaAnnotation3 = 0;
	float alphaAnnotation4 = 0;
	float alphaAnnotation5 = 0;
	float alphaAnnotation6 = 0;
	float alphaAnnotation7 = 0;
	float alphaAnnotation8 = 0;
	float alphaAnnotation9 = 0;
	float alphaAnnotation10 = 0;
	float alphaAnnotation11 = 0;
	float alphaAnnotation12 = 0;
	
	//| Legend
	LabelOrientation label;
	
	public OrientationSlideshow(PApplet p)
	{
		parent = p;
	}
	
	public void setup()
	{
		//| Main Graphics	
		bayModel = parent.loadImage("data/images/bounds4_1500x1200.jpg");

		elevationCurrentArray = new PImage[65];
		elevationPastArray = new PImage[28];
		
		for(int i = 0; i < 64; i++) {
			elevationCurrentArray[i] = parent.loadImage("data/images/elevation/current/"+ i +".png");
			if(i < 28) elevationPastArray[i] = parent.loadImage("data/images/elevation/past/"+ i +".png");
		}
		
		labelsWaterbodies = parent.loadImage("data/images/elevation/labels/1_waterbodies.png");
		labelsTerrestrials = parent.loadImage("data/images/elevation/labels/2_terrestrials.png");
		labelsLandmarks = parent.loadImage("data/images/elevation/labels/3_landmarks.png");
		labelsSecondaries = parent.loadImage("data/images/elevation/labels/4_secondaries.png");
		labelsPrimaries = parent.loadImage("data/images/elevation/labels/5_primaries.png");
		
		elevationResidue1 = parent.loadImage("data/images/elevation/annotationlabels/1.png");
		elevationResidue2 = parent.loadImage("data/images/elevation/annotationlabels/2.png");
		elevationResedue4 = parent.loadImage("data/images/elevation/annotationlabels/4.png");
		elevationResedue5 = parent.loadImage("data/images/elevation/annotationlabels/5.png");
		elevationResedue6 = parent.loadImage("data/images/elevation/annotationlabels/6.png");
		elevationResedue7 = parent.loadImage("data/images/elevation/annotationlabels/7.png");
		elevationResedue8 = parent.loadImage("data/images/elevation/annotationlabels/8.png");
		elevationResedue9 = parent.loadImage("data/images/elevation/annotationlabels/9.png");
		elevationResedue10 = parent.loadImage("data/images/elevation/annotationlabels/10.png");
		elevationResedue11 = parent.loadImage("data/images/elevation/annotationlabels/11.png");
		elevationResedue12 = parent.loadImage("data/images/elevation/annotationlabels/12.png");
		
		elevationAnnotation1 = parent.loadImage("data/images/elevation/annotations/1.png");
		elevationAnnotation2 = parent.loadImage("data/images/elevation/annotations/2.png");
		elevationAnnotation3 = parent.loadImage("data/images/elevation/annotations/3.png");
		elevationAnnotation4 = parent.loadImage("data/images/elevation/annotations/4.png");
		elevationAnnotation5 = parent.loadImage("data/images/elevation/annotations/5.png");
		elevationAnnotation6 = parent.loadImage("data/images/elevation/annotations/6.png");
		elevationAnnotation7 = parent.loadImage("data/images/elevation/annotations/7.png");
		elevationAnnotation8 = parent.loadImage("data/images/elevation/annotations/8.png");
		elevationAnnotation9 = parent.loadImage("data/images/elevation/annotations/9.png");
		elevationAnnotation10 = parent.loadImage("data/images/elevation/annotations/10.png");
		elevationAnnotation11 = parent.loadImage("data/images/elevation/annotations/11.png");
		elevationAnnotation12 = parent.loadImage("data/images/elevation/annotations/12.png");

		//| Tweens
		Motion.setup(parent);
		
		tweenBayModelIN = new Tween(0f, 255f, 80f);
		
		tweenElevationIN = new Tween(0f, 255f, 17f); //| 30
		tweenElevationOUT = new Tween(255f, 0f, 50f, Tween.LINEAR_EASE_OUT); //| 15, Tween.LINEAR_EASE_OUT
		tweenPastElevationDIM = new Tween(180f, 100f, 30f, Tween.LINEAR_EASE_OUT);

		tweenResidue1IN = new Tween(0f, 255f, 20f);
		tweenResidue2IN = new Tween(0f, 255f, 20f);
		tweenResidue3IN = new Tween(0f, 255f, 20f);
		tweenResidue4IN = new Tween(0f, 255f, 20f);

		tweenAnnotation1IN = new Tween(0f, 255f, 20f);
		tweenAnnotation2IN = new Tween(0f, 255f, 20f);
		tweenAnnotation3IN = new Tween(0f, 255f, 20f);
		tweenAnnotation4IN = new Tween(0f, 255f, 30f, 90f);
		tweenAnnotation5IN = new Tween(0f, 255f, 20f);
		tweenAnnotation6IN = new Tween(0f, 255f, 20f);
		tweenAnnotation7IN = new Tween(0f, 255f, 20f);
		tweenAnnotation8IN = new Tween(0f, 255f, 20f);
		tweenAnnotation9IN = new Tween(0f, 255f, 20f);
		tweenAnnotation10IN = new Tween(0f, 255f, 20f);
		tweenAnnotation11IN = new Tween(0f, 255f, 20f);
		tweenAnnotation12IN = new Tween(0f, 255f, 20f);
		
		tweenAnnotation1OUT = new Tween(255f, 0f, 10f);
		tweenAnnotation2OUT = new Tween(255f, 0f, 10f);
		tweenAnnotation3OUT = new Tween(255f, 0f, 10f);
		tweenAnnotation3OUT = new Tween(255f, 0f, 10f);
		tweenAnnotation4OUT = new Tween(255f, 0f, 20f);
		tweenAnnotation5OUT = new Tween(255f, 0f, 10f);
		tweenAnnotation6OUT = new Tween(255f, 0f, 10f, 5f);
		tweenAnnotation7OUT = new Tween(255f, 0f, 10f, 10f);
		tweenAnnotation8OUT = new Tween(255f, 0f, 10f);
		tweenAnnotation9OUT = new Tween(255f, 0f, 10f);
		tweenAnnotation10OUT = new Tween(255f, 0f, 10f, 5f);
		tweenAnnotation11OUT = new Tween(255f, 0f, 10f, 10f);
		tweenAnnotation12OUT = new Tween(255f, 0f, 10f, 15f);
		
		pTweenFinalSequence = new TweenParallel();
		pTweenFinalSequence.addChild(new Tween("WaterbodiesIN", 0f, 255f, 20f));
		pTweenFinalSequence.addChild(new Tween("TerrestrialsIN", 0f, 255f, 20f, 50f));
		pTweenFinalSequence.addChild(new Tween("LandmarksIN", 0f, 255f, 20f, 100f));
		pTweenFinalSequence.addChild(new Tween("SecondariesIN", 0f, 255f, 20f, 110f));
		pTweenFinalSequence.addChild(new Tween("PrimariesIN", 0f, 255f, 20f, 120f));

		pTweenFinalSequenceOUT = new TweenParallel();
		pTweenFinalSequenceOUT.addChild(new Tween("ResidueOUT", 255f, 0f, 20f));
		pTweenFinalSequenceOUT.addChild(new Tween("WaterbodiesOUT", 255f, 0f, 10f));
		pTweenFinalSequenceOUT.addChild(new Tween("TerrestrialsOUT", 255f, 0f, 20f, 20f));
		pTweenFinalSequenceOUT.addChild(new Tween("LandmarksOUT", 255f, 0f, 20f, 30f));
		pTweenFinalSequenceOUT.addChild(new Tween("PastElevationOUT", 100f, 0f, 30f, 60f));
		pTweenFinalSequenceOUT.addChild(new Tween("BayModelOUT", 255f, 0f, 50f, 80f));
		
		//| Legend
		label = new LabelOrientation(parent);
		label.setup();
	}
	
	public void start()
	{
		STATUS = "ANIMATING IN";
		annotationCurrent = 1;
		tweenAnnotation1IN.play();
		PApplet.println(STATUS);
	}
	
	public void step1()
	{
		STATUS = "WATERBODIES UP";
		timekeeper = 0;
		tweenElevationIN.play();
		
		PApplet.println(STATUS);
	}
	
	public void step2()
	{
		STATUS = "WATERBODIES FINISH";
		
		timekeeper = 0;
		annotationCurrent = 1;
		
		elevationCountPast += 1;
		elevationCountResidue += 1;
		
		tweenElevationOUT.play();
		tweenAnnotation3OUT.play();
		tweenBayModelIN.play();
		tweenAnnotation4IN.play();
		
		PApplet.println(STATUS);
	}
	
	public void step3()
	{
		STATUS = "TERRESTRIALS UP";
		timekeeper = 0;
		stepkeeper = 0;
		annotationCurrent = 1;
		
		tweenElevationIN.play();
		
		PApplet.println(STATUS);
	}
	
	public void step4()
	{
		STATUS = "TERRESTRIALS LAST ELEVATION";
		timekeeper = 0;
		tweenElevationOUT.play();
		label.sequenceEnd();
		PApplet.println(STATUS);
	}

	public void step5()
	{
		STATUS = "TERRESTRIALS FINISH";
		timekeeper = 0;
		tweenAnnotation9OUT.play();
		tweenAnnotation10OUT.play();
		tweenAnnotation11OUT.play();
		tweenAnnotation12OUT.play();
		tweenPastElevationDIM.play();
		tweenResidue4IN.play();
		
		PApplet.println(STATUS);
	}

	public void step6()
	{
		STATUS = "LAST SEQUENCE";
		timekeeper = 0;
		
		pTweenFinalSequence.play();
		
		PApplet.println(STATUS);
	}
	
	public void close()
	{
		STATUS = "ANIMATING OUT"; 
		timekeeper = 0;
		pTweenFinalSequenceOUT.play();
		label.close();
		
		PApplet.println(STATUS);
	}
	
	public void closed()
	{
		STATUS = "DONE";
		PApplet.println(STATUS);
	}
		
	public void update()
	{
		if(STATUS.equals("OFF")) {
		
		} else if(STATUS.equals("ANIMATING IN")) {
			alphaAnnotation1 = tweenAnnotation1IN.getPosition();
			if(alphaAnnotation1 == 255) timekeeper += 1;
			if(timekeeper > 200) this.step1();

/////////////////////////////////////////////////////////////////////////////////////////////////|	
/////////////////////////////////////////////////////////////////////////////////////////////////|				
		} else if(STATUS.equals("WATERBODIES UP")) {
			//| Manage Alpha Firstly
			alphaCurrentElevation = tweenElevationIN.getPosition();
			if(stepkeeper == 2) alphaPreviousElevation = tweenElevationOUT.getPosition();

			switch (annotationCurrent) {
				case 1:
					alphaAnnotation1 = tweenAnnotation1OUT.getPosition();
					alphaAnnotation2 = tweenAnnotation2IN.getPosition();
					alphaResidue1 = tweenResidue1IN.getPosition();
					break;
					
				case 2:
					alphaAnnotation2 = tweenAnnotation2OUT.getPosition();
					break;
					
				case 3:
					alphaResidue2 = tweenResidue1IN.getPosition();
					alphaAnnotation3 = tweenAnnotation3IN.getPosition();
					break;	
					
				default:
					break;
			}
			
			//| Manage Elevations
			switch (slightPause) {
				case 0:	
					if(elevationCountCurrent < 16) {
						if(alphaCurrentElevation == 255 && stepkeeper == 0) {
							tweenElevationIN.play();
							
							alphaPastElevation = 180;
							elevationCountForLabel += 1;
			
							alphaPreviousElevation = 255;
							alphaCurrentElevation = 0;
							elevationCountCurrent += 1;
							
							stepkeeper = 1;
							
						} else if(alphaCurrentElevation == 255 && stepkeeper == 1) {
							tweenElevationOUT.play();
							
							stepkeeper = 2;
							
						} else if(alphaPreviousElevation == 0 && stepkeeper == 2) {
							tweenElevationIN.play();
							
							elevationCountForLabel += 1;
							elevationCountPast += 1;
							elevationCountResidue = elevationCountPast;
			
							alphaPreviousElevation = 255;
							alphaCurrentElevation = 0;
							
							elevationCountCurrent += 1;
							
							stepkeeper = 1;
						}
						
					} else if(elevationCountCurrent == 16 && alphaPreviousElevation == 255) {
						tweenElevationOUT.play();
						stepkeeper = 2;
					
					} else {
						timekeeper += 1;
					} 
					break;
					
				default:
					slightPause -= 1;
					
					if(alphaPreviousElevation == 255) {
						tweenElevationOUT.play();
						stepkeeper = 2;
					}
					break;
			}
			
			//| Sequence Cues
			switchnew = elevationCountCurrent;
			
			switch (elevationCountCurrent) {
				case 5:
					if(this.littleSwitch()) break;
					annotationCurrent = 1;
					tweenAnnotation1OUT.play();
					break;
					
				case 6: 
					if(this.littleSwitch()) break;
					label.open(); 
					break;
					
				case 7:
					if(this.littleSwitch()) break;
					tweenResidue1IN.play();
					break;
					
				case 11:
					if(this.littleSwitch()) break;
					tweenAnnotation2IN.play();
					isPaused = true;
					slightPause = 200;
					break;
					
				case 12:
					if(this.littleSwitch()) break;
					annotationCurrent = 2;
					tweenAnnotation2OUT.play();
					isPaused = true;
					slightPause = 15;
					break;
					
				case 16:
					if(this.littleSwitch()) break;
					annotationCurrent = 3;
					tweenResidue1IN.play();
					tweenAnnotation3IN.play();
					break;
					
				default:
					break;
			}

			if(timekeeper > 400) this.step2();

/////////////////////////////////////////////////////////////////////////////////////////////////|	
/////////////////////////////////////////////////////////////////////////////////////////////////|				
		} else if(STATUS.equals("WATERBODIES FINISH")) {
			alphaCurrentElevation = tweenElevationOUT.getPosition();
			alphaAnnotation3 = tweenAnnotation3OUT.getPosition();
			alphaBayModel = tweenBayModelIN.getPosition();
			
			switch (annotationCurrent) {
				case 1:
					alphaAnnotation4 = tweenAnnotation4IN.getPosition();
					if(alphaAnnotation4 == 255) slightPause += 1;
					if(slightPause > 300) {
						slightPause = 0;
						annotationCurrent = 2;
						tweenAnnotation4OUT.play();
					}
					break;
	
				case 2:
					alphaAnnotation4 = tweenAnnotation4OUT.getPosition();
					
					if(alphaAnnotation4 == 0) slightPause += 1;
					if(slightPause > 100) {
						slightPause = 0;
						this.step3();
					}
					break;
			}
		
/////////////////////////////////////////////////////////////////////////////////////////////////|	
/////////////////////////////////////////////////////////////////////////////////////////////////|			
		} else if(STATUS.equals("TERRESTRIALS UP")) { 
			//| Manage Alpha Firstly
			alphaCurrentElevation = tweenElevationIN.getPosition();
			if(stepkeeper == 2) alphaPreviousElevation = tweenElevationOUT.getPosition();
			
			alphaResidue5 = alphaResidue6 = alphaResidue7 = tweenResidue2IN.getPosition();
			alphaResidue8 = tweenResidue3IN.getPosition();

			switch (annotationCurrent) {
				case 1:
					alphaAnnotation5 = tweenAnnotation5IN.getPosition();
					alphaAnnotation6 = tweenAnnotation6IN.getPosition();
					alphaAnnotation7 = tweenAnnotation7IN.getPosition();
					break;
					
				case 2:
					alphaAnnotation5 = tweenAnnotation5OUT.getPosition();
					alphaAnnotation6 = tweenAnnotation6OUT.getPosition();
					alphaAnnotation7 = tweenAnnotation7OUT.getPosition();
					break;

				case 3:
					alphaResidue4 = tweenResidue1IN.getPosition();
					alphaAnnotation8 = tweenAnnotation8IN.getPosition();
					break;

				case 4:
					alphaAnnotation8 = tweenAnnotation8OUT.getPosition();
					
					alphaAnnotation9 = tweenAnnotation9IN.getPosition();
					alphaAnnotation10 = tweenAnnotation10IN.getPosition();
					alphaAnnotation11 = tweenAnnotation11IN.getPosition();
					alphaAnnotation12 = tweenAnnotation12IN.getPosition();
					break;
					
				default:
					break;
			}
			
			//| Manage Elevations
			switch (slightPause) {
				case 0:	
					if(elevationCountCurrent < 63) {
						if(alphaCurrentElevation == 255 && stepkeeper == 0) {
							tweenElevationIN.play();

							alphaPastElevation = 180;
							elevationCountForLabel += 1;
			
							alphaPreviousElevation = 255;
							alphaCurrentElevation = 0;
							
							elevationCountCurrent += 1;
							
							stepkeeper = 1;
							
						} else if(alphaCurrentElevation == 255 && stepkeeper == 1) {
							tweenElevationOUT.play();
							
							stepkeeper = 2;
							
						} else if(alphaPreviousElevation == 0 && stepkeeper == 2) {
							tweenElevationIN.play();
							
							if(elevationCountCurrent < 63) elevationCountForLabel += 1;
							elevationCountPast += 1;

							if(terrestrialkeeper > 2){
								terrestrialkeeper = 0;
								elevationCountResidue += 1;
							} else {
								terrestrialkeeper += 1;
							}

							alphaPreviousElevation = 255;
							alphaCurrentElevation = 0;
							
							if(elevationCountCurrent < 63) elevationCountCurrent += 1;
							
							stepkeeper = 1;
						}
						
					} else if(elevationCountCurrent == 63 && isLastElevation == false) {
						tweenElevationOUT.play();
						elevationCountPast += 1;
						stepkeeper = 2;
						isLastElevation = true;
						
					} else {
						timekeeper += 1;
					} 
					break;
					
				default:
					slightPause -= 1;
					
					if(alphaPreviousElevation == 255) {
						tweenElevationOUT.play();
						stepkeeper = 2;
					}
					break;
			}
			
			//| Sequence Cues
			switchnew = elevationCountCurrent;
			
			switch (elevationCountCurrent) {
					
				case 17:
					if(this.littleSwitch()) break;
					tweenAnnotation4OUT.play();
					isPaused = true;
					slightPause = 15;
					break;
					
				case 21://| Annotation: Telegraph Hill
					if(this.littleSwitch()) break;
					tweenAnnotation5IN.play();
					break;
					
				case 28://| Annotation: Twin Peaks
					if(this.littleSwitch()) break;
					tweenAnnotation6IN.play();
					break;
					
				case 29://| Annotation: Mt. Davidson
					if(this.littleSwitch()) break;
					tweenAnnotation7IN.play();
					isPaused = true;
					slightPause = 100;
					break;

				case 30:
					if(this.littleSwitch()) break;
					annotationCurrent = 2;
					tweenAnnotation5OUT.play();
					tweenAnnotation6OUT.play();
					tweenAnnotation7OUT.play();
					break;
					
				case 31:
					if(this.littleSwitch()) break;
					annotationCurrent = 3;
					tweenResidue1IN.play();
					break;

				case 32://| Annotation: Sweeny Ridge
					if(this.littleSwitch()) break;
					tweenResidue2IN.play();
					tweenAnnotation8IN.play();
					break;
					
				case 41:
					if(this.littleSwitch()) break;
					annotationCurrent = 4;
					tweenAnnotation8OUT.play();
					tweenResidue3IN.play();
					break;
					
				case 47://| Annotation: Kings Mt.
					if(this.littleSwitch()) break;
					tweenAnnotation9IN.play();
					break;
					
				case 49://| Annotation: Mt. Tam
					if(this.littleSwitch()) break;
					tweenAnnotation10IN.play();
					break;
					
				case 52://| Annotation: Mt. Diablo
					if(this.littleSwitch()) break;
					tweenAnnotation11IN.play();
					break;
					
				case 57://| Annotation: Mt. Diablo
					if(this.littleSwitch()) break;
					tweenAnnotation12IN.play();
					break;
					
				default:
					break;
			}
			
//			if(alphaAnnotation2 == 255) timekeeper += 1;
			if(timekeeper > 10) this.step4();

/////////////////////////////////////////////////////////////////////////////////////////////////|	
/////////////////////////////////////////////////////////////////////////////////////////////////|				
		} else if(STATUS.equals("TERRESTRIALS LAST ELEVATION")) {
			alphaPreviousElevation = tweenElevationOUT.getPosition();	
			alphaAnnotation12 = tweenAnnotation12IN.getPosition();

			if(alphaPreviousElevation == 0 && alphaAnnotation12 == 255) timekeeper += 1;
			if(timekeeper > 100) this.step5();

/////////////////////////////////////////////////////////////////////////////////////////////////|	
/////////////////////////////////////////////////////////////////////////////////////////////////|				
		} else if(STATUS.equals("TERRESTRIALS FINISH")) {
			alphaAnnotation9 = tweenAnnotation9OUT.getPosition();
			alphaAnnotation10 = tweenAnnotation10OUT.getPosition();
			alphaAnnotation11 = tweenAnnotation11OUT.getPosition();
			alphaAnnotation12 = tweenAnnotation11OUT.getPosition();
		
			alphaPastElevation = tweenPastElevationDIM.getPosition();
			alphaResidue9 = alphaResidue10 = alphaResidue11 = alphaResidue12 = tweenResidue4IN.getPosition();

			if(alphaResidue9 == 255) timekeeper += 1;
			if(timekeeper > 20) this.step6();

/////////////////////////////////////////////////////////////////////////////////////////////////|	
/////////////////////////////////////////////////////////////////////////////////////////////////|				
		} else if(STATUS.equals("LAST SEQUENCE")) {
			alphaWaterbodies = pTweenFinalSequence.getTween("WaterbodiesIN").getPosition();
			alphaTerrestrials = pTweenFinalSequence.getTween("TerrestrialsIN").getPosition();
			alphaLandmarks = pTweenFinalSequence.getTween("LandmarksIN").getPosition();
			alphaSecondaries = pTweenFinalSequence.getTween("SecondariesIN").getPosition();
			alphaPrimaries = pTweenFinalSequence.getTween("PrimariesIN").getPosition();
			
			if(alphaPrimaries == 255)timekeeper += 1;
			if(timekeeper > 800) this.close(); //| This is the mark for the final pause before it transitions out
			
		} else if(STATUS.equals("ANIMATING OUT")) {			
			alphaBayModel = pTweenFinalSequenceOUT.getTween("BayModelOUT").getPosition();
			alphaPastElevation = pTweenFinalSequenceOUT.getTween("PastElevationOUT").getPosition();
			alphaWaterbodies = pTweenFinalSequenceOUT.getTween("WaterbodiesOUT").getPosition();
			alphaTerrestrials = pTweenFinalSequenceOUT.getTween("TerrestrialsOUT").getPosition();
			
			float landmarksOUT = pTweenFinalSequenceOUT.getTween("LandmarksOUT").getPosition();
			alphaLandmarks = landmarksOUT;
			alphaSecondaries = landmarksOUT;
			alphaPrimaries = landmarksOUT;			

			float residueOUT = pTweenFinalSequenceOUT.getTween("ResidueOUT").getPosition();
			alphaResidue1 = residueOUT;
			alphaResidue2 = residueOUT;
			alphaResidue4 = residueOUT;
			alphaResidue5 = residueOUT;
			alphaResidue6 = residueOUT;
			alphaResidue7 = residueOUT;
			alphaResidue8 = residueOUT;
			alphaResidue9 = residueOUT;
			alphaResidue10 = residueOUT;
			alphaResidue11 = residueOUT;
			alphaResidue12 = residueOUT;
			
			if(alphaBayModel == 0) timekeeper += 1;
			if(timekeeper > 100) this.closed();
		}

		label.setElevation(elevationCountForLabel);
		label.update();
	}
	
	public boolean littleSwitch()
	{
		//| Bad workaround so that the switch doesnt call repeatedly
		boolean s = false;
		if(switchold == switchnew) s = true;
		else switchold = switchnew; 
		return s;
	}
	
	public void draw()
	{
		//| Bay Area Satalite Map Image
		parent.noSmooth();
		parent.tint(255, alphaBayModel);
		parent.image(bayModel, 0, 0);
		
		//| Elivation Contours; Past, Previous, & Current
		parent.tint(255, alphaPastElevation);
		parent.image(elevationPastArray[elevationCountResidue], 0, 0);
		
		parent.tint(255, alphaPreviousElevation);
		parent.image(elevationCurrentArray[elevationCountPast], 0, 0);
		
		parent.tint(255, alphaCurrentElevation);
		parent.image(elevationCurrentArray[elevationCountCurrent], 0, 0);

		//| Labels
		if(alphaWaterbodies > 0) {
			parent.tint(255, alphaWaterbodies);
			parent.image(labelsWaterbodies, 0, 0);
		}
		
		if(alphaTerrestrials > 0) {
			parent.tint(255, alphaTerrestrials);
			parent.image(labelsTerrestrials, 0, 0);
		}
		
		if(alphaLandmarks > 0) {
			parent.tint(255, alphaLandmarks);
			parent.image(labelsLandmarks, 0, 0);
		}
		
		if(alphaSecondaries > 0) {
			parent.tint(255, alphaSecondaries);
			parent.image(labelsSecondaries, 0, 0);
		}

		if(alphaPrimaries > 0) {
			parent.tint(255, alphaPrimaries);
			parent.image(labelsPrimaries, 0, 0);
		}
		
		//| Annotations Past Residue
		if(alphaResidue1 > 0) {
			parent.tint(255, alphaResidue1);
			parent.image(elevationResidue1, 0, 0);
		}
		
		if(alphaResidue2 > 0) {
			parent.tint(255, alphaResidue2);
			parent.image(elevationResidue2, 0, 0);
		}
		
		if(alphaResidue4 > 0) {
			parent.tint(255, alphaResidue4);
			parent.image(elevationResedue4, 0, 0);
		}
		
		if(alphaResidue5 > 0) {
			parent.tint(255, alphaResidue5);
			parent.image(elevationResedue5, 0, 0);
		}
		
		if(alphaResidue6 > 0) {
			parent.tint(255, alphaResidue6);
			parent.image(elevationResedue6, 0, 0);
		}
		
		if(alphaResidue7 > 0) {
			parent.tint(255, alphaResidue7);
			parent.image(elevationResedue7, 0, 0);
		}
		
		if(alphaResidue8 > 0) {
			parent.tint(255, alphaResidue8);
			parent.image(elevationResedue8, 0, 0);
		}
		
		if(alphaResidue9 > 0) {
			parent.tint(255, alphaResidue9);
			parent.image(elevationResedue9, 0, 0);
		}
		
		if(alphaResidue10 > 0) {
			parent.tint(255, alphaResidue10);
			parent.image(elevationResedue10, 0, 0);
		}
		
		if(alphaResidue11 > 0) {
			parent.tint(255, alphaResidue11);
			parent.image(elevationResedue11, 0, 0);
		}
		
		if(alphaResidue12 > 0) {
			parent.tint(255, alphaResidue12);
			parent.image(elevationResedue12, 0, 0);
		}
		
		//| Annotations
		if(alphaAnnotation1 > 0) {
			parent.tint(255, alphaAnnotation1);
			parent.image(elevationAnnotation1, 0, 0);//| Annotation: Pioneer Canyon -400 meters
		}
		
		if(alphaAnnotation2 > 0) {
			parent.tint(255, alphaAnnotation2);
			parent.image(elevationAnnotation2, 0, 0);//| Annotation: The Golden Gate -100 meters
		}
		
		if(alphaAnnotation3 > 0) {
			parent.tint(255, alphaAnnotation3);
			parent.image(elevationAnnotation3, 0, 0);//| Annotation: Ocean Beach, Sea Level
		}
		
		if(alphaAnnotation4 > 0) {
			parent.tint(255, alphaAnnotation4);
			parent.image(elevationAnnotation4, 0, 0);//| Annotation: You Are Here, 4 meters
		}
		
		if(alphaAnnotation5 > 0) {
			parent.tint(255, alphaAnnotation5);
			parent.image(elevationAnnotation5, 0, 0);//| Annotation: Telegraph Hill
		}
		
		if(alphaAnnotation6 > 0) {
			parent.tint(255, alphaAnnotation6);
			parent.image(elevationAnnotation6, 0, 0);//| Annotation: Twin Peaks
		}
		
		if(alphaAnnotation7 > 0) {
			parent.tint(255, alphaAnnotation7);
			parent.image(elevationAnnotation7, 0, 0);//| Annotation: Mt Davidson
		}
		
		if(alphaAnnotation8 > 0) {
			parent.tint(255, alphaAnnotation8);
			parent.image(elevationAnnotation8, 0, 0);//| Annotation: 
		}
		
		if(alphaAnnotation9 > 0) {
			parent.tint(255, alphaAnnotation9);
			parent.image(elevationAnnotation9, 0, 0);//| Annotation: 
		}
		
		if(alphaAnnotation10 > 0) {
			parent.tint(255, alphaAnnotation10);
			parent.image(elevationAnnotation10, 0, 0);//| Annotation: 
		}
		
		if(alphaAnnotation11 > 0) {
			parent.tint(255, alphaAnnotation11);
			parent.image(elevationAnnotation11, 0, 0);//| Annotation: 
		}
		
		if(alphaAnnotation12 > 0) {
			parent.tint(255, alphaAnnotation12);
			parent.image(elevationAnnotation12, 0, 0);//| Annotation: 
		}
		//| Label
		label.draw();
	}
	
	public void kill()
	{
		STATUS = "OFF";
		
		isLastElevation = false;
		isPaused = false;
		isElevationIn = false;
		timekeeper = 0;
		stepkeeper = 0;
		terrestrialkeeper = 0;
		annotationCurrent = 0;
		elevation = 0;
		elevationCountCurrent = 0;
		elevationCountPast = 0;	
		elevationCountResidue = 0;	
		elevationCountForLabel = 0;
		slightPause = 0;
		switchold = 0;
		switchnew = 0;

		alphaBayModel = 0;
		alphaCurrentElevation = 0;
		alphaPreviousElevation = 0;
		alphaPastElevation = 0;
		alphaWaterbodies = 0;
		alphaTerrestrials = 0;
		alphaLandmarks = 0;
		alphaSecondaries = 0;
		alphaPrimaries = 0;
		alphaResidue1 = 0;
		alphaResidue2 = 0;
		alphaResidue4 = 0;
		alphaResidue5 = 0;
		alphaResidue6 = 0;
		alphaResidue7 = 0;
		alphaResidue8 = 0;
		alphaResidue9 = 0;
		alphaResidue10 = 0;
		alphaResidue11 = 0;
		alphaResidue12 = 0;
		alphaAnnotation1 = 0;
		alphaAnnotation2 = 0;
		alphaAnnotation3 = 0;
		alphaAnnotation4 = 0;
		alphaAnnotation5 = 0;
		alphaAnnotation6 = 0;
		alphaAnnotation7 = 0;
		alphaAnnotation8 = 0;
		alphaAnnotation9 = 0;
		alphaAnnotation10 = 0;
		alphaAnnotation11 = 0;
		alphaAnnotation12 = 0;
		
		pTweenFinalSequence.seek(0);
		pTweenFinalSequenceOUT.seek(0);
		
		tweenBayModelIN.seek(0);
		
		tweenElevationIN.seek(0);
		tweenElevationOUT.seek(0);
		tweenPastElevationDIM.seek(0);

		tweenResidue1IN.seek(0);
		tweenResidue2IN.seek(0);
		tweenResidue3IN.seek(0);
		tweenResidue4IN.seek(0);

		tweenAnnotation1IN.seek(0);
		tweenAnnotation2IN.seek(0);
		tweenAnnotation3IN.seek(0);
		tweenAnnotation4IN.seek(0);
		tweenAnnotation5IN.seek(0);
		tweenAnnotation6IN.seek(0);
		tweenAnnotation7IN.seek(0);
		tweenAnnotation8IN.seek(0);
		tweenAnnotation9IN.seek(0);
		tweenAnnotation10IN.seek(0);
		tweenAnnotation11IN.seek(0);
		tweenAnnotation12IN.seek(0);
		
		tweenAnnotation1OUT.seek(0);
		tweenAnnotation2OUT.seek(0);
		tweenAnnotation3OUT.seek(0);
		tweenAnnotation3OUT.seek(0);
		tweenAnnotation4OUT.seek(0);
		tweenAnnotation5OUT.seek(0);
		tweenAnnotation6OUT.seek(0);
		tweenAnnotation7OUT.seek(0);
		tweenAnnotation8OUT.seek(0);
		tweenAnnotation9OUT.seek(0);
		tweenAnnotation10OUT.seek(0);
		tweenAnnotation11OUT.seek(0);
		tweenAnnotation12OUT.seek(0);
	
		label.kill();
	}
	
	public void off() 
	{
		this.kill();
	}

	public String status() 
	{
		return STATUS;
	}
}
