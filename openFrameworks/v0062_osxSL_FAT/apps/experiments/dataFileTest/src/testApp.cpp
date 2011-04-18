#include "testApp.h"

#include <iostream>
using std::cerr;
using std::cout;
using std::endl;

#include <fstream>
using std::ifstream;



//--------------------------------------------------------------
void testApp::setup(){

	ifstream indata; // indata is like cin
	int num; // variable for input value
	//indata.open("http://sfports.wr.usgs.gov/~wind/windsuv.dat ");
	indata.open("temps.dat"); // opens the file
	if(!indata) { // file couldn't be opened
		cerr << "Error: file could not be opened" << endl;
		return;
	}
	indata >> num;
	while ( !indata.eof() ) { // keep reading until end-of-file
		cout << "The next number is " << num << endl;
		indata >> num; // sets EOF flag if no value found
	}
	indata.close();
	cout << "End-of-file reached.." << endl;

	
	return;

}

//--------------------------------------------------------------
void testApp::update(){

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

