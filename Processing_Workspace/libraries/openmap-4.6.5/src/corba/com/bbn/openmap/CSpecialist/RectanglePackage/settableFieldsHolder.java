package com.bbn.openmap.CSpecialist.RectanglePackage;

/**
* com/bbn/openmap/CSpecialist/RectanglePackage/settableFieldsHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.1"
* from com/bbn/openmap/layer/specialist/Specialist.idl
* Wednesday, March 4, 2009 5:10:38 PM EST
*/

public final class settableFieldsHolder implements org.omg.CORBA.portable.Streamable
{
  public com.bbn.openmap.CSpecialist.RectanglePackage.settableFields value = null;

  public settableFieldsHolder ()
  {
  }

  public settableFieldsHolder (com.bbn.openmap.CSpecialist.RectanglePackage.settableFields initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = com.bbn.openmap.CSpecialist.RectanglePackage.settableFieldsHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    com.bbn.openmap.CSpecialist.RectanglePackage.settableFieldsHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return com.bbn.openmap.CSpecialist.RectanglePackage.settableFieldsHelper.type ();
  }

}
