package thegroundbeneath.app.util;

import thegroundbeneath.app.util.DataBuffer;

public interface DataLoadCallback 
{
	  public void itemLoaded(DataBuffer buffer);
	  public void itemFailed(Exception e);
	  public void queueComplete(DataBuffer[] buffers);
}
