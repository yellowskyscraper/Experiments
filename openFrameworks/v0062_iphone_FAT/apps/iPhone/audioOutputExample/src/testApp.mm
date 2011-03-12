#include "testApp.h"

//--------------------------------------------------------------
void testApp::setup(){

	// IMPORTANT!!! if your sound doesn't work in the simulator - read this post - which requires you set the output stream to 24bit 
	//	http://www.cocos2d-iphone.org/forum/topic/4159

	// register touch events
	ofRegisterTouchEvents(this);
	ofxiPhoneSetOrientation(OFXIPHONE_ORIENTATION_PORTRAIT);
	ofBackground(255,255,255);

	// 2 output channels,
	// 0 input channels
	// 44100 samples per second
	// 512 samples per buffer
	// 4 num buffers (latency)

	sampleRate 			= 44100;
	phase 				= 0;
	phaseAdder 			= 0.0f;
	phaseAdderTarget 	= 0.0;
	volume				= 0.15f;
	pan					= 0.5;
	bNoise 				= false;
	
	//for some reason on the iphone simulator 256 doesn't work - it comes in as 512!
	//so we do 512 - otherwise we crash
	initialBufferSize	= 512;
	
	lAudio				= new float[initialBufferSize];
	rAudio				= new float[initialBufferSize];
	
	memset(lAudio, 0, initialBufferSize * sizeof(float));
	memset(rAudio, 0, initialBufferSize * sizeof(float));
	
	//we do this because we don't have a mouse move function to work with:
	targetFrequency = 444.0;
	phaseAdderTarget = (targetFrequency / (float) sampleRate) * TWO_PI;
	
	ofSoundStreamSetup(2,0,this, sampleRate, initialBufferSize, 4);
	ofSetFrameRate(60);

}

//--------------------------------------------------------------
void testApp::update(){
	
}

//--------------------------------------------------------------
void testApp::draw(){

	float boxW		= ofGetScreenWidth();
	float boxH		= ofGetScreenHeight();
	
	float topY		= 0;
	float leftX		= 0;
	
	ofSetColor(0x333333);
	ofRect(0,0,boxW,boxH);
	
	ofSetColor(0xFFFFFF);
	for (int i = 0; i < initialBufferSize; i++){
		float x = ofMap(i, 0, initialBufferSize, 0, boxW, true);
		ofLine(leftX+x,topY + boxH/2,leftX+x,topY + boxH/2 + lAudio[i]*boxH*0.5);
	}
	
	ofSetColor(0xffffff);
	char reportString[255];
	sprintf(reportString, "volume: (%f) \npan: (%f)\nsynthesis: %s", volume, pan, bNoise ? "noise" : "sine wave");
	if (!bNoise) sprintf(reportString, "%s (%fhz)", reportString, targetFrequency);

	ofDrawBitmapString(reportString,10,15);

}

//--------------------------------------------------------------
void testApp::audioRequested(float * output, int bufferSize, int nChannels){
		
	if( initialBufferSize != bufferSize ){
		ofLog(OF_LOG_ERROR, "your buffer size was set to %i - but the stream needs a buffer size of %i", initialBufferSize, bufferSize);
		return;
	}	
	//pan = 0.5f;
	float leftScale = 1 - pan;
	float rightScale = pan;

	// sin (n) seems to have trouble when n is very large, so we
	// keep phase in the range of 0-TWO_PI like this:
	while (phase > TWO_PI){
		phase -= TWO_PI;
	}

	if ( bNoise == true){
		// White Noise Waveform
		/*
		for (int i = 0; i < bufferSize; i++){
			lAudio[i] = output[i*nChannels    ] = ofRandomf() * volume * leftScale;
			rAudio[i] = output[i*nChannels + 1] = ofRandomf() * volume * rightScale;
		}
		*/
		
		// Brown Noise Waveform
		for (int i = 0; i < bufferSize; i++){
			lAudio[i] = output[i*nChannels    ] = ofRandomf() * volume * leftScale;
			rAudio[i] = output[i*nChannels + 1] = ofRandomf() * volume * rightScale;
		}
		
	} else {
		// Sin Waveform
		phaseAdder = 0.95f * phaseAdder + 0.05f * phaseAdderTarget;
		for (int i = 0; i < bufferSize; i++){
			phase += phaseAdder;
			float sample = sin(phase);
			lAudio[i] = output[i*nChannels    ] = sample * volume * leftScale;
			rAudio[i] = output[i*nChannels + 1] = sample * volume * rightScale;
		}
	}
}

//--------------------------------------------------------------
void testApp::touchDown(ofTouchEventArgs &touch){
	bNoise = true;
}

//--------------------------------------------------------------
void testApp::touchMoved(ofTouchEventArgs &touch){
	if( touch.id ==  0 ){
		int width = ofGetWidth();
		pan = (float) touch.x / (float)width;
	}
}

//--------------------------------------------------------------
void testApp::touchUp(ofTouchEventArgs &touch){
	bNoise = false;
}

//--------------------------------------------------------------
void testApp::touchDoubleTap(ofTouchEventArgs &touch){

}
