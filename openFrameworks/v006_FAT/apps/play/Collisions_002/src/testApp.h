#ifndef _TEST_APP
#define _TEST_APP


#include "ofMain.h"
#include "ofxVectorMath.h"
#include "Circle.h"

#define TOTAL_CIRCLES 20


class testApp : public ofBaseApp{

	public:
		void setup();
		void update();
		void draw();
		void checkCollision();
	
		Circle* circles[TOTAL_CIRCLES];
	
		ofTrueTypeFont  header;
		ofxVec3f gravity;
};

#endif
