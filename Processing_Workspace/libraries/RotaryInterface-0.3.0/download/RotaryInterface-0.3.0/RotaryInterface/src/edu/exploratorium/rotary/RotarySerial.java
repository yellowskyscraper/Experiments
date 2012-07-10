package edu.exploratorium.rotary;

import edu.exploratorium.rotary.serial.Serial;
import processing.core.PApplet;
//import processing.serial.Serial;

public class RotarySerial implements IRotaryInput {
	private static final int BAUD_RATE = 9600;
	
	private RotaryInterface parent;
	private Serial serial;
	
	
	RotarySerial (RotaryInterface parent) {
		this(parent, Serial.list()[0]);
	}
	RotarySerial (RotaryInterface parent, String portName) {
		this.parent = parent;
		init(parent.p, portName);
	}
	
	public void serialEvent (Serial _serial) {
//		int input = serial.read();
		int input = serial.last();
		parent.onRotaryInput(input, 0);
	}
	
	public void dispose () {
		serial.stop();
		serial = null;
	}
	
	private void init (PApplet p, String portName) {
		serial = new Serial(p, Serial.list()[0], BAUD_RATE);
		serial.addListener(this);
		serial.buffer(1);
		
		// flush input stream on startup
		while (serial.available() > 0) {
			serial.read();
		}
	}
}