/**
 *	CLASS : StringUtils
 *  VERSION : 1.00 
 *  DATE : 7/14/2009
 *  ACTIONSCRIPT VERSION : 3.0 
 *  AUTHOR : Stephen Braitsch : stephen@ordinarykids.com
**/

package com.ordinarykids.utils {

	public class StringUtils {

		public static function formatToURL($str:String):String
		{
		//| remove ' / ', ' ', and line breaks  				
			var p:RegExp = / \/ | /g;
			return $str.toLowerCase().replace(p, '-').replace(/<br>/g, '');
		}
		
		public static function toTitleCase($str:String):String
		{
			return $str.charAt(0).toUpperCase() + $str.substr(1);
		}			
	
	}

}

