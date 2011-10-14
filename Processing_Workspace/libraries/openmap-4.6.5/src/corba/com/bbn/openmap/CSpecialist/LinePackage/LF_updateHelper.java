package com.bbn.openmap.CSpecialist.LinePackage;


/**
* com/bbn/openmap/CSpecialist/LinePackage/LF_updateHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.1"
* from com/bbn/openmap/layer/specialist/Specialist.idl
* Wednesday, March 4, 2009 5:10:38 PM EST
*/

abstract public class LF_updateHelper
{
  private static String  _id = "IDL:CSpecialist/Line/LF_update:1.0";

  public static void insert (org.omg.CORBA.Any a, com.bbn.openmap.CSpecialist.LinePackage.LF_update that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static com.bbn.openmap.CSpecialist.LinePackage.LF_update extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      org.omg.CORBA.TypeCode _disTypeCode0;
      _disTypeCode0 = com.bbn.openmap.CSpecialist.LinePackage.settableFieldsHelper.type ();
      org.omg.CORBA.UnionMember[] _members0 = new org.omg.CORBA.UnionMember [4];
      org.omg.CORBA.TypeCode _tcOf_members0;
      org.omg.CORBA.Any _anyOf_members0;

      // Branch for p1 (case label LF_p1)
      _anyOf_members0 = org.omg.CORBA.ORB.init ().create_any ();
      com.bbn.openmap.CSpecialist.LinePackage.settableFieldsHelper.insert (_anyOf_members0, com.bbn.openmap.CSpecialist.LinePackage.settableFields.LF_p1);
      _tcOf_members0 = com.bbn.openmap.CSpecialist.XYPointHelper.type ();
      _members0[0] = new org.omg.CORBA.UnionMember (
        "p1",
        _anyOf_members0,
        _tcOf_members0,
        null);

      // Branch for p2 (case label LF_p2)
      _anyOf_members0 = org.omg.CORBA.ORB.init ().create_any ();
      com.bbn.openmap.CSpecialist.LinePackage.settableFieldsHelper.insert (_anyOf_members0, com.bbn.openmap.CSpecialist.LinePackage.settableFields.LF_p2);
      _tcOf_members0 = com.bbn.openmap.CSpecialist.XYPointHelper.type ();
      _members0[1] = new org.omg.CORBA.UnionMember (
        "p2",
        _anyOf_members0,
        _tcOf_members0,
        null);

      // Branch for ll1 (case label LF_ll1)
      _anyOf_members0 = org.omg.CORBA.ORB.init ().create_any ();
      com.bbn.openmap.CSpecialist.LinePackage.settableFieldsHelper.insert (_anyOf_members0, com.bbn.openmap.CSpecialist.LinePackage.settableFields.LF_ll1);
      _tcOf_members0 = com.bbn.openmap.CSpecialist.LLPointHelper.type ();
      _members0[2] = new org.omg.CORBA.UnionMember (
        "ll1",
        _anyOf_members0,
        _tcOf_members0,
        null);

      // Branch for ll2 (case label LF_ll2)
      _anyOf_members0 = org.omg.CORBA.ORB.init ().create_any ();
      com.bbn.openmap.CSpecialist.LinePackage.settableFieldsHelper.insert (_anyOf_members0, com.bbn.openmap.CSpecialist.LinePackage.settableFields.LF_ll2);
      _tcOf_members0 = com.bbn.openmap.CSpecialist.LLPointHelper.type ();
      _members0[3] = new org.omg.CORBA.UnionMember (
        "ll2",
        _anyOf_members0,
        _tcOf_members0,
        null);
      __typeCode = org.omg.CORBA.ORB.init ().create_union_tc (com.bbn.openmap.CSpecialist.LinePackage.LF_updateHelper.id (), "LF_update", _disTypeCode0, _members0);
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static com.bbn.openmap.CSpecialist.LinePackage.LF_update read (org.omg.CORBA.portable.InputStream istream)
  {
    com.bbn.openmap.CSpecialist.LinePackage.LF_update value = new com.bbn.openmap.CSpecialist.LinePackage.LF_update ();
    com.bbn.openmap.CSpecialist.LinePackage.settableFields _dis0 = null;
    _dis0 = com.bbn.openmap.CSpecialist.LinePackage.settableFieldsHelper.read (istream);
    switch (_dis0.value ())
    {
      case com.bbn.openmap.CSpecialist.LinePackage.settableFields._LF_p1:
        com.bbn.openmap.CSpecialist.XYPoint _p1 = null;
        _p1 = com.bbn.openmap.CSpecialist.XYPointHelper.read (istream);
        value.p1 (_p1);
        break;
      case com.bbn.openmap.CSpecialist.LinePackage.settableFields._LF_p2:
        com.bbn.openmap.CSpecialist.XYPoint _p2 = null;
        _p2 = com.bbn.openmap.CSpecialist.XYPointHelper.read (istream);
        value.p2 (_p2);
        break;
      case com.bbn.openmap.CSpecialist.LinePackage.settableFields._LF_ll1:
        com.bbn.openmap.CSpecialist.LLPoint _ll1 = null;
        _ll1 = com.bbn.openmap.CSpecialist.LLPointHelper.read (istream);
        value.ll1 (_ll1);
        break;
      case com.bbn.openmap.CSpecialist.LinePackage.settableFields._LF_ll2:
        com.bbn.openmap.CSpecialist.LLPoint _ll2 = null;
        _ll2 = com.bbn.openmap.CSpecialist.LLPointHelper.read (istream);
        value.ll2 (_ll2);
        break;
    }
    return value;
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, com.bbn.openmap.CSpecialist.LinePackage.LF_update value)
  {
    com.bbn.openmap.CSpecialist.LinePackage.settableFieldsHelper.write (ostream, value.discriminator ());
    switch (value.discriminator ().value ())
    {
      case com.bbn.openmap.CSpecialist.LinePackage.settableFields._LF_p1:
        com.bbn.openmap.CSpecialist.XYPointHelper.write (ostream, value.p1 ());
        break;
      case com.bbn.openmap.CSpecialist.LinePackage.settableFields._LF_p2:
        com.bbn.openmap.CSpecialist.XYPointHelper.write (ostream, value.p2 ());
        break;
      case com.bbn.openmap.CSpecialist.LinePackage.settableFields._LF_ll1:
        com.bbn.openmap.CSpecialist.LLPointHelper.write (ostream, value.ll1 ());
        break;
      case com.bbn.openmap.CSpecialist.LinePackage.settableFields._LF_ll2:
        com.bbn.openmap.CSpecialist.LLPointHelper.write (ostream, value.ll2 ());
        break;
    }
  }

}
