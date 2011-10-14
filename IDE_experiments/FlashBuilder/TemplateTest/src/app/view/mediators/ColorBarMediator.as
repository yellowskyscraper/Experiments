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

package app.view.mediators
{
	import flash.events.MouseEvent;
	
	import app.events.ApplicationEvent;
	import app.model.ColorModel;
	import org.robotlegs.mvcs.Mediator;
	import app.view.ColorBar;
	
	public class ColorBarMediator extends Mediator
	{
		[Inject] public var view:ColorBar;		
		[Inject] public var colorModel:ColorModel;
		
		public function ColorBarMediator()
		{

		}
		
		override public function onRegister():void
		{
			eventMap.mapListener(view.headR, MouseEvent.MOUSE_DOWN, onHeadDragR);
			eventMap.mapListener(view.headG, MouseEvent.MOUSE_DOWN, onHeadDragG);
			eventMap.mapListener(view.headB, MouseEvent.MOUSE_DOWN, onHeadDragB);
		}
		
		private function onHeadDragR(e:MouseEvent):void 
		{
			view.setStartDragR();
			onHeadDrag();
		}
		
		private function onHeadDragG(e:MouseEvent):void 
		{
			view.setStartDragG();
			onHeadDrag();
		}
		
		private function onHeadDragB(e:MouseEvent):void 
		{
			view.setStartDragB();
			onHeadDrag();
		}
		
		private function onHeadDrag():void
		{
			eventMap.mapListener(view.stage, MouseEvent.MOUSE_UP, onHeadDrop);
			eventMap.mapListener(view.stage, MouseEvent.MOUSE_MOVE, onHeadMove);
		}
		
		private function onHeadMove(e:MouseEvent):void 
		{
			view.updateHead();
			colorModel.recordValue(view.getCombinedColor());
			eventDispatcher.dispatchEvent(new ApplicationEvent(ApplicationEvent.COLOR_CHANGED));
		}
		
		private function onHeadDrop(e:MouseEvent):void 
		{
			view.setStopDrag();
			eventMap.unmapListener(view.stage, MouseEvent.MOUSE_UP, onHeadDrop);
			eventMap.unmapListener(view.stage, MouseEvent.MOUSE_MOVE, onHeadMove);
		}
	}
}



