#include "testApp.h"

//--------------------------------------------------------------
void testApp::setup(){
	
	ofSetFrameRate(60);
	ofBackground(11, 33, 40);
    ofEnableSmoothing();
	ofEnableAlphaBlending();
	ofSetCircleResolution(200);
	ofSetVerticalSync(true);
	
	circle = new Circle();
	header.loadFont("Georgia Bold.ttf", 23, true, true);
}

//--------------------------------------------------------------
void testApp::update(){
	
	circle->update();
}

//--------------------------------------------------------------
void testApp::draw(){
	
	circle->draw();
	
	ofSetColor(0xFFFFFF);
	header.drawString("Heartbeat", 16,40);
}