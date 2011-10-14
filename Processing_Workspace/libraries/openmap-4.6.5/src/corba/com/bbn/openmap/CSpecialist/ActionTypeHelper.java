package com.bbn.openmap.CSpecialist;


/**
* com/bbn/openmap/CSpecialist/ActionTypeHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.1"
* from com/bbn/openmap/layer/specialist/Specialist.idl
* Wednesday, March 4, 2009 5:10:38 PM EST
*/

abstract public class ActionTypeHelper
{
  private static String  _id = "IDL:CSpecialist/ActionType:1.0";

  public static void insert (org.omg.CORBA.Any a, com.bbn.openmap.CSpecialist.ActionType that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static com.bbn.openmap.CSpecialist.ActionType extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = org.omg.CORBA.ORB.init ().create_enum_tc (com.bbn.openmap.CSpecialist.ActionTypeHelper.id (), "ActionType", new String[] { "UpdateGraphics", "UpdatePalette", "InfoText", "PlainText", "HTMLText", "URL"} );
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static com.bbn.openmap.CSpecialist.ActionType read (org.omg.CORBA.portable.InputStream istream)
  {
    return com.bbn.openmap.CSpecialist.ActionType.from_int (istream.read_long ());
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, com.bbn.openmap.CSpecialist.ActionType value)
  {
    ostream.write_long (value.value ());
  }

}
