class CustomMotionEventListener implements MotionEventListener {
  void onMotionEvent(MotionEvent te) {
    println(te.type);
  }
}
