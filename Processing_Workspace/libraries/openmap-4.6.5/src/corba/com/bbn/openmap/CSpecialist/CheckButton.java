package com.bbn.openmap.CSpecialist;


/**
* com/bbn/openmap/CSpecialist/CheckButton.java .
* Generated by the IDL-to-Java compiler (portable), version "3.1"
* from com/bbn/openmap/layer/specialist/Specialist.idl
* Wednesday, March 4, 2009 5:10:38 PM EST
*/


// ----------------------------------------------------------------------
public final class CheckButton implements org.omg.CORBA.portable.IDLEntity
{
  public String button_label = null;
  public boolean checked = false;

  public CheckButton ()
  {
  } // ctor

  public CheckButton (String _button_label, boolean _checked)
  {
    button_label = _button_label;
    checked = _checked;
  } // ctor

} // class CheckButton
