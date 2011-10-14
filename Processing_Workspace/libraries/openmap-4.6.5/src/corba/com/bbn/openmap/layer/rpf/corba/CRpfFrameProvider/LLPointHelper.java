package com.bbn.openmap.layer.rpf.corba.CRpfFrameProvider;


/**
* com/bbn/openmap/layer/rpf/corba/CRpfFrameProvider/LLPointHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.1"
* from com/bbn/openmap/layer/rpf/corba/CorbaRpfFrameProvider.idl
* Wednesday, March 4, 2009 5:10:37 PM EST
*/

abstract public class LLPointHelper
{
  private static String  _id = "IDL:CRpfFrameProvider/LLPoint:1.0";

  public static void insert (org.omg.CORBA.Any a, com.bbn.openmap.layer.rpf.corba.CRpfFrameProvider.LLPoint that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static com.bbn.openmap.layer.rpf.corba.CRpfFrameProvider.LLPoint extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  private static boolean __active = false;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      synchronized (org.omg.CORBA.TypeCode.class)
      {
        if (__typeCode == null)
        {
          if (__active)
          {
            return org.omg.CORBA.ORB.init().create_recursive_tc ( _id );
          }
          __active = true;
          org.omg.CORBA.StructMember[] _members0 = new org.omg.CORBA.StructMember [2];
          org.omg.CORBA.TypeCode _tcOf_members0 = null;
          _tcOf_members0 = org.omg.CORBA.ORB.init ().get_primitive_tc (org.omg.CORBA.TCKind.tk_float);
          _members0[0] = new org.omg.CORBA.StructMember (
            "lat",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().get_primitive_tc (org.omg.CORBA.TCKind.tk_float);
          _members0[1] = new org.omg.CORBA.StructMember (
            "lon",
            _tcOf_members0,
            null);
          __typeCode = org.omg.CORBA.ORB.init ().create_struct_tc (com.bbn.openmap.layer.rpf.corba.CRpfFrameProvider.LLPointHelper.id (), "LLPoint", _members0);
          __active = false;
        }
      }
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static com.bbn.openmap.layer.rpf.corba.CRpfFrameProvider.LLPoint read (org.omg.CORBA.portable.InputStream istream)
  {
    com.bbn.openmap.layer.rpf.corba.CRpfFrameProvider.LLPoint value = new com.bbn.openmap.layer.rpf.corba.CRpfFrameProvider.LLPoint ();
    value.lat = istream.read_float ();
    value.lon = istream.read_float ();
    return value;
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, com.bbn.openmap.layer.rpf.corba.CRpfFrameProvider.LLPoint value)
  {
    ostream.write_float (value.lat);
    ostream.write_float (value.lon);
  }

}
