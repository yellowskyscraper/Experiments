package com.bbn.openmap.CSpecialist;


/**
* com/bbn/openmap/CSpecialist/_ServerStub.java .
* Generated by the IDL-to-Java compiler (portable), version "3.1"
* from com/bbn/openmap/layer/specialist/Specialist.idl
* Wednesday, March 4, 2009 5:10:38 PM EST
*/


//------------------------------------------------------------
public class _ServerStub extends org.omg.CORBA.portable.ObjectImpl implements com.bbn.openmap.CSpecialist.Server
{


  // unique host:pid:layer context
  public com.bbn.openmap.CSpecialist.UGraphic[] getRectangle (com.bbn.openmap.CSpecialist.CProjection p, com.bbn.openmap.CSpecialist.LLPoint ll1, com.bbn.openmap.CSpecialist.LLPoint ll2, String staticArgs, org.omg.CORBA.StringHolder dynamicArgs, org.omg.CORBA.ShortHolder graphicSeletableDistance, org.omg.CORBA.BooleanHolder areaEvents, com.bbn.openmap.CSpecialist.GraphicChange notifyOnChange, String uniqueID)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("getRectangle", true);
                com.bbn.openmap.CSpecialist.CProjectionHelper.write ($out, p);
                com.bbn.openmap.CSpecialist.LLPointHelper.write ($out, ll1);
                com.bbn.openmap.CSpecialist.LLPointHelper.write ($out, ll2);
                $out.write_string (staticArgs);
                $out.write_string (dynamicArgs.value);
                com.bbn.openmap.CSpecialist.GraphicChangeHelper.write ($out, notifyOnChange);
                $out.write_string (uniqueID);
                $in = _invoke ($out);
                com.bbn.openmap.CSpecialist.UGraphic $result[] = com.bbn.openmap.CSpecialist.UGraphicSeqHelper.read ($in);
                dynamicArgs.value = $in.read_string ();
                graphicSeletableDistance.value = $in.read_short ();
                areaEvents.value = $in.read_boolean ();
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return getRectangle (p, ll1, ll2, staticArgs, dynamicArgs, graphicSeletableDistance, areaEvents, notifyOnChange, uniqueID        );
            } finally {
                _releaseReply ($in);
            }
  } // getRectangle

  public com.bbn.openmap.CSpecialist.ActionUnion[] sendGesture (com.bbn.openmap.CSpecialist.MouseEvent gesture, String uniqueID)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("sendGesture", true);
                com.bbn.openmap.CSpecialist.MouseEventHelper.write ($out, gesture);
                $out.write_string (uniqueID);
                $in = _invoke ($out);
                com.bbn.openmap.CSpecialist.ActionUnion $result[] = com.bbn.openmap.CSpecialist.ActionSeqHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return sendGesture (gesture, uniqueID        );
            } finally {
                _releaseReply ($in);
            }
  } // sendGesture


  // longer needed for context host:pid:layer
  public void signoff (String uniqueID)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("signoff", false);
                $out.write_string (uniqueID);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                signoff (uniqueID        );
            } finally {
                _releaseReply ($in);
            }
  } // signoff

  public com.bbn.openmap.CSpecialist.UWidget[] getPaletteConfig (com.bbn.openmap.CSpecialist.WidgetChange notifyOnChange, String staticArgs, org.omg.CORBA.StringHolder dynamicArgs, String uniqueID)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("getPaletteConfig", true);
                com.bbn.openmap.CSpecialist.WidgetChangeHelper.write ($out, notifyOnChange);
                $out.write_string (staticArgs);
                $out.write_string (dynamicArgs.value);
                $out.write_string (uniqueID);
                $in = _invoke ($out);
                com.bbn.openmap.CSpecialist.UWidget $result[] = com.bbn.openmap.CSpecialist.UWidgetSeqHelper.read ($in);
                dynamicArgs.value = $in.read_string ();
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return getPaletteConfig (notifyOnChange, staticArgs, dynamicArgs, uniqueID        );
            } finally {
                _releaseReply ($in);
            }
  } // getPaletteConfig

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:CSpecialist/Server:1.0"};

  public String[] _ids ()
  {
    return (String[])__ids.clone ();
  }

  private void readObject (java.io.ObjectInputStream s) throws java.io.IOException
  {
     String str = s.readUTF ();
     String[] args = null;
     java.util.Properties props = null;
     org.omg.CORBA.Object obj = org.omg.CORBA.ORB.init (args, props).string_to_object (str);
     org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl) obj)._get_delegate ();
     _set_delegate (delegate);
  }

  private void writeObject (java.io.ObjectOutputStream s) throws java.io.IOException
  {
     String[] args = null;
     java.util.Properties props = null;
     String str = org.omg.CORBA.ORB.init (args, props).object_to_string (this);
     s.writeUTF (str);
  }
} // class _ServerStub