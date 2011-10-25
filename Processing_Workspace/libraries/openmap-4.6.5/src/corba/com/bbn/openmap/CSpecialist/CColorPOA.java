package com.bbn.openmap.CSpecialist;


/**
* com/bbn/openmap/CSpecialist/CColorPOA.java .
* Generated by the IDL-to-Java compiler (portable), version "3.1"
* from com/bbn/openmap/layer/specialist/Specialist.idl
* Wednesday, March 4, 2009 5:10:37 PM EST
*/

public abstract class CColorPOA extends org.omg.PortableServer.Servant
 implements com.bbn.openmap.CSpecialist.CColorOperations, org.omg.CORBA.portable.InvokeHandler
{

  // Constructors

  private static java.util.Hashtable _methods = new java.util.Hashtable ();
  static
  {
    _methods.put ("_get_red", new java.lang.Integer (0));
    _methods.put ("_set_red", new java.lang.Integer (1));
    _methods.put ("_get_green", new java.lang.Integer (2));
    _methods.put ("_set_green", new java.lang.Integer (3));
    _methods.put ("_get_blue", new java.lang.Integer (4));
    _methods.put ("_set_blue", new java.lang.Integer (5));
    _methods.put ("fill", new java.lang.Integer (6));
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
       case 0:  // CSpecialist/CColor/_get_red
       {
         short $result = (short)0;
         $result = this.red ();
         out = $rh.createReply();
         out.write_ushort ($result);
         break;
       }

       case 1:  // CSpecialist/CColor/_set_red
       {
         short newRed = in.read_ushort ();
         this.red (newRed);
         out = $rh.createReply();
         break;
       }

       case 2:  // CSpecialist/CColor/_get_green
       {
         short $result = (short)0;
         $result = this.green ();
         out = $rh.createReply();
         out.write_ushort ($result);
         break;
       }

       case 3:  // CSpecialist/CColor/_set_green
       {
         short newGreen = in.read_ushort ();
         this.green (newGreen);
         out = $rh.createReply();
         break;
       }

       case 4:  // CSpecialist/CColor/_get_blue
       {
         short $result = (short)0;
         $result = this.blue ();
         out = $rh.createReply();
         out.write_ushort ($result);
         break;
       }

       case 5:  // CSpecialist/CColor/_set_blue
       {
         short newBlue = in.read_ushort ();
         this.blue (newBlue);
         out = $rh.createReply();
         break;
       }

       case 6:  // CSpecialist/CColor/fill
       {
         com.bbn.openmap.CSpecialist.CColorPackage.EColor $result = null;
         $result = this.fill ();
         out = $rh.createReply();
         com.bbn.openmap.CSpecialist.CColorPackage.EColorHelper.write (out, $result);
         break;
       }

       default:
         throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);
    }

    return out;
  } // _invoke

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:CSpecialist/CColor:1.0"};

  public String[] _all_interfaces (org.omg.PortableServer.POA poa, byte[] objectId)
  {
    return (String[])__ids.clone ();
  }

  public CColor _this() 
  {
    return CColorHelper.narrow(
    super._this_object());
  }

  public CColor _this(org.omg.CORBA.ORB orb) 
  {
    return CColorHelper.narrow(
    super._this_object(orb));
  }


} // class CColorPOA