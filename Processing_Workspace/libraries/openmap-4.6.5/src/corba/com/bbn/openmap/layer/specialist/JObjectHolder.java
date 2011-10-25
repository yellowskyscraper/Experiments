// **********************************************************************
// 
// <copyright>
// 
//  BBN Technologies
//  10 Moulton Street
//  Cambridge, MA 02138
//  (617) 873-8000
// 
//  Copyright (C) BBNT Solutions LLC. All rights reserved.
// 
// </copyright>
// **********************************************************************
// 
// $Source: /cvs/distapps/openmap/src/corba/com/bbn/openmap/layer/specialist/JObjectHolder.java,v $
// $RCSfile: JObjectHolder.java,v $
// $Revision: 1.1.1.1.2.1 $
// $Date: 2004/10/14 18:26:30 $
// $Author: dietrick $
// 
// **********************************************************************

package com.bbn.openmap.layer.specialist;

/** class JGraphic */
public interface JObjectHolder {

    public void setObject(com.bbn.openmap.CSpecialist.EComp aObject);

    public com.bbn.openmap.CSpecialist.EComp getObject();

    public void update(
                       com.bbn.openmap.CSpecialist.GraphicPackage.GF_update update);
}