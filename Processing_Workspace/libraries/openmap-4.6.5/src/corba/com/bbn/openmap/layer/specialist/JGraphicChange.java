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
// $Source: /cvs/distapps/openmap/src/corba/com/bbn/openmap/layer/specialist/JGraphicChange.java,v $
// $RCSfile: JGraphicChange.java,v $
// $Revision: 1.3.2.1 $
// $Date: 2004/10/14 18:26:30 $
// $Author: dietrick $
// 
// **********************************************************************

package com.bbn.openmap.layer.specialist;

import com.bbn.openmap.CSpecialist.GraphicChange;
import com.bbn.openmap.CSpecialist._GraphicChangeStub;

/**
 */
public class JGraphicChange extends _GraphicChangeStub implements GraphicChange {

    protected CSpecLayer layer = null;

    public JGraphicChange(CSpecLayer aLayer) {
        layer = aLayer;
    }

    /**
     */
    public void ChangeNotify(boolean forceRedraw, java.lang.String[] gIDseq) {}

    /**
     */
    public void ForgetAll(boolean forceRedraw) {
        if (forceRedraw)
            layer.doPrepare();
    }

    /**
     */
    public void RaiseGraphic(boolean forceRedraw, java.lang.String[] gIDseq) {}

    /**
     */
    public void LowerGraphic(boolean forceRedraw, java.lang.String[] gIDseq) {}

    /**
     */
    public void SetClientAttributes(
                                    boolean forceRedraw,
                                    com.bbn.openmap.CSpecialist.UpdateRecord[] info) {}

    /**
     */
    public void ping() {}
}