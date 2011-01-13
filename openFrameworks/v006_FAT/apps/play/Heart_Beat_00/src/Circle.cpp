#include "Circle.h"

Circle::Circle()
{
	color = 0x00ffff;
	radius = 200;
	angle = 0;
	
	i = 0; 
	del = 0.1f; 
	stage = 0;
	
	refresh_period = ofGetElapsedTimef() + del; 
	
	location.set(400, 400, 0.0);
}
//--------------------------------------------------------------
void Circle::update()
{
	//radius += ((50 + sin(angle) * 100) - radius) * 0.02;
	//angle += 0.05f; 
	
	int top = 20;
	int midbottom = 10;
	int bottom = 0;
	
	float up = 2;
	float down1 = 0.5;
	float down2 = 0.25;
	
	switch(stage){
		case 0:  
			radius += up; 
			i += up; 
			if(i > top || i == top) stage = 1;        
			break; 
			
		case 1:		
			radius -= down1;  
			i -= down1;   
			if(i < midbottom || i == midbottom) stage = 2;  
			break;
			
		case 2:		
			radius += up; 
			i += up; 
			if(i > top || i == top) stage = 3; 
			break;
			
		case 3:		
			radius -= down2;  
			i -= down2;   
			if(i < bottom || i == bottom) stage = 0;  
			break;
	}
	
/*	
	for(i = 0; i < top; i++) {
		analogWrite(LED,i);
		delay(((60000/rate)*.1)/pmw); fast up
	}
	
	for (i = top; i > 0; i--){
		analogWrite(LED,i);
		delay(((60000/rate)*.2)/pmw); mid slow down 
	}
	
	for(i = 0; i < top; i++) {
		analogWrite(LED,i);
		delay(((60000/rate)*.1)/pmw); fast up
	}
	
	for (i = top; i > 0; i--){
		analogWrite(LED,i);
		delay(((60000/rate)*.6)/pmw); real slow down 
	} 
*/
}
//--------------------------------------------------------------
void Circle::draw()
{
	ofSetColor(color);
	ofNoFill();
	ofCircle(location.x, location.y, radius);
	
	ofFill();
	ofCircle(location.x, location.y, 2);
	ofNoFill();
	ofCircle(location.x, location.y, 2);
}





























