package edu.exploratorium.rotary;

import processing.core.PApplet;

public class RotaryKeyboard implements IRotaryInput {
	private RotaryInterface parent;
	
	RotaryKeyboard (RotaryInterface parent) {
		this.parent = parent;
		init();
	}
	
	public void dispose () {
		//
	}
	
	private void init () {
		// hook into PApplet keyboard handler
	}
}