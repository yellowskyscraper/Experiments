package com.bbn.openmap.CSpecialist;


/**
* com/bbn/openmap/CSpecialist/XYPointSeqHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.1"
* from com/bbn/openmap/layer/specialist/Specialist.idl
* Wednesday, March 4, 2009 5:10:37 PM EST
*/

public final class XYPointSeqHolder implements org.omg.CORBA.portable.Streamable
{
  public com.bbn.openmap.CSpecialist.XYPoint value[] = null;

  public XYPointSeqHolder ()
  {
  }

  public XYPointSeqHolder (com.bbn.openmap.CSpecialist.XYPoint[] initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = com.bbn.openmap.CSpecialist.XYPointSeqHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    com.bbn.openmap.CSpecialist.XYPointSeqHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return com.bbn.openmap.CSpecialist.XYPointSeqHelper.type ();
  }

}
