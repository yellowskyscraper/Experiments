package com.bbn.openmap.CSpecialist;


/**
* com/bbn/openmap/CSpecialist/PolyOperations.java .
* Generated by the IDL-to-Java compiler (portable), version "3.1"
* from com/bbn/openmap/layer/specialist/Specialist.idl
* Wednesday, March 4, 2009 5:10:37 PM EST
*/

public interface PolyOperations  extends com.bbn.openmap.CSpecialist.GraphicOperations
{
  com.bbn.openmap.CSpecialist.LLPoint ll1 ();
  void ll1 (com.bbn.openmap.CSpecialist.LLPoint newLl1);
  com.bbn.openmap.CSpecialist.PolyPackage.CoordMode cMode ();
  void cMode (com.bbn.openmap.CSpecialist.PolyPackage.CoordMode newCMode);
  com.bbn.openmap.CSpecialist.XYPoint[] xypoints ();
  void xypoints (com.bbn.openmap.CSpecialist.XYPoint[] newXypoints);
  com.bbn.openmap.CSpecialist.LLPoint[] llpoints ();
  void llpoints (com.bbn.openmap.CSpecialist.LLPoint[] newLlpoints);
  com.bbn.openmap.CSpecialist.PolyPackage.EPoly fill ();
} // interface PolyOperations
