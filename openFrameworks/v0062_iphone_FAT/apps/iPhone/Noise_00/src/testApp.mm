#include "testApp.h"

//--------------------------------------------------------------
void testApp::setup(){
	
	// set up
	ofRegisterTouchEvents(this);
	ofxAccelerometer.setup();
	ofxiPhoneAlerts.addListener(this);
	ofxiPhoneSetOrientation(OFXIPHONE_ORIENTATION_PORTRAIT);
	ofBackground(11, 33, 40);
	
	rand.seed();
	titleCopy.loadFont("Georgia Bold.ttf", 23, true, true);
	bodyCopy.loadFont("Georgia.ttf", 10, false, true);
	
	sampleRate 			= 44100;
	phase 				= 0;
	phaseAdder 			= 0.0f;
	phaseAdderTarget 	= 0.0;
	volume				= 0.20f; 
	pan					= 0.5;
	bNoise 				= false;
	iNoise 				= 0;
	
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
	
	char titleString[255];
	char bodyString[255];
	uint bgColor;
	
	if (iNoise == 0) {
		sprintf(titleString, "Noise");
		sprintf(bodyString, "The many colors of noise. Tap me \nto experience a few of them.");
		bgColor = 0x0b2128;
		
	} else if (iNoise == 1) {
		sprintf(titleString, "White Noise");
		sprintf(bodyString, "noun Physics \na random signal (or process) with a flat \npower spectral density. In other words, \nthe signal contains equal power within a \nfixed bandwidth at any center frequency. ");
		bgColor = 0xCCCCCC;
		bgColor = 0x242424;
		
	} else if (iNoise == 2) {
		sprintf(titleString, "Pink (1/f) Noise");
		sprintf(bodyString, "noun Physics \nrandom noise having equal energy per \noctave, and so having more low-\nfrequency components than white noise.");
		bgColor = 0xeac8ea;
		bgColor = 0x26132a;
		
	} else if (iNoise == 3) {
		sprintf(titleString, "Brownian Noise");
		sprintf(bodyString, "noun Physics \nsignal noise produced by Brownian \nmotion, hence its alternative name of \nrandom walk noise.");
		bgColor = 0x2c2316;
		
	} else if (iNoise == 4) {
		sprintf(titleString, "Sine Curve");
		sprintf(bodyString, "noun \na curve representing periodic oscillations \nof constant amplitude as given by a sine \nfunction. Also called sinusoid.");
		bgColor = 0x19221a;
	}
	
	ofSetColor(bgColor);
	ofRect(0,0,boxW,boxH);
	
	ofSetColor(0xFFFFFF);
	titleCopy.drawString(titleString, 16,40);
	bodyCopy.drawString(bodyString, 20,65);
	for (int i = 0; i < initialBufferSize; i++){
		float x = ofMap(i, 0, initialBufferSize, 0, boxW, true);
		if (iNoise != 0) ofLine(leftX+x, topY + boxH - 1, leftX+x, topY + boxH + lAudio[i]*boxH*7);
	}
	
	ofSetColor(bgColor);
	ofRect(0,boxH - 2 ,boxW,2);
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
	
	if (iNoise == 0) {
		for (int i = 0; i < bufferSize; i++){
			float sample = 0;
			lAudio[i] = output[i*nChannels    ] = sample * 0 * leftScale;
			rAudio[i] = output[i*nChannels + 1] = sample * 0 * rightScale;
		}
		
	} else if (iNoise == 1) {
		for (int i = 0; i < bufferSize; i++){
			float sample = rand.white();
			lAudio[i] = output[i*nChannels    ] = sample * volume * leftScale;
			rAudio[i] = output[i*nChannels + 1] = sample * volume * rightScale;
		}
		
	} else if (iNoise == 2) {
		for (int i = 0; i < bufferSize; i++){
			float sample = rand.pink();
			lAudio[i] = output[i*nChannels    ] = sample * volume * leftScale;
			rAudio[i] = output[i*nChannels + 1] = sample * volume * rightScale;
		}
		
	} else if (iNoise == 3) {
		for (int i = 0; i < bufferSize; i++){
			float sample = rand.brown();
			lAudio[i] = output[i*nChannels    ] = sample * volume * leftScale;
			rAudio[i] = output[i*nChannels + 1] = sample * volume * rightScale;
		}
		
	} else if (iNoise == 4) {
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
void testApp::exit(){

}

//--------------------------------------------------------------
void testApp::touchDown(ofTouchEventArgs &touch){
	bNoise = true;
	
	iNoise += 1;
	if (iNoise > 4) iNoise = 0;
}

//--------------------------------------------------------------
void testApp::touchMoved(ofTouchEventArgs &touch){

}

//--------------------------------------------------------------
void testApp::touchUp(ofTouchEventArgs &touch){
	bNoise = false;
}

//--------------------------------------------------------------
void testApp::touchDoubleTap(ofTouchEventArgs &touch){

}

//--------------------------------------------------------------
void testApp::lostFocus(){

}

//--------------------------------------------------------------
void testApp::gotFocus(){

}

//--------------------------------------------------------------
void testApp::gotMemoryWarning(){

}

//--------------------------------------------------------------
void testApp::deviceOrientationChanged(int newOrientation){

}

