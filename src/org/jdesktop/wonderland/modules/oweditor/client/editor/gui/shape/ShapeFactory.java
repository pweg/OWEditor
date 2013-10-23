package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.shape;

/**
 * A factory, which is used to create different kinds of
 * shapes.
 * 
 * @author Patrick
 *
 */
public class ShapeFactory {
    
    public static final int AVATAR = 0;
    public static final int RECTANGLE = 1;
    public static final int CIRCLE = 2;
    public static final int DRAGGINGRECTANGLE = 11;
    public static final int DRAGGINGRELLIPSE = 12;
    
    public InternalShapeMediatorInterface smi = null;
    
    public ShapeFactory(InternalShapeMediatorInterface smi){
        this.smi = smi;
    }

    /**
     * Creates a new ShapeObject.
     * 
     * @param type the type of shape that should be created.
     * @param x the x coordinate.
     * @param y the y coordinate.
     * @param width the width of the rectangle.
     * @param height the height of the rectangle.
     * @param id the id for the rectangle, which has to be the same id as in the virtual world.
     */
    public ShapeObject createShapeObject(int type, int x, int y, int width, int height, 
            long id, String name, double rotation){
        
        switch(type){
            case AVATAR:
                return new ShapeObjectAvatar(id, x,y,width, height);
            case RECTANGLE:
                return new ShapeObjectRectangle(x,y,width, height, id, name, rotation);
            case CIRCLE:
                return new ShapeObjectEllipse(x,y,width,height, id, name, rotation);
            case DRAGGINGRECTANGLE:
                return new ShapeObjectDraggingRect(x,y,width,height, id, rotation,
                        smi.getScale());
            default:
                throw new IllegalArgumentException(
                    "Unknown type");
        }
    }

    /*
    public SimpleShapeObject createSimpleShapeObject(int type, int x, int y, int width, int height){
        
        switch(type){
            case SELECTIONRECTANGLE:
                return new ShapeObjectSelectionRect(x,y,width,height); 
            default:
                throw new IllegalArgumentException(
                    "Unknown type");
        }
    }*/

}
