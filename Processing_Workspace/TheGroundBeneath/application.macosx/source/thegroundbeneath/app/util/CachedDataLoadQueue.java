package thegroundbeneath.app.util;

import java.io.IOException;
import processing.core.PApplet;
import thegroundbeneath.app.util.DataLoadCallback;
import thegroundbeneath.app.util.DataBuffer;

class CachedDataLoadQueue extends Thread 
{
	private DataLoadCallback caller;
	private PApplet app;
	private String[] files;
	private DataBuffer[] buffers;
	 
	private boolean isAlive = true;
	 
	public int sleepTime = 30;
	 
	public CachedDataLoadQueue(PApplet app, String[] files) 
	{
		System.out.println("CachedDataLoadQueue");
		this.app = app;
		this.files = files;
		buffers = new DataBuffer[files.length];
	}
	 
	public void setCallback(DataLoadCallback cb) 
	{
		System.out.println("CachedDataLoadQueue setCallback");
		caller = cb;
		if (caller == null) isAlive = false;
	}
	
	//| some web APIs require a minimum delay between requests from the same IP
	//| you can set this duration here (in milliseconds)...
	public void setSleepTime(int delay) 
	{
		sleepTime = delay;
	}
	 
	//| main thread loop, triggered by the start() method
	public void run() 
	{
		System.out.println("CachedDataLoadQueue run");
		int id=0;
		while(isAlive && id<files.length) {
			System.out.println("CachedDataLoadQueue run while");
			
			try {
				System.out.println("CachedDataLoadQueue run while try");
				String itemKey = files[id];
				DataBuffer buffer = null;
				
				byte[] bytes = app.loadBytes(itemKey);
				
				if (bytes!=null) {
					System.out.println("CachedDataLoadQueue run while try, bytes not null");
					buffer = new DataBuffer(bytes);
					buffers[id] = buffer;
					Thread.sleep(sleepTime);
					
				} else {
					System.out.println("CachedDataLoadQueue run while try, bytes are null");
					isAlive = false;
					Exception e=new IOException("file not found: "+itemKey);
					if (caller!=null) caller.itemFailed(e);
					else e.printStackTrace();
					
					return;
				}
					
				id++;
				
			} catch(InterruptedException e) {
				System.out.println("CachedDataLoadQueue run while catch "+e);
				break;
			}
		}
		
	}
	 
	public DataBuffer[] getResultBuffers() 
	{
		return buffers;
	}
}