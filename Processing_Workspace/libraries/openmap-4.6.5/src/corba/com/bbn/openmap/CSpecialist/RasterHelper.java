package com.bbn.openmap.CSpecialist;


/**
* com/bbn/openmap/CSpecialist/RasterHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.1"
* from com/bbn/openmap/layer/specialist/Specialist.idl
* Wednesday, March 4, 2009 5:10:38 PM EST
*/

abstract public class RasterHelper
{
  private static String  _id = "IDL:CSpecialist/Raster:1.0";

  public static void insert (org.omg.CORBA.Any a, com.bbn.openmap.CSpecialist.Raster that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static com.bbn.openmap.CSpecialist.Raster extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = org.omg.CORBA.ORB.init ().create_interface_tc (com.bbn.openmap.CSpecialist.RasterHelper.id (), "Raster");
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static com.bbn.openmap.CSpecialist.Raster read (org.omg.CORBA.portable.InputStream istream)
  {
    return narrow (istream.read_Object (_RasterStub.class));
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, com.bbn.openmap.CSpecialist.Raster value)
  {
    ostream.write_Object ((org.omg.CORBA.Object) value);
  }

  public static com.bbn.openmap.CSpecialist.Raster narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof com.bbn.openmap.CSpecialist.Raster)
      return (com.bbn.openmap.CSpecialist.Raster)obj;
    else if (!obj._is_a (id ()))
      throw new org.omg.CORBA.BAD_PARAM ();
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      com.bbn.openmap.CSpecialist._RasterStub stub = new com.bbn.openmap.CSpecialist._RasterStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

}
