package com.bbn.openmap.layer.rpf.corba.CRpfFrameProvider;

/**
* com/bbn/openmap/layer/rpf/corba/CRpfFrameProvider/CRFPCoverageBoxHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.1"
* from com/bbn/openmap/layer/rpf/corba/CorbaRpfFrameProvider.idl
* Wednesday, March 4, 2009 5:10:37 PM EST
*/

public final class CRFPCoverageBoxHolder implements org.omg.CORBA.portable.Streamable
{
  public com.bbn.openmap.layer.rpf.corba.CRpfFrameProvider.CRFPCoverageBox value = null;

  public CRFPCoverageBoxHolder ()
  {
  }

  public CRFPCoverageBoxHolder (com.bbn.openmap.layer.rpf.corba.CRpfFrameProvider.CRFPCoverageBox initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = com.bbn.openmap.layer.rpf.corba.CRpfFrameProvider.CRFPCoverageBoxHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    com.bbn.openmap.layer.rpf.corba.CRpfFrameProvider.CRFPCoverageBoxHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return com.bbn.openmap.layer.rpf.corba.CRpfFrameProvider.CRFPCoverageBoxHelper.type ();
  }

}