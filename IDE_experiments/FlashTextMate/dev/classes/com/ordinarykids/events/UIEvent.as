/**
 *  CLASS: MediaEvent
 *  VERSION: 1.00 
 *  DATE: 02/04/2008
 *  ACTIONSCRIPT VERSION: 3.0 
 *  AUTHOR : STEPHEN BRAITSCH : stephen@ordinarykids.com
**/

package com.ordinarykids.events {
	
	import flash.events.Event;

	public class UIEvent extends Event 
	{
	    
		public static var KEY_PRESSED  		:String = 'RegisteredKeyPressed';
		public static var KEY_RELEASED  	:String = 'RegisteredKeyReleased';
		public static var MOUSE_DOWN  		:String = 'MouseDown';
		public static var MOUSE_UP  		:String = 'MouseUp';
		public static var MOUSE_OVER  		:String = 'MouseOver';
		public static var MOUSE_OUT  		:String = 'MouseOut';		
		public static var MOUSE_CLICKED  	:String = 'MouseClicked';
		public static var MOUSE_DRAGGED  	:String = 'MouseDragged';
		public static var SCROLL	 	 	:String = 'ScrollBarScrolled';
			
			
 //- PRIVATE VARIABLES ----------------------------------------------------------------------        
        
		private var _evt_type 			:String;
		private var _evt_data 			:Object;
		
		public function UIEvent ( $type:String, $data:Object = null ) 
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