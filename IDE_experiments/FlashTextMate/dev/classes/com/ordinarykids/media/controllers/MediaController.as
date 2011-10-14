/**
 *	CLASS : MediaController
 *  VERSION : 1.00 
 *  DATE : 5/05/2009
 *  ACTIONSCRIPT VERSION : 3.0 
 *  AUTHOR : Stephen Braitsch : stephen@ordinarykids.com
**/

package com.ordinarykids.media.controllers {

	import flash.events.EventDispatcher;
	import com.ordinarykids.events.MediaEvent;

/*
	Base class for the AudioController and VideoController classes
	to group common event dispatching for UI and load events 
*/	
	
	public class MediaController extends EventDispatcher {

		public static var ROOT_PATH:String = '';			// global static pointer to an assets directory for testing purposes //		

		protected var _formatTimecode	:Boolean = true;	// auto convert timecode to rounded seconds and minutes //
		protected var _buffered			:Boolean = false;	// if the min amount of file has loaded //
		public var debug				:Boolean = false;	// trace log messages to the console //
		
		protected function dispatchTimecode($pos:Number, $len:Number):void
		{
		// first round values to integers //	
			$pos = Math.floor($pos); 
			$len = Math.floor($len);		
            var esecs:String = (_formatTimecode) ? doubleDigit($pos%60) : $pos.toString();
		    var emins:String = (_formatTimecode) ? doubleDigit($pos/60) : ($pos/60).toString();
			var tsecs:String = (_formatTimecode) ? doubleDigit($len%60)	: ($len%60).toString();
			var tmins:String = (_formatTimecode) ? doubleDigit($len/60)	: ($len/60).toString();

            dispatchEvent(new MediaEvent(MediaEvent.TIMECODE, { elapsed : {seconds:esecs, minutes:emins}, 
                                                                total   : {seconds:tsecs, minutes:tmins} }));				
		}
		
		protected function dispatchLoadProgress($loaded:Number, $total:Number):void
		{
            log(MediaEvent.LOAD_PROGRESS+ ' = '+($loaded/1024/1024)+' of '+($total/1024/1024)+' MBS');			
			dispatchEvent(new MediaEvent(MediaEvent.LOAD_PROGRESS, {loaded:$loaded, total:$total}));	
			
			if ($loaded==$total) dispatchEvent(new MediaEvent(MediaEvent.LOAD_COMPLETE));
		}
		
		protected function log($msg:String):void
		{
			if (debug) trace(this, $msg);
		}
		
		public function get buffered():Boolean
		{
			return _buffered;
		}
		
 //- UTILITYS ----------------------------------------------------------------------		
		
        private function doubleDigit($i:Number):String
        {
            var n:uint = Math.floor($i);
            if (n<10) {
				return '0'+n;
			}	else{
				return n.toString();
			}
        }		
	
	}

}

