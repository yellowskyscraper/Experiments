#ifndef _TEST_APP
#define _TEST_APP


#include "ofMain.h"
#include "ofxVectorMath.h"
#include "Circle.h"

#define TOTAL_CIRCLES 15


class testApp : public ofBaseApp{

	public:
		void setup();
		void update();
		void draw();
	
		Circle* circle;
		ofTrueTypeFont  header;
};

#endif
