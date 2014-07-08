package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.graphics.shapes;

import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;

import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.GUISettings;

/**
 * The 2d graphic implementation of an avatar.
 * 
 * @author Patrick
 */
public class Avatar extends ShapeObject{
    
    private long id = 0;
    private Ellipse2D originalShape = null;
    private Shape transformedShape = null;
    private final Paint color = GUISettings.AVATARCOLOR;
    private String name = "";
    
    public Avatar(long id, int x, int y, int width, int height){
        originalShape = new Ellipse2D.Double(x, y, width, height);
        this.id = id;
    }

    @Override
    public Shape getTransformedShape() {
        return transformedShape;
    }

    @Override
    public Shape getShape() {
        return originalShape;
    }

    @Override
    public void paintOriginal(Graphics2D g, AffineTransform at) {
        g.setPaint(color);  
        
        transformedShape = at.createTransformedShape(originalShape);
        
        g.fill(at.createTransformedShape(originalShape));
    }

    @Override
    public void setLocation(int x, int y) {
        originalShape.setFrame(x, y, originalShape.getWidth(), originalShape.getHeight());
    }

    @Override
    public void setTranslation(double distance_x, double distance_y) {
        
    }

    @Override
    public int getX() {
        return (int)originalShape.getX();
    }

    @Override
    public int getY() {
        return (int)originalShape.getY();
    }
    
    @Override
    public double getZ(){
        return 0;
    }

    @Override
    public int getWidth() {
        return (int)originalShape.getWidth();
    }

    @Override
    public int getHeight() {
        return (int)originalShape.getHeight();
    }

    @Override
    public long getID() {
        return id;
    }

    @Override
    public void setSelected(boolean select) {
    
    }

    @Override
    public boolean isSelected() {
        return false;
    }

    @Override
    public void switchSelection() {
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setRotation(double rotation) {
    }

    @Override
    public double getRotation() {
        return 0;
    }

    @Override
    public void set(int x, int y, int width, int height) {
        originalShape.setFrame(x, y, width, height);
    }

    @Override
    public ShapeObject clone() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public double getScale() {
        return 1;
    }

    @Override
    public void setScale(double scale) {
    }

    @Override
    public boolean isNameAbbreviated() {
        return false;
    }

    @Override
    public void setImage(String name, String dir) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setColor(Paint color) {
        // TODO Auto-generated method stub
        
    }

}
