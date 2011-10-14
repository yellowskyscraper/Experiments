/**
 *	CLASS : MediaViewer
 *  VERSION : 1.00 
 *  DATE : 5/05/2009
 *  ACTIONSCRIPT VERSION : 3.0 
 *  AUTHOR : Stephen Braitsch : stephen@ordinarykids.com
**/

package com.ordinarykids.media.view {

	import flash.media.Video;
	import flash.display.Sprite;
	import flash.display.Bitmap;
	import flash.display.DisplayObject;	
	import flash.events.MouseEvent;
	import com.ordinarykids.events.MediaEvent;
	import com.ordinarykids.net.ImageRequest;
	import com.ordinarykids.events.HTTPRequestEvent;
	import gs.TweenLite;
/*
	Main display for Videos and Image Assets and Preloaders
*/	
	public class MediaViewer extends Sprite {

	// view components defined in subclass //
		protected var _playbtn		:Sprite;
		protected var _pausebtn		:Sprite;
		protected var _preloader	:MediaPreloader;		
	// private vars //		
		private var _width			:uint;
		private var _height			:uint;
		private var _video			:Video;
		private var _preview		:Sprite;				
		private var _request		:ImageRequest;	
		private var _playing		:Boolean = false;
		private var _lockPreview	:Boolean = false;
		
		public function MediaViewer($width:uint = 500, $height:uint = 400)
		{
			_width = $width; _height = $height; 
			_request = new ImageRequest();
			_request.addEventListener(HTTPRequestEvent.COMPLETE, onImageLoadComplete);
		// create a placeholder video container //	
			_video = new Video(); addChildAt(_video, 0);
		// create a container to display preview images //
			_preview = new Sprite(); addChildAt(_preview, 1);		
			registerEventHandlers();			
			drawBackground(_width, _height);						
		}
	
 //- PUBLIC GETTERS / SETTERS ----------------------------------------------------------------------			
		
		public function get video():Video
		{
	// expose so subclass can resize / animate	
			return _video;
		}
		
		public function get image():Sprite
		{
	// expose so subclass can resize / animate			
			return _preview;
		}		
		
		public function set video($video):void
		{
			_video = $video; 
		// attach the passed video to this display object //
			this.addChildAt(_video, 0);
			_video.width = _width; _video.height = _height; 
		}
		
		public function set lockPreview($lock:Boolean):void
		{
			_lockPreview = $lock;
		}
		
 //- PUBLIC METHODS ----------------------------------------------------------------------		
		
		public function load($file:String):void
		{
	//		_preloader.show();
			_request.load($file);
		}
		
		public function drawBackground($w:uint, $h:uint, $alpha:Number = 1, $color:uint = 0x000000):void
		{
			_preview.graphics.clear();
			_preview.graphics.beginFill($color, $alpha);
			_preview.graphics.drawRect(0, 0, $w, $h);
			_preview.graphics.endFill();			
		}		
		
 //- DISPLAY IMAGE ----------------------------------------------------------------------		
		
		private function onImageLoadComplete(evt:HTTPRequestEvent):void
		{
	//		 _preloader.hide();
			var img:Bitmap = evt.data.image as Bitmap;
				img.width = _width; img.height = _height; 			
				_preview.addChild(img);
			TweenLite.from(img, .5, {alpha:0, onComplete:function(){
			// remove any previous images //	
				while(_preview.numChildren > 1) _preview.removeChildAt(0);
			}});
		}
		
 //- BUTTON DISPLAYS ----------------------------------------------------------------------		
		
		private function registerEventHandlers():void
		{
			this.buttonMode = true;
			addEventListener(MouseEvent.CLICK, togglePlayback);			
			addEventListener(MouseEvent.ROLL_OVER, showControlButton);
			addEventListener(MouseEvent.ROLL_OUT, hideControlButton);			
		}
		
		private function togglePlayback(evt:MouseEvent):void
		{
			if (!_video) return;
			_playing ? pause() : resume();		
			dispatchEvent(new MediaEvent(MediaEvent.PLAY_PAUSE, _playing));							
		}
		
		public function pause():void
		{
			_playing = false;
			TweenLite.to(_playbtn, .5, {alpha:1});
			TweenLite.to(_pausebtn, .5, {alpha:0});				
		// don't fade preview when an audio file is playing //	
			if (!_lockPreview) TweenLite.to(_preview, .5, {alpha:1});				
		}
		
		public function resume():void
		{
			_playing = true;
			TweenLite.to(_playbtn, .5, {alpha:0});
			TweenLite.to(_pausebtn, .5, {alpha:1});				
		// don't fade preview when an audio file is playing //	
			if (!_lockPreview) TweenLite.to(_preview, .5, {alpha:0});				
		}	
		
		private function showControlButton(evt:MouseEvent):void
		{
			TweenLite.to(_playing ? _pausebtn : _playbtn, .5, {alpha:1});
		}
		
		private function hideControlButton(evt:MouseEvent):void
		{	
			TweenLite.to(_playing ? _pausebtn : _playbtn, .5, {alpha:0});
		}		
		
	}

}

