package edu.exploratorium.rotary;

public class RotaryEvent {
	public static final int ROTARY_CHANGE = 1;
	public static final int OUT_OF_ROTARY_RANGE = 2;
	
	private int type;
	private float speed;
	private int channel;
	private int rawValue;
	
	public RotaryEvent (int type, float speed, int channel, int rawValue) {
		this.type = type;
		this.speed = speed;
		this.channel = channel;
		this.rawValue = rawValue;
	}
	
	/**
	 * The event type.
	 * Handlers should switch on this type
	 * to respond accordingly to the event.
	 */
	public int type () {
		return type;
	}
	
	public float speed () {
		return speed;
	}
	
	public int channel () {
		return channel;
	}
	
	public int rawValue () {
		return rawValue;
	}
	
	
	@Override
	public String toString () {
		return ("Event::"+ type);
	}
}