package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.graphics;

import java.util.ArrayList;
import java.util.HashMap;

import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.graphics.shapes.ShapeObject;

/**
 * This class is used to transform normal shapes, 
 * usually when a server update is due.
 * 
 * @author Patrick
 *
 */
public class ServerTransformationManager {
    
    private IInternalMediator med = null;
    
    private HashMap<Long, ArrayList<Object>> lateTranslate = 
            new HashMap<Long, ArrayList<Object>>();
    
    public ServerTransformationManager(IInternalMediator med){
        this.med = med;
    }
    
    /**
     * Translates a shape with the id to the specified
     * coordinates. This is called, when an update
     * comes from the data package
     * 
     * @param id the id of the shape to translate.
     * @param x the new x coordinate.
     * @param y the new y coordinate.
     */
    public void translateShape(long id, int x, int y, double z){
        ShapeObject shape = med.getShape(id);
        
        if(shape == null){
            ArrayList<Object> list = new ArrayList<Object>();

            list.add(x);
            list.add(y);
            list.add(z);
            lateTranslate.put(id, list);
            
            return;
        }
        
        double old_z = shape.getZ();
        shape.setLocation(x, y, z);
        
        if(old_z != z){
            med.readdShape(shape);
            med.repaint();
        }
    }
    
    /**
     * Does a late translation with backed up coordinates.
     * 
     * @param id Id of the shape.
     */
    public void lateTransform(long id){
        if(lateTranslate.containsKey(id)){
            lateTranslate.remove(id);
            ArrayList<Object> list = lateTranslate.get(id);
            
            try{
                int x = (Integer) list.get(0);
                int y = (Integer) list.get(1);
                double z = (Double) list.get(2);

                translateShape(id, x,y,z);
            }catch(Exception ex){
                
            }
        }
    }

    /**
     * Sets the rotation of one shape.
     * 
     * @param id The id of the shape.
     * @param rotation The rotation angle.
     */
    public void setRotation(long id, double rotation) {
        ShapeObject shape = med.getShape(id);
        shape.setRotation(rotation);
    }

    /**
     * Sets the scale of a specific shape.
     * 
     * @param id The id of the shape.
     * @param scale The new scale.
     */
    public void setScale(long id, double scale) {
        ShapeObject shape = med.getShape(id);
        shape.setScale(scale);
    }
    

}
