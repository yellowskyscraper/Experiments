package com.bbn.openmap.CSpecialist.ForceArrowPackage;


/**
* com/bbn/openmap/CSpecialist/ForceArrowPackage/FAF_update.java .
* Generated by the IDL-to-Java compiler (portable), version "3.1"
* from com/bbn/openmap/layer/specialist/Specialist.idl
* Wednesday, March 4, 2009 5:10:38 PM EST
*/

public final class FAF_update implements org.omg.CORBA.portable.IDLEntity
{
  private com.bbn.openmap.CSpecialist.XYPoint ___p1;
  private com.bbn.openmap.CSpecialist.XYPoint ___p2;
  private com.bbn.openmap.CSpecialist.XYPoint ___p3;
  private com.bbn.openmap.CSpecialist.LLPoint ___ll1;
  private com.bbn.openmap.CSpecialist.LLPoint ___ll2;
  private com.bbn.openmap.CSpecialist.LLPoint ___ll3;
  private com.bbn.openmap.CSpecialist.LLPoint ___offset;
  private com.bbn.openmap.CSpecialist.ForceArrowPackage.settableFields __discriminator;
  private boolean __uninitialized = true;

  public FAF_update ()
  {
  }

  public com.bbn.openmap.CSpecialist.ForceArrowPackage.settableFields discriminator ()
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
    __discriminator = com.bbn.openmap.CSpecialist.ForceArrowPackage.settableFields.FAF_p1;
    ___p1 = value;
    __uninitialized = false;
  }

  public void p1 (com.bbn.openmap.CSpecialist.ForceArrowPackage.settableFields discriminator, com.bbn.openmap.CSpecialist.XYPoint value)
  {
    verifyp1 (discriminator);
    __discriminator = discriminator;
    ___p1 = value;
    __uninitialized = false;
  }

  private void verifyp1 (com.bbn.openmap.CSpecialist.ForceArrowPackage.settableFields discriminator)
  {
    if (discriminator != com.bbn.openmap.CSpecialist.ForceArrowPackage.settableFields.FAF_p1)
      throw new org.omg.CORBA.BAD_OPERATION ();
  }

  public com.bbn.openmap.CSpecialist.XYPoint p2 ()
  {
    if (__uninitialized)
      throw new org.omg.CORBA.BAD_OPERATION ();
    verifyp2 (__discriminator);
    return ___p2;
  }

  public void p2 (com.bbn.openmap.CSpecialist.XYPoint value)
  {
    __discriminator = com.bbn.openmap.CSpecialist.ForceArrowPackage.settableFields.FAF_p2;
    ___p2 = value;
    __uninitialized = false;
  }

  public void p2 (com.bbn.openmap.CSpecialist.ForceArrowPackage.settableFields discriminator, com.bbn.openmap.CSpecialist.XYPoint value)
  {
    verifyp2 (discriminator);
    __discriminator = discriminator;
    ___p2 = value;
    __uninitialized = false;
  }

  private void verifyp2 (com.bbn.openmap.CSpecialist.ForceArrowPackage.settableFields discriminator)
  {
    if (discriminator != com.bbn.openmap.CSpecialist.ForceArrowPackage.settableFields.FAF_p2)
      throw new org.omg.CORBA.BAD_OPERATION ();
  }

  public com.bbn.openmap.CSpecialist.XYPoint p3 ()
  {
    if (__uninitialized)
      throw new org.omg.CORBA.BAD_OPERATION ();
    verifyp3 (__discriminator);
    return ___p3;
  }

  public void p3 (com.bbn.openmap.CSpecialist.XYPoint value)
  {
    __discriminator = com.bbn.openmap.CSpecialist.ForceArrowPackage.settableFields.FAF_p3;
    ___p3 = value;
    __uninitialized = false;
  }

  public void p3 (com.bbn.openmap.CSpecialist.ForceArrowPackage.settableFields discriminator, com.bbn.openmap.CSpecialist.XYPoint value)
  {
    verifyp3 (discriminator);
    __discriminator = discriminator;
    ___p3 = value;
    __uninitialized = false;
  }

  private void verifyp3 (com.bbn.openmap.CSpecialist.ForceArrowPackage.settableFields discriminator)
  {
    if (discriminator != com.bbn.openmap.CSpecialist.ForceArrowPackage.settableFields.FAF_p3)
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
    __discriminator = com.bbn.openmap.CSpecialist.ForceArrowPackage.settableFields.FAF_ll1;
    ___ll1 = value;
    __uninitialized = false;
  }

  public void ll1 (com.bbn.openmap.CSpecialist.ForceArrowPackage.settableFields discriminator, com.bbn.openmap.CSpecialist.LLPoint value)
  {
    verifyll1 (discriminator);
    __discriminator = discriminator;
    ___ll1 = value;
    __uninitialized = false;
  }

  private void verifyll1 (com.bbn.openmap.CSpecialist.ForceArrowPackage.settableFields discriminator)
  {
    if (discriminator != com.bbn.openmap.CSpecialist.ForceArrowPackage.settableFields.FAF_ll1)
      throw new org.omg.CORBA.BAD_OPERATION ();
  }

  public com.bbn.openmap.CSpecialist.LLPoint ll2 ()
  {
    if (__uninitialized)
      throw new org.omg.CORBA.BAD_OPERATION ();
    verifyll2 (__discriminator);
    return ___ll2;
  }

  public void ll2 (com.bbn.openmap.CSpecialist.LLPoint value)
  {
    __discriminator = com.bbn.openmap.CSpecialist.ForceArrowPackage.settableFields.FAF_ll2;
    ___ll2 = value;
    __uninitialized = false;
  }

  public void ll2 (com.bbn.openmap.CSpecialist.ForceArrowPackage.settableFields discriminator, com.bbn.openmap.CSpecialist.LLPoint value)
  {
    verifyll2 (discriminator);
    __discriminator = discriminator;
    ___ll2 = value;
    __uninitialized = false;
  }

  private void verifyll2 (com.bbn.openmap.CSpecialist.ForceArrowPackage.settableFields discriminator)
  {
    if (discriminator != com.bbn.openmap.CSpecialist.ForceArrowPackage.settableFields.FAF_ll2)
      throw new org.omg.CORBA.BAD_OPERATION ();
  }

  public com.bbn.openmap.CSpecialist.LLPoint ll3 ()
  {
    if (__uninitialized)
      throw new org.omg.CORBA.BAD_OPERATION ();
    verifyll3 (__discriminator);
    return ___ll3;
  }

  public void ll3 (com.bbn.openmap.CSpecialist.LLPoint value)
  {
    __discriminator = com.bbn.openmap.CSpecialist.ForceArrowPackage.settableFields.FAF_ll3;
    ___ll3 = value;
    __uninitialized = false;
  }

  public void ll3 (com.bbn.openmap.CSpecialist.ForceArrowPackage.settableFields discriminator, com.bbn.openmap.CSpecialist.LLPoint value)
  {
    verifyll3 (discriminator);
    __discriminator = discriminator;
    ___ll3 = value;
    __uninitialized = false;
  }

  private void verifyll3 (com.bbn.openmap.CSpecialist.ForceArrowPackage.settableFields discriminator)
  {
    if (discriminator != com.bbn.openmap.CSpecialist.ForceArrowPackage.settableFields.FAF_ll3)
      throw new org.omg.CORBA.BAD_OPERATION ();
  }

  public com.bbn.openmap.CSpecialist.LLPoint offset ()
  {
    if (__uninitialized)
      throw new org.omg.CORBA.BAD_OPERATION ();
    verifyoffset (__discriminator);
    return ___offset;
  }

  public void offset (com.bbn.openmap.CSpecialist.LLPoint value)
  {
    __discriminator = com.bbn.openmap.CSpecialist.ForceArrowPackage.settableFields.FAF_offset;
    ___offset = value;
    __uninitialized = false;
  }

  public void offset (com.bbn.openmap.CSpecialist.ForceArrowPackage.settableFields discriminator, com.bbn.openmap.CSpecialist.LLPoint value)
  {
    verifyoffset (discriminator);
    __discriminator = discriminator;
    ___offset = value;
    __uninitialized = false;
  }

  private void verifyoffset (com.bbn.openmap.CSpecialist.ForceArrowPackage.settableFields discriminator)
  {
    if (discriminator != com.bbn.openmap.CSpecialist.ForceArrowPackage.settableFields.FAF_offset)
      throw new org.omg.CORBA.BAD_OPERATION ();
  }

} // class FAF_update
