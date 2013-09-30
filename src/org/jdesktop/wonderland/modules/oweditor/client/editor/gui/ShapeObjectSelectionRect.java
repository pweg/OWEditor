package org.jdesktop.wonderland.modules.oweditor.client.editor.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;

public class ShapeObjectSelectionRect extends ShapeObject{
    
    private Rectangle originalShape = null;
    private Shape transformedShape = null;
    private boolean isSelected = false;
    private Paint color = GUISettings.selectionRectangleColor;
    
    final static BasicStroke dashed = new BasicStroke(
            1.0f,                      // Width
            BasicStroke.CAP_SQUARE,    // End cap
            BasicStroke.JOIN_MITER,    // Join style
            10.0f,                     // Miter limit
            new float[] {5.0f,5.0f}, // Dash pattern
            0.0f);  
    
    public ShapeObjectSelectionRect(int x, int y, int width, int height){
        
        originalShape = new Rectangle (x, y, width, height);
        
    }
    
    
    
    @Override
    public Shape getTransformedShape() {
        return transformedShape;
    }

    @Override
    public Rectangle getShape() {
        return originalShape;
    }

    @Override
    public int getID() {
        return 0;
    }
    

    @Override
    public void paintOriginal(Graphics2D g) {
        g.setPaint(color); 
        g.setStroke(dashed); 

        g.draw(originalShape);
    }

    @Override
    public void paintOriginal(Graphics2D g, AffineTransform at, double scale) {
        
        g.setPaint(color); 
        g.setStroke(dashed); 
        
        transformedShape = at.createTransformedShape(originalShape);
                
        g.draw(at.createTransformedShape(originalShape)); 
    }

    @Override
    public void paintTransformed(Graphics2D g, AffineTransform at) {
        if(transformedShape == null)
            return;
        
        g.setPaint(color); 
        g.setStroke(dashed); 
        
        g.draw(transformedShape);
        
    }



    @Override
    public void setSelected(boolean select) {
        isSelected = select;
        
    }



    @Override
    public boolean isSelected() {
        return isSelected;
    }



    @Override
    public void switchSelection() {
        isSelected = !isSelected;
        
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
    public String getName() {
        return "selectionRectangle";
    }

}
