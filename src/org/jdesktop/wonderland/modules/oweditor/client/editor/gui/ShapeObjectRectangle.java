package org.jdesktop.wonderland.modules.oweditor.client.editor.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

import javax.swing.JLabel;

public class ShapeObjectRectangle extends ShapeObject{
    
    private Rectangle originalShape = null;
    private Shape transformedShape = null;
    private int id = -1;
    private boolean isSelected = false;
    private Paint color = GUISettings.objectColor;
    private Paint nameColor = GUISettings.objectNameColor;
    private String name = "";
    private boolean nameWrapp = false;
    
    //These variables are used to determine, where the name of the object should be.
    private int nameBoundsX = 5;
    private int nameBoundsAbove = 20;
    
    public ShapeObjectRectangle(int x, int y, int width, int height, int id, String name){
        
        originalShape = new Rectangle (x, y, width, height);
        this.name = name;
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
    public void paintOriginal(Graphics2D g, AffineTransform at, double scale) {

        g.setPaint(color);  
        
        transformedShape = at.createTransformedShape(originalShape);
        
        g.fill(at.createTransformedShape(originalShape));
        if(isSelected)
            g.setPaint(GUISettings.selectionBorderColor);
        
        g.draw(at.createTransformedShape(originalShape)); 
        
                
        g.setPaint(nameColor);  
        if(isSelected)
            g.setPaint(GUISettings.selectionBorderColor);

        int screenRes = Toolkit.getDefaultToolkit().getScreenResolution();
        int font_size = (int)Math.round(GUISettings.objectNameSize*scale * screenRes / 72.0);


        Font font = new Font(GUISettings.objectNameTextType, Font.PLAIN, font_size);
        g.setFont(font);
        

        Rectangle r = transformedShape.getBounds();
        int x = (int) (r.getX() + Math.round(nameBoundsX*scale));
        int y = (int) (r.getY() + Math.round(nameBoundsAbove*scale));
        
        if(!nameWrapp)
            nameWrapp(g,at,scale, font, r);
        
        
        g.drawString(name, x, y);  
        
    }
    
    
    private void nameWrapp(Graphics2D g, AffineTransform at, double scale, Font font, Rectangle r){
        
        FontRenderContext context = g.getFontRenderContext();
        
        
        double width = font.getStringBounds(name, context).getWidth();
        double xBounds = nameBoundsX/scale;
        
        double max_width = r.width-2*xBounds;
        if(width > max_width){
            double percent = max_width/width*100;
            int cut = (int) Math.round(name.length()*percent/100)-3;
            if(cut <= 1)
                cut = 3;
            
            name = name.substring(0,cut)+"...";
        }
        
        nameWrapp = true;
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



    @Override
    public String getName() {
        return name;
    }

}
