package dynamicearth.app.util;

import processing.core.PApplet;
import ijeoma.motion.event.MotionEvent;
import ijeoma.motion.event.MotionEventListener;

public class EventListener implements MotionEventListener {
	
	public void onMotionEvent(MotionEvent me) {
		if (me.type.equals(MotionEvent.TWEEN_ENDED)) PApplet.println(me.getSource());
	}
}
