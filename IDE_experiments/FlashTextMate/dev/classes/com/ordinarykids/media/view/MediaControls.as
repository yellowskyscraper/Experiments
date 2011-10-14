/**
 *	CLASS : MediaControls
 *  VERSION : 1.00 
 *  DATE : 5/05/2009
 *  ACTIONSCRIPT VERSION : 3.0 
 *  AUTHOR : Stephen Braitsch : stephen@ordinarykids.com
**/

package com.ordinarykids.media.view {

	import flash.geom.Rectangle;
	import flash.display.Sprite;
	import flash.text.TextField;
	import flash.events.Event;
	import flash.events.MouseEvent;
	import com.ordinarykids.events.MediaEvent;
	import gs.TweenLite;
	import gs.easing.*;
/*
	Abstract class from which a libary symbol can extend 
	so that we can customize the view from project to project 
*/
	public class MediaControls extends Sprite {

	// required instance vars //
		protected var _playbtn			:Sprite;
		protected var _pausebtn			:Sprite;
		
	// optional //	
		protected var _volumeOn			:Sprite;
		protected var _volumeOff		:Sprite;
		protected var _nextbtn			:Sprite;
		protected var _prevbtn			:Sprite;
		protected var _fullscreen		:Sprite;
		protected var _resetButton		:Sprite;
	
	// scrub controls //	
		protected var _playhead			:Sprite;
		protected var _playbar			:Sprite;
		protected var _loadbar			:Sprite;
		protected var _playheadHomeX	:uint = 0;	// xposition of playhead when media has reset //	
		protected var _playheadWidth	:uint;		// width of playhead graphic without invisible padding //
		protected var _padding			:uint = 5;	// invisible padding around buttons to increase hit area //

	// timecode display //
		protected var _total_seconds	:TextField;
		protected var _total_minutes	:TextField;			
		protected var _elapsed_seconds 	:TextField;
		protected var _elapsed_minutes 	:TextField;
		
		private var _lock		:Boolean = false;	// allow locking if we need to adjust controls //
		private var _dragging	:Boolean = false;	// playhead dragging
		private var _fsActive	:Boolean = false; 	// fullscreen active

		public function MediaControls() { }
		
		protected function initialize():void
		{
			if ( _playbtn ) 	registerButton(_playbtn);
			if ( _pausebtn ) 	registerButton(_pausebtn);
			if ( _volumeOn ) 	registerButton(_volumeOn);
			if ( _volumeOff ) 	registerButton(_volumeOff);
			if ( _nextbtn ) 	registerButton(_nextbtn);
			if ( _prevbtn ) 	registerButton(_prevbtn);
			if ( _fullscreen )	registerButton(_fullscreen);	
			if ( _resetButton )	registerButton(_resetButton);					
			if ( _playhead )	registerPlayHead();									
		}
		
		protected function registerButton($btn:Sprite):void
		{
			$btn.buttonMode = true;
			$btn.addEventListener(MouseEvent.CLICK, onButtonSelection);
			addMousePadding($btn);
		}		
		
 //- PUBLIC GETTERS / SETTERS----------------------------------------------------------------------		
		
		public function set lock($lock:Boolean):void
		{
			_lock = $lock;
		}
		
		public function set timecode($time:Object):void
		{
			_total_seconds.text   = $time.total.seconds;
			_total_minutes.text   = $time.total.minutes;					
			_elapsed_seconds.text = $time.elapsed.seconds;
			_elapsed_minutes.text = $time.elapsed.minutes;	
		}	
		
		public function set playheadX($percent:Number):void
		{
			if (_lock) return;
//			trace('percent = '+$percent, _playhead.x);			
			if (!_dragging) _playhead.x = _playheadHomeX + ((_playbar.width-_playheadWidth) * $percent);
		}	
		
		public function set loadPercent($p:Number):void
		{
			if (_lock) return;
			_loadbar.scaleX = $p;
		}
		
		public function get dragging():Boolean
		{
			return _dragging;
		}
		
 //- PUBLIC METHODS ----------------------------------------------------------------------		
		
		public function reset():void
		{
//			trace(this, 'reset called');
			_elapsed_seconds.text = '00';
			_elapsed_minutes.text = '00';
			TweenLite.to(_playhead, .5, {x:_playbar.x, ease:Strong.easeOut});			
		}
		
		public function pause():void
		{
			_playbtn.visible = true;
			_pausebtn.visible = false;			
		}
		
		public function resume():void
		{
			_playbtn.visible = false;
			_pausebtn.visible = true;			
		}
		
		public function togglePlayback(evt:MouseEvent = null):void
		{
			(_playbtn.visible) ? resume() : pause();
			if (evt) dispatchEvent(new MediaEvent(MediaEvent.PLAY_PAUSE, (evt.target==_playbtn) ? 1 : 0));		
		}	
		
		public function toggleVolume(evt:MouseEvent = null):void
		{
			_volumeOn.visible = !_volumeOn.visible;
			_volumeOff.visible = !_volumeOff.visible;
			if (evt) dispatchEvent(new MediaEvent(MediaEvent.TOGGLE_VOLUME, (evt.target==_volumeOn) ? 1 : 0));			
		}				

 //- BUTTON CONTROLS ----------------------------------------------------------------------		
	
		private function onButtonSelection(evt:MouseEvent):void
		{
			switch(evt.target){
				case _playbtn 		: togglePlayback(evt); break;
				case _pausebtn 		: togglePlayback(evt); break;
				case _volumeOn 		: toggleVolume(evt); break;
				case _volumeOff 	: toggleVolume(evt); break;
				case _nextbtn   	: changeMedia(evt); break;
				case _prevbtn 		: changeMedia(evt); break;
				case _fullscreen	: dispatchFullScreen(); break;
				case _resetButton	: dispatchResetEvent(); break;				
			}
		}
		
		private function addMousePadding($btn:Sprite):void
		{
			$btn.graphics.beginFill(0xFF0000, 0);
			$btn.graphics.drawRect(-_padding, -_padding, $btn.width+(_padding*2), $btn.height+(_padding*2))			
			$btn.graphics.endFill();
		}
		
		private function changeMedia(evt:MouseEvent):void
		{
			dispatchEvent(new MediaEvent(MediaEvent.PREV_NEXT, (evt.target==_nextbtn) ? 1 : 0));
		}			
		
		private function dispatchFullScreen():void
		{
			_fsActive = !_fsActive;
			dispatchEvent(new MediaEvent(MediaEvent.FULLSCREEN, _fsActive));
		}
		
		private function dispatchResetEvent():void
		{
			dispatchEvent(new MediaEvent(MediaEvent.RESET));
		}		
		
 //- PLAYHEAD SCRUBBING ----------------------------------------------------------------------		
		
		private function registerPlayHead():void
		{
			_playheadWidth = _playhead.width;
			_playhead.buttonMode = true;
			_playhead.addEventListener(MouseEvent.MOUSE_DOWN, onPlayheadDrag);
		// give our playhead some invisible padding //
			addMousePadding(_playhead);
		}		
		
		private function onPlayheadDrag(evt:MouseEvent):void
		{
			if (_loadbar.width-_playheadWidth < 0) return;
			_dragging = true;
	        _playhead.startDrag(false, new Rectangle(_playbar.x, _playbar.y, _loadbar.width-_playheadWidth, 0));
            _playhead.addEventListener(Event.ENTER_FRAME, dispatchScrubPercent);			
			_playhead.stage.addEventListener(MouseEvent.MOUSE_UP, onPlayheadRelease);
		}
		
		private function onPlayheadRelease(evt:MouseEvent):void
		{
			_dragging = false;
			_playhead.stopDrag();
			_playhead.removeEventListener(Event.ENTER_FRAME, dispatchScrubPercent);
			_playhead.stage.removeEventListener(MouseEvent.MOUSE_UP, onPlayheadRelease);			
			dispatchEvent(new MediaEvent(MediaEvent.SCRUBBER_RELEASE));
		}
		
		private function dispatchScrubPercent(evt:Event):void
		{	
			var percent:Number = (_playhead.x-_playheadHomeX)/(_playbar.width-_playheadWidth);
			dispatchEvent(new MediaEvent(MediaEvent.SCRUBBER_DRAG, percent));			
		}
		
	}

}

