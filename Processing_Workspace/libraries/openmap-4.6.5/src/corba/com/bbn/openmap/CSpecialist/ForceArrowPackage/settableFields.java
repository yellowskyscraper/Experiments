package com.bbn.openmap.CSpecialist.ForceArrowPackage;


/**
* com/bbn/openmap/CSpecialist/ForceArrowPackage/settableFields.java .
* Generated by the IDL-to-Java compiler (portable), version "3.1"
* from com/bbn/openmap/layer/specialist/Specialist.idl
* Wednesday, March 4, 2009 5:10:38 PM EST
*/

public class settableFields implements org.omg.CORBA.portable.IDLEntity
{
  private        int __value;
  private static int __size = 7;
  private static com.bbn.openmap.CSpecialist.ForceArrowPackage.settableFields[] __array = new com.bbn.openmap.CSpecialist.ForceArrowPackage.settableFields [__size];

  public static final int _FAF_p1 = 0;
  public static final com.bbn.openmap.CSpecialist.ForceArrowPackage.settableFields FAF_p1 = new com.bbn.openmap.CSpecialist.ForceArrowPackage.settableFields(_FAF_p1);
  public static final int _FAF_p2 = 1;
  public static final com.bbn.openmap.CSpecialist.ForceArrowPackage.settableFields FAF_p2 = new com.bbn.openmap.CSpecialist.ForceArrowPackage.settableFields(_FAF_p2);
  public static final int _FAF_p3 = 2;
  public static final com.bbn.openmap.CSpecialist.ForceArrowPackage.settableFields FAF_p3 = new com.bbn.openmap.CSpecialist.ForceArrowPackage.settableFields(_FAF_p3);
  public static final int _FAF_ll1 = 3;
  public static final com.bbn.openmap.CSpecialist.ForceArrowPackage.settableFields FAF_ll1 = new com.bbn.openmap.CSpecialist.ForceArrowPackage.settableFields(_FAF_ll1);
  public static final int _FAF_ll2 = 4;
  public static final com.bbn.openmap.CSpecialist.ForceArrowPackage.settableFields FAF_ll2 = new com.bbn.openmap.CSpecialist.ForceArrowPackage.settableFields(_FAF_ll2);
  public static final int _FAF_ll3 = 5;
  public static final com.bbn.openmap.CSpecialist.ForceArrowPackage.settableFields FAF_ll3 = new com.bbn.openmap.CSpecialist.ForceArrowPackage.settableFields(_FAF_ll3);
  public static final int _FAF_offset = 6;
  public static final com.bbn.openmap.CSpecialist.ForceArrowPackage.settableFields FAF_offset = new com.bbn.openmap.CSpecialist.ForceArrowPackage.settableFields(_FAF_offset);

  public int value ()
  {
    return __value;
  }

  public static com.bbn.openmap.CSpecialist.ForceArrowPackage.settableFields from_int (int value)
  {
    if (value >= 0 && value < __size)
      return __array[value];
    else
      throw new org.omg.CORBA.BAD_PARAM ();
  }

  protected settableFields (int value)
  {
    __value = value;
    __array[__value] = this;
  }
} // class settableFields
