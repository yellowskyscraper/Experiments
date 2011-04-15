#pragma once

#include "ofMain.h"
#include "ofxiPhone.h"
#include "ofxiPhoneExtras.h"
#include "VRand.h"

class testApp : public ofxiPhoneApp {
	
public:
	void setup();
	void update();
	void draw();
	void audioRequested( float * output, int bufferSize, int nChannels );
	void exit();
	
	void touchDown(ofTouchEventArgs &touch);
	void touchMoved(ofTouchEventArgs &touch);
	void touchUp(ofTouchEventArgs &touch);
	void touchDoubleTap(ofTouchEventArgs &touch);

	void lostFocus();
	void gotFocus();
	void gotMemoryWarning();
	void deviceOrientationChanged(int newOrientation);
	
	
	//------------------- External Noise Generator
	VRand rand;
	ofTrueTypeFont  titleCopy;
	ofTrueTypeFont	bodyCopy;
	
	float 	pan;
	int		sampleRate;
	bool 	bNoise;
	int 	iNoise;
	float 	volume;
	
	float 	* lAudio;
	float   * rAudio;
	
	//------------------- For Sine Wave Synthesis
	float 	targetFrequency;
	float 	phase;
	float 	phaseAdder;
	float 	phaseAdderTarget;
	int		initialBufferSize;

};


