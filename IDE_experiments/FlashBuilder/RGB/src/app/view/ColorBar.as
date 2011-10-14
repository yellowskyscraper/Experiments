/*
 * Copyright (c) 2009 the original author or authors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package app.view
{
	import flash.display.Shape;
	import flash.display.Sprite;
	import flash.events.MouseEvent;
	import flash.external.ExternalInterface;
	import flash.geom.Rectangle;
	
	public class ColorBar extends Sprite
	{
		private var headHeight:int = 70;
		private var mainHeignt:int = 600;
		
		private var bg:Shape;
		private var boundsRectR:Rectangle;
		private var boundsRectG:Rectangle;
		private var boundsRectB:Rectangle;
		public var headR:Sprite;
		public var headG:Sprite;
		public var headB:Sprite;
		
		public function ColorBar()
		{
			draw();
		}
		
		public function draw():void
		{
			bg = new Shape();
			//bg.graphics.beginFill(0x000000);
			//bg.graphics.drawRect(-1,-1,156, mainHeignt + 2);
			//bg.graphics.endFill();
			
			bg.graphics.beginFill(0xFFFFFF);
			bg.graphics.drawRect(0,0,50,mainHeignt);
			bg.graphics.drawRect(50,0,49,mainHeignt);
			bg.graphics.drawRect(99,0,49,mainHeignt);
			bg.graphics.endFill();
			
			headR = new Sprite();
			headR.useHandCursor = true;
			headR.buttonMode = true;
			boundsRectR = new Rectangle(0,0,0, mainHeignt - headHeight - 2);
			
			headG = new Sprite();
			headG.useHandCursor = true;
			headG.buttonMode = true;
			boundsRectG = new Rectangle(49,0,0, mainHeignt - headHeight - 2);
			
			headB = new Sprite();
			headB.useHandCursor = true;
			headB.buttonMode = true;
			boundsRectB = new Rectangle(98,0,0, mainHeignt - headHeight - 2);
			
			addChild(bg);
			addChild(headR);
			addChild(headG);
			addChild(headB);
			
			setupInterface();
		}
		
		private function setupInterface():void
		{
			updateHead();
			
			headR.x = 0;
			headR.y = 0;
			
			headG.x = 49;
			headG.y = 0;
			
			headB.x = 98;
			headB.y = 0;
			
		}
////////////////////////////////////////////////////////////////////////////|
//| Bar Head, Update and Functionality
		
		public function updateHead():void
		{
			var R:int = findValueR();
			var G:int = findValueG();
			var B:int = findValueB();
			
			headR.graphics.clear();
			headR.graphics.beginFill(R<<16 | 0<<8 | 0);
			headR.graphics.drawRoundRect(1,1,48,headHeight, 10,10);
			headR.graphics.drawRect(1,headHeight/2-1, 1, 5);
			headR.graphics.drawRect(2,headHeight/2, 1, 3);
			headR.graphics.drawRect(3,headHeight/2+1 , 1, 1);
			
			headG.graphics.clear();
			headG.graphics.beginFill(0<<16 | G<<8 | 0);
			headG.graphics.drawRoundRect(1,1,48,headHeight, 10,10);
			headG.graphics.drawRect(1,headHeight/2-1, 1, 5);
			headG.graphics.drawRect(2,headHeight/2, 1, 3);
			headG.graphics.drawRect(3,headHeight/2+1 , 1, 1);
			
			headB.graphics.clear();
			headB.graphics.beginFill(0<<16 | 0<<8 | B);
			headB.graphics.drawRoundRect(1,1,48,headHeight, 10,10);
			headB.graphics.drawRect(1,headHeight/2-1, 1, 5);
			headB.graphics.drawRect(2,headHeight/2, 1, 3);
			headB.graphics.drawRect(3,headHeight/2+1 , 1, 1);
		}
		
		public function setStartDragR():void
		{
			headR.startDrag(false, boundsRectR);
		}
		
		public function setStartDragG():void
		{
			headG.startDrag(false, boundsRectG);
		}
		
		public function setStartDragB():void
		{
			headB.startDrag(false, boundsRectB);
		}
		
		public function setStopDrag():void
		{
			headR.stopDrag();
			headG.stopDrag();
			headB.stopDrag();
		}
////////////////////////////////////////////////////////////////////////////|
//| Helpers and Converters
		
		public function getCombinedColor():uint
		{
			var R:int = findValueR();
			var G:int = findValueG();
			var B:int = findValueB();
			
			return (R<<16 | G<<8 | B);	
		}
		
		public function findValueR():int
		{
			var headPosition:Number = headR.y;
			var availableHeight:Number = boundsRectR.height;
			var value:int = (headPosition / availableHeight) * 255;
			return value;
		}
		
		public function findValueG():int
		{
			var headPosition:Number = headG.y;
			var availableHeight:Number = boundsRectG.height;
			var value:int = (headPosition / availableHeight) * 255;
			return value;
		}
		
		public function findValueB():int
		{
			var headPosition:Number = headB.y;
			var availableHeight:Number = boundsRectB.height;
			var value:int = (headPosition / availableHeight) * 255;
			return value;
		}
////////////////////////////////////////////////////////////////////////////|
	}
}




