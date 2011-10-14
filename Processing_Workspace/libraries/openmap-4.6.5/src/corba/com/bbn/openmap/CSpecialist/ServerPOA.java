package com.bbn.openmap.CSpecialist;


/**
* com/bbn/openmap/CSpecialist/ServerPOA.java .
* Generated by the IDL-to-Java compiler (portable), version "3.1"
* from com/bbn/openmap/layer/specialist/Specialist.idl
* Wednesday, March 4, 2009 5:10:38 PM EST
*/


//------------------------------------------------------------
public abstract class ServerPOA extends org.omg.PortableServer.Servant
 implements com.bbn.openmap.CSpecialist.ServerOperations, org.omg.CORBA.portable.InvokeHandler
{

  // Constructors

  private static java.util.Hashtable _methods = new java.util.Hashtable ();
  static
  {
    _methods.put ("getRectangle", new java.lang.Integer (0));
    _methods.put ("sendGesture", new java.lang.Integer (1));
    _methods.put ("signoff", new java.lang.Integer (2));
    _methods.put ("getPaletteConfig", new java.lang.Integer (3));
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

  // unique host:pid:layer context
       case 0:  // CSpecialist/Server/getRectangle
       {
         com.bbn.openmap.CSpecialist.CProjection p = com.bbn.openmap.CSpecialist.CProjectionHelper.read (in);
         com.bbn.openmap.CSpecialist.LLPoint ll1 = com.bbn.openmap.CSpecialist.LLPointHelper.read (in);
         com.bbn.openmap.CSpecialist.LLPoint ll2 = com.bbn.openmap.CSpecialist.LLPointHelper.read (in);
         String staticArgs = in.read_string ();
         org.omg.CORBA.StringHolder dynamicArgs = new org.omg.CORBA.StringHolder ();
         dynamicArgs.value = in.read_string ();
         org.omg.CORBA.ShortHolder graphicSeletableDistance = new org.omg.CORBA.ShortHolder ();
         org.omg.CORBA.BooleanHolder areaEvents = new org.omg.CORBA.BooleanHolder ();
         com.bbn.openmap.CSpecialist.GraphicChange notifyOnChange = com.bbn.openmap.CSpecialist.GraphicChangeHelper.read (in);
         String uniqueID = in.read_string ();
         com.bbn.openmap.CSpecialist.UGraphic $result[] = null;
         $result = this.getRectangle (p, ll1, ll2, staticArgs, dynamicArgs, graphicSeletableDistance, areaEvents, notifyOnChange, uniqueID);
         out = $rh.createReply();
         com.bbn.openmap.CSpecialist.UGraphicSeqHelper.write (out, $result);
         out.write_string (dynamicArgs.value);
         out.write_short (graphicSeletableDistance.value);
         out.write_boolean (areaEvents.value);
         break;
       }

       case 1:  // CSpecialist/Server/sendGesture
       {
         com.bbn.openmap.CSpecialist.MouseEvent gesture = com.bbn.openmap.CSpecialist.MouseEventHelper.read (in);
         String uniqueID = in.read_string ();
         com.bbn.openmap.CSpecialist.ActionUnion $result[] = null;
         $result = this.sendGesture (gesture, uniqueID);
         out = $rh.createReply();
         com.bbn.openmap.CSpecialist.ActionSeqHelper.write (out, $result);
         break;
       }


  // longer needed for context host:pid:layer
       case 2:  // CSpecialist/Server/signoff
       {
         String uniqueID = in.read_string ();
         this.signoff (uniqueID);
         out = $rh.createReply();
         break;
       }

       case 3:  // CSpecialist/Server/getPaletteConfig
       {
         com.bbn.openmap.CSpecialist.WidgetChange notifyOnChange = com.bbn.openmap.CSpecialist.WidgetChangeHelper.read (in);
         String staticArgs = in.read_string ();
         org.omg.CORBA.StringHolder dynamicArgs = new org.omg.CORBA.StringHolder ();
         dynamicArgs.value = in.read_string ();
         String uniqueID = in.read_string ();
         com.bbn.openmap.CSpecialist.UWidget $result[] = null;
         $result = this.getPaletteConfig (notifyOnChange, staticArgs, dynamicArgs, uniqueID);
         out = $rh.createReply();
         com.bbn.openmap.CSpecialist.UWidgetSeqHelper.write (out, $result);
         out.write_string (dynamicArgs.value);
         break;
       }

       default:
         throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);
    }

    return out;
  } // _invoke

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:CSpecialist/Server:1.0"};

  public String[] _all_interfaces (org.omg.PortableServer.POA poa, byte[] objectId)
  {
    return (String[])__ids.clone ();
  }

  public Server _this() 
  {
    return ServerHelper.narrow(
    super._this_object());
  }

  public Server _this(org.omg.CORBA.ORB orb) 
  {
    return ServerHelper.narrow(
    super._this_object(orb));
  }


} // class ServerPOA
