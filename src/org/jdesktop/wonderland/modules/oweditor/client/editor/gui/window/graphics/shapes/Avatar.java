package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.window.graphics.shapes;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

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
    
    private int nameBoundsAbove = GUISettings.AVATARNAMEPOSITIONTINY;
    
    public Avatar(long id, int x, int y, int width, int height, String name){
        originalShape = new Ellipse2D.Double(x, y, width, height);
        this.id = id;
        
        if(name.length() > 10){
            name = name.substring(0,7)+"...";
        }
        this.name = name;
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
        paintName(g, at);
    }
    
    /**
     * Paints the name of the object. This is not implemented in the
     * normal paint function, because the names have to be painted
     * later, in order to see them, if another object is blocking the view.
     * 
     * @param g the graphics2d instance.
     * @param at the affine transformation.
     * @param scale
     */
    private void paintName(Graphics2D g, AffineTransform at) {
        g.setPaint(color); 
        
        double scaleFactor = at.getScaleX();       

        int screenRes = Toolkit.getDefaultToolkit().getScreenResolution();
        int font_size = (int)Math.round(GUISettings.OBJECTNAMESIZE*scaleFactor * screenRes / 72.0);

        Font font = new Font(GUISettings.OBJECTNAMEFONTTYPE, Font.PLAIN, font_size);        
        g.setFont(font);
        
        Rectangle r = transformedShape.getBounds();
        System.out.println(r.getCenterX() + " " +r.getY());
        int x = (int) (r.getCenterX() - Math.round(getXAdding(g,font)));
        int y = (int) (r.getY() - Math.round(nameBoundsAbove*scaleFactor));
        
        System.out.println(x + " " + y);

        g.drawString(name, x, y);  
    }
    
    private double getXAdding(Graphics2D g, Font font){
        
        FontRenderContext context = g.getFontRenderContext();
        
        Rectangle2D bounds_r = font.getStringBounds(name, context);
        
        double width = bounds_r.getWidth();
        
        return width/2;
        
    }

    @Override
    public void setLocation(int x, int y, double z) {
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
