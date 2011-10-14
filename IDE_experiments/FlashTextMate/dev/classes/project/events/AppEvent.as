package project.events {

	import flash.events.Event;
////////////////////////////////////////////////////////////////////////////////////////////////////////////|
////////////////////////////////////////////////////////////////////////////////////////////////////////////|
////////////////////////////////////////////////////////////////////////////////////////////////////////////|
////////////////////////////////////////////////////////////////////////////////////////////////////////////| 
//| Application Specific Events	

    public class AppEvent extends Event {
    
    //| Application Events
		public static const XML_READY   			:String = 'AllXMLFilesLoaded';		
		  
	//| Scene Cues
        public static const SCENE_TRANSITIONING 	:String = 'SceneTransitioning';
        public static const SCENE_FINISHED      	:String = 'SceneFinished';     
        public static const EXIT_COMPLETE       	:String = 'ExitComplete';	
        public static const ENTER_COMPLETE      	:String = 'EnterComplete';
        
	//| Event Variables
		private var _evt_type 						:String;
		private var _evt_data 						:Object;
		     
	    public function AppEvent($type:String, $data:Object = null)
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
////////////////////////////////////////////////////////////////////////////////////////////////////////////| 
    }

}






