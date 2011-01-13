#include "testApp.h"
#include "stdio.h"

bool locked = false;

//--------------------------------------------------------------
void testApp::setup(){
	mainAppsCount = 0;
	TO.start();
}

//--------------------------------------------------------------
void testApp::update(){
	ofBackground(0,0,0);   // black because threads are EVIL ;)
	mainAppsCount++;
}

//--------------------------------------------------------------
void testApp::draw(){
	ofSetColor(0xffffff);
	TO.draw();


    string str = "I am a the main opengl thread.\nmy current count is: ";
	str += ofToString(mainAppsCount);
    ofDrawBitmapString(str, 350, 56);


    ofSetColor(0xff0033);

    ofDrawBitmapString("press 's' to stop the thread and 'a' to start it", 50, 160);
}

//--------------------------------------------------------------
void testApp::keyPressed  (int key){

    if (key == 'a'){
        TO.start();
    } else if (key == 's'){
        TO.stop();
    }
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
void testApp::windowResized(int w, int h){

}

