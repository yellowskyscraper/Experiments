#ifndef CIRCLE
#define CIRCLE

#include "ofMain.h"
#include "ofxVectorMath.h"

class Circle {
	
	public:
	Circle();
	void update();
	void draw();
	
	int color;
	float radius;
	ofxVec3f location;
		
	float angle;
	
	float i;   
	int stage;  
	float del;  
	unsigned long refresh_period;  
	
};
#endif
