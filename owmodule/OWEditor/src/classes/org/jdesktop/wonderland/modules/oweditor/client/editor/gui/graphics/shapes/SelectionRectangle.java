package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.graphics.shapes;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;

import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.GUISettings;

/**
 * This shape class is used for the selection rectangle and should
 * only exist once.
 * @author Patrick
 *
 */
public class SelectionRectangle extends SimpleShapeObject{
    
    private Rectangle originalShape = null;
    private Shape transformedShape = null;
    private Paint color = GUISettings.SELECTIONRECTCOLOR;
    
    final static BasicStroke dashed = new BasicStroke(
            1.0f,                      // Width
            BasicStroke.CAP_SQUARE,    // End cap
            BasicStroke.JOIN_MITER,    // Join style
            10.0f,                     // Miter limit
            new float[] {5.0f,5.0f}, // Dash pattern
            0.0f);  
    
    /**
     * Creates a new instance of the selection rectangle shape class.
     * 
     * @param x the x coordinate.
     * @param y the y coordinate.
     * @param width the width.
     * @param height the height.
     */
    public SelectionRectangle(int x, int y, int width, int height){
        
        originalShape = new Rectangle (x, y, width, height);
    }

    @Override
    public Rectangle getShape() {
        return originalShape;
    }
    
    @Override
    public void paintOriginal(Graphics2D g, AffineTransform at) {
        
        g.setPaint(color); 
        g.setStroke(dashed); 
        
        /*
         * may be changed later, because rectangle should be transformed.
         */
        //transformedShape = at.createTransformedShape(originalShape);
        g.draw(at.createTransformedShape(originalShape)); 
    }

    @Override
    public void setLocation(int x, int y) {
        originalShape.setLocation(x, y); 
    }

    @Override
    public void setTranslation(double distance_x, double distance_y) {
        int new_x = (int) Math.round(originalShape.x-distance_x);
        int new_y = (int) Math.round(originalShape.y-distance_y);
        originalShape.setLocation(new_x, new_y);
    }
    
    public void set(int x, int y, int width, int height){
        originalShape.setRect(x, y, width, height);
    }

    @Override
    public int getX() {
        return originalShape.x;
    }

    @Override
    public int getY() {
        return originalShape.y;
    }

    @Override
    public int getWidth() {
        return originalShape.width;
    }

    @Override
    public int getHeight() {
        return originalShape.height;
    }

    @Override
    public Shape getTransformedShape() {
        return transformedShape;
    }

}
