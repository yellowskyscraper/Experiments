#include "Circle.h"

Circle::Circle()
{
	radius = ofRandom(11, 100);
	//radius = 10;
	color = 0x00ffff;
	
	location.set(ofRandom(100.0f, 800.0f), radius, 0.0);
	velocity.set(0.0f, 0.0f, 0.0f);
	acceleration.set(0.0f, 0.0f, 0.0f);
	
	mass = radius;
	maximum_velocity = 5;
	bounce = 1.0;

}
//--------------------------------------------------------------
void Circle::update()
{
	
	velocity += acceleration;
	location += velocity;
	acceleration.set(0.0, 0.0, 0.0);
	
	if(location.y > (ofGetHeight() - radius)) {
		velocity.y *= -bounce;
		location.y = (ofGetHeight() - radius);
	}
	
	if(location.y < radius) {
		velocity.y *= -bounce;
		location.y = radius;
	}
	
	if(location.x > (ofGetWidth() - radius)) {
		velocity.x *= -bounce;
		location.x = (ofGetWidth() - radius);
	}
	
	if(location.x < radius) {
		velocity.x *= -bounce;
		location.x = radius;
	}
	
}
//--------------------------------------------------------------
void Circle::draw()
{
	ofSetColor(color);
	
	ofNoFill();
	ofCircle(location.x, location.y, radius);
	
	ofFill();
	ofCircle(location.x, location.y, radius - 10);
}
//--------------------------------------------------------------
void Circle::addForce(ofxVec3f force) {
	force /= mass;
	acceleration += force;
}
//--------------------------------------------------------------
void Circle::collision(Circle* c1){
	float m1 = mass;
	float m2 = c1->mass;
	float x1 = location.x;
	float x2 = c1->location.x;
	float y1 = location.y;
	float y2 = c1->location.y;
	
	float newMass = m2/m1;
	float newX = x2 - x1;
	float newY = y2 - y1;
	float newVelocityX = c1->velocity.x - velocity.x;
	float newVelocityY = c1->velocity.y - velocity.y;
	
	if((newVelocityX * newX + newVelocityY * newY) >= 0) return;
	
	float fy21 = 1.0E-12 * fabs(newY);
	float sine;
	
	if(fabs(newX) < fy21){
		if(newX < 0) { sine = -1; } else { sine = 1; }
		newX = fy21 * sine;
	}
	
	float angle = newY/newX;
	float diff = -2 * (newVelocityX + angle * newVelocityY)/((1 + angle * angle) * (1 + newMass));
	
	c1->velocity.x = c1->velocity.x + diff;
	c1->velocity.y = c1->velocity.y + angle * diff;
	
	velocity.x = velocity.x - newMass * diff;
	velocity.y = velocity.y - angle * newMass * diff;
	
}





























