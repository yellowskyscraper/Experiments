package com.bbn.openmap.CSpecialist;

/**
* com/bbn/openmap/CSpecialist/ServerHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.1"
* from com/bbn/openmap/layer/specialist/Specialist.idl
* Wednesday, March 4, 2009 5:10:38 PM EST
*/


//------------------------------------------------------------
public final class ServerHolder implements org.omg.CORBA.portable.Streamable
{
  public com.bbn.openmap.CSpecialist.Server value = null;

  public ServerHolder ()
  {
  }

  public ServerHolder (com.bbn.openmap.CSpecialist.Server initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = com.bbn.openmap.CSpecialist.ServerHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    com.bbn.openmap.CSpecialist.ServerHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return com.bbn.openmap.CSpecialist.ServerHelper.type ();
  }

}