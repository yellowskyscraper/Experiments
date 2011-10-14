/**
 *	CLASS : MediaPlayer
 *  VERSION : 1.00 
 *  DATE : 5/05/2009
 *  ACTIONSCRIPT VERSION : 3.0 
 *  AUTHOR : Stephen Braitsch : stephen@ordinarykids.com
**/

package com.ordinarykids.media.view {

	import flash.display.Sprite;
	import flash.events.MouseEvent;
	import com.ordinarykids.events.MediaEvent;
	import com.ordinarykids.media.controllers.*;
/*
	Abstract base class that a view component can extend 
	to provide functionality for a simple Audio/Video Player
*/
	public class MediaPlayer extends Sprite {
		 
	// media controllers //	
		private var _video		:VideoController;
		private var _audio		:AudioController;
	// view components //			
		private var _viewer		:MediaViewer;
		private var _controls	:MediaControls;
	// the active controller //
		private var _controller	:Object;

		public function MediaPlayer($viewer:MediaViewer, $controls:MediaControls)
		{
			initMediaViewer($viewer);
			initAudioController();
			initVideoController();			
			initMediaControls($controls);			
		}
		
 //- EXTERNAL METHODS ----------------------------------------------------------------------		
		
		public function loadMedia($file:String):void
		{
		// check for valid file type //	
			switch($file.substr(-3)){
				case 'flv' : loadVideo($file); break;
				case 'f4v' : loadVideo($file); break;				
				case 'mov' : loadVideo($file); break;
				case 'mp3' : loadAudio($file); break;
				case 'aac' : loadAudio($file); break;
				case 'jpg' : loadImage($file); break;
				case 'png' : loadImage($file); break;
				case 'gif' : loadImage($file); break;			
				default : trace('Error : Invalid Media Received');
			}
		}
		
		public function clearAndReset():void
		{		
			trace('player clearAndReset called');	
			if (_controller!=null) {
				reset(); pause();
	// and clear the media out of memory //		
				_controller.destroy();						
			}					
		}
	
		public function toggleVolume():void
		{
	// expose this for a global mute button ?	
		}		
		
 //- LOAD MEDIA ----------------------------------------------------------------------		
		
		private function loadVideo($file:String):void
		{
			_video.load($file);
			_controller = _video;
			_viewer.lockPreview = false;			
		}
		
		private function loadAudio($file:String):void
		{
			_audio.load($file);
			_controller = _audio;
			_viewer.lockPreview = true;			
		}
		
		private function loadImage($file:String):void
		{
			_viewer.load($file);
		}	
		
 //- INIT CONTROLLERS ----------------------------------------------------------------------		
		
		private function initVideoController():void
		{		
			_video = new VideoController(); 
			_video.debug = true;
			_video.addEventListener(MediaEvent.TIMECODE, onMediaTimeCode);			
			_video.addEventListener(MediaEvent.LOAD_PROGRESS, onLoadProgress);			
			_video.addEventListener(MediaEvent.BUFFER_FULL, onMediaBufferFull);
			_video.addEventListener(MediaEvent.COMPLETE, onMediaComplete);						
		// pass the video object into the media viewer //
			_viewer.video = _video.video;		
		}	
		
		private function initAudioController():void
		{
			_audio = new AudioController(); 
//			_audio.debug = true;
			_audio.addEventListener(MediaEvent.TIMECODE, onMediaTimeCode);			
			_audio.addEventListener(MediaEvent.LOAD_PROGRESS, onLoadProgress);			
			_audio.addEventListener(MediaEvent.BUFFER_FULL, onMediaBufferFull);
			_audio.addEventListener(MediaEvent.COMPLETE, onMediaComplete);							
		}	
		
 //- INIT VIEW COMPONENTS ----------------------------------------------------------------------		
	
		private function initMediaViewer($viewer:MediaViewer):void
		{
			_viewer = $viewer;
			_viewer.addEventListener(MediaEvent.PLAY_PAUSE, onTogglePlayback);
		}
		
		private function initMediaControls($controls:MediaControls):void
		{
			_controls = $controls;			
			_controls.addEventListener(MediaEvent.PLAY_PAUSE, onTogglePlayback);
			_controls.addEventListener(MediaEvent.TOGGLE_VOLUME, onToggleVolume);			
			_controls.addEventListener(MediaEvent.PREV_NEXT, onPrevNext);	
			_controls.addEventListener(MediaEvent.SCRUBBER_DRAG, onScrubberDown);
			_controls.addEventListener(MediaEvent.SCRUBBER_RELEASE, onScrubberUp);
			_controls.addEventListener(MediaEvent.RESET, onResetEvent);											
		}
	
 //- MEDIA EVENT HANDLERS ----------------------------------------------------------------------	
	
		private function onLoadProgress(evt:MediaEvent):void
		{
			_controls.loadPercent = evt.data.loaded/evt.data.total;
		}
	
		private function onMediaTimeCode(evt:MediaEvent):void
		{
			_controls.timecode = evt.data;
			_controls.playheadX = _controller.position;
		}
	
		private function onMediaBufferFull(evt:MediaEvent):void
		{
	// toggle the viewer and controls if we're autoplaying //			
			if (_controller.playing) resume();
		}
		
		private function onResetEvent(evt:MediaEvent):void
		{
	// always start media when we get this button event // 				
			reset(); resume();
		}				
		
		private function onMediaComplete(evt:MediaEvent):void
		{
			if (!_controls.dragging) {			
				reset(); pause();
			}
		}	
		
 //- RESET / PAUSE / RESUME ----------------------------------------------------------------------		
		
		private function reset():void
		{
			trace(this, 'reset');
			_controls.reset();
			_controller.reset();				
		}
		
		private function pause():void
		{
			trace(this, 'pause');
			_viewer.pause(); 			
			_controls.pause();	
			_controller.pause();						
		}
		
		private function resume():void
		{
			trace(this, 'resume');			
			_viewer.resume(); 			
			_controls.resume();
			_controller.resume();								
		}		
		
 //- CONTROLLER EVENT HANDLERS ----------------------------------------------------------------------			
		
		private function onTogglePlayback(evt:MediaEvent):void
		{
			var target = (evt.target==_controls) ? _viewer : _controls;		
			evt.data ? target.resume() : target.pause();
		// in all cases toggle the controller //	
			_controller.playing ? _controller.pause() : _controller.resume();
		}	

		private function onToggleVolume(evt:MediaEvent):void
		{			
		// need to allow this to be called from outside player ... 	
			_controller.volume = evt.data;
		}	
		
		private function onScrubberUp(evt:MediaEvent):void
		{
		// resume playback if we were paused //	
			if (!_controller.playing) resume();
		}	
		
		private function onScrubberDown(evt:MediaEvent):void
		{
			_controller.seekToPosition(evt.data);
		}	
		
		private function onPrevNext(evt:MediaEvent):void
		{
	// in case we have an array of files to step through...
		}																							
		
	}

}

