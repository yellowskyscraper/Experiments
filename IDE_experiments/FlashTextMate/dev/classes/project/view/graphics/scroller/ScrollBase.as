﻿package project.view.graphics.scroller {		import flash.display.Sprite;	import flash.display.Shape;	import flash.geom.Rectangle;	import flash.events.MouseEvent;		import project.model.Model;	import project.events.AppEvent;		import gs.TweenLite;	import gs.OverwriteManager;    import gs.easing.Quart;    import gs.easing.Expo; ////////////////////////////////////////////////////////////////////////////////////////////////////////////|////////////////////////////////////////////////////////////////////////////////////////////////////////////|////////////////////////////////////////////////////////////////////////////////////////////////////////////|////////////////////////////////////////////////////////////////////////////////////////////////////////////| //| Initial Scroller Base Functionality	public class ScrollBase extends Sprite {	    	    protected var _textNodeArray                :Array;	    protected var _textNodeContainer            :Sprite;	    protected var _textNodeMask                 :Sprite;	    	    private var _scroll                         :Sprite;	    private var _scrollhead                     :Sprite;	    private var _scrollbar                      :Shape;		private var _scrollbounds                   :Rectangle;				private var _headcolor:uint = 0x7A707F;		private var _basecolor:uint = 0xDDD8D6;				private var _headHeight:Number = 30;	    	    private var _textContainerHeight            :Number;	    private var _textContainerMarker            :Number;			    protected var _masterWidth                  :int;	    protected var _masterHeight                 :int;	    protected var _scrollHeight                 :int;	    	    protected var _buffer                       :int = 10;////////////////////////////////////////////////////////////////////////////////////////////////////////////|//| Constructor		public function ScrollBase($gui:Object) 		{				_masterWidth            = $gui.w;			_masterHeight           = $gui.h;			_scrollHeight           = $gui.s;		//| Instantiate Containers    		    _textNodeArray          = new Array();		    _textNodeContainer      = new Sprite();		    		//| Color Themes		    if($gui.theme == "BLACK"){                _headcolor = 0x555555;                _basecolor = 0x000000;		    }		    		    addChild(_textNodeContainer);		}////////////////////////////////////////////////////////////////////////////////////////////////////////////|////////////////////////////////////////////////////////////////////////////////////////////////////////////|//| Scroller Set Up & Control        public function scrollInit():void        {        //| Set Nodes In Container            var heightCount:int = 0;            var arrayLength:int = _textNodeArray.length;                        for(var i:int = 0; i < arrayLength; i++){                _textNodeArray[i].y = heightCount;                heightCount += (_textNodeArray[i].height + 20);            }                    //| Determin Scroll Head Height in Proportion to amout to be Scrolled            var containerHeight     :Number = _textNodeContainer.height;            var viewableHeight      :Number = _masterHeight;            var scrollHeadHeight    :Number = viewableHeight / (containerHeight / viewableHeight);            _headHeight = Math.round(scrollHeadHeight)                    //| Draw And Set Up Scroll Bar            _scroll = new Sprite();            _scroll.x = _masterWidth + _buffer;                    _scrollhead = new Sprite();            _scrollhead.graphics.beginFill(_headcolor);            //_scrollhead.graphics.drawRect(0, 0, 5, 30);            _scrollhead.graphics.drawRect(0, 0, 5, _headHeight);            _scrollhead.graphics.endFill();            _scrollhead.buttonMode = true;			_scrollhead.useHandCursor = true;		    _scrollhead.mouseChildren = false;        	_scrollhead.addEventListener(MouseEvent.MOUSE_DOWN, startScroll)        	_scrollhead.addEventListener(MouseEvent.MOUSE_UP, stopScroll);                    _scrollbar = new Shape();            _scrollbar.graphics.beginFill(_basecolor);            _scrollbar.graphics.drawRect(0, 0, 5, _scrollHeight);            _scrollbar.graphics.endFill();        	        	_scrollbounds = new Rectangle(0, 0, 0, (_scrollHeight - _headHeight));                    _scroll.addChild(_scrollbar);            _scroll.addChild(_scrollhead);            addChild(_scroll);                        if(_textNodeContainer.height < _masterHeight) _scroll.visible = false;                    //| Draw Mask               _textNodeMask = new Sprite();             _textNodeMask.graphics.beginFill(0x333333);             _textNodeMask.graphics.drawRect(0, 0, _masterWidth, _masterHeight);            _textNodeMask.graphics.endFill();            _textNodeMask.x = 0;            _textNodeMask.y = 0;            addChild(_textNodeMask);        //| Mask Text Collumn             _textNodeContainer.mask = _textNodeMask;        }				public function resetScroller():void        {		    _textNodeContainer.y = _scrollhead.y = 0;	    }////////////////////////////////////////////////////////////////////////////////////////////////////////////|////////////////////////////////////////////////////////////////////////////////////////////////////////////|//|	Mouse Controllers			private function scrollController(e:MouseEvent):void        {        //| designating constraints            var containerLower:Number = 0;            var containerUpper:Number = _masterHeight - _textNodeContainer.height;            var containerRange:Number = containerLower - containerUpper;            var moved:Number = _scrollhead.y;            var percentMoved:Number = moved/(_scrollHeight - _headHeight);            var containerMoved:Number = percentMoved*containerRange;            TweenLite.to(_textNodeContainer, 1, {y:(containerLower - containerMoved), ease:Quart.easeOut});		}			        private function startScroll(e:MouseEvent):void        {            _scrollhead.startDrag(false, _scrollbounds);    	    stage.addEventListener(MouseEvent.MOUSE_UP, stopScroll);    	    stage.addEventListener(MouseEvent.MOUSE_MOVE, scrollController);        }        private function stopScroll(e:MouseEvent):void         {	        stage.removeEventListener(MouseEvent.MOUSE_UP, stopScroll);    	    stage.removeEventListener(MouseEvent.MOUSE_MOVE, scrollController);            _scrollhead.stopDrag();        }////////////////////////////////////////////////////////////////////////////////////////////////////////////|		}}