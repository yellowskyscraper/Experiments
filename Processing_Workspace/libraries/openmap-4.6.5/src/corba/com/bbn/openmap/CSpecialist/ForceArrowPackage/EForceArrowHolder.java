package com.bbn.openmap.CSpecialist.ForceArrowPackage;

/**
* com/bbn/openmap/CSpecialist/ForceArrowPackage/EForceArrowHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.1"
* from com/bbn/openmap/layer/specialist/Specialist.idl
* Wednesday, March 4, 2009 5:10:38 PM EST
*/

public final class EForceArrowHolder implements org.omg.CORBA.portable.Streamable
{
  public com.bbn.openmap.CSpecialist.ForceArrowPackage.EForceArrow value = null;

  public EForceArrowHolder ()
  {
  }

  public EForceArrowHolder (com.bbn.openmap.CSpecialist.ForceArrowPackage.EForceArrow initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = com.bbn.openmap.CSpecialist.ForceArrowPackage.EForceArrowHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    com.bbn.openmap.CSpecialist.ForceArrowPackage.EForceArrowHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return com.bbn.openmap.CSpecialist.ForceArrowPackage.EForceArrowHelper.type ();
  }

}
