package app.view
{
	import flash.display.Shape;
	import flash.display.Sprite;
	import flash.geom.ColorTransform;
	import flash.geom.Transform;
	import flash.text.TextField;
	import flash.text.TextFormat;
	import flash.text.TextFieldAutoSize;
	import flash.net.URLRequest;
	import flash.display.Loader;
	import flash.events.Event;
	
	import org.robotlegs.mvcs.Actor;
	
	public class ColorScreen extends Sprite
	{
		
		private var R:int = 0;
		private var G:int = 0;
		private var B:int = 0;
		private var color:uint;
		private var generatedColor:uint;
		private var loader:Loader;
		
		private var screen:Sprite;
		private var screenA:Shape;
		private var screenB:Shape;
		private var txtA:TextField;
		private var txtB:TextField;
		private var txtFmt:TextFormat;
		
		public function ColorScreen()
		{
			super();
			
			loader = new Loader();
			loader.contentLoaderInfo.addEventListener(Event.COMPLETE, onLoaderReady);
			var fileRequest:URLRequest = new URLRequest("data/vignette.png");
			loader.load(fileRequest);
			
			txtFmt = new TextFormat;
			txtFmt.font = "Monoco";
			txtFmt.size = 25;
			
			generatedColor = generateColor();
			
			screen = new Sprite();
			
			screenA = new Shape();
			screenA.y = 384;
			screenA.graphics.beginFill(generatedColor);
			screenA.graphics.drawRect(0,0, 1024, 384);
			screenA.graphics.endFill();
			
			txtA = new TextField();
			txtA.x = 84;
			txtA.y = 384;
			txtA.autoSize = TextFieldAutoSize.LEFT;
			txtA.background = true;
			txtA.border = false;
			txtA.text= generatedColor.toString(16);
			txtA.setTextFormat(txtFmt);
			
			screenB = new Shape();
			screenB.graphics.beginFill(color);
			screenB.graphics.drawRect(0,0, 1024, 384);
			screenB.graphics.endFill();
			
			txtB = new TextField();
			txtB.x = 84;
			txtB.y = 351;
			txtB.autoSize = TextFieldAutoSize.LEFT;
			txtB.background = true;
			txtB.border = false;
			txtB.text= color.toString(16);
			txtB.setTextFormat(txtFmt);
			
			addChild(screenA)
			addChild(screenB);
			addChild(screen);
			
			addChild(txtA)
			addChild(txtB);
		}
		
		public function onLoaderReady(e:Event):void 
		{      
			screen.addChild(loader);
		}
		
		public function update():void
		{				
			screenB.graphics.beginFill(color);
			screenB.graphics.drawRect(0,0, 1024, 384);
			screenB.graphics.endFill();	
			
			txtA.text = generatedColor.toString(16);
			txtA.setTextFormat(txtFmt);
			txtB.text = color.toString(16);
			txtB.setTextFormat(txtFmt);
			
			if(color === generatedColor) {
				txtB.text = "WIN; HOLY COW I WIN AT COLOR!";
				txtB.setTextFormat(txtFmt);
			}
		}
		
		public function updateColor(c:uint):void
		{	
			color = c;
			update();
		}
		
		private function generateColor():uint
		{
			var R:int = 0 + (255 - 0) * Math.random();
			var G:int = 0 + (255 - 0) * Math.random();
			var B:int = 0 + (255 - 0) * Math.random();
			var color:uint = (R<<16 | G<<8 | B);
			
			return color;
		}
	}
} 




