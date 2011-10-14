/**
 *	CLASS : KeyboardManager
 *  VERSION : 1.10 
 *  DATE : 05/14/2009
 *  ACTIONSCRIPT VERSION : 3.0 
 *  AUTHOR : Stephen Braitsch : stephen@ordinarykids.com
**/

package com.ordinarykids.utils {

	import flash.display.Stage;
	import flash.events.Event;
	import flash.events.KeyboardEvent;
	import flash.display.DisplayObject;
	import flash.events.EventDispatcher;		

	import com.ordinarykids.events.UIEvent;		
		
	public class KeyboardManager extends EventDispatcher
	{

		private var _keys	:Array = [];	// keys we are listening to //
		private var _stage	:Stage;		
				
	// individual keys //				
		public static const SPACE			:String = 'space-bar';				
		public static const ENTER			:String = 'enter-key';
		public static const LEFT			:String = 'arrow-left';
		public static const RIGHT			:String = 'arrow-right';
		public static const UP				:String = 'arrow-up';
		public static const DOWN			:String = 'arrow-down';						
		
	// key groups //
		public static const ALL				:String = 'everything';	
		public static const ARROWS			:String = 'all-arrows';
		public static const NUMBERS			:String = 'all-numbers';
		public static const LETTERS			:String = 'all-letters';
		
	// map of all available keys //	
		public static const KEY_MAP     	:Array = [	{id:'a', code:65},	{id:'b', code:66},	{id:'c', code:67},	{id:'d', code:68},	{id:'e', code:69},
														{id:'f', code:70},	{id:'g', code:71},	{id:'h', code:72},	{id:'i', code:73},	{id:'j', code:74},	
														{id:'k', code:75},	{id:'l', code:76},	{id:'m', code:77},	{id:'n', code:78},	{id:'o', code:79},	
														{id:'p', code:80},	{id:'q', code:81},	{id:'r', code:82},	{id:'s', code:83},	{id:'t', code:84},	
														{id:'u', code:85},	{id:'v', code:86},	{id:'w', code:87},	{id:'x', code:88},	{id:'y', code:89},	
														{id:'z', code:90},
														
														{id:'0', code:48},	{id:'1', code:49},	{id:'2', code:50},	{id:'3', code:51},	{id:'4', code:52},
														{id:'5', code:53},	{id:'6', code:54},	{id:'7', code:55},	{id:'8', code:56},	{id:'9', code:57},
														
														{id:'arrow-left', code:37}, {id:'arrow-right', code:39}, {id:'arrow-up', code:38}, {id:'arrow-down', code:40},
														{id:'space-bar', code:32}, {id:'enter-key', code:13}];
                                                 	                                                   	                                
	                                                   
		public function KeyboardManager($do:DisplayObject)
		{
			if ($do.stage){
				_stage = $do.stage;
				_stage.addEventListener(KeyboardEvent.KEY_DOWN, onKeyPress);
			}	else{
				trace(this, 'The Registered D.O. Does Not Have Stage Access Yet');				
				$do.addEventListener(Event.ADDED_TO_STAGE, onAddedToStage);
			}
		}
		
		public function release():void
		{
		// allows us to disengage this instance and delete //	
			_keys = [];
			_stage.removeEventListener(KeyboardEvent.KEY_DOWN, onKeyPress);
		}
		
 //- ADD INDIVIDUAL KEYS OR KEY GROUPS USING CONSTANTS ----------------------------------------------------------------------		
		
		public function set addKey($str:String):void
		{
		// find the string in the keymap //
			for (var i:int = 0; i < KEY_MAP.length; i++)
			{
				if ($str==KEY_MAP[i].id) _keys.push(KEY_MAP[i]);
			}
			trace(this, $str);
		}
		
		public function set addKeys($str:String):void
		{
			switch($str){			
				case 'all-arrows' 	: addArrows(); 		break;
				case 'all-numbers' 	: addNumbers(); 	break;
				case 'all-letters' 	: addLetters(); 	break;
				case 'everything' 	: addEverything(); 	break;						
				default : trace(this, 'The Key Group You Are Trying To Add Was Not Found');
			}
		}
		
 //- KEY GROUPS ----------------------------------------------------------------------		
		
		private function addArrows():void
		{
			addKeyToKeyArray('arrow-left');
			addKeyToKeyArray('arrow-right');
			addKeyToKeyArray('arrow-up');
			addKeyToKeyArray('arrow-down');
		}
		
		private function addNumbers():void
		{
			for (var i:int = 0; i < 10; i++){
				addKeyToKeyArray(i.toString());
			}
		}
		
		private function addLetters():void
		{
			for (var i:int = 0; i < 26; i++){
				_keys.push(KEY_MAP[i]);
			}				
		}		
		
		private function addEverything():void
		{
			for (var i:int = 0; i < KEY_MAP.length; i++){
				_keys.push(KEY_MAP[i]);
			}			
		}
		
 //- FIND MATCHES AND CREATE THE KEY ARRAY ----------------------------------------------------------------------		
		
		private function addKeyToKeyArray($str:String):void
		{
		// find the string in the keymap //
			for (var i:int = 0; i < KEY_MAP.length; i++)
			{
				if ($str==KEY_MAP[i].id) { _keys.push(KEY_MAP[i]); return; }
			}			
			trace(this, 'The Key You Are Trying To Add Was Not Found');			
		}		
		
 //- LISTEN FOR AND DISPATCH EVENTS ----------------------------------------------------------------------
	
	    private function onKeyPress(evt:KeyboardEvent):void
	    {
//			trace('key pressed ='+evt.keyCode);
			for (var i:int = 0; i < _keys.length; i++){
				if (evt.keyCode==_keys[i].code) dispatchEvent(new UIEvent(UIEvent.KEY_PRESSED, _keys[i].id));
			}
	    }
	
		private function onAddedToStage(e:Event):void
		{
			_stage = e.target.stage;
			_stage.addEventListener(KeyboardEvent.KEY_DOWN, onKeyPress);			
		}
	
	}
	
}
