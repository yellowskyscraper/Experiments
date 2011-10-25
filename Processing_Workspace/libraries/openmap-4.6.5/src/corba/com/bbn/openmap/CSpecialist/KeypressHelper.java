package com.bbn.openmap.CSpecialist;


/**
* com/bbn/openmap/CSpecialist/KeypressHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.1"
* from com/bbn/openmap/layer/specialist/Specialist.idl
* Wednesday, March 4, 2009 5:10:38 PM EST
*/

abstract public class KeypressHelper
{
  private static String  _id = "IDL:CSpecialist/Keypress:1.0";

  public static void insert (org.omg.CORBA.Any a, com.bbn.openmap.CSpecialist.Keypress that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static com.bbn.openmap.CSpecialist.Keypress extract (org.omg.CORBA.Any a)
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
          org.omg.CORBA.StructMember[] _members0 = new org.omg.CORBA.StructMember [3];
          org.omg.CORBA.TypeCode _tcOf_members0 = null;
          _tcOf_members0 = com.bbn.openmap.CSpecialist.XYPointHelper.type ();
          _members0[0] = new org.omg.CORBA.StructMember (
            "point",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().get_primitive_tc (org.omg.CORBA.TCKind.tk_char);
          _members0[1] = new org.omg.CORBA.StructMember (
            "key",
            _tcOf_members0,
            null);
          _tcOf_members0 = com.bbn.openmap.CSpecialist.key_modifiersHelper.type ();
          _members0[2] = new org.omg.CORBA.StructMember (
            "modifiers",
            _tcOf_members0,
            null);
          __typeCode = org.omg.CORBA.ORB.init ().create_struct_tc (com.bbn.openmap.CSpecialist.KeypressHelper.id (), "Keypress", _members0);
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

  public static com.bbn.openmap.CSpecialist.Keypress read (org.omg.CORBA.portable.InputStream istream)
  {
    com.bbn.openmap.CSpecialist.Keypress value = new com.bbn.openmap.CSpecialist.Keypress ();
    value.point = com.bbn.openmap.CSpecialist.XYPointHelper.read (istream);
    value.key = istream.read_char ();
    value.modifiers = com.bbn.openmap.CSpecialist.key_modifiersHelper.read (istream);
    return value;
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, com.bbn.openmap.CSpecialist.Keypress value)
  {
    com.bbn.openmap.CSpecialist.XYPointHelper.write (ostream, value.point);
    ostream.write_char (value.key);
    com.bbn.openmap.CSpecialist.key_modifiersHelper.write (ostream, value.modifiers);
  }

}