package com.bbn.openmap.layer.rpf.corba.CRpfFrameProvider;


/**
* com/bbn/openmap/layer/rpf/corba/CRpfFrameProvider/CRFPCoverageBoxSeqHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.1"
* from com/bbn/openmap/layer/rpf/corba/CorbaRpfFrameProvider.idl
* Wednesday, March 4, 2009 5:10:37 PM EST
*/

abstract public class CRFPCoverageBoxSeqHelper
{
  private static String  _id = "IDL:CRpfFrameProvider/CRFPCoverageBoxSeq:1.0";

  public static void insert (org.omg.CORBA.Any a, com.bbn.openmap.layer.rpf.corba.CRpfFrameProvider.CRFPCoverageBox[] that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static com.bbn.openmap.layer.rpf.corba.CRpfFrameProvider.CRFPCoverageBox[] extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = com.bbn.openmap.layer.rpf.corba.CRpfFrameProvider.CRFPCoverageBoxHelper.type ();
      __typeCode = org.omg.CORBA.ORB.init ().create_sequence_tc (0, __typeCode);
      __typeCode = org.omg.CORBA.ORB.init ().create_alias_tc (com.bbn.openmap.layer.rpf.corba.CRpfFrameProvider.CRFPCoverageBoxSeqHelper.id (), "CRFPCoverageBoxSeq", __typeCode);
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static com.bbn.openmap.layer.rpf.corba.CRpfFrameProvider.CRFPCoverageBox[] read (org.omg.CORBA.portable.InputStream istream)
  {
    com.bbn.openmap.layer.rpf.corba.CRpfFrameProvider.CRFPCoverageBox value[] = null;
    int _len0 = istream.read_long ();
    value = new com.bbn.openmap.layer.rpf.corba.CRpfFrameProvider.CRFPCoverageBox[_len0];
    for (int _o1 = 0;_o1 < value.length; ++_o1)
      value[_o1] = com.bbn.openmap.layer.rpf.corba.CRpfFrameProvider.CRFPCoverageBoxHelper.read (istream);
    return value;
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, com.bbn.openmap.layer.rpf.corba.CRpfFrameProvider.CRFPCoverageBox[] value)
  {
    ostream.write_long (value.length);
    for (int _i0 = 0;_i0 < value.length; ++_i0)
      com.bbn.openmap.layer.rpf.corba.CRpfFrameProvider.CRFPCoverageBoxHelper.write (ostream, value[_i0]);
  }

}
