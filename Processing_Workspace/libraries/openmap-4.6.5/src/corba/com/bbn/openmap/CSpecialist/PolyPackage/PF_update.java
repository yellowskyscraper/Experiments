package com.bbn.openmap.CSpecialist.PolyPackage;


/**
* com/bbn/openmap/CSpecialist/PolyPackage/PF_update.java .
* Generated by the IDL-to-Java compiler (portable), version "3.1"
* from com/bbn/openmap/layer/specialist/Specialist.idl
* Wednesday, March 4, 2009 5:10:38 PM EST
*/

public final class PF_update implements org.omg.CORBA.portable.IDLEntity
{
  private com.bbn.openmap.CSpecialist.LLPoint ___ll1;
  private com.bbn.openmap.CSpecialist.PolyPackage.CoordMode ___cMode;
  private com.bbn.openmap.CSpecialist.XYPoint[] ___xypoints;
  private com.bbn.openmap.CSpecialist.LLPoint[] ___llpoints;
  private com.bbn.openmap.CSpecialist.PolyPackage.settableFields __discriminator;
  private boolean __uninitialized = true;

  public PF_update ()
  {
  }

  public com.bbn.openmap.CSpecialist.PolyPackage.settableFields discriminator ()
  {
    if (__uninitialized)
      throw new org.omg.CORBA.BAD_OPERATION ();
    return __discriminator;
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
    __discriminator = com.bbn.openmap.CSpecialist.PolyPackage.settableFields.PF_ll1;
    ___ll1 = value;
    __uninitialized = false;
  }

  public void ll1 (com.bbn.openmap.CSpecialist.PolyPackage.settableFields discriminator, com.bbn.openmap.CSpecialist.LLPoint value)
  {
    verifyll1 (discriminator);
    __discriminator = discriminator;
    ___ll1 = value;
    __uninitialized = false;
  }

  private void verifyll1 (com.bbn.openmap.CSpecialist.PolyPackage.settableFields discriminator)
  {
    if (discriminator != com.bbn.openmap.CSpecialist.PolyPackage.settableFields.PF_ll1)
      throw new org.omg.CORBA.BAD_OPERATION ();
  }

  public com.bbn.openmap.CSpecialist.PolyPackage.CoordMode cMode ()
  {
    if (__uninitialized)
      throw new org.omg.CORBA.BAD_OPERATION ();
    verifycMode (__discriminator);
    return ___cMode;
  }

  public void cMode (com.bbn.openmap.CSpecialist.PolyPackage.CoordMode value)
  {
    __discriminator = com.bbn.openmap.CSpecialist.PolyPackage.settableFields.PF_cMode;
    ___cMode = value;
    __uninitialized = false;
  }

  public void cMode (com.bbn.openmap.CSpecialist.PolyPackage.settableFields discriminator, com.bbn.openmap.CSpecialist.PolyPackage.CoordMode value)
  {
    verifycMode (discriminator);
    __discriminator = discriminator;
    ___cMode = value;
    __uninitialized = false;
  }

  private void verifycMode (com.bbn.openmap.CSpecialist.PolyPackage.settableFields discriminator)
  {
    if (discriminator != com.bbn.openmap.CSpecialist.PolyPackage.settableFields.PF_cMode)
      throw new org.omg.CORBA.BAD_OPERATION ();
  }

  public com.bbn.openmap.CSpecialist.XYPoint[] xypoints ()
  {
    if (__uninitialized)
      throw new org.omg.CORBA.BAD_OPERATION ();
    verifyxypoints (__discriminator);
    return ___xypoints;
  }

  public void xypoints (com.bbn.openmap.CSpecialist.XYPoint[] value)
  {
    __discriminator = com.bbn.openmap.CSpecialist.PolyPackage.settableFields.PF_xypoints;
    ___xypoints = value;
    __uninitialized = false;
  }

  public void xypoints (com.bbn.openmap.CSpecialist.PolyPackage.settableFields discriminator, com.bbn.openmap.CSpecialist.XYPoint[] value)
  {
    verifyxypoints (discriminator);
    __discriminator = discriminator;
    ___xypoints = value;
    __uninitialized = false;
  }

  private void verifyxypoints (com.bbn.openmap.CSpecialist.PolyPackage.settableFields discriminator)
  {
    if (discriminator != com.bbn.openmap.CSpecialist.PolyPackage.settableFields.PF_xypoints)
      throw new org.omg.CORBA.BAD_OPERATION ();
  }

  public com.bbn.openmap.CSpecialist.LLPoint[] llpoints ()
  {
    if (__uninitialized)
      throw new org.omg.CORBA.BAD_OPERATION ();
    verifyllpoints (__discriminator);
    return ___llpoints;
  }

  public void llpoints (com.bbn.openmap.CSpecialist.LLPoint[] value)
  {
    __discriminator = com.bbn.openmap.CSpecialist.PolyPackage.settableFields.PF_llpoints;
    ___llpoints = value;
    __uninitialized = false;
  }

  public void llpoints (com.bbn.openmap.CSpecialist.PolyPackage.settableFields discriminator, com.bbn.openmap.CSpecialist.LLPoint[] value)
  {
    verifyllpoints (discriminator);
    __discriminator = discriminator;
    ___llpoints = value;
    __uninitialized = false;
  }

  private void verifyllpoints (com.bbn.openmap.CSpecialist.PolyPackage.settableFields discriminator)
  {
    if (discriminator != com.bbn.openmap.CSpecialist.PolyPackage.settableFields.PF_llpoints)
      throw new org.omg.CORBA.BAD_OPERATION ();
  }

} // class PF_update
