#ifndef CIRCLE
#define CIRCLE

#include "ofMain.h"
#include "ofxVectorMath.h"

#define otherObjsCount 2

class Circle {
	
	public:
		Circle();
	
		void update();
		void draw();
		void addForce(ofxVec3f force);
		void collision(Circle* c1);
	
		int color;
		int radius;
		
		float mass;
		float maximum_velocity;
		float bounce;
	
		ofxVec3f location;
		ofxVec3f velocity;
		ofxVec3f acceleration;
};
#endif
