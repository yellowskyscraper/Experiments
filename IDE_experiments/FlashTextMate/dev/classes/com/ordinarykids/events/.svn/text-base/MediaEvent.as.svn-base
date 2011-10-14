/**
 *  CLASS: MediaEvent
 *  VERSION: 1.00 
 *  DATE: 02/04/2008
 *  ACTIONSCRIPT VERSION: 3.0 
 *  AUTHOR : STEPHEN BRAITSCH : stephen@ordinarykids.com
**/

package com.ordinarykids.events {

	import flash.events.Event
	
    public class MediaEvent extends Event {

 //- LOAD EVENTS ----------------------------------------------------------------------

		public static const FILE_OPEN       : String = 'MediaFileFound';
		public static const LOAD_PROGRESS   : String = 'MediaLoadProgress';
		public static const LOAD_COMPLETE   : String = 'MediaLoadComplete';
		public static const BUFFER_FULL     : String = 'MediaBuffered';			

 //- PLAYBACK EVENTS ----------------------------------------------------------------------

        public static const TIMECODE        : String = 'TimecodeReceived';
		public static const COMPLETE        : String = 'MediaPlaybackComplete';			

 //- BUTTON EVENTS ----------------------------------------------------------------------

		public static const RESET 	 		: String = 'MediaReset';   	
        public static const PLAY_PAUSE      : String = 'PlayPauseToggled';
        public static const PREV_NEXT       : String = 'PrevNextSelection';
		public static const TOGGLE_VOLUME   : String = 'VolumeToggled';
        public static const SCRUBBER_DRAG   : String = 'ScrubberStartDrag';        
        public static const SCRUBBER_RELEASE: String = 'ScrubberRelease';	 	       
		public static const FULLSCREEN 	 	: String = 'FullScreenEvent';   	     
        
 //- PRIVATE VARIABLES ----------------------------------------------------------------------        
        
		private var _evt_type 			:String;
		private var _evt_data 			:Object;
		     
	    public function MediaEvent( $type:String, $data:Object = null )
	    {
			_evt_type = $type;
			_evt_data = $data;
			super($type, true, true);
	    }
	    
		public function get data():Object 
		{
			return _evt_data;
		}

		public override function get type():String
		{
			return _evt_type;
		}	    

    }

}






