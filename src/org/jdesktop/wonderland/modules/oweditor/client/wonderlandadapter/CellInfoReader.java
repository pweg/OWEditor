/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jdesktop.wonderland.modules.oweditor.client.wonderlandadapter;

import com.jme.bounding.BoundingBox;
import com.jme.bounding.BoundingSphere;
import com.jme.bounding.BoundingVolume;
import com.jme.math.Vector3f;
import org.jdesktop.wonderland.client.cell.Cell;
import org.jdesktop.wonderland.common.cell.CellTransform;

/**
 *
 * @author Patrick
 */
public class CellInfoReader {
    
    public static Vector3fInfo createCellInfo(Cell cell){
        
        BoundingVolume bounds = cell.getWorldBounds();
        Vector3fInfo v = new Vector3fInfo();
        
        if(bounds instanceof BoundingBox){
            BoundingBox box = (BoundingBox) bounds;
            v.width = box.xExtent*2;
            v.height = box.zExtent*2;            
        }else if(bounds instanceof BoundingSphere){
            BoundingSphere sphere = (BoundingSphere) bounds;
            v.width= sphere.radius*2;
            v.height= sphere.radius*2;
        }
        CellTransform transform = cell.getLocalTransform();
        Vector3f transl = transform.getTranslation(null);
                
        /*
        * Remember: The editor uses y as height value in a 2D environment,
        * so it should be changed with z.
        */
        v.x = transl.x;
        v.z = transl.y;
        v.y = transl.z;
        
        return v;
    }
    
}
