package com.bbn.openmap.CSpecialist.RasterPackage;


/**
* com/bbn/openmap/CSpecialist/RasterPackage/RASF_updateHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.1"
* from com/bbn/openmap/layer/specialist/Specialist.idl
* Wednesday, March 4, 2009 5:10:38 PM EST
*/

abstract public class RASF_updateHelper
{
  private static String  _id = "IDL:CSpecialist/Raster/RASF_update:1.0";

  public static void insert (org.omg.CORBA.Any a, com.bbn.openmap.CSpecialist.RasterPackage.RASF_update that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static com.bbn.openmap.CSpecialist.RasterPackage.RASF_update extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      org.omg.CORBA.TypeCode _disTypeCode0;
      _disTypeCode0 = com.bbn.openmap.CSpecialist.RasterPackage.settableFieldsHelper.type ();
      org.omg.CORBA.UnionMember[] _members0 = new org.omg.CORBA.UnionMember [10];
      org.omg.CORBA.TypeCode _tcOf_members0;
      org.omg.CORBA.Any _anyOf_members0;

      // Branch for p1 (case label RASF_p1)
      _anyOf_members0 = org.omg.CORBA.ORB.init ().create_any ();
      com.bbn.openmap.CSpecialist.RasterPackage.settableFieldsHelper.insert (_anyOf_members0, com.bbn.openmap.CSpecialist.RasterPackage.settableFields.RASF_p1);
      _tcOf_members0 = com.bbn.openmap.CSpecialist.XYPointHelper.type ();
      _members0[0] = new org.omg.CORBA.UnionMember (
        "p1",
        _anyOf_members0,
        _tcOf_members0,
        null);

      // Branch for ll1 (case label RASF_ll1)
      _anyOf_members0 = org.omg.CORBA.ORB.init ().create_any ();
      com.bbn.openmap.CSpecialist.RasterPackage.settableFieldsHelper.insert (_anyOf_members0, com.bbn.openmap.CSpecialist.RasterPackage.settableFields.RASF_ll1);
      _tcOf_members0 = com.bbn.openmap.CSpecialist.LLPointHelper.type ();
      _members0[1] = new org.omg.CORBA.UnionMember (
        "ll1",
        _anyOf_members0,
        _tcOf_members0,
        null);

      // Branch for pixels (case label RASF_pixels)
      _anyOf_members0 = org.omg.CORBA.ORB.init ().create_any ();
      com.bbn.openmap.CSpecialist.RasterPackage.settableFieldsHelper.insert (_anyOf_members0, com.bbn.openmap.CSpecialist.RasterPackage.settableFields.RASF_pixels);
      _tcOf_members0 = org.omg.CORBA.ORB.init ().get_primitive_tc (org.omg.CORBA.TCKind.tk_octet);
      _tcOf_members0 = org.omg.CORBA.ORB.init ().create_sequence_tc (0, _tcOf_members0);
      _tcOf_members0 = org.omg.CORBA.ORB.init ().create_alias_tc (com.bbn.openmap.CSpecialist.pixeldataHelper.id (), "pixeldata", _tcOf_members0);
      _members0[2] = new org.omg.CORBA.UnionMember (
        "pixels",
        _anyOf_members0,
        _tcOf_members0,
        null);

      // Branch for width (case label RASF_width)
      _anyOf_members0 = org.omg.CORBA.ORB.init ().create_any ();
      com.bbn.openmap.CSpecialist.RasterPackage.settableFieldsHelper.insert (_anyOf_members0, com.bbn.openmap.CSpecialist.RasterPackage.settableFields.RASF_width);
      _tcOf_members0 = org.omg.CORBA.ORB.init ().get_primitive_tc (org.omg.CORBA.TCKind.tk_ushort);
      _members0[3] = new org.omg.CORBA.UnionMember (
        "width",
        _anyOf_members0,
        _tcOf_members0,
        null);

      // Branch for height (case label RASF_height)
      _anyOf_members0 = org.omg.CORBA.ORB.init ().create_any ();
      com.bbn.openmap.CSpecialist.RasterPackage.settableFieldsHelper.insert (_anyOf_members0, com.bbn.openmap.CSpecialist.RasterPackage.settableFields.RASF_height);
      _tcOf_members0 = org.omg.CORBA.ORB.init ().get_primitive_tc (org.omg.CORBA.TCKind.tk_ushort);
      _members0[4] = new org.omg.CORBA.UnionMember (
        "height",
        _anyOf_members0,
        _tcOf_members0,
        null);

      // Branch for x_hot (case label RASF_x_hot)
      _anyOf_members0 = org.omg.CORBA.ORB.init ().create_any ();
      com.bbn.openmap.CSpecialist.RasterPackage.settableFieldsHelper.insert (_anyOf_members0, com.bbn.openmap.CSpecialist.RasterPackage.settableFields.RASF_x_hot);
      _tcOf_members0 = org.omg.CORBA.ORB.init ().get_primitive_tc (org.omg.CORBA.TCKind.tk_ushort);
      _members0[5] = new org.omg.CORBA.UnionMember (
        "x_hot",
        _anyOf_members0,
        _tcOf_members0,
        null);

      // Branch for y_hot (case label RASF_y_hot)
      _anyOf_members0 = org.omg.CORBA.ORB.init ().create_any ();
      com.bbn.openmap.CSpecialist.RasterPackage.settableFieldsHelper.insert (_anyOf_members0, com.bbn.openmap.CSpecialist.RasterPackage.settableFields.RASF_y_hot);
      _tcOf_members0 = org.omg.CORBA.ORB.init ().get_primitive_tc (org.omg.CORBA.TCKind.tk_ushort);
      _members0[6] = new org.omg.CORBA.UnionMember (
        "y_hot",
        _anyOf_members0,
        _tcOf_members0,
        null);

      // Branch for colorsTotal (case label RASF_colorsTotal)
      _anyOf_members0 = org.omg.CORBA.ORB.init ().create_any ();
      com.bbn.openmap.CSpecialist.RasterPackage.settableFieldsHelper.insert (_anyOf_members0, com.bbn.openmap.CSpecialist.RasterPackage.settableFields.RASF_colorsTotal);
      _tcOf_members0 = org.omg.CORBA.ORB.init ().get_primitive_tc (org.omg.CORBA.TCKind.tk_ushort);
      _members0[7] = new org.omg.CORBA.UnionMember (
        "colorsTotal",
        _anyOf_members0,
        _tcOf_members0,
        null);

      // Branch for ct (case label RASF_ct)
      _anyOf_members0 = org.omg.CORBA.ORB.init ().create_any ();
      com.bbn.openmap.CSpecialist.RasterPackage.settableFieldsHelper.insert (_anyOf_members0, com.bbn.openmap.CSpecialist.RasterPackage.settableFields.RASF_ct);
      _tcOf_members0 = com.bbn.openmap.CSpecialist.CTEntryHelper.type ();
      _tcOf_members0 = org.omg.CORBA.ORB.init ().create_sequence_tc (0, _tcOf_members0);
      _tcOf_members0 = org.omg.CORBA.ORB.init ().create_alias_tc (com.bbn.openmap.CSpecialist.colorTableHelper.id (), "colorTable", _tcOf_members0);
      _members0[8] = new org.omg.CORBA.UnionMember (
        "ct",
        _anyOf_members0,
        _tcOf_members0,
        null);

      // Branch for transparent (case label RASF_transparent)
      _anyOf_members0 = org.omg.CORBA.ORB.init ().create_any ();
      com.bbn.openmap.CSpecialist.RasterPackage.settableFieldsHelper.insert (_anyOf_members0, com.bbn.openmap.CSpecialist.RasterPackage.settableFields.RASF_transparent);
      _tcOf_members0 = org.omg.CORBA.ORB.init ().get_primitive_tc (org.omg.CORBA.TCKind.tk_ushort);
      _members0[9] = new org.omg.CORBA.UnionMember (
        "transparent",
        _anyOf_members0,
        _tcOf_members0,
        null);
      __typeCode = org.omg.CORBA.ORB.init ().create_union_tc (com.bbn.openmap.CSpecialist.RasterPackage.RASF_updateHelper.id (), "RASF_update", _disTypeCode0, _members0);
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static com.bbn.openmap.CSpecialist.RasterPackage.RASF_update read (org.omg.CORBA.portable.InputStream istream)
  {
    com.bbn.openmap.CSpecialist.RasterPackage.RASF_update value = new com.bbn.openmap.CSpecialist.RasterPackage.RASF_update ();
    com.bbn.openmap.CSpecialist.RasterPackage.settableFields _dis0 = null;
    _dis0 = com.bbn.openmap.CSpecialist.RasterPackage.settableFieldsHelper.read (istream);
    switch (_dis0.value ())
    {
      case com.bbn.openmap.CSpecialist.RasterPackage.settableFields._RASF_p1:
        com.bbn.openmap.CSpecialist.XYPoint _p1 = null;
        _p1 = com.bbn.openmap.CSpecialist.XYPointHelper.read (istream);
        value.p1 (_p1);
        break;
      case com.bbn.openmap.CSpecialist.RasterPackage.settableFields._RASF_ll1:
        com.bbn.openmap.CSpecialist.LLPoint _ll1 = null;
        _ll1 = com.bbn.openmap.CSpecialist.LLPointHelper.read (istream);
        value.ll1 (_ll1);
        break;
      case com.bbn.openmap.CSpecialist.RasterPackage.settableFields._RASF_pixels:
        byte _pixels[] = null;
        _pixels = com.bbn.openmap.CSpecialist.pixeldataHelper.read (istream);
        value.pixels (_pixels);
        break;
      case com.bbn.openmap.CSpecialist.RasterPackage.settableFields._RASF_width:
        short _width = (short)0;
        _width = istream.read_ushort ();
        value.width (_width);
        break;
      case com.bbn.openmap.CSpecialist.RasterPackage.settableFields._RASF_height:
        short _height = (short)0;
        _height = istream.read_ushort ();
        value.height (_height);
        break;
      case com.bbn.openmap.CSpecialist.RasterPackage.settableFields._RASF_x_hot:
        short _x_hot = (short)0;
        _x_hot = istream.read_ushort ();
        value.x_hot (_x_hot);
        break;
      case com.bbn.openmap.CSpecialist.RasterPackage.settableFields._RASF_y_hot:
        short _y_hot = (short)0;
        _y_hot = istream.read_ushort ();
        value.y_hot (_y_hot);
        break;
      case com.bbn.openmap.CSpecialist.RasterPackage.settableFields._RASF_colorsTotal:
        short _colorsTotal = (short)0;
        _colorsTotal = istream.read_ushort ();
        value.colorsTotal (_colorsTotal);
        break;
      case com.bbn.openmap.CSpecialist.RasterPackage.settableFields._RASF_ct:
        com.bbn.openmap.CSpecialist.CTEntry _ct[] = null;
        _ct = com.bbn.openmap.CSpecialist.colorTableHelper.read (istream);
        value.ct (_ct);
        break;
      case com.bbn.openmap.CSpecialist.RasterPackage.settableFields._RASF_transparent:
        short _transparent = (short)0;
        _transparent = istream.read_ushort ();
        value.transparent (_transparent);
        break;
      default:
        value._default( _dis0 ) ;
        break;
    }
    return value;
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, com.bbn.openmap.CSpecialist.RasterPackage.RASF_update value)
  {
    com.bbn.openmap.CSpecialist.RasterPackage.settableFieldsHelper.write (ostream, value.discriminator ());
    switch (value.discriminator ().value ())
    {
      case com.bbn.openmap.CSpecialist.RasterPackage.settableFields._RASF_p1:
        com.bbn.openmap.CSpecialist.XYPointHelper.write (ostream, value.p1 ());
        break;
      case com.bbn.openmap.CSpecialist.RasterPackage.settableFields._RASF_ll1:
        com.bbn.openmap.CSpecialist.LLPointHelper.write (ostream, value.ll1 ());
        break;
      case com.bbn.openmap.CSpecialist.RasterPackage.settableFields._RASF_pixels:
        com.bbn.openmap.CSpecialist.pixeldataHelper.write (ostream, value.pixels ());
        break;
      case com.bbn.openmap.CSpecialist.RasterPackage.settableFields._RASF_width:
        ostream.write_ushort (value.width ());
        break;
      case com.bbn.openmap.CSpecialist.RasterPackage.settableFields._RASF_height:
        ostream.write_ushort (value.height ());
        break;
      case com.bbn.openmap.CSpecialist.RasterPackage.settableFields._RASF_x_hot:
        ostream.write_ushort (value.x_hot ());
        break;
      case com.bbn.openmap.CSpecialist.RasterPackage.settableFields._RASF_y_hot:
        ostream.write_ushort (value.y_hot ());
        break;
      case com.bbn.openmap.CSpecialist.RasterPackage.settableFields._RASF_colorsTotal:
        ostream.write_ushort (value.colorsTotal ());
        break;
      case com.bbn.openmap.CSpecialist.RasterPackage.settableFields._RASF_ct:
        com.bbn.openmap.CSpecialist.colorTableHelper.write (ostream, value.ct ());
        break;
      case com.bbn.openmap.CSpecialist.RasterPackage.settableFields._RASF_transparent:
        ostream.write_ushort (value.transparent ());
        break;
    }
  }

}
