/*
 *  Circle.h
 *  heartbeat
 *
 *  Created by James Hovell on 10/15/10.
 *  Copyright 2010 Yellowskyscraper. All rights reserved.
 *
 */
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

