package com.bbn.openmap.CSpecialist.PolyPackage;

/**
* com/bbn/openmap/CSpecialist/PolyPackage/PF_updateHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.1"
* from com/bbn/openmap/layer/specialist/Specialist.idl
* Wednesday, March 4, 2009 5:10:38 PM EST
*/

public final class PF_updateHolder implements org.omg.CORBA.portable.Streamable
{
  public com.bbn.openmap.CSpecialist.PolyPackage.PF_update value = null;

  public PF_updateHolder ()
  {
  }

  public PF_updateHolder (com.bbn.openmap.CSpecialist.PolyPackage.PF_update initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = com.bbn.openmap.CSpecialist.PolyPackage.PF_updateHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    com.bbn.openmap.CSpecialist.PolyPackage.PF_updateHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return com.bbn.openmap.CSpecialist.PolyPackage.PF_updateHelper.type ();
  }

}