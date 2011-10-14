/**
 *	CLASS : AudioController
 *  VERSION : 1.00 
 *  DATE : 5/05/2009
 *  ACTIONSCRIPT VERSION : 3.0 
 *  AUTHOR : Stephen Braitsch : stephen@ordinarykids.com
**/

package com.ordinarykids.media.controllers {
   
	import flash.media.Sound;
	import flash.media.SoundChannel;
	import flash.media.SoundTransform;
	import flash.media.SoundLoaderContext;
	import flash.net.URLRequest;
    import flash.errors.IOError; 	  	 
    import flash.events.Event;
    import flash.events.ProgressEvent; 	      
    import flash.events.IOErrorEvent; 	
	import flash.utils.setInterval;	
	import flash.utils.clearInterval;	
	
	import com.ordinarykids.events.MediaEvent;
	
/*
	Class responsible for instantiating a new sound object
	and dispatching events about any loaded audio files
*/	
    
    public class AudioController extends MediaController implements IMediaController {   
        
	    private var _sound                  :Sound;
		private var _channel                :SoundChannel;
		private var _transform				:SoundTransform;
		private var _context            	:SoundLoaderContext;		
        private var _position               :Number = 0;        // playback position in seconds //
        private var _length              	:Number = 30; 		// length of sound file in seconds //
	    private var _volume                 :Number = 1;		// acceptable values between 0 - 1 //
	    private var _buffertime             :Number = 10;       // number of seconds to buffer before playback //      
		private var _isplaying		        :Boolean = false;   // boolean to track toggle state //
		private var _trackint	            :uint;              // refresh interval to track playback //	
		        
        public function AudioController()
		{ 
            _context = new SoundLoaderContext( _buffertime * 1000, false );
        }
         
 //- GETTERS / SETTERS ----------------------------------------------------------------------

        public function set volume($v:Number):void
        {
            _volume = $v; 
            if (_volume<.15) _volume = 0; // round to 0
            if (_volume>.90) _volume = 1; // round to 1
            var st = _channel.soundTransform;
		        st.volume = _volume; 
			_channel.soundTransform = st;		                 
        }    
        
	    /*public function set bufferTime($secs:uint):void 
	    {
	        _context.bufferTime = $secs * 1000; 
	    }  */ 	
		
		public function get playing():Boolean
		{
		    return _isplaying;
		}	
		
		public function get position():Number
		{
		// return playhead position as a percentage //	
			return _position / _length;
		}						      
    
 //- PUBLIC METHODS ----------------------------------------------------------------------
      
        public function load($file:String):void
        {
            if (_sound) destroy();
            _sound = new Sound(); 
			_sound.load(new URLRequest($file));	
            _sound.addEventListener(Event.OPEN, onSoundFileFound);	
            _sound.addEventListener(ProgressEvent.PROGRESS, onSoundLoadProgress);			 
            _sound.addEventListener(Event.COMPLETE, onSoundLoadComplete);            		            	
            _sound.addEventListener(IOErrorEvent.IO_ERROR, onSoundLoadFailure);
        }	      
        
        public function destroy():void
        {
            if (!_sound) return;
            clearInterval(_trackint); _channel.stop(); 
            try {_sound.close()} catch (evt:IOError) {trace(evt.message)}            
            _sound = null; _isplaying = false;
			if (debug) log('destroyed');
        } 

		public function pause():void
		{
			_isplaying = false; 
			_channel.stop();			
			clearInterval(_trackint);     		        
		}
		
		public function resume():void
		{
			_isplaying = true;
		// _sound.play takes value in milliseconds //	
			_channel = _sound.play(_position * 1000);
			clearInterval(_trackint);
			_trackint = setInterval(trackPlayback, 50);			        
		}
		
		public function reset():void
		{
			this.pause();
			_position = 0;
 		// dispatch timecode so we update a numerical display
			super.dispatchTimecode(_position, _length);			
		} 			
		
        public function seekToPosition($percent:Number):void
        {
			this.pause();
			_position = $percent*_length;
 		// dispatch timecode so we update a numerical display
			super.dispatchTimecode(_position, _length);
        }				
            

 //- LOAD AND PREPARE AUDIO REQUESTS ----------------------------------------------------------------------		
	            
        private function onSoundFileFound(evt:Event):void
        {
            _channel = new SoundChannel();
            _buffered = false;  	                
            dispatchEvent(new MediaEvent(MediaEvent.FILE_OPEN));  
        }
        
        private function onSoundLoadProgress(evt:ProgressEvent):void
        {
            _length = _sound.length / 1000;
			super.dispatchLoadProgress(_sound.bytesLoaded, _sound.bytesTotal);
            if( _sound.isBuffering && !_buffered )  {
                _buffered = true;             
                dispatchEvent(new MediaEvent(MediaEvent.BUFFER_FULL));
            }
        }
        
        private function onSoundLoadComplete(evt:Event):void
        {
			_length = _sound.length / 1000; 	
	// check that this fires when file has finished loading not finished playing.
            _sound.removeEventListener(Event.OPEN, onSoundFileFound);	
            _sound.removeEventListener(ProgressEvent.PROGRESS, onSoundLoadProgress);			 
            _sound.removeEventListener(Event.COMPLETE, onSoundLoadComplete);            		            	
            _sound.removeEventListener(IOErrorEvent.IO_ERROR, onSoundLoadFailure);               
        }
		
		private function onSoundLoadFailure(evt:IOErrorEvent):void
		{		    
			if (debug) log('Unable to load audio at : '+_sound.url);
		}        

 //- TRACK PLAYBACK PROGRESS ----------------------------------------------------------------------
		
		private function trackPlayback():void
		{
			_position = _channel.position / 1000;
		// send position and length, both in seconds //	
			super.dispatchTimecode(_position, _length);
			
        // check to see if we've reached the end of the video //    
            if (_position >= _length - 1) {
				this.reset();			
                dispatchEvent(new MediaEvent(MediaEvent.COMPLETE));  
            }
		}			  
		    
    }

}

