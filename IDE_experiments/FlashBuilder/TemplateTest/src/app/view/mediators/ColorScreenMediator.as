package app.view.mediators
{
	import flash.events.MouseEvent;
	
	import org.robotlegs.mvcs.Mediator;
	
	import app.events.ApplicationEvent;
	import app.model.ColorModel;
	import app.view.ColorScreen;
	
	public class ColorScreenMediator extends Mediator
	{
		[Inject] public var view:ColorScreen;
		[Inject] public var colorModel:ColorModel;
		
		public function ColorScreenMediator()
		{
			super();
		}
		
		override public function onRegister():void
		{
			eventMap.mapListener(eventDispatcher, ApplicationEvent.COLOR_CHANGED, onUpdate);
		}
		
		protected function onUpdate(e:ApplicationEvent):void
		{
			view.updateColor(colorModel.retrieveColor);
		}
	}
}