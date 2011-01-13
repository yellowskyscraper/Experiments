#include "testApp.h"

//--------------------------------------------------------------
void testApp::setup(){
	ofSetFrameRate(60);
    ofBackground(0, 10, 10);
    ofEnableSmoothing();
	ofEnableAlphaBlending();
	ofSetCircleResolution(200);
	ofSetVerticalSync(true);
	
	for(int i=0; i<TOTAL_CIRCLES; i++){
		circles[i] = new Circle();
	}
	
	header.loadFont("Andale Mono", 9, true, true);
	header.setLineHeight(15.0f);
	
	gravity = ofxVec3f(0.0f, 10.0f, 0.0f);
}

//--------------------------------------------------------------
void testApp::update(){
	
	for(int i=0; i<TOTAL_CIRCLES; i++){
		circles[i]->addForce(gravity);
		circles[i]->update();
	}

	checkCollision();
}

//--------------------------------------------------------------
void testApp::draw(){
	
	for(int i=0; i<TOTAL_CIRCLES; i++){
		circles[i]->draw();
	}
	
	ofSetColor(0x00FFFF);
	header.drawString("Yellowskyscraper :: Ordinary Kids \nApplied Physics Prototype - Collision Resolution 002", 10,18);
}
//--------------------------------------------------------------
void testApp::checkCollision(){
	int i, j;
	
	for(i=0; i<TOTAL_CIRCLES-1; i++){
		for(j=i+1; j<TOTAL_CIRCLES; j++){
			float dx = circles[i]->location.x - circles[j]->location.x;
			float dy = circles[i]->location.y - circles[j]->location.y;
			float dist = sqrt (dx * dx + dy * dy);
			
			if(dist < (circles[i]->radius + circles[j]->radius)){
				circles[i]->collision(circles[j]);
			}
			
		}
	}
}

