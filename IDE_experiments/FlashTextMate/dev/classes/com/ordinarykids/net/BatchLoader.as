/**
 *  CLASS : BatchLoader
 *  VERSION : 1.20 
 *  DATE : 06/10/2009
 *  ACTIONSCRIPT VERSION : 3.0 
 *  AUTHOR : STEPHEN BRAITSCH : stephen@ordinarykids.com
**/

package com.ordinarykids.net {

	import flash.events.Event;
	import flash.events.EventDispatcher;

	import com.ordinarykids.events.BatchLoadEvent;
	import com.ordinarykids.events.XMLRequestEvent;
	import com.ordinarykids.events.HTTPRequestEvent;
		
	public class BatchLoader extends EventDispatcher
	{
	
	    private var _path           	:String = '';		// path to files directory //
		private var _batch        		:Array = [];		// array of files that have successfully loaded //
		private var _requests			:Array = [];		// array of load request in this batch //
		private var _relatedContent		:Array = [];		// array of objects to be mapped to each load request //		
		
		private var _total          	:uint = 0;  		// total files to load //	 
		private var _failed				:uint = 0;			// count failed loads
		private var _counter	        :uint = 0;  		// assigns a unique id to each load request in this batch //
	    private var _debug      	    :Boolean = false;	// trace events to output window //
	
	/**
	 *	Simple class to handle multiple simultaneous loads. 
	 *  Constructor takes an XMLList or Array of file names (Strings) - required 
	 *  A path to the directory that contains the files to be loaded (String) - optional
	**/	

		public function BatchLoader($files:* = null, $path:String = '')
		{ 
			if ($files) load($files, $path);
		}		
		
 //- PUBLIC METHODS ----------------------------------------------------------------------		

		public function set debug($b:Boolean):void
		{			
			_debug = $b;
		}
		
		public function set relatedContent($obj:Array):void
		{
			_relatedContent = $obj;
		}
		
		public function load($files:*, $path:String = ''):void
		{
		// reset for the next batch //
			if 	(_requests.length) this.killRequest();
			_batch = []; 
			_requests = [];
			_counter = _failed = 0;
			
			_total = ($files is XMLList) ? $files.length() : $files.length;	
			for (var i:int = 0; i < _total; i++) {
				switch($files[i].substr(-3)){
					case 'swf' : loadDisplayObject($files[i], $path); break;
					case 'jpg' : loadDisplayObject($files[i], $path); break;
					case 'png' : loadDisplayObject($files[i], $path); break;
					case 'gif' : loadDisplayObject($files[i], $path); break;			
					default : trace(this, 'Error : Invalid Media Received');
				}								
			}
		}
	
 //- LOADERS ----------------------------------------------------------------------		
			
		private function loadDisplayObject($file:String, $path:String = ''):void
		{
			var req:DisplayObjectRequest = new DisplayObjectRequest($file, $path);
				req.relatedContent = _relatedContent[_counter];
				req.id = _counter++;
		    	req.debug = _debug;
            	req.addEventListener(HTTPRequestEvent.OPEN, onLoadInit);
            	req.addEventListener(HTTPRequestEvent.PROGRESS, onLoadProgress);
            	req.addEventListener(HTTPRequestEvent.CANCELLED, onLoadFailure);
            	req.addEventListener(HTTPRequestEvent.COMPLETE, onLoadComplete);
			_requests.push(req);
		}
		
		private function killRequest():void
		{
			for (var i:int = 0; i < _requests.length; i++) _requests[i].kill();		
		}		
		
 //- LOAD EVENT HANDLERS ----------------------------------------------------------------------	
	
        private function onLoadInit(evt:HTTPRequestEvent):void
        {
            log( evt.type, " : STARTING IMAGE "+(evt.target.id + 1)+' OF '+_total);
        }	
        
        private function onLoadProgress(evt:HTTPRequestEvent):void
        {
			var bl:Number = evt.data.loaded;
			var bt:Number = evt.data.total;
			log( evt.type, ' : '+bl+' OF '+bt+' LOADED = '+Math.ceil((bl/bt)*100)+' %' );
		// dispatch //			
			dispatchEvent(new BatchLoadEvent(BatchLoadEvent.LOAD_PROGRESS, {loaded:bl, total:bt}));
        }	          
	
		private function onLoadComplete(evt:*):void
		{  
			_batch.push(evt.data);
		    log(evt.type, " : SUCCESSFULLY LOADED "+_batch.length+' OF '+_total+' FILES IN THIS BATCH');		
		// dispatch //
		    dispatchEvent(new BatchLoadEvent(BatchLoadEvent.BATCH_PROGRESS, {loaded:_batch.length, total:_total, request:evt.data}));    
		    checkLoadQueue();
		}
		
		private function onLoadFailure(evt:HTTPRequestEvent):void
		{
			_failed++;
		    checkLoadQueue();
		}	
		
 //- INSPECT LOAD QUEUE ----------------------------------------------------------------------
 
 		private function checkLoadQueue():void
		{
			if ((_batch.length+_failed)==_total) {
		// sort loaded files by counter id //		
				_batch.sortOn('id');		
				log(BatchLoadEvent.BATCH_COMPLETE, 'Successful Loads = '+_batch.length+' : Failed Loads = '+_failed)
				dispatchEvent(new BatchLoadEvent(BatchLoadEvent.BATCH_COMPLETE, _batch));		
			}
		}		
		
 //- OUTPUT LOG ----------------------------------------------------------------------	
		
        private function log($type:String = '', $msg:String = ''):void
        {
            if (_debug) trace('[ BatchLoader ] : '+$type, $msg);
		}
		
	}
	
}

