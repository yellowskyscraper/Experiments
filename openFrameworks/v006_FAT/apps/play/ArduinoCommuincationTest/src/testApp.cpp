#include "testApp.h"

//--------------------------------------------------------------
void testApp::setup(){
	ofBackground(255,255,255);
	
	bSendSerialMessage = false;
	serial.enumerateDevices();
	serial.setup("/dev/cu.usbserial-A6008bKX", 9600);
}

//--------------------------------------------------------------
void testApp::update(){
	if(bSendSerialMessage){
		//sIf the erial.writeByte('x');
		unsigned char bytesReturned[NUM_BYTES];
		memset(bytesReturned, 0, NUM_BYTES);
		while(serial.readBytes(bytesReturned, NUM_BYTES) > 0) { }
		bSendSerialMessage = false;
	}

}

//--------------------------------------------------------------
void testApp::draw(){

}

//--------------------------------------------------------------
void testApp::keyPressed(int key){

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
void testApp::windowResized(int w, int h){

}

