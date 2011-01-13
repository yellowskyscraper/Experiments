#include "testApp.h"

//--------------------------------------------------------------
void testApp::setup(){
	ofSetVerticalSync(true);
	
    //load the squirrel model - the 3ds and the texture file need to be in the same folder
    //modelLoader.loadModel("ysok/tile.3ds", 20);
	
    //you can create as many rotations as you want
    //choose which axis you want it to effect
    //you can update these rotations later on
    //modelLoader.setRotation(0, 90, 1, 0, 0);
    //modelLoader.setRotation(1, 270, 0, 0, 1);
    //modelLoader.setScale(0.0008, 0.0008, 0.0008);
    //modelLoader.setPosition(ofGetWidth()/2, ofGetHeight()/2, 50);
}

//--------------------------------------------------------------
void testApp::update(){
	
}


//--------------------------------------------------------------
void testApp::draw(){
	//glEnable (GL_DEPTH_TEST);
	//modelLoader.draw();
	//glDisable(GL_DEPTH_TEST);
	
	ofSetColor(0x000000);
    ofDrawBitmapString("fps: " + ofToString(ofGetFrameRate(), 2), 10, 15);
}

//--------------------------------------------------------------
void testApp::keyPressed  (int key){

}

//--------------------------------------------------------------
void testApp::keyReleased(int key){

}

//--------------------------------------------------------------
void testApp::mouseMoved(int x, int y ){

}

//--------------------------------------------------------------
void testApp::mouseDragged(int x, int y, int button){

}

//--------------------------------------------------------------
void testApp::mousePressed(int x, int y, int button){

}

//--------------------------------------------------------------
void testApp::mouseReleased(int x, int y, int button){

}

//--------------------------------------------------------------
void testApp::resized(int w, int h){

}

