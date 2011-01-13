#ifndef _TEST_APP
#define _TEST_APP


#include "ofMain.h"
#include "Circle.h"

class testApp : public ofBaseApp{
	
	public:
		void setup();
		void update();
		void draw();
		
		Circle* circle;
		ofTrueTypeFont  header;
	
};

#endif
