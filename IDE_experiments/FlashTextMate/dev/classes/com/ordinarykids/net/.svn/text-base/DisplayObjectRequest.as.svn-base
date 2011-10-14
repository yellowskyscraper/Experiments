/**
 *  CLASS : DisplayObjectRequest
 *  VERSION : 1.0
 *  DATE : 06/04/2009
 *  ACTIONSCRIPT VERSION : 3.0 
 *  AUTHOR : Stephen Braitsch : stephen@ordinarykids.com
**/

package com.ordinarykids.net {
	
	import flash.net.URLRequest;
	import flash.display.Loader;
	import flash.display.LoaderInfo;
	import flash.display.Bitmap;
	import flash.display.AVM1Movie;			
	import flash.display.MovieClip;	
			
	import flash.events.Event;
	import flash.events.IOErrorEvent;
	import flash.events.ProgressEvent;
	import flash.events.HTTPStatusEvent;
	import flash.events.SecurityErrorEvent;
	import flash.events.EventDispatcher;
	
    import com.ordinarykids.events.HTTPRequestEvent;

/*
	Class to load AS2/AS3 SWFS, JPG, PNG, and GIF Files
*/

	public class DisplayObjectRequest extends EventDispatcher
	{
		public static var ROOT_PATH:String = '';		// global static pointer to an assets directory for testing purposes //		
	 		
		private var _id						:uint = 0; 	// unique id for this load instance //
	    private var _file                   :String;    // the file currently being loaded // 
		private var _swf					:*;			// AVM1Movie or MovieClip
		private var _bitmap					:Bitmap;	
		private var _relatedContent			:Object;	// object to associate with this load request //			
			
	    private var _loader                 :Loader;	
	    private var _request                :URLRequest;
		private var _debug                  :Boolean = false; 
		
		public function DisplayObjectRequest($file:String = '', $path:String = ''):void
		{
            _loader = new Loader();        
            _loader.contentLoaderInfo.addEventListener(Event.OPEN, onRequestOpen);
            _loader.contentLoaderInfo.addEventListener(ProgressEvent.PROGRESS, onRequestLoadProgress);
            _loader.contentLoaderInfo.addEventListener(IOErrorEvent.IO_ERROR, ioErrorHandler);  
            _loader.contentLoaderInfo.addEventListener(HTTPStatusEvent.HTTP_STATUS, httpStatusHandler);
            _loader.contentLoaderInfo.addEventListener(SecurityErrorEvent.SECURITY_ERROR, onSecurityError);          
            _loader.contentLoaderInfo.addEventListener(Event.COMPLETE, onRequestLoadComplete);
		// if a file was passed into the constructor automatically start the request //		
			if ($file) load($file, $path);
		}
		
 //- PUBLIC SETTERS / GETTERS ----------------------------------------------------------------------		
		
		public function set debug($b:Boolean):void
		{
            _debug = $b;
		}
		
		public function set id($id:uint):void
		{
			_id = $id;
		}
		
		public function get id():uint
		{
			return _id;
		}
		
		public function set relatedContent($obj:Object):void 
		{
			_relatedContent = $obj;
		}
		
		public function get relatedContent():Object
		{
			return _relatedContent;
		}
		
		public function get image():Bitmap
		{
			return _bitmap;
		}	
		
		public function get swf():* 
		{
			return _swf;
		}	
		
 //- START AND STOP REQUEST ----------------------------------------------------------------------		
		
		public function load($file:String, $path:String = ''):void
		{
			_file = ROOT_PATH + $path + $file;
			_request = new URLRequest(_file);
			_loader.load(_request);				
		}
		
		public function kill():void
		{
	        try { _loader.close(); } 
				catch (e:Error) 
				{
            		log("killRequest Failed, load probably completed already");
                }  		  
            _request = null;
		}		

		
 //- EXTERNAL STATUS DISPATCHERS ----------------------------------------------------------------------

        private function onRequestOpen(evt:Event):void 
		{
            log( HTTPRequestEvent.OPEN );
        // dispatch //    
            dispatchEvent(new HTTPRequestEvent(HTTPRequestEvent.OPEN, null));
        }	
		
        private function onRequestLoadProgress(evt:ProgressEvent):void 
		{
            var bl:Number = evt.bytesLoaded;
            var bt:Number = evt.bytesTotal;
            log( HTTPRequestEvent.PROGRESS, ' : '+bl+' OF '+bt+' LOADED = '+Math.ceil((bl/bt)*100)+' %' );
        // dispatch //            
            dispatchEvent(new HTTPRequestEvent(HTTPRequestEvent.PROGRESS, {loaded:bl, total:bt, id:_id}));
        }		
		
        private function onRequestLoadComplete(evt:Event):void 
		{
        // clean up //    
			_loader = null;
            _request = null; 
  			if (evt.target.content is Bitmap) _bitmap = evt.target.content;
			if (evt.target.content is AVM1Movie) _swf = evt.target.content;	// AS2 SWF
			if (evt.target.content is MovieClip) _swf = evt.target.content;	// AS3 SWF
            log( HTTPRequestEvent.COMPLETE, ' At : '+_file);
        // dispatch //            
            dispatchEvent(new HTTPRequestEvent(HTTPRequestEvent.COMPLETE, {image:_bitmap, swf:_swf, relatedContent:_relatedContent, id:_id}));
        }	 		
	    	    
	
 //- INTERNAL ERROR HANDLERS ----------------------------------------------------------------------	
	
		private function ioErrorHandler(evt:IOErrorEvent):void
		{
		    log(HTTPRequestEvent.IO_ERROR, ' : '+_file);
		    dispatchEvent(new HTTPRequestEvent(HTTPRequestEvent.IO_ERROR));		
		}

        private function onSecurityError(evt:SecurityErrorEvent):void {
            log(HTTPRequestEvent.SECURITY_ERROR, ' : '+_file);
            dispatchEvent(new HTTPRequestEvent(HTTPRequestEvent.SECURITY_ERROR));
        }

        private function httpStatusHandler(evt:HTTPStatusEvent):void {
            log(HTTPRequestEvent.HTTP_STATUS, ' : '+evt.status);
            dispatchEvent(new HTTPRequestEvent(HTTPRequestEvent.HTTP_STATUS));
        }
        
        private function log($type:String, $msg:String = ''):void
        {
            if (_debug) trace('[ HTTPRequest ] : '+$type, $msg);            
        }	         	
		
	}
	
}



