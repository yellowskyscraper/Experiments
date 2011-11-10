class CustomMotionEventListener implements MotionEventListener {
		public void onMotionEvent(MotionEvent me) {
			PApplet.println(me.type);
		}
	}
