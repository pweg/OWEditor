package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.graphics;

import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.graphics.shapes.DraggingObject;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.graphics.shapes.DraggingRect;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.graphics.shapes.SelectionRectangle;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.graphics.shapes.ShapeObject;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.graphics.shapes.Avatar;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.graphics.shapes.ShapeEllipse;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.graphics.shapes.ShapeRectangle;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.graphics.shapes.SimpleShapeObject;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.graphics.shapes.ToolTip;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.graphics.shapes.IToolTip;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.graphics.shapes.TransformationBorder;
import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.graphics.shapes.ITransformationBorder;

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
    //public static final int DRAGGINGRECTANGLE = 11;
   // public static final int DRAGGINGRELLIPSE = 12;
    
    public IInternalMediator smi = null;
    
    public ShapeFactory(IInternalMediator smi){
        this.smi = smi;
    }

    /**
     * Creates a new ShapeObject.
     * 
     * @param type The type of shape that should be created.
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @param width The width of the rectangle.
     * @param height The height of the rectangle.
     * @param id The id for the object, which has to be the same id as in the virtual world.
     * @param img The representational image.
     * @return The created shape.
     */
    public ShapeObject createShapeObject(int type, int x, int y, double z, int width, int height, 
            long id, String name, double rotation, double scale, String imgName,
            String imgDir){
        
        switch(type){
            case AVATAR:
                return new Avatar(id, x,y,width, height);
            case RECTANGLE:
                return new ShapeRectangle(x,y,z,
                        width, height, id, name, rotation, scale, imgName, imgDir, smi);
            case CIRCLE:
                return new ShapeEllipse(x,y,z,
                        width,height, id, name, rotation, scale, imgName, imgDir, smi);
            default:
                throw new IllegalArgumentException(
                    "Unknown type");
        }
    }

    /**
     * Creates a simple shape object. They are used for creating the selection
     * rectangle (use RECTANGLE).
     * 
     * @param type the type of shape that should be created.
     * @param x the x coordinate.
     * @param y the y coordinate.
     * @param width the width of the rectangle.
     * @param height the height of the rectangle.     
     * @return The created shape.
     */
    public SimpleShapeObject createSimpleShapeObject(int type, int x, int y, int width, int height){
        
        switch(type){
            case RECTANGLE:
                return new SelectionRectangle(x,y,width,height); 
            default:
                throw new IllegalArgumentException(
                    "Unknown type");
        }
    }
    
    /**
     * Creates a dragging shape, which is used for transformation
     * and translation operations. It is a hollow copy of the original shape.
     * 
     * @param type the type of shape that should be created.
     * @param x the x coordinate.
     * @param y the y coordinate.
     * @param width the width of the rectangle.
     * @param height the height of the rectangle.
     * @param id the id for the object the dragging shape is based on
     *        which has to be the same id as in the virtual world.
     * @param name the name of the shape
     * @param rotation the rotation of the shape
     * @param scale the scale of the shape
     * @param at the affine transformation last used on the original shape.
     * @return The created shape.
     */
    public DraggingObject createDraggingShapeObject(int type, int x, int y, int width, int height, 
            long id, String name, double rotation, double scale, AffineTransform at){
        switch(type){
            case RECTANGLE:
                return new DraggingRect(x,y,width,height, id, rotation, scale,
                        at);
            default:
                throw new IllegalArgumentException(
                    "Unknown type");  
        }
    }
    
    /**
     * Creates a transformation border, which is shown by transformation,
     * like rotation and scaling.
     * 
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @param width The width.
     * @param height The height.
     * @param at The last affine transform used to paint the image.
     * @param mode The mode of the border.
     * @return The created border.
     */
    public ITransformationBorder createTransformBorder(int x, int y, 
            int width, int height, 
            AffineTransform at,
            byte mode){
        return new TransformationBorder(x, y, width, height, at,
                mode);
    }
    
    /**
     * Creates a tooltip shape.
     * 
     * @param coordinates the position of the tooltip
     * @param text the text the tooltip should contain.
     * @return The created tooltip.
     */
    public IToolTip createToolTip(Point coordinates, String text){
        return new ToolTip(coordinates, text);
    }


}
