package org.jdesktop.wonderland.modules.oweditor.client.editor.gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;

public class ShapeObjectRectangle extends ShapeObject{
    
    private Rectangle originalShape = null;
    private Shape transformedShape = null;
    private int id = -1;
    private boolean isSelected = false;
    private Paint color = GUISettings.objectColor;
    
    public ShapeObjectRectangle(int x, int y, int width, int height, int id){
        
        originalShape = new Rectangle (x, y, width, height);
        this.id = id;
        
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
        return id;
    }
    

    @Override
    public void paintOriginal(Graphics2D g) {
        g.setPaint(color);  

        g.fill(originalShape);
        
        if(isSelected)
            g.setPaint(GUISettings.selectionBorderColor);
        g.draw(originalShape);
    }

    @Override
    public void paintOriginal(Graphics2D g, AffineTransform at) {

        g.setPaint(color);  
        
        transformedShape = at.createTransformedShape(originalShape);
        
        g.fill(at.createTransformedShape(originalShape));
        if(isSelected)
            g.setPaint(GUISettings.selectionBorderColor);
        
        g.draw(at.createTransformedShape(originalShape)); 
    }

    @Override
    public void paintTransformed(Graphics2D g, AffineTransform at) {
        
        if(transformedShape == null)
            return;
        
        g.setPaint(color);  
        
        g.fill(transformedShape);
        if(isSelected)
            g.setPaint(GUISettings.selectionBorderColor);
        
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

}
