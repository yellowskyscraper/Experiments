package com.bbn.openmap.layer.rpf.corba.CRpfFrameProvider;

/**
* com/bbn/openmap/layer/rpf/corba/CRpfFrameProvider/XYPointHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.1"
* from com/bbn/openmap/layer/rpf/corba/CorbaRpfFrameProvider.idl
* Wednesday, March 4, 2009 5:10:37 PM EST
*/

public final class XYPointHolder implements org.omg.CORBA.portable.Streamable
{
  public com.bbn.openmap.layer.rpf.corba.CRpfFrameProvider.XYPoint value = null;

  public XYPointHolder ()
  {
  }

  public XYPointHolder (com.bbn.openmap.layer.rpf.corba.CRpfFrameProvider.XYPoint initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = com.bbn.openmap.layer.rpf.corba.CRpfFrameProvider.XYPointHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    com.bbn.openmap.layer.rpf.corba.CRpfFrameProvider.XYPointHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return com.bbn.openmap.layer.rpf.corba.CRpfFrameProvider.XYPointHelper.type ();
  }

}
