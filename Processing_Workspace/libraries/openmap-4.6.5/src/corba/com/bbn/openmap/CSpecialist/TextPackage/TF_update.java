package com.bbn.openmap.CSpecialist.TextPackage;


/**
* com/bbn/openmap/CSpecialist/TextPackage/TF_update.java .
* Generated by the IDL-to-Java compiler (portable), version "3.1"
* from com/bbn/openmap/layer/specialist/Specialist.idl
* Wednesday, March 4, 2009 5:10:38 PM EST
*/

public final class TF_update implements org.omg.CORBA.portable.IDLEntity
{
  private com.bbn.openmap.CSpecialist.XYPoint ___p1;
  private com.bbn.openmap.CSpecialist.LLPoint ___ll1;
  private String ___data;
  private String ___font;
  private short ___justify;
  private com.bbn.openmap.CSpecialist.TextPackage.settableFields __discriminator;
  private boolean __uninitialized = true;

  public TF_update ()
  {
  }

  public com.bbn.openmap.CSpecialist.TextPackage.settableFields discriminator ()
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
    __discriminator = com.bbn.openmap.CSpecialist.TextPackage.settableFields.TF_p1;
    ___p1 = value;
    __uninitialized = false;
  }

  public void p1 (com.bbn.openmap.CSpecialist.TextPackage.settableFields discriminator, com.bbn.openmap.CSpecialist.XYPoint value)
  {
    verifyp1 (discriminator);
    __discriminator = discriminator;
    ___p1 = value;
    __uninitialized = false;
  }

  private void verifyp1 (com.bbn.openmap.CSpecialist.TextPackage.settableFields discriminator)
  {
    if (discriminator != com.bbn.openmap.CSpecialist.TextPackage.settableFields.TF_p1)
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
    __discriminator = com.bbn.openmap.CSpecialist.TextPackage.settableFields.TF_ll1;
    ___ll1 = value;
    __uninitialized = false;
  }

  public void ll1 (com.bbn.openmap.CSpecialist.TextPackage.settableFields discriminator, com.bbn.openmap.CSpecialist.LLPoint value)
  {
    verifyll1 (discriminator);
    __discriminator = discriminator;
    ___ll1 = value;
    __uninitialized = false;
  }

  private void verifyll1 (com.bbn.openmap.CSpecialist.TextPackage.settableFields discriminator)
  {
    if (discriminator != com.bbn.openmap.CSpecialist.TextPackage.settableFields.TF_ll1)
      throw new org.omg.CORBA.BAD_OPERATION ();
  }

  public String data ()
  {
    if (__uninitialized)
      throw new org.omg.CORBA.BAD_OPERATION ();
    verifydata (__discriminator);
    return ___data;
  }

  public void data (String value)
  {
    __discriminator = com.bbn.openmap.CSpecialist.TextPackage.settableFields.TF_data;
    ___data = value;
    __uninitialized = false;
  }

  public void data (com.bbn.openmap.CSpecialist.TextPackage.settableFields discriminator, String value)
  {
    verifydata (discriminator);
    __discriminator = discriminator;
    ___data = value;
    __uninitialized = false;
  }

  private void verifydata (com.bbn.openmap.CSpecialist.TextPackage.settableFields discriminator)
  {
    if (discriminator != com.bbn.openmap.CSpecialist.TextPackage.settableFields.TF_data)
      throw new org.omg.CORBA.BAD_OPERATION ();
  }

  public String font ()
  {
    if (__uninitialized)
      throw new org.omg.CORBA.BAD_OPERATION ();
    verifyfont (__discriminator);
    return ___font;
  }

  public void font (String value)
  {
    __discriminator = com.bbn.openmap.CSpecialist.TextPackage.settableFields.TF_font;
    ___font = value;
    __uninitialized = false;
  }

  public void font (com.bbn.openmap.CSpecialist.TextPackage.settableFields discriminator, String value)
  {
    verifyfont (discriminator);
    __discriminator = discriminator;
    ___font = value;
    __uninitialized = false;
  }

  private void verifyfont (com.bbn.openmap.CSpecialist.TextPackage.settableFields discriminator)
  {
    if (discriminator != com.bbn.openmap.CSpecialist.TextPackage.settableFields.TF_font)
      throw new org.omg.CORBA.BAD_OPERATION ();
  }

  public short justify ()
  {
    if (__uninitialized)
      throw new org.omg.CORBA.BAD_OPERATION ();
    verifyjustify (__discriminator);
    return ___justify;
  }

  public void justify (short value)
  {
    __discriminator = com.bbn.openmap.CSpecialist.TextPackage.settableFields.TF_justify;
    ___justify = value;
    __uninitialized = false;
  }

  public void justify (com.bbn.openmap.CSpecialist.TextPackage.settableFields discriminator, short value)
  {
    verifyjustify (discriminator);
    __discriminator = discriminator;
    ___justify = value;
    __uninitialized = false;
  }

  private void verifyjustify (com.bbn.openmap.CSpecialist.TextPackage.settableFields discriminator)
  {
    if (discriminator != com.bbn.openmap.CSpecialist.TextPackage.settableFields.TF_justify)
      throw new org.omg.CORBA.BAD_OPERATION ();
  }

} // class TF_update
