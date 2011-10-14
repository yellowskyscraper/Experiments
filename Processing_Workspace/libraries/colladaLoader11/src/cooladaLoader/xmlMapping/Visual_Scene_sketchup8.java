package cooladaLoader.xmlMapping;
import java.util.HashMap;
import java.util.HashSet;
import processing.xml.XMLElement;
/**
 * <p>Lucerne University of Applied Sciences and Arts <a href="http://www.hslu.ch">http://www.hslu.ch</a></p>
 *
 * <p>This source is free; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License and by nameing of the originally author</p>
 * 
 *<p> this class maps the Visual_Scene-tag and binds meterials to the geometries</p>
 * 
 * @author Markus Zimmermann <a href="http://www.die-seite.ch">http://www.die-seite.ch</a>
 * @version 1.0
 */
class Visual_Scene_sketchup8 {

    private String ID;


    Visual_Scene_sketchup8(XMLElement aScene, Library_Materials lib_materials, Library_Geometries lib_geometries)
    {
        ID = aScene.getAttribute("id");

        for (XMLElement node : aScene.getChildren("node")) {
            for (XMLElement instance_geometry : node.getChildren("instance_geometry")) {

                String geometryID = instance_geometry.getAttribute("url").substring(1);
                HashMap<String,String> materialBinds = new HashMap<String, String>(); //Format: <Symbol,MatID>

                //scan all Materialbinds that belongs to the geometry
                for (XMLElement instance_material : instance_geometry.getChild("bind_material/technique_common").getChildren("instance_material"))
                {
                    String materialSymbol = instance_material.getAttribute("symbol");
                    String materialID = instance_material.getAttribute("target").substring(1);
                    materialBinds.put(materialSymbol, materialID);
                }
                //get the geometry-obj and start the material-binds to its triangles and lines
                Geometry geometry = lib_geometries.getGeometryByID(geometryID);
                geometry.bindMaterials(lib_materials, materialBinds);
                //System.out.println(geometry); //useful on debugging
            }
        }
    }
    /**
     * 
     * @return the ID of this scene
     */
    String getID()
    {
        return ID;
    }
}
