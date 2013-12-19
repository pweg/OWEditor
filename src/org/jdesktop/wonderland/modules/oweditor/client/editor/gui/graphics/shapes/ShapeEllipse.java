package org.jdesktop.wonderland.modules.oweditor.client.editor.gui.graphics.shapes;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

import org.jdesktop.wonderland.modules.oweditor.client.editor.gui.GUISettings;

/**
 * A class for standard rectangles. 
 * 
 * @author Patrick
 *
 */
public class ShapeEllipse extends ShapeObject{
    
    private Rectangle originalShape = null;
    private Shape transformedShape = null;
    private long id = -1;
    private boolean isSelected = false;
    private Paint color = GUISettings.OBJECTCOLOR;
    private Paint nameColor = GUISettings.OBJECTNAMECOLOR;
    private String name = "";
    private boolean nameWrapp = false;
    private double rotation = 0;
    private double scale = 0;
    
    //These variables are used to determine, where the name of the object should be.
    private int nameBoundsX = GUISettings.NAMEPOSITIONINX;
    private int nameBoundsAbove = GUISettings.NAMEPOSITIONINY;
    
    /**
     * Creates a new ObjectRectangle shape instance.
     * 
     * @param x the x coordinate of the shape.
     * @param y the y coordinate of the shape.
     * @param width the width of the shape.
     * @param height the height of the shape.
     * @param id the shape id.
     * @param name the name of the shape.
     */
    public ShapeEllipse(int x, int y, int width, int height, long id, String name,
            double rotation, double scale){
        
        originalShape = new Rectangle (x, y, width, height);
        this.name = name;
        this.id = id;
        this.rotation = rotation;
        this.scale = scale;
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
    public long getID() {
        return id;
    }

    @Override
    public void paintOriginal(Graphics2D g, AffineTransform at) {
        g.setPaint(color);  
        
        transformedShape = at.createTransformedShape(originalShape);
        
        g.fill(at.createTransformedShape(originalShape));
        
        //changes color when selected.
        if(isSelected)
            g.setPaint(GUISettings.SELECTIONCOLOR);
        
        g.draw(at.createTransformedShape(originalShape)); 
    }

    @Override
    public void paintName(Graphics2D g, AffineTransform at) {


        g.setPaint(nameColor); 
        //changes color when selected.
        if(isSelected)
            g.setPaint(GUISettings.SELECTIONCOLOR);

        int screenRes = Toolkit.getDefaultToolkit().getScreenResolution();
        int font_size = (int)Math.round(GUISettings.OBJECTNAMESIZE*scale * screenRes / 72.0);


        Rectangle r = transformedShape.getBounds();
        //int x = (int) (r.getX() + Math.round(nameBoundsX*scale));
        //int y = (int) (r.getY() + Math.round(nameBoundsAbove*scale));
        
        Font font = new Font(GUISettings.OBJECTNAMEFONTTYPE, Font.PLAIN, font_size);
        g.setFont(font);
        
        if(!nameWrapp)
            nameWrapp(g,scale, font, r);
        
        //g.drawString(name, x, y);  
    }
    
    /**
     * Wrappes the name, when it is too long to fit into the shape and
     * also moves it outside, when the shapes height is too small.
     * 
     * @param g Graphics2D
     * @param scale The scale of the whole 2d graphic.
     * @param font the font of the text.
     * @param r the bounds of the transformed shape.
     */
    private void nameWrapp(Graphics2D g, double scale, Font font, Rectangle r){
        
        FontRenderContext context = g.getFontRenderContext();
        
        Rectangle2D bounds_r = font.getStringBounds(name, context);
        
        double width = bounds_r.getWidth();
        double height = bounds_r.getHeight();
        double xBounds = nameBoundsX*scale;
        
        if(height+(GUISettings.NAMEPOSITIONINYADD*scale) > r.height){
            nameBoundsX = GUISettings.NAMEPOSITIONOUTX;
            nameBoundsAbove = GUISettings.NAMEPOSITIONOUTY;
        }
        
        double max_width = r.width-2*xBounds;
        
        if(width > max_width){
            double percent = max_width/width*100;
            int cut = (int) Math.round(name.length()*percent/100)-3;
            if(cut <= 1)
                cut = 3;
            
            if(cut < name.length())
                name = name.substring(0,cut)+"...";
        }
        nameWrapp = true;
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
    public void setName(String name) {
        nameWrapp = false;
        this.name = name;
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
    
    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setRotation(double rotation) {
        this.rotation = rotation;
    }

    @Override
    public double getRotation() {
        return rotation;
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
        return scale;
    }

    @Override
    public void setScale(double scale) {
        this.scale = scale;
    }

    @Override
    public boolean isNameAbbreviated() {
        // TODO Auto-generated method stub
        return false;
    }

}
