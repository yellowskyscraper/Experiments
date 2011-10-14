/**
 *  CLASS: BatchLoadEvent
 *  VERSION: 1.00 
 *  DATE: 03/25/2009
 *  ACTIONSCRIPT VERSION: 3.0 
 *  AUTHOR : STEPHEN BRAITSCH : stephen@ordinarykids.com
**/

package com.ordinarykids.events {
	
	import flash.events.Event;
	
	public class BatchLoadEvent extends Event 
	{
        
 //- STATUS MESSAGES ----------------------------------------------------------------------        

        public static const LOAD_PROGRESS 	:String = "SingleLoadProgress";
		public static const LOAD_COMPLETE 	:String = "SingleLoadComplete";
        public static const BATCH_PROGRESS	:String = "BatchLoadProgress";        
	    public static const BATCH_COMPLETE  :String = 'BatchLoadComplete';    
        
 //- PRIVATE VARIABLES ----------------------------------------------------------------------        
        
		private var _evt_type 			:String;
		private var _evt_data 			:Object;
		private var _progress			:Number;
		
		public function BatchLoadEvent ( $type:String, $data:Object = null ) 
		{
			_evt_type = $type;
			_evt_data = $data;
			_progress = $data.loaded / $data.total;
			super($type, true, true);
		}
		
		public function get data():Object 
		{
			return _evt_data;
		}
		
		public function get progress():Number
		{
			return _progress;
		}

		public override function get type():String
		{
			return _evt_type;
		}
		
	}
	
}