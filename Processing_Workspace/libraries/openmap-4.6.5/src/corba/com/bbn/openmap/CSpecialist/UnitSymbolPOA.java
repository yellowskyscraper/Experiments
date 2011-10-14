package com.bbn.openmap.CSpecialist;


/**
* com/bbn/openmap/CSpecialist/UnitSymbolPOA.java .
* Generated by the IDL-to-Java compiler (portable), version "3.1"
* from com/bbn/openmap/layer/specialist/Specialist.idl
* Wednesday, March 4, 2009 5:10:38 PM EST
*/

public abstract class UnitSymbolPOA extends org.omg.PortableServer.Servant
 implements com.bbn.openmap.CSpecialist.UnitSymbolOperations, org.omg.CORBA.portable.InvokeHandler
{

  // Constructors

  private static java.util.Hashtable _methods = new java.util.Hashtable ();
  static
  {
    _methods.put ("_get_p1", new java.lang.Integer (0));
    _methods.put ("_set_p1", new java.lang.Integer (1));
    _methods.put ("_get_ll1", new java.lang.Integer (2));
    _methods.put ("_set_ll1", new java.lang.Integer (3));
    _methods.put ("_get_group", new java.lang.Integer (4));
    _methods.put ("_set_group", new java.lang.Integer (5));
    _methods.put ("_get_symbol", new java.lang.Integer (6));
    _methods.put ("_set_symbol", new java.lang.Integer (7));
    _methods.put ("_get_echelon", new java.lang.Integer (8));
    _methods.put ("_set_echelon", new java.lang.Integer (9));
    _methods.put ("_get_left1", new java.lang.Integer (10));
    _methods.put ("_set_left1", new java.lang.Integer (11));
    _methods.put ("_get_left2", new java.lang.Integer (12));
    _methods.put ("_set_left2", new java.lang.Integer (13));
    _methods.put ("_get_left3", new java.lang.Integer (14));
    _methods.put ("_set_left3", new java.lang.Integer (15));
    _methods.put ("_get_left4", new java.lang.Integer (16));
    _methods.put ("_set_left4", new java.lang.Integer (17));
    _methods.put ("_get_right1", new java.lang.Integer (18));
    _methods.put ("_set_right1", new java.lang.Integer (19));
    _methods.put ("_get_right2", new java.lang.Integer (20));
    _methods.put ("_set_right2", new java.lang.Integer (21));
    _methods.put ("_get_right3", new java.lang.Integer (22));
    _methods.put ("_set_right3", new java.lang.Integer (23));
    _methods.put ("_get_right4", new java.lang.Integer (24));
    _methods.put ("_set_right4", new java.lang.Integer (25));
    _methods.put ("_get_top1", new java.lang.Integer (26));
    _methods.put ("_set_top1", new java.lang.Integer (27));
    _methods.put ("_get_bottom1", new java.lang.Integer (28));
    _methods.put ("_set_bottom1", new java.lang.Integer (29));
    _methods.put ("_get_nom_size", new java.lang.Integer (30));
    _methods.put ("_set_nom_size", new java.lang.Integer (31));
    _methods.put ("_get_min_size", new java.lang.Integer (32));
    _methods.put ("_set_min_size", new java.lang.Integer (33));
    _methods.put ("_get_max_size", new java.lang.Integer (34));
    _methods.put ("_set_max_size", new java.lang.Integer (35));
    _methods.put ("_get_scale", new java.lang.Integer (36));
    _methods.put ("_set_scale", new java.lang.Integer (37));
    _methods.put ("_get_is_hq", new java.lang.Integer (38));
    _methods.put ("_set_is_hq", new java.lang.Integer (39));
    _methods.put ("_get_rotate", new java.lang.Integer (40));
    _methods.put ("_set_rotate", new java.lang.Integer (41));
    _methods.put ("fill", new java.lang.Integer (42));
    _methods.put ("_get_gID", new java.lang.Integer (43));
    _methods.put ("_get_gType", new java.lang.Integer (44));
    _methods.put ("_get_obj", new java.lang.Integer (45));
    _methods.put ("_set_obj", new java.lang.Integer (46));
    _methods.put ("_get_lType", new java.lang.Integer (47));
    _methods.put ("_set_lType", new java.lang.Integer (48));
    _methods.put ("_get_rType", new java.lang.Integer (49));
    _methods.put ("_set_rType", new java.lang.Integer (50));
    _methods.put ("_get_color", new java.lang.Integer (51));
    _methods.put ("_set_color", new java.lang.Integer (52));
    _methods.put ("_get_fillColor", new java.lang.Integer (53));
    _methods.put ("_set_fillColor", new java.lang.Integer (54));
    _methods.put ("_get_lineWidth", new java.lang.Integer (55));
    _methods.put ("_set_lineWidth", new java.lang.Integer (56));
    _methods.put ("_get_stipple", new java.lang.Integer (57));
    _methods.put ("_set_stipple", new java.lang.Integer (58));
    _methods.put ("_get_fillStipple", new java.lang.Integer (59));
    _methods.put ("_set_fillStipple", new java.lang.Integer (60));
    _methods.put ("_get_dcType", new java.lang.Integer (61));
    _methods.put ("_set_dcType", new java.lang.Integer (62));
    _methods.put ("gfill", new java.lang.Integer (63));
  }

  public org.omg.CORBA.portable.OutputStream _invoke (String $method,
                                org.omg.CORBA.portable.InputStream in,
                                org.omg.CORBA.portable.ResponseHandler $rh)
  {
    org.omg.CORBA.portable.OutputStream out = null;
    java.lang.Integer __method = (java.lang.Integer)_methods.get ($method);
    if (__method == null)
      throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);

    switch (__method.intValue ())
    {
       case 0:  // CSpecialist/UnitSymbol/_get_p1
       {
         com.bbn.openmap.CSpecialist.XYPoint $result = null;
         $result = this.p1 ();
         out = $rh.createReply();
         com.bbn.openmap.CSpecialist.XYPointHelper.write (out, $result);
         break;
       }

       case 1:  // CSpecialist/UnitSymbol/_set_p1
       {
         com.bbn.openmap.CSpecialist.XYPoint newP1 = com.bbn.openmap.CSpecialist.XYPointHelper.read (in);
         this.p1 (newP1);
         out = $rh.createReply();
         break;
       }

       case 2:  // CSpecialist/UnitSymbol/_get_ll1
       {
         com.bbn.openmap.CSpecialist.LLPoint $result = null;
         $result = this.ll1 ();
         out = $rh.createReply();
         com.bbn.openmap.CSpecialist.LLPointHelper.write (out, $result);
         break;
       }

       case 3:  // CSpecialist/UnitSymbol/_set_ll1
       {
         com.bbn.openmap.CSpecialist.LLPoint newLl1 = com.bbn.openmap.CSpecialist.LLPointHelper.read (in);
         this.ll1 (newLl1);
         out = $rh.createReply();
         break;
       }

       case 4:  // CSpecialist/UnitSymbol/_get_group
       {
         String $result = null;
         $result = this.group ();
         out = $rh.createReply();
         out.write_string ($result);
         break;
       }

       case 5:  // CSpecialist/UnitSymbol/_set_group
       {
         String newGroup = in.read_string ();
         this.group (newGroup);
         out = $rh.createReply();
         break;
       }

       case 6:  // CSpecialist/UnitSymbol/_get_symbol
       {
         String $result = null;
         $result = this.symbol ();
         out = $rh.createReply();
         out.write_string ($result);
         break;
       }

       case 7:  // CSpecialist/UnitSymbol/_set_symbol
       {
         String newSymbol = in.read_string ();
         this.symbol (newSymbol);
         out = $rh.createReply();
         break;
       }

       case 8:  // CSpecialist/UnitSymbol/_get_echelon
       {
         String $result = null;
         $result = this.echelon ();
         out = $rh.createReply();
         out.write_string ($result);
         break;
       }

       case 9:  // CSpecialist/UnitSymbol/_set_echelon
       {
         String newEchelon = in.read_string ();
         this.echelon (newEchelon);
         out = $rh.createReply();
         break;
       }

       case 10:  // CSpecialist/UnitSymbol/_get_left1
       {
         String $result = null;
         $result = this.left1 ();
         out = $rh.createReply();
         out.write_string ($result);
         break;
       }

       case 11:  // CSpecialist/UnitSymbol/_set_left1
       {
         String newLeft1 = in.read_string ();
         this.left1 (newLeft1);
         out = $rh.createReply();
         break;
       }

       case 12:  // CSpecialist/UnitSymbol/_get_left2
       {
         String $result = null;
         $result = this.left2 ();
         out = $rh.createReply();
         out.write_string ($result);
         break;
       }

       case 13:  // CSpecialist/UnitSymbol/_set_left2
       {
         String newLeft2 = in.read_string ();
         this.left2 (newLeft2);
         out = $rh.createReply();
         break;
       }

       case 14:  // CSpecialist/UnitSymbol/_get_left3
       {
         String $result = null;
         $result = this.left3 ();
         out = $rh.createReply();
         out.write_string ($result);
         break;
       }

       case 15:  // CSpecialist/UnitSymbol/_set_left3
       {
         String newLeft3 = in.read_string ();
         this.left3 (newLeft3);
         out = $rh.createReply();
         break;
       }

       case 16:  // CSpecialist/UnitSymbol/_get_left4
       {
         String $result = null;
         $result = this.left4 ();
         out = $rh.createReply();
         out.write_string ($result);
         break;
       }

       case 17:  // CSpecialist/UnitSymbol/_set_left4
       {
         String newLeft4 = in.read_string ();
         this.left4 (newLeft4);
         out = $rh.createReply();
         break;
       }

       case 18:  // CSpecialist/UnitSymbol/_get_right1
       {
         String $result = null;
         $result = this.right1 ();
         out = $rh.createReply();
         out.write_string ($result);
         break;
       }

       case 19:  // CSpecialist/UnitSymbol/_set_right1
       {
         String newRight1 = in.read_string ();
         this.right1 (newRight1);
         out = $rh.createReply();
         break;
       }

       case 20:  // CSpecialist/UnitSymbol/_get_right2
       {
         String $result = null;
         $result = this.right2 ();
         out = $rh.createReply();
         out.write_string ($result);
         break;
       }

       case 21:  // CSpecialist/UnitSymbol/_set_right2
       {
         String newRight2 = in.read_string ();
         this.right2 (newRight2);
         out = $rh.createReply();
         break;
       }

       case 22:  // CSpecialist/UnitSymbol/_get_right3
       {
         String $result = null;
         $result = this.right3 ();
         out = $rh.createReply();
         out.write_string ($result);
         break;
       }

       case 23:  // CSpecialist/UnitSymbol/_set_right3
       {
         String newRight3 = in.read_string ();
         this.right3 (newRight3);
         out = $rh.createReply();
         break;
       }

       case 24:  // CSpecialist/UnitSymbol/_get_right4
       {
         String $result = null;
         $result = this.right4 ();
         out = $rh.createReply();
         out.write_string ($result);
         break;
       }

       case 25:  // CSpecialist/UnitSymbol/_set_right4
       {
         String newRight4 = in.read_string ();
         this.right4 (newRight4);
         out = $rh.createReply();
         break;
       }

       case 26:  // CSpecialist/UnitSymbol/_get_top1
       {
         String $result = null;
         $result = this.top1 ();
         out = $rh.createReply();
         out.write_string ($result);
         break;
       }

       case 27:  // CSpecialist/UnitSymbol/_set_top1
       {
         String newTop1 = in.read_string ();
         this.top1 (newTop1);
         out = $rh.createReply();
         break;
       }

       case 28:  // CSpecialist/UnitSymbol/_get_bottom1
       {
         String $result = null;
         $result = this.bottom1 ();
         out = $rh.createReply();
         out.write_string ($result);
         break;
       }

       case 29:  // CSpecialist/UnitSymbol/_set_bottom1
       {
         String newBottom1 = in.read_string ();
         this.bottom1 (newBottom1);
         out = $rh.createReply();
         break;
       }

       case 30:  // CSpecialist/UnitSymbol/_get_nom_size
       {
         short $result = (short)0;
         $result = this.nom_size ();
         out = $rh.createReply();
         out.write_ushort ($result);
         break;
       }

       case 31:  // CSpecialist/UnitSymbol/_set_nom_size
       {
         short newNom_size = in.read_ushort ();
         this.nom_size (newNom_size);
         out = $rh.createReply();
         break;
       }


  // nominal size is in pixels
       case 32:  // CSpecialist/UnitSymbol/_get_min_size
       {
         short $result = (short)0;
         $result = this.min_size ();
         out = $rh.createReply();
         out.write_ushort ($result);
         break;
       }


  // nominal size is in pixels
       case 33:  // CSpecialist/UnitSymbol/_set_min_size
       {
         short newMin_size = in.read_ushort ();
         this.min_size (newMin_size);
         out = $rh.createReply();
         break;
       }


  // minimal size is in pixels
       case 34:  // CSpecialist/UnitSymbol/_get_max_size
       {
         short $result = (short)0;
         $result = this.max_size ();
         out = $rh.createReply();
         out.write_ushort ($result);
         break;
       }


  // minimal size is in pixels
       case 35:  // CSpecialist/UnitSymbol/_set_max_size
       {
         short newMax_size = in.read_ushort ();
         this.max_size (newMax_size);
         out = $rh.createReply();
         break;
       }


  // maximum size is in pixels
       case 36:  // CSpecialist/UnitSymbol/_get_scale
       {
         int $result = (int)0;
         $result = this.scale ();
         out = $rh.createReply();
         out.write_ulong ($result);
         break;
       }


  // maximum size is in pixels
       case 37:  // CSpecialist/UnitSymbol/_set_scale
       {
         int newScale = in.read_ulong ();
         this.scale (newScale);
         out = $rh.createReply();
         break;
       }


  // scale at which size is nom_size
       case 38:  // CSpecialist/UnitSymbol/_get_is_hq
       {
         boolean $result = false;
         $result = this.is_hq ();
         out = $rh.createReply();
         out.write_boolean ($result);
         break;
       }


  // scale at which size is nom_size
       case 39:  // CSpecialist/UnitSymbol/_set_is_hq
       {
         boolean newIs_hq = in.read_boolean ();
         this.is_hq (newIs_hq);
         out = $rh.createReply();
         break;
       }


  // Headquarters mark display
       case 40:  // CSpecialist/UnitSymbol/_get_rotate
       {
         float $result = (float)0;
         $result = this.rotate ();
         out = $rh.createReply();
         out.write_float ($result);
         break;
       }


  // Headquarters mark display
       case 41:  // CSpecialist/UnitSymbol/_set_rotate
       {
         float newRotate = in.read_float ();
         this.rotate (newRotate);
         out = $rh.createReply();
         break;
       }

       case 42:  // CSpecialist/UnitSymbol/fill
       {
         com.bbn.openmap.CSpecialist.UnitSymbolPackage.EUnitSymbol $result = null;
         $result = this.fill ();
         out = $rh.createReply();
         com.bbn.openmap.CSpecialist.UnitSymbolPackage.EUnitSymbolHelper.write (out, $result);
         break;
       }


  //  original spot
       case 43:  // CSpecialist/Graphic/_get_gID
       {
         String $result = null;
         $result = this.gID ();
         out = $rh.createReply();
         out.write_string ($result);
         break;
       }

       case 44:  // CSpecialist/Graphic/_get_gType
       {
         com.bbn.openmap.CSpecialist.GraphicPackage.GraphicType $result = null;
         $result = this.gType ();
         out = $rh.createReply();
         com.bbn.openmap.CSpecialist.GraphicPackage.GraphicTypeHelper.write (out, $result);
         break;
       }

       case 45:  // CSpecialist/Graphic/_get_obj
       {
         com.bbn.openmap.CSpecialist.Comp $result = null;
         $result = this.obj ();
         out = $rh.createReply();
         com.bbn.openmap.CSpecialist.CompHelper.write (out, $result);
         break;
       }

       case 46:  // CSpecialist/Graphic/_set_obj
       {
         com.bbn.openmap.CSpecialist.Comp newObj = com.bbn.openmap.CSpecialist.CompHelper.read (in);
         this.obj (newObj);
         out = $rh.createReply();
         break;
       }

       case 47:  // CSpecialist/Graphic/_get_lType
       {
         com.bbn.openmap.CSpecialist.GraphicPackage.LineType $result = null;
         $result = this.lType ();
         out = $rh.createReply();
         com.bbn.openmap.CSpecialist.GraphicPackage.LineTypeHelper.write (out, $result);
         break;
       }

       case 48:  // CSpecialist/Graphic/_set_lType
       {
         com.bbn.openmap.CSpecialist.GraphicPackage.LineType newLType = com.bbn.openmap.CSpecialist.GraphicPackage.LineTypeHelper.read (in);
         this.lType (newLType);
         out = $rh.createReply();
         break;
       }

       case 49:  // CSpecialist/Graphic/_get_rType
       {
         com.bbn.openmap.CSpecialist.GraphicPackage.RenderType $result = null;
         $result = this.rType ();
         out = $rh.createReply();
         com.bbn.openmap.CSpecialist.GraphicPackage.RenderTypeHelper.write (out, $result);
         break;
       }

       case 50:  // CSpecialist/Graphic/_set_rType
       {
         com.bbn.openmap.CSpecialist.GraphicPackage.RenderType newRType = com.bbn.openmap.CSpecialist.GraphicPackage.RenderTypeHelper.read (in);
         this.rType (newRType);
         out = $rh.createReply();
         break;
       }

       case 51:  // CSpecialist/Graphic/_get_color
       {
         com.bbn.openmap.CSpecialist.CColor $result = null;
         $result = this.color ();
         out = $rh.createReply();
         com.bbn.openmap.CSpecialist.CColorHelper.write (out, $result);
         break;
       }

       case 52:  // CSpecialist/Graphic/_set_color
       {
         com.bbn.openmap.CSpecialist.CColor newColor = com.bbn.openmap.CSpecialist.CColorHelper.read (in);
         this.color (newColor);
         out = $rh.createReply();
         break;
       }

       case 53:  // CSpecialist/Graphic/_get_fillColor
       {
         com.bbn.openmap.CSpecialist.CColor $result = null;
         $result = this.fillColor ();
         out = $rh.createReply();
         com.bbn.openmap.CSpecialist.CColorHelper.write (out, $result);
         break;
       }

       case 54:  // CSpecialist/Graphic/_set_fillColor
       {
         com.bbn.openmap.CSpecialist.CColor newFillColor = com.bbn.openmap.CSpecialist.CColorHelper.read (in);
         this.fillColor (newFillColor);
         out = $rh.createReply();
         break;
       }

       case 55:  // CSpecialist/Graphic/_get_lineWidth
       {
         short $result = (short)0;
         $result = this.lineWidth ();
         out = $rh.createReply();
         out.write_ushort ($result);
         break;
       }

       case 56:  // CSpecialist/Graphic/_set_lineWidth
       {
         short newLineWidth = in.read_ushort ();
         this.lineWidth (newLineWidth);
         out = $rh.createReply();
         break;
       }

       case 57:  // CSpecialist/Graphic/_get_stipple
       {
         com.bbn.openmap.CSpecialist.CStipple $result = null;
         $result = this.stipple ();
         out = $rh.createReply();
         com.bbn.openmap.CSpecialist.CStippleHelper.write (out, $result);
         break;
       }

       case 58:  // CSpecialist/Graphic/_set_stipple
       {
         com.bbn.openmap.CSpecialist.CStipple newStipple = com.bbn.openmap.CSpecialist.CStippleHelper.read (in);
         this.stipple (newStipple);
         out = $rh.createReply();
         break;
       }

       case 59:  // CSpecialist/Graphic/_get_fillStipple
       {
         com.bbn.openmap.CSpecialist.CStipple $result = null;
         $result = this.fillStipple ();
         out = $rh.createReply();
         com.bbn.openmap.CSpecialist.CStippleHelper.write (out, $result);
         break;
       }

       case 60:  // CSpecialist/Graphic/_set_fillStipple
       {
         com.bbn.openmap.CSpecialist.CStipple newFillStipple = com.bbn.openmap.CSpecialist.CStippleHelper.read (in);
         this.fillStipple (newFillStipple);
         out = $rh.createReply();
         break;
       }

       case 61:  // CSpecialist/Graphic/_get_dcType
       {
         com.bbn.openmap.CSpecialist.GraphicPackage.DeclutterType $result = null;
         $result = this.dcType ();
         out = $rh.createReply();
         com.bbn.openmap.CSpecialist.GraphicPackage.DeclutterTypeHelper.write (out, $result);
         break;
       }

       case 62:  // CSpecialist/Graphic/_set_dcType
       {
         com.bbn.openmap.CSpecialist.GraphicPackage.DeclutterType newDcType = com.bbn.openmap.CSpecialist.GraphicPackage.DeclutterTypeHelper.read (in);
         this.dcType (newDcType);
         out = $rh.createReply();
         break;
       }

       case 63:  // CSpecialist/Graphic/gfill
       {
         com.bbn.openmap.CSpecialist.GraphicPackage.EGraphic $result = null;
         $result = this.gfill ();
         out = $rh.createReply();
         com.bbn.openmap.CSpecialist.GraphicPackage.EGraphicHelper.write (out, $result);
         break;
       }

       default:
         throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);
    }

    return out;
  } // _invoke

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:CSpecialist/UnitSymbol:1.0", 
    "IDL:CSpecialist/Graphic:1.0"};

  public String[] _all_interfaces (org.omg.PortableServer.POA poa, byte[] objectId)
  {
    return (String[])__ids.clone ();
  }

  public UnitSymbol _this() 
  {
    return UnitSymbolHelper.narrow(
    super._this_object());
  }

  public UnitSymbol _this(org.omg.CORBA.ORB orb) 
  {
    return UnitSymbolHelper.narrow(
    super._this_object(orb));
  }


} // class UnitSymbolPOA
