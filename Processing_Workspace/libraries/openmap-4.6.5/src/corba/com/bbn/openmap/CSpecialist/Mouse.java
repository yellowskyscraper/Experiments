package com.bbn.openmap.CSpecialist;


/**
* com/bbn/openmap/CSpecialist/Mouse.java .
* Generated by the IDL-to-Java compiler (portable), version "3.1"
* from com/bbn/openmap/layer/specialist/Specialist.idl
* Wednesday, March 4, 2009 5:10:38 PM EST
*/

public final class Mouse implements org.omg.CORBA.portable.IDLEntity
{
  public com.bbn.openmap.CSpecialist.XYPoint point = null;
  public com.bbn.openmap.CSpecialist.LLPoint llpoint = null;
  public short mousebutton = (short)0;
  public boolean press = false;

  //true for press, false for release
  public com.bbn.openmap.CSpecialist.key_modifiers modifiers = null;

  public Mouse ()
  {
  } // ctor

  public Mouse (com.bbn.openmap.CSpecialist.XYPoint _point, com.bbn.openmap.CSpecialist.LLPoint _llpoint, short _mousebutton, boolean _press, com.bbn.openmap.CSpecialist.key_modifiers _modifiers)
  {
    point = _point;
    llpoint = _llpoint;
    mousebutton = _mousebutton;
    press = _press;
    modifiers = _modifiers;
  } // ctor

} // class Mouse