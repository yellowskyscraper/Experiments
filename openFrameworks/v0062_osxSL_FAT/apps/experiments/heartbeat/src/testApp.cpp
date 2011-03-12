#include "testApp.h"

//--------------------------------------------------------------
void testApp::setup(){
	ofSetFrameRate(60);
    ofBackground(0, 10, 10);
    ofEnableSmoothing();
	ofEnableAlphaBlending();
	ofSetCircleResolution(200);
	ofSetVerticalSync(true);
	
	circle = new Circle();
	
	header.loadFont("Andale Mono", 9, true, true);
	header.setLineHeight(15.0f);
}

//--------------------------------------------------------------
void testApp::update(){
	
	circle->update();
}

//--------------------------------------------------------------
void testApp::draw(){
	
	circle->draw();
	
	ofSetColor(0x00FFFF);
	header.drawString("A Simple Heart Rate", 10, 206);
}