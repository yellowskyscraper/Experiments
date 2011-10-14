package com.bbn.openmap.CSpecialist.RasterPackage;


/**
* com/bbn/openmap/CSpecialist/RasterPackage/RASF_update.java .
* Generated by the IDL-to-Java compiler (portable), version "3.1"
* from com/bbn/openmap/layer/specialist/Specialist.idl
* Wednesday, March 4, 2009 5:10:38 PM EST
*/

public final class RASF_update implements org.omg.CORBA.portable.IDLEntity
{
  private com.bbn.openmap.CSpecialist.XYPoint ___p1;
  private com.bbn.openmap.CSpecialist.LLPoint ___ll1;
  private byte[] ___pixels;
  private short ___width;
  private short ___height;
  private short ___x_hot;
  private short ___y_hot;
  private short ___colorsTotal;
  private com.bbn.openmap.CSpecialist.CTEntry[] ___ct;
  private short ___transparent;
  private com.bbn.openmap.CSpecialist.RasterPackage.settableFields __discriminator;
  private boolean __uninitialized = true;

  public RASF_update ()
  {
  }

  public com.bbn.openmap.CSpecialist.RasterPackage.settableFields discriminator ()
  {
    if (__uninitialized)
      throw new org.omg.CORBA.BAD_OPERATION ();
    return __discriminator;
  }

  public com.bbn.openmap.CSpecialist.XYPoint p1 ()
  {
    if (__uninitialized)
      throw new org.omg.CORBA.BAD_OPERATION ();
    verifyp1 (__discriminator);
    return ___p1;
  }

  public void p1 (com.bbn.openmap.CSpecialist.XYPoint value)
  {
    __discriminator = com.bbn.openmap.CSpecialist.RasterPackage.settableFields.RASF_p1;
    ___p1 = value;
    __uninitialized = false;
  }

  public void p1 (com.bbn.openmap.CSpecialist.RasterPackage.settableFields discriminator, com.bbn.openmap.CSpecialist.XYPoint value)
  {
    verifyp1 (discriminator);
    __discriminator = discriminator;
    ___p1 = value;
    __uninitialized = false;
  }

  private void verifyp1 (com.bbn.openmap.CSpecialist.RasterPackage.settableFields discriminator)
  {
    if (discriminator != com.bbn.openmap.CSpecialist.RasterPackage.settableFields.RASF_p1)
      throw new org.omg.CORBA.BAD_OPERATION ();
  }

  public com.bbn.openmap.CSpecialist.LLPoint ll1 ()
  {
    if (__uninitialized)
      throw new org.omg.CORBA.BAD_OPERATION ();
    verifyll1 (__discriminator);
    return ___ll1;
  }

  public void ll1 (com.bbn.openmap.CSpecialist.LLPoint value)
  {
    __discriminator = com.bbn.openmap.CSpecialist.RasterPackage.settableFields.RASF_ll1;
    ___ll1 = value;
    __uninitialized = false;
  }

  public void ll1 (com.bbn.openmap.CSpecialist.RasterPackage.settableFields discriminator, com.bbn.openmap.CSpecialist.LLPoint value)
  {
    verifyll1 (discriminator);
    __discriminator = discriminator;
    ___ll1 = value;
    __uninitialized = false;
  }

  private void verifyll1 (com.bbn.openmap.CSpecialist.RasterPackage.settableFields discriminator)
  {
    if (discriminator != com.bbn.openmap.CSpecialist.RasterPackage.settableFields.RASF_ll1)
      throw new org.omg.CORBA.BAD_OPERATION ();
  }

  public byte[] pixels ()
  {
    if (__uninitialized)
      throw new org.omg.CORBA.BAD_OPERATION ();
    verifypixels (__discriminator);
    return ___pixels;
  }

  public void pixels (byte[] value)
  {
    __discriminator = com.bbn.openmap.CSpecialist.RasterPackage.settableFields.RASF_pixels;
    ___pixels = value;
    __uninitialized = false;
  }

  public void pixels (com.bbn.openmap.CSpecialist.RasterPackage.settableFields discriminator, byte[] value)
  {
    verifypixels (discriminator);
    __discriminator = discriminator;
    ___pixels = value;
    __uninitialized = false;
  }

  private void verifypixels (com.bbn.openmap.CSpecialist.RasterPackage.settableFields discriminator)
  {
    if (discriminator != com.bbn.openmap.CSpecialist.RasterPackage.settableFields.RASF_pixels)
      throw new org.omg.CORBA.BAD_OPERATION ();
  }

  public short width ()
  {
    if (__uninitialized)
      throw new org.omg.CORBA.BAD_OPERATION ();
    verifywidth (__discriminator);
    return ___width;
  }

  public void width (short value)
  {
    __discriminator = com.bbn.openmap.CSpecialist.RasterPackage.settableFields.RASF_width;
    ___width = value;
    __uninitialized = false;
  }

  public void width (com.bbn.openmap.CSpecialist.RasterPackage.settableFields discriminator, short value)
  {
    verifywidth (discriminator);
    __discriminator = discriminator;
    ___width = value;
    __uninitialized = false;
  }

  private void verifywidth (com.bbn.openmap.CSpecialist.RasterPackage.settableFields discriminator)
  {
    if (discriminator != com.bbn.openmap.CSpecialist.RasterPackage.settableFields.RASF_width)
      throw new org.omg.CORBA.BAD_OPERATION ();
  }

  public short height ()
  {
    if (__uninitialized)
      throw new org.omg.CORBA.BAD_OPERATION ();
    verifyheight (__discriminator);
    return ___height;
  }

  public void height (short value)
  {
    __discriminator = com.bbn.openmap.CSpecialist.RasterPackage.settableFields.RASF_height;
    ___height = value;
    __uninitialized = false;
  }

  public void height (com.bbn.openmap.CSpecialist.RasterPackage.settableFields discriminator, short value)
  {
    verifyheight (discriminator);
    __discriminator = discriminator;
    ___height = value;
    __uninitialized = false;
  }

  private void verifyheight (com.bbn.openmap.CSpecialist.RasterPackage.settableFields discriminator)
  {
    if (discriminator != com.bbn.openmap.CSpecialist.RasterPackage.settableFields.RASF_height)
      throw new org.omg.CORBA.BAD_OPERATION ();
  }

  public short x_hot ()
  {
    if (__uninitialized)
      throw new org.omg.CORBA.BAD_OPERATION ();
    verifyx_hot (__discriminator);
    return ___x_hot;
  }

  public void x_hot (short value)
  {
    __discriminator = com.bbn.openmap.CSpecialist.RasterPackage.settableFields.RASF_x_hot;
    ___x_hot = value;
    __uninitialized = false;
  }

  public void x_hot (com.bbn.openmap.CSpecialist.RasterPackage.settableFields discriminator, short value)
  {
    verifyx_hot (discriminator);
    __discriminator = discriminator;
    ___x_hot = value;
    __uninitialized = false;
  }

  private void verifyx_hot (com.bbn.openmap.CSpecialist.RasterPackage.settableFields discriminator)
  {
    if (discriminator != com.bbn.openmap.CSpecialist.RasterPackage.settableFields.RASF_x_hot)
      throw new org.omg.CORBA.BAD_OPERATION ();
  }

  public short y_hot ()
  {
    if (__uninitialized)
      throw new org.omg.CORBA.BAD_OPERATION ();
    verifyy_hot (__discriminator);
    return ___y_hot;
  }

  public void y_hot (short value)
  {
    __discriminator = com.bbn.openmap.CSpecialist.RasterPackage.settableFields.RASF_y_hot;
    ___y_hot = value;
    __uninitialized = false;
  }

  public void y_hot (com.bbn.openmap.CSpecialist.RasterPackage.settableFields discriminator, short value)
  {
    verifyy_hot (discriminator);
    __discriminator = discriminator;
    ___y_hot = value;
    __uninitialized = false;
  }

  private void verifyy_hot (com.bbn.openmap.CSpecialist.RasterPackage.settableFields discriminator)
  {
    if (discriminator != com.bbn.openmap.CSpecialist.RasterPackage.settableFields.RASF_y_hot)
      throw new org.omg.CORBA.BAD_OPERATION ();
  }

  public short colorsTotal ()
  {
    if (__uninitialized)
      throw new org.omg.CORBA.BAD_OPERATION ();
    verifycolorsTotal (__discriminator);
    return ___colorsTotal;
  }

  public void colorsTotal (short value)
  {
    __discriminator = com.bbn.openmap.CSpecialist.RasterPackage.settableFields.RASF_colorsTotal;
    ___colorsTotal = value;
    __uninitialized = false;
  }

  public void colorsTotal (com.bbn.openmap.CSpecialist.RasterPackage.settableFields discriminator, short value)
  {
    verifycolorsTotal (discriminator);
    __discriminator = discriminator;
    ___colorsTotal = value;
    __uninitialized = false;
  }

  private void verifycolorsTotal (com.bbn.openmap.CSpecialist.RasterPackage.settableFields discriminator)
  {
    if (discriminator != com.bbn.openmap.CSpecialist.RasterPackage.settableFields.RASF_colorsTotal)
      throw new org.omg.CORBA.BAD_OPERATION ();
  }

  public com.bbn.openmap.CSpecialist.CTEntry[] ct ()
  {
    if (__uninitialized)
      throw new org.omg.CORBA.BAD_OPERATION ();
    verifyct (__discriminator);
    return ___ct;
  }

  public void ct (com.bbn.openmap.CSpecialist.CTEntry[] value)
  {
    __discriminator = com.bbn.openmap.CSpecialist.RasterPackage.settableFields.RASF_ct;
    ___ct = value;
    __uninitialized = false;
  }

  public void ct (com.bbn.openmap.CSpecialist.RasterPackage.settableFields discriminator, com.bbn.openmap.CSpecialist.CTEntry[] value)
  {
    verifyct (discriminator);
    __discriminator = discriminator;
    ___ct = value;
    __uninitialized = false;
  }

  private void verifyct (com.bbn.openmap.CSpecialist.RasterPackage.settableFields discriminator)
  {
    if (discriminator != com.bbn.openmap.CSpecialist.RasterPackage.settableFields.RASF_ct)
      throw new org.omg.CORBA.BAD_OPERATION ();
  }

  public short transparent ()
  {
    if (__uninitialized)
      throw new org.omg.CORBA.BAD_OPERATION ();
    verifytransparent (__discriminator);
    return ___transparent;
  }

  public void transparent (short value)
  {
    __discriminator = com.bbn.openmap.CSpecialist.RasterPackage.settableFields.RASF_transparent;
    ___transparent = value;
    __uninitialized = false;
  }

  public void transparent (com.bbn.openmap.CSpecialist.RasterPackage.settableFields discriminator, short value)
  {
    verifytransparent (discriminator);
    __discriminator = discriminator;
    ___transparent = value;
    __uninitialized = false;
  }

  private void verifytransparent (com.bbn.openmap.CSpecialist.RasterPackage.settableFields discriminator)
  {
    if (discriminator != com.bbn.openmap.CSpecialist.RasterPackage.settableFields.RASF_transparent)
      throw new org.omg.CORBA.BAD_OPERATION ();
  }

  public void _default ()
  {
    __discriminator = com.bbn.openmap.CSpecialist.RasterPackage.settableFields.RASF_p1;
    __uninitialized = false;
  }

  public void _default (com.bbn.openmap.CSpecialist.RasterPackage.settableFields discriminator)
  {
    verifyDefault( discriminator ) ;
    __discriminator = discriminator ;
    __uninitialized = false;
  }

  private void verifyDefault( com.bbn.openmap.CSpecialist.RasterPackage.settableFields value )
  {
    switch (value.value()) {
      case com.bbn.openmap.CSpecialist.RasterPackage.settableFields._RASF_p1:
      case com.bbn.openmap.CSpecialist.RasterPackage.settableFields._RASF_ll1:
      case com.bbn.openmap.CSpecialist.RasterPackage.settableFields._RASF_pixels:
      case com.bbn.openmap.CSpecialist.RasterPackage.settableFields._RASF_width:
      case com.bbn.openmap.CSpecialist.RasterPackage.settableFields._RASF_height:
      case com.bbn.openmap.CSpecialist.RasterPackage.settableFields._RASF_x_hot:
      case com.bbn.openmap.CSpecialist.RasterPackage.settableFields._RASF_y_hot:
      case com.bbn.openmap.CSpecialist.RasterPackage.settableFields._RASF_colorsTotal:
      case com.bbn.openmap.CSpecialist.RasterPackage.settableFields._RASF_ct:
      case com.bbn.openmap.CSpecialist.RasterPackage.settableFields._RASF_transparent:
        throw new org.omg.CORBA.BAD_OPERATION() ;

      default:
        return;
    }
  }

} // class RASF_update
