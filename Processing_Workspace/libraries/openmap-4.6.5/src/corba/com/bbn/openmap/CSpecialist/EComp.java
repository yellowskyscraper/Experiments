package com.bbn.openmap.CSpecialist;


/**
* com/bbn/openmap/CSpecialist/EComp.java .
* Generated by the IDL-to-Java compiler (portable), version "3.1"
* from com/bbn/openmap/layer/specialist/Specialist.idl
* Wednesday, March 4, 2009 5:10:37 PM EST
*/

public final class EComp implements org.omg.CORBA.portable.IDLEntity
{
  public com.bbn.openmap.CSpecialist.Comp comp = null;
  public String cID = null;

  public EComp ()
  {
  } // ctor

  public EComp (com.bbn.openmap.CSpecialist.Comp _comp, String _cID)
  {
    comp = _comp;
    cID = _cID;
  } // ctor

} // class EComp