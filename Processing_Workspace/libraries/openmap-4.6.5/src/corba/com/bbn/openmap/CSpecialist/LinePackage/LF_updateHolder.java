package com.bbn.openmap.CSpecialist.LinePackage;

/**
* com/bbn/openmap/CSpecialist/LinePackage/LF_updateHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.1"
* from com/bbn/openmap/layer/specialist/Specialist.idl
* Wednesday, March 4, 2009 5:10:38 PM EST
*/

public final class LF_updateHolder implements org.omg.CORBA.portable.Streamable
{
  public com.bbn.openmap.CSpecialist.LinePackage.LF_update value = null;

  public LF_updateHolder ()
  {
  }

  public LF_updateHolder (com.bbn.openmap.CSpecialist.LinePackage.LF_update initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = com.bbn.openmap.CSpecialist.LinePackage.LF_updateHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    com.bbn.openmap.CSpecialist.LinePackage.LF_updateHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return com.bbn.openmap.CSpecialist.LinePackage.LF_updateHelper.type ();
  }

}
