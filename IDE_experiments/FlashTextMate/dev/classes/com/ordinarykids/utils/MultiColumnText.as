/**
 *	CLASS : MultiColumnText
 *  VERSION : 1.00 
 *  DATE : 7/14/2009
 *  ACTIONSCRIPT VERSION : 3.0 
 *  AUTHOR : Stephen Braitsch : stephen@ordinarykids.com
**/

package com.ordinarykids.utils {

	import flash.text.TextField;

/*
	Simple class to flow a large string of text across multiple textfields. 
*/

	public class MultiColumnText {

		private static var _columns:Array;

		public function MultiColumnText($tfields:Array)
		{
			_columns = $tfields;
		}
		
		public function set text($txt:String):void
		{
			_columns[0].htmlText = $txt;
			for (var i:int = 0; i < _columns.length-1; i++)
			{
				flowFieldToField(_columns[i], _columns[i+1]);
			}
		}
		
		private function flowFieldToField($tf1:TextField, $tf2:TextField):void
		{
			var nextCharIndex:uint = $tf1.getLineOffset($tf1.bottomScrollV);
		// remove any preceding whitespace from the next column  //
			$tf2.htmlText = $tf1.text.substr(nextCharIndex).replace(/^\s+/, '');
			$tf1.text.substr(0, nextCharIndex);
		}		
	
	}

}
