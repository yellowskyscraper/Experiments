package thegroundbeneath.app.util;

import processing.core.PApplet;
import processing.core.PImage;
import thegroundbeneath.app.util.DataBuffer;

public class QueueListener implements DataLoadCallback {

  CachedDataLoadQueue queue;
  public boolean isMediaLoaded = false;
  public PImage[] images;
 
  public QueueListener(PApplet parent, String[] files) 
  {
    queue = new CachedDataLoadQueue(parent, files);
    queue.setCallback(this);
    queue.start();
    PApplet.println("QueueListener Doc");
  }
 
  // this method is called after a file has been downloaded successfully
  public void itemLoaded(DataBuffer buffer) 
  {
	PApplet.println("QueueListener itemloaded: "+ buffer);
  }

  // called each time there was an error with downloading a file
  public void itemFailed(Exception e) 
  {
	PApplet.println("QueueListener itemFailed: "+ e);
  }

  // final callback to say the whole queue has been processed
  public void queueComplete(DataBuffer[] buffers) 
  {
	PApplet.println("QueueListener queueComplete: "+buffers.length+" loaded");
    images = new PImage[buffers.length];
    for(int i = 0; i < buffers.length; i++) {
      images[i] = buffers[i].getAsImage();
    }
    isMediaLoaded = true;
  }
}
