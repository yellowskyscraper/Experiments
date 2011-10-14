/**
 *	CLASS : VideoController
 *  VERSION : 1.00 
 *  DATE : 5/05/2009
 *  ACTIONSCRIPT VERSION : 3.0 
 *  AUTHOR : Stephen Braitsch : stephen@ordinarykids.com
**/

package com.ordinarykids.media.controllers {

	import flash.media.Video
	import flash.geom.Rectangle;
	import flash.net.NetConnection;
	import flash.net.NetStream;
	import flash.errors.IOError;
	import flash.events.Event;
	import flash.events.ProgressEvent;	
    import flash.events.IOErrorEvent;		
	import flash.events.NetStatusEvent;
	import flash.events.AsyncErrorEvent;
	import flash.events.EventDispatcher;
	import flash.utils.setInterval;	
	import flash.utils.clearInterval;	
	
	import com.ordinarykids.events.MediaEvent;
	
/*
	Class responsible for instantiating a new netstream 
	object and dispatching events about any loaded videos
*/
			
	public class VideoController extends MediaController implements IMediaController {
	
		private var _nc				        :NetConnection;     // single net connection //
		private var _ns				        :NetStream;         // netstream to play videos // 
		private var _video			        :Video;             // video object container//		
        private var _file                   :String;            // path to current video file //
		private var _length 		        :Number;            // duration of the current flv //
		private var _buffer	                :uint = 10;         // percent of the video to buffer before playback // 
		private var _autoplay				:Boolean = false;	// immediately start playing after buffer is full //
		private var _isplaying		        :Boolean = false;   // boolean to track toggle state //
	    private var _volume                 :Number = 1;        // valid value is 0-1 ///
		private var _trackint	            :uint;              // interval to track playback //
		private var _loadProgressInt		:uint; 				// interval to track load progress //
	
    	public function VideoController() {
		// set up netconnection, netstream and video objects //	
			_nc = new NetConnection();  _nc.connect(null); 
			_ns = new NetStream(_nc);   _ns.client = this; 
			_ns.addEventListener(NetStatusEvent.NET_STATUS, netStatusHandler);
			_ns.addEventListener(AsyncErrorEvent.ASYNC_ERROR, asyncErrorHandler);
			_video = new Video(320, 240); 
			_video.attachNetStream(_ns);
    	}
       
 //- PUBLIC SETTERS ----------------------------------------------------------------------
    
        public function set volume($v:Number):void
        {
            _volume = $v; 
            if (_volume<.15) _volume = 0; // round to 0
            if (_volume>.90) _volume = 1; // round to 1
            var st = _ns.soundTransform;
		        st.volume = _volume; _ns.soundTransform = st;		                 
        }    
        
	    public function set bufferpercent($p:uint):void 
	    {
	        _buffer = $p; 
	    }
	    
	    public function set seek($i:Number):void 
	    {
            _ns.seek($i);
	    }
	
		public function set autoplay($b:Boolean):void
		{
			_autoplay = $b;
		}
	
		public function set formatTimecode($b:Boolean):void 
		{
			_formatTimecode = $b;
		}
	  
	
 //- PUBLIC GETTERS ----------------------------------------------------------------------	    
		
		public function get video():Video
		{
			return _video;
		}
		
		public function get playing():Boolean
		{
		    return _isplaying;
		}
		
		public function get volume():Number
		{
		    return _volume;
		}
		
		public function get position():Number
		{
		// return playhead position as a percentage //	
			return _ns.time / _length;
		}
		
 //- PUBLIC METHODS ----------------------------------------------------------------------  

	    public function load($file:String):void
	    {	       
            if (_file != $file) {
        // a new file is to be loaded //                          
                this.destroy();
            }
            _file = ROOT_PATH + $file; _ns.play(_file);     
	    }
	    
        public function destroy():void
        {
            if (!_ns) return; 
            clearInterval(_trackint);  
			clearInterval(_loadProgressInt);	
            _isplaying = false; 
            _ns.close(); _video.clear();
			log('destroyed');        
        }

		public function reset():void
		{
			_ns.seek(0); 			
			log('reset');     			
		}	

		public function pause():void
		{
			_ns.pause(); _isplaying = false; 
			clearInterval(_trackint); 			
			log('paused');     						
		}
		
		public function resume():void
		{
			_ns.resume(); _isplaying = true; 
            clearInterval(_trackint);  			
			_trackint = setInterval(trackPlayback, 50);			
			log('resumed');     									
		}	
	  
        public function seekToPosition($percent:Number):void
        {
            _ns.seek( _length * $percent );          
 		// dispatch timecode so we update a numerical display
			super.dispatchTimecode(_ns.time, _length);	
		//	log('seeking to time : '+Math.floor(_length * $percent));     			
        }		

 //- REQUIRED NETSTREAM EVENT HANDLERS ----------------------------------------------------------------------
		
		public function onMetaData(info:Object):void
		{
			_length = info.duration;	
		// set buffer to percent of video length //
            _ns.bufferTime = _length*(_buffer/100);
		// auto set the vid object to fit the loaded video //			
			if (_video.width==320 || _video.height==240)
			{
			    _video.width = info.width;
			    _video.height = info.height;
			}		
            log('Setting Buffer To : '+_ns.bufferTime+' = '+_buffer+'% of '+_length+' total seconds');
		}
		
        public function asyncErrorHandler(evt:AsyncErrorEvent):void 
        {
            log(evt.text);
        }
        
        public function onCuePoint(info:Object):void 
        {
            for (var p:String in info){
                log("A Cue Point Was Found :  "+p+' -- '+info[p]);                
            }
        }
		
		private function netStatusHandler(evt:NetStatusEvent):void
		{
	        log(evt.info.code);
			if (evt.info.code=='NetStream.Play.Start') { 
			// dispatch event to display a preloader //    
                dispatchEvent(new MediaEvent(MediaEvent.FILE_OPEN, _video));
            // stop playback so we can preload video //                 
				_ns.pause(); _isplaying = false; 
				_loadProgressInt = setInterval(checkBufferProgress, 50)
			}	
		}
		
 //- BUFFER VIDEO ----------------------------------------------------------------------				
		
		private function checkBufferProgress():void
		{   
			var bl:Number = _ns.bytesLoaded;
			var bt:Number = _ns.bytesTotal;
			if (bl >= bt) clearInterval(_loadProgressInt);
			super.dispatchLoadProgress(bl, bt);			
		// check if buffer is full to begin playing //	
		    var pc:Number = (bl/bt)*100;
			if (pc > _buffer && !_buffered) {     
				_buffered = true;
        // check to auto resume playback after video has finished buffering         
                if (_autoplay) resume();
               	dispatchEvent(new MediaEvent(MediaEvent.BUFFER_FULL));      												
			}
		}

 //- TRACK PLAYBACK PROGRESS ----------------------------------------------------------------------
		
		private function trackPlayback():void
		{	
        // check to see if we've reached the end of the video //    
            if (_ns.time >= _length-.01) {
				dispatchEvent(new MediaEvent(MediaEvent.COMPLETE));  
			}	else{
				super.dispatchTimecode(_ns.time, _length);			
			}
		}	
		    
    }

}






