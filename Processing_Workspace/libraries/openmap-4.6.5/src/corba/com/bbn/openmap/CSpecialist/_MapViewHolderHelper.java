package com.bbn.openmap.CSpecialist;


/**
* com/bbn/openmap/CSpecialist/_MapViewHolderHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.1"
* from com/bbn/openmap/layer/specialist/Specialist.idl
* Wednesday, March 4, 2009 5:10:38 PM EST
*/

abstract public class _MapViewHolderHelper
{
  private static String  _id = "IDL:CSpecialist/MapViewHolder:1.0";

  public static void insert (org.omg.CORBA.Any a, com.bbn.openmap.CSpecialist._MapViewHolder that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static com.bbn.openmap.CSpecialist._MapViewHolder extract (org.omg.CORBA.Any a)
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
          _tcOf_members0 = com.bbn.openmap.CSpecialist.LLPointHelper.type ();
          _members0[0] = new org.omg.CORBA.StructMember (
            "nwcorner",
            _tcOf_members0,
            null);
          _tcOf_members0 = com.bbn.openmap.CSpecialist.LLPointHelper.type ();
          _members0[1] = new org.omg.CORBA.StructMember (
            "secorner",
            _tcOf_members0,
            null);
          __typeCode = org.omg.CORBA.ORB.init ().create_struct_tc (com.bbn.openmap.CSpecialist._MapViewHolderHelper.id (), "MapViewHolder", _members0);
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

  public static com.bbn.openmap.CSpecialist._MapViewHolder read (org.omg.CORBA.portable.InputStream istream)
  {
    com.bbn.openmap.CSpecialist._MapViewHolder value = new com.bbn.openmap.CSpecialist._MapViewHolder ();
    value.nwcorner = com.bbn.openmap.CSpecialist.LLPointHelper.read (istream);
    value.secorner = com.bbn.openmap.CSpecialist.LLPointHelper.read (istream);
    return value;
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, com.bbn.openmap.CSpecialist._MapViewHolder value)
  {
    com.bbn.openmap.CSpecialist.LLPointHelper.write (ostream, value.nwcorner);
    com.bbn.openmap.CSpecialist.LLPointHelper.write (ostream, value.secorner);
  }

}
