package org.jdesktop.wonderland.modules.oweditor.client.editor.gui;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;

public abstract class ShapeObject {
    
    
    public abstract Shape getTransformedShape();
    
    public abstract Shape getShape();
    
    public abstract long getID();

    public abstract void paintOriginal(Graphics2D g, AffineTransform at, double scale);
    
    public abstract void paintTransformed(Graphics2D g, AffineTransform at);

    public abstract void paintOriginal(Graphics2D g2);
    
    public abstract void setSelected(boolean select);
    
    public abstract boolean isSelected();
    
    public abstract void switchSelection();
    
    public abstract void setLocation(int x, int y);

    public abstract void setTranslation(double distance_x, double distance_y) ;
    
    public abstract int getX();
    
    public abstract int getY();
    
    public abstract int getWidth();
    
    public abstract int getHeight();   
    
    public abstract String getName();
    

}
