package edu.exploratorium.rotary;

import java.awt.event.KeyEvent;
import processing.core.PApplet;

public class RotaryKeyboard implements IRotaryInput {
	private RotaryInterface parent;
	
	RotaryKeyboard (RotaryInterface parent, PApplet p) {
		this.parent = parent;
		init(p);
	}
	
	public void dispose () {
		//
	}
	
	/**
	 * @exclude
	 * Implementation of PApplet KeyEvent registration.
	 * Developers should not call this method directly.
	 */
	public void keyEvent (KeyEvent keyEvent) {
		// read only key presses, no other events
		if (keyEvent.getID() == KeyEvent.KEY_PRESSED) {
			char keyChar = keyEvent.getKeyChar();
			
			// filter out SHIFT press
			if (keyEvent.getKeyCode() == 16) { return; }
			
			parent.onRotaryInput(keyChar, 0);
		}		
	}
	
	private void init (PApplet p) {
		p.registerKeyEvent(this);
		
	}
}